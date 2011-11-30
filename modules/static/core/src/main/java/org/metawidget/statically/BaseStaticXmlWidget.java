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

package org.metawidget.statically;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.metawidget.statically.StaticUtils.IndentedWriter;
import org.metawidget.statically.StaticUtils.LeadingSpaceWriter;
import org.metawidget.util.CollectionUtils;

/**
 * @author Richard Kennard
 */

public abstract class BaseStaticXmlWidget
	extends BaseStaticWidget
	implements StaticXmlWidget {

	//
	// Private members
	//

	private String				mPrefix;

	private String				mTagName;

	private String				mNamespaceURI;

	private Map<String, String>	mAttributes;

	private String				mTextContent;

	//
	// Constructor
	//

	/**
	 * @param prefix
	 *            the namespace prefix of this tag, or <tt>null</tt> if it is unspecified
	 * @param tagName
	 *            the name of this tag
	 * @param namespaceURI
	 *            the namespace URI for this tag, or <tt>null</tt> if it is unspecified
	 */

	protected BaseStaticXmlWidget( String prefix, String tagName, String namespaceURI ) {

		mPrefix = prefix;
		mTagName = tagName;
		mNamespaceURI = namespaceURI;
	}

	//
	// Public methods
	//

	/**
	 * Gets the namespace prefix of this tag, or <tt>null</tt> if it is unspecified
	 */

	public String getPrefix() {

		return mPrefix;
	}

	/**
	 * Gets the namespace URI for this tag, or <tt>null</tt> if it is unspecified.
	 */

	public String getNamespaceURI() {

		return mNamespaceURI;
	}

	public String getAttribute( String name ) {

		if ( mAttributes == null ) {
			return null;
		}

		return mAttributes.get( name );
	}

	public void putAttribute( String name, String value ) {

		if ( mAttributes == null ) {

			// (use TreeMap for consistent unit tests)

			mAttributes = CollectionUtils.newTreeMap();
		}

		mAttributes.put( name, value );
	}

	public String getTextContent() {

		return mTextContent;
	}

	public void setTextContent( String textContent ) {

		mTextContent = textContent;
	}

	@Override
	public void write( Writer writer )
		throws IOException {

		if ( getChildren().isEmpty() ) {
			writeStartTag( writer );

			// Non-indented text content

			if ( getTextContent() != null ) {
				writer.append( ">" );
				writer.append( mTextContent );
				if ( writer instanceof IndentedWriter ) {
					( (IndentedWriter) writer ).indent();
				}
				writeEndTag( writer );
			} else {
				writer.append( "/>" );
				if ( writer instanceof IndentedWriter ) {
					writer.append( "\r\n" );
				}
			}
		} else {
			writeStartTag( writer );
			writer.append( ">" );
			if ( writer instanceof IndentedWriter ) {
				writer.append( "\r\n" );
				( (IndentedWriter) writer ).indent();
			}

			super.write( writer );

			// Indented text content

			if ( getTextContent() != null ) {
				writer.append( mTextContent );
				if ( writer instanceof IndentedWriter ) {
					writer.append( "\r\n" );
				}
			}

			writeEndTag( writer );
		}
	}

	//
	// Protected methods
	//

	/**
	 * Writes the attribute with the given name and value to the given writer.
	 * <p>
	 * By default, does not write attributes whose value is null or empty. Subclasses can override
	 * this method to suppress writing attributes for other reasons.
	 */

	protected void writeAttribute( Writer writer, String name, String value )
		throws IOException {

		if ( value == null || value.length() == 0 ) {
			return;
		}

		writer.append( name );
		writer.append( "=\"" );
		writer.append( value );
		writer.append( "\"" );
	}

	//
	// Private methods
	//

	private void writeStartTag( Writer writer )
		throws IOException {

		writer.append( '<' );

		if ( mPrefix != null ) {
			writer.append( mPrefix );
			writer.append( ':' );
		}

		writer.append( mTagName );

		if ( mAttributes != null ) {

			for ( Map.Entry<String, String> entry : mAttributes.entrySet() ) {

				writeAttribute( new LeadingSpaceWriter( writer ), entry.getKey(), entry.getValue() );
			}
		}
	}

	private void writeEndTag( Writer writer )
		throws IOException {

		if ( writer instanceof IndentedWriter ) {
			( (IndentedWriter) writer ).outdent();
		}
		writer.append( "</" );

		if ( mPrefix != null ) {
			writer.append( mPrefix );
			writer.append( ':' );
		}

		writer.append( mTagName );
		writer.append( ">" );

		if ( writer instanceof IndentedWriter ) {
			writer.append( "\r\n" );
		}
	}
}
