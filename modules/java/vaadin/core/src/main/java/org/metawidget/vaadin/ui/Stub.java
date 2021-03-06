// Metawidget (licensed under LGPL)
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

package org.metawidget.vaadin.ui;

import java.util.Iterator;
import java.util.Map;

import org.metawidget.util.CollectionUtils;

import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;

/**
 * Stub for Vaadin environments.
 * <p>
 * Stubs are used to 'stub out' what Metawidget would normally create - either to suppress widget
 * creation entirely or to create child widgets with a different name. They differ from Facets in
 * that Facets are simply 'decorations' (such as button bars) to be recognized and arranged at the
 * discretion of the Layout.
 * <p>
 * We define separate Stub widgets, as opposed to simply a <code>VaadinMetawidget.addStub</code>
 * method, as this is more amenable to visual UI builders.
 *
 * @author Loghman Barari
 */

public class Stub
	extends Panel {

	//
	// Private members
	//

	private Map<String, String>	mAttributes;

	//
	// Constructors
	//

	public Stub() {

		super( new com.vaadin.ui.VerticalLayout() );

		addStyleName( "light" );
		((com.vaadin.ui.VerticalLayout) getContent()).setMargin( false );
	}

	/**
	 * Convenience constructor.
	 * <p>
	 * Useful for creating stubs that will otherwise be empty, such as
	 * <code>metawidget.add( new Stub( "foo" ))</code>
	 */

	public Stub( String data ) {

		this();
		setData( data );
	}

	//
	// Public methods
	//

	public void setAttribute( String name, String value ) {

		if ( mAttributes == null ) {
			mAttributes = CollectionUtils.newHashMap();
		}

		mAttributes.put( name, value );
	}

	public Map<String, String> getAttributes() {

		return mAttributes;
	}

	/**
	 * Reproduce old Vaadin 6 structure.
	 */

	public void addComponent( Component component ) {

		((com.vaadin.ui.VerticalLayout) getContent()).addComponent( component );
	}

	/**
	 * Reproduce old Vaadin 6 structure.
	 */

	public Iterator<Component> getComponentIterator() {

		return ((com.vaadin.ui.VerticalLayout) getContent()).iterator();
	}
}
