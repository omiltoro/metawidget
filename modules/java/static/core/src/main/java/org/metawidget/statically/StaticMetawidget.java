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

package org.metawidget.statically;

import static org.metawidget.inspector.InspectionResultConstants.*;

import java.io.Writer;
import java.util.List;
import java.util.Map;

import org.metawidget.config.iface.ConfigReader;
import org.metawidget.iface.MetawidgetException;
import org.metawidget.inspectionresultprocessor.iface.InspectionResultProcessor;
import org.metawidget.inspector.iface.Inspector;
import org.metawidget.layout.iface.Layout;
import org.metawidget.pipeline.w3c.W3CPipeline;
import org.metawidget.statically.StaticUtils.IndentedWriter;
import org.metawidget.util.simple.PathUtils;
import org.metawidget.util.simple.PathUtils.TypeAndNames;
import org.metawidget.util.simple.StringUtils;
import org.metawidget.widgetbuilder.iface.WidgetBuilder;
import org.metawidget.widgetprocessor.iface.WidgetProcessor;
import org.w3c.dom.Element;

/**
 * Base Metawidget for static code generation.
 * <p>
 * Although Metawidget focuses on <em>runtime</em> UI generation, there are a category of
 * applications that require static code generation instead. It is still possible to make full use
 * of Metawidget's 5 stage pipeline (inspectors, inspection result processors, widget builders,
 * widget processors, layouts) during static code generation.
 * <p>
 * An important requirement for static code generation is the Metawidget should not rely on the
 * technology being generated being available. For example, a static JSF Metawidget should not rely
 * on <code>FacesContext.getCurrentInstance</code>. Architectually, this makes all static
 * Metawidgets more similar to each other than to their corresponding runtime version. For example,
 * the static JSF Metawidget is more similar to the static Spring Metawidget than it is to the
 * runtime JSF Metawidget. Therefore, it makes sense to have a <code>StaticMetawidget</code> base
 * class.
 *
 * @author Richard Kennard
 */

public abstract class StaticMetawidget
	extends BaseStaticWidget {

	//
	// Private members
	//

	/**
	 * Path to inspect.
	 */

	private String		mPath;

	private Pipeline	mPipeline;

	//
	// Constructor
	//

	public StaticMetawidget() {

		mPipeline = newPipeline();
	}

	//
	// Public methods
	//

	public void setPath( String path ) {

		mPath = path;
	}

	public String getPath() {

		return mPath;
	}

	public ConfigReader getConfigReader() {

		return mPipeline.getConfigReader();
	}

	public void setConfigReader( ConfigReader configReader ) {

		mPipeline.setConfigReader( configReader );
	}

	public String getConfig() {

		return (String) mPipeline.getConfig();
	}

	public void setConfig( String config ) {

		mPipeline.setConfig( config );
	}

	public void setMaximumInspectionDepth( int maximumInspectionDepth ) {

		mPipeline.setMaximumInspectionDepth( maximumInspectionDepth );
	}

	public String getLabelString( Map<String, String> attributes ) {

		if ( attributes == null ) {
			return "";
		}

		// Explicit label

		String label = attributes.get( LABEL );

		if ( label != null ) {
			// (may be forced blank)

			if ( "".equals( label ) ) {
				return null;
			}

			return label.trim();
		}

		// Default name

		String name = attributes.get( NAME );

		if ( name != null ) {
			return StringUtils.uncamelCase( name );
		}

		return "";
	}

	public boolean isReadOnly() {

		return mPipeline.isReadOnly();
	}

	public void setReadOnly( boolean readOnly ) {

		mPipeline.setReadOnly( readOnly );
	}

	public Inspector getInspector() {

		return mPipeline.getInspector();
	}

	public void setInspector( Inspector inspector ) {

		mPipeline.setInspector( inspector );
	}

	/**
	 * Useful for WidgetBuilders to perform nested inspections (eg. for Collections).
	 */

	public String inspect( Object toInspect, String type, String... names ) {

		return mPipeline.inspect( toInspect, type, names );
	}

	/**
	 * Useful for WidgetBuilders to setup nested Metawidgets (eg. for wrapping them in a
	 * panelGroup).
	 */

	public void initNestedMetawidget( StaticMetawidget nestedMetawidget, Map<String, String> attributes ) {

		// Don't reconfigure...

		nestedMetawidget.setConfig( null );

		// ...instead, copy runtime values

		mPipeline.initNestedPipeline( nestedMetawidget.mPipeline, attributes );

		if ( nestedMetawidget.getPath() == null ) {
			nestedMetawidget.setPath( mPath + StringUtils.SEPARATOR_FORWARD_SLASH_CHAR + attributes.get( NAME ) );
		}
	}

	@SuppressWarnings( { "unchecked" } )
	public void setInspectionResultProcessors( InspectionResultProcessor<? extends StaticMetawidget>... inspectionResultProcessors ) {

		mPipeline.setInspectionResultProcessors( (InspectionResultProcessor[]) inspectionResultProcessors );
	}

	public WidgetBuilder<StaticWidget, StaticMetawidget> getWidgetBuilder() {

		return mPipeline.getWidgetBuilder();
	}

	@SuppressWarnings( { "unchecked", "rawtypes" } )
	public <W extends StaticWidget, M extends W> void setWidgetBuilder( WidgetBuilder<W, M> widgetBuilder ) {

		mPipeline.setWidgetBuilder( (WidgetBuilder) widgetBuilder );
	}

	@SuppressWarnings( { "unchecked", "rawtypes" } )
	public <W extends StaticWidget, M extends W> void addWidgetProcessor( WidgetProcessor<W, M> widgetProcessor ) {

		mPipeline.addWidgetProcessor( (WidgetProcessor) widgetProcessor );
	}

	public List<WidgetProcessor<StaticWidget, StaticMetawidget>> getWidgetProcessors() {

		return mPipeline.getWidgetProcessors();
	}

	@SuppressWarnings( { "unchecked" } )
	public <W extends StaticWidget, M extends W> void setWidgetProcessors( WidgetProcessor<W, M>... widgetProcessors ) {

		mPipeline.setWidgetProcessors( (WidgetProcessor[]) widgetProcessors );
	}

	public <T> T getWidgetProcessor( Class<T> widgetProcessorClass ) {

		return mPipeline.getWidgetProcessor( widgetProcessorClass );
	}

	@SuppressWarnings( { "unchecked", "rawtypes" } )
	public <W extends StaticWidget, M extends W> void removeWidgetProcessor( WidgetProcessor<W, M> widgetProcessor ) {

		mPipeline.removeWidgetProcessor( (WidgetProcessor) widgetProcessor );
	}

	/**
	 * Useful for WidgetBuilders.
	 */

	public Layout<StaticWidget, StaticWidget, StaticMetawidget> getLayout() {

		return mPipeline.getLayout();
	}

	@SuppressWarnings( { "unchecked", "rawtypes" } )
	public <W extends StaticWidget, C extends W, M extends C> void setLayout( Layout<W, C, M> layout ) {

		mPipeline.setLayout( (Layout) layout );
	}

	/**
	 * Clients should override the version that takes <tt>initialIndent</tt> instead.
	 */

	@Override
	public final void write( Writer writer ) {

		write( writer, -1 );
	}

	/**
	 * Write the Metawidget output using the given Writer.
	 *
	 * @param initialIndent
	 *            the initialIndent that will be applied to every line. 0 for no initial indent. -1
	 *            for no subsequent indenting either
	 */

	public void write( Writer writer, int initialIndent ) {

		try {
			mPipeline.buildWidgets( inspect() );

			Writer writerToUse = writer;

			if ( initialIndent >= 0 ) {
				writerToUse = new IndentedWriter( writer, initialIndent );
			}

			super.write( writerToUse );
		} catch ( Exception e ) {
			throw MetawidgetException.newException( e );
		}
	}

	//
	// Protected methods
	//

	/**
	 * Instantiate the Pipeline used by this Metawidget.
	 * <p>
	 * Subclasses wishing to use their own Pipeline should override this method to instantiate their
	 * version.
	 */

	protected Pipeline newPipeline() {

		return new Pipeline();
	}

	protected void startBuild() {

		getChildren().clear();
	}

	/**
	 * @param element
	 *            the parent element that will be iterated over
	 */

	protected void beforeBuildCompoundWidget( Element element ) {

		// Do nothing by default
	}

	protected Element inspect() {

		if ( mPath == null ) {
			return null;
		}

		TypeAndNames typeAndNames = PathUtils.parsePath( mPath, StringUtils.SEPARATOR_FORWARD_SLASH_CHAR );
		String type = typeAndNames.getType();

		return mPipeline.inspectAsDom( null, type, typeAndNames.getNamesAsArray() );
	}

	protected abstract String getDefaultConfiguration();

	//
	// Inner class
	//

	/**
	 * Internal Metawidget pipeline.
	 * <p>
	 * We must use &lt;StaticWidget, StaticWidget, StaticMetawidget&gt; because that is what
	 * <tt>org.metawidget.statically.layout.SimpleLayout</tt> uses. Therefore we must expose
	 * <tt>setLayout(&lt;StaticWidget, StaticWidget, StaticMetawidget&gt;)</tt>.
	 * <p>
	 * We <em>also</em> need subclasses such as
	 * <tt>setLayout(&lt;StaticXmlWidget, StaticXmlWidget, StaticXmlMetawidget&gt;)</tt>. So we must
	 * allow extensions of <tt>StaticWidget</tt> (eg. <tt>W extends StaticWidget</tt>) during
	 * <tt>setLayout</tt>.
	 * <p>
	 * However this causes problems because if <tt>W extends StaticWidget</tt> then we cannot
	 * guarantee that <tt>M extends W</tt> and <em>also</em> extends <tt>StaticMetawidget</tt>. Java
	 * Generics cannot express this, even with Bounded Type Parameters. Hence some
	 * <tt>SuppressWarnings</tt> above.
	 */

	protected class Pipeline
		extends W3CPipeline<StaticWidget, StaticWidget, StaticMetawidget> {

		//
		// Protected methods
		//

		@Override
		protected String getDefaultConfiguration() {

			return StaticMetawidget.this.getDefaultConfiguration();
		}

		@Override
		protected Map<String, String> getAdditionalAttributes( StaticWidget tag ) {

			return null;
		}

		@Override
		protected void startBuild() {

			StaticMetawidget.this.startBuild();
			super.startBuild();
		}

		@Override
		protected void buildCompoundWidget( Element element )
			throws Exception {

			StaticMetawidget.this.beforeBuildCompoundWidget( element );
			super.buildCompoundWidget( element );
		}

		@Override
		protected StaticMetawidget buildNestedMetawidget( final Map<String, String> attributes )
			throws Exception {

			StaticMetawidget metawidgetTag = StaticMetawidget.this.getClass().newInstance();
			StaticMetawidget.this.initNestedMetawidget( metawidgetTag, attributes );

			return metawidgetTag;
		}

		@Override
		protected StaticMetawidget getPipelineOwner() {

			return StaticMetawidget.this;
		}
	}
}
