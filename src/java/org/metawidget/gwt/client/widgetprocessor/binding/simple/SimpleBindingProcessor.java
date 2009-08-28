// Metawidget
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package org.metawidget.gwt.client.widgetprocessor.binding.simple;

import static org.metawidget.inspector.InspectionResultConstants.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.metawidget.gwt.client.ui.GwtMetawidget;
import org.metawidget.gwt.client.ui.Stub;
import org.metawidget.util.simple.PathUtils;
import org.metawidget.widgetprocessor.impl.BaseWidgetProcessor;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Widget;

/**
 * Simple, Generator-based property and action binding processor.
 *
 * @author Richard Kennard
 */

public class SimpleBindingProcessor
	extends BaseWidgetProcessor<Widget, GwtMetawidget>
{
	//
	// Private statics
	//

	private final static Map<Class<?>, SimpleBindingProcessorAdapter<?>>	ADAPTERS	= new HashMap<Class<?>, SimpleBindingProcessorAdapter<?>>();

	private final static Map<Class<?>, Converter<?>>						CONVERTERS	= new HashMap<Class<?>, Converter<?>>();

	static
	{
		// Register default converters

		Converter<?> simpleConverter = new SimpleConverter();

		@SuppressWarnings( "unchecked" )
		Converter<Boolean> booleanConverter = (Converter<Boolean>) simpleConverter;
		registerConverter( Boolean.class, booleanConverter );

		@SuppressWarnings( "unchecked" )
		Converter<Character> characterConverter = (Converter<Character>) simpleConverter;
		registerConverter( Character.class, characterConverter );

		@SuppressWarnings( "unchecked" )
		Converter<Number> numberConverter = (Converter<Number>) simpleConverter;
		registerConverter( Number.class, numberConverter );
	}

	//
	// Public statics
	//

	/**
	 * Registers the given SimpleBindingAdapter for the given Class.
	 * <p>
	 * Adapters also apply to subclasses of the given Class. So for example registering an Adapter
	 * for <code>Contact.class</code> will match <code>PersonalContact.class</code>,
	 * <code>BusinessContact.class</code> etc., unless a more subclass-specific Adapter is also
	 * registered
	 */

	public static <T> void registerAdapter( Class<T> forClass, SimpleBindingProcessorAdapter<T> adapter )
	{
		ADAPTERS.put( forClass, adapter );
	}

	/**
	 * Registers the given Converter for the given Class.
	 * <p>
	 * Converters also apply to subclasses of the given Class. So for example registering a
	 * Converter for <code>Number.class</code> will match <code>Integer.class</code>,
	 * <code>Double.class</code> etc., unless a more subclass-specific Converter is also registered.
	 */

	public static <T> void registerConverter( Class<T> forClass, Converter<T> converter )
	{
		CONVERTERS.put( forClass, converter );
	}

	//
	// Private members
	//

	private Set<Object[]>	mBindings;

	//
	// Public methods
	//

	@Override
	public void onAdd( Widget widget, Map<String, String> attributes, final GwtMetawidget metawidget )
	{
		// SimpleBinding doesn't bind to Stubs or FlexTables

		if ( widget instanceof Stub || widget instanceof FlexTable )
			return;

		final String[] names = PathUtils.parsePath( metawidget.getPath() ).getNamesAsArray();

		// Bind actions to FocusWidgets

		if ( widget instanceof FocusWidget )
		{
			// Bind the action

			FocusWidget focusWidget = (FocusWidget) widget;
			focusWidget.addClickHandler( new ClickHandler()
			{
				public void onClick( ClickEvent event )
				{
					// Use the adapter...

					Object toInspect = metawidget.getToInspect();

					if ( toInspect == null )
						return;

					Class<?> classToBindTo = toInspect.getClass();
					SimpleBindingProcessorAdapter<Object> adapter = getAdapter( classToBindTo );

					if ( adapter == null )
						throw new RuntimeException( "Don't know how to bind to a " + classToBindTo );

					// ...to invoke the action

					adapter.invokeAction( toInspect, names );
				}
			} );

			return;
		}

		// From the adapter...

		Object toInspect = metawidget.getToInspect();

		if ( toInspect == null )
			return;

		Class<?> classToBindTo = toInspect.getClass();
		SimpleBindingProcessorAdapter<Object> adapter = getAdapter( classToBindTo );

		if ( adapter == null )
			throw new RuntimeException( "Don't know how to bind to a " + classToBindTo );

		// ...fetch the value...

		Object value = adapter.getProperty( toInspect, names );

		// ...convert it (if necessary)...

		Class<?> propertyType = adapter.getPropertyType( toInspect, names );
		Converter<Object> converter = getConverter( propertyType );

		if ( converter != null )
			value = converter.convertForWidget( widget, value );

		// ...and set it

		try
		{
			metawidget.setValue( value, widget );

			if ( TRUE.equals( attributes.get( NO_SETTER ) ) )
				return;

			if ( mBindings == null )
				mBindings = new HashSet<Object[]>();

			mBindings.add( new Object[] { widget, names, converter, propertyType } );
		}
		catch ( Exception e )
		{
			Window.alert( path + ": " + e.getMessage() );
		}
	}

	public void rebind( GwtMetawidget metawidget )
	{
		if ( mBindings == null )
			return;

		Object toInspect = metawidget.getToInspect();

		if ( toInspect == null )
			return;

		// From the adapter...

		Class<?> classToBindTo = toInspect.getClass();
		SimpleBindingProcessorAdapter<Object> adapter = getAdapter( classToBindTo );

		if ( adapter == null )
			throw new RuntimeException( "Don't know how to rebind to a " + classToBindTo );

		// ...for each bound property...

		for ( Object[] binding : mBindings )
		{
			Widget widget = (Widget) binding[0];
			String[] names = (String[]) binding[1];
			@SuppressWarnings( "unchecked" )
			Converter<Object> converter = (Converter<Object>) binding[2];

			// ...fetch the value...

			Object value = adapter.getProperty( toInspect, names );

			// ...convert it (if necessary)...

			if ( converter != null )
				value = converter.convertForWidget( widget, value );

			// ...and set it

			metawidget.setValue( value, widget );
		}
	}

	public void save( GwtMetawidget metawidget )
	{
		if ( mBindings == null )
			return;

		Object toInspect = metawidget.getToInspect();

		if ( toInspect == null )
			return;

		// From the adapter...

		Class<?> classToBindTo = toInspect.getClass();
		SimpleBindingProcessorAdapter<Object> adapter = getAdapter( classToBindTo );

		if ( adapter == null )
			throw new RuntimeException( "Don't know how to save to a " + classToBindTo );

		// ...for each bound property...

		for ( Object[] binding : mBindings )
		{
			Widget widget = (Widget) binding[0];
			String[] names = (String[]) binding[1];
			@SuppressWarnings( "unchecked" )
			Converter<Object> converter = (Converter<Object>) binding[2];
			Class<?> type = (Class<?>) binding[3];

			// ...fetch the value...

			Object value = metawidget.getValue( widget );

			// ...convert it (if necessary)...

			if ( value != null && converter != null )
				value = converter.convertFromWidget( widget, value, type );

			// ...and set it

			adapter.setProperty( toInspect, value, names );
		}
	}

	//
	// Protected methods
	//

	/**
	 * Gets the Adapter for the given class (if any).
	 * <p>
	 * Includes traversing superclasses of the given Class for a suitable Converter, so for example
	 * registering an Adapter for <code>Contact.class</code> will match
	 * <code>PersonalContact.class</code>, <code>BusinessContact.class</code> etc., unless a more
	 * subclass-specific Adapter is also registered.
	 */

	protected <T extends SimpleBindingProcessorAdapter<?>> T getAdapter( Class<?> classToBindTo )
	{
		Class<?> classTraversal = classToBindTo;

		while ( classTraversal != null )
		{
			@SuppressWarnings( "unchecked" )
			T adapter = (T) ADAPTERS.get( classTraversal );

			if ( adapter != null )
				return adapter;

			classTraversal = classTraversal.getSuperclass();
		}

		return null;
	}

	//
	// Private methods
	//

	/**
	 * Gets the Converter for the given Class (if any).
	 * <p>
	 * Includes traversing superclasses of the given Class for a suitable Converter, so for example
	 * registering a Converter for <code>Number.class</code> will match <code>Integer.class</code>,
	 * <code>Double.class</code> etc., unless a more subclass-specific Converter is also registered.
	 */

	private <T extends Converter<?>> T getConverter( Class<?> classToConvert )
	{
		Class<?> classTraversal = classToConvert;

		while ( classTraversal != null )
		{
			@SuppressWarnings( "unchecked" )
			T converter = (T) CONVERTERS.get( classTraversal );

			if ( converter != null )
				return converter;

			classTraversal = classTraversal.getSuperclass();
		}

		return null;
	}
}