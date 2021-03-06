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

package org.metawidget.inspector.faces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates the field should use the given Faces converter in the UI.
 * <p>
 * This annotation uses the converter <em>id</em>, not its class. Alternatively, this annotation can
 * be an EL expression that evaluates to a <code>javax.faces.convert.Converter</code> instance.
 * 
 * @author Richard Kennard
 */

@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.FIELD, ElementType.METHOD } )
public @interface UiFacesConverter {

	/**
	 * The id of the converter, must match that defined in <code>faces-config.xml</code>'s
	 * <code>&lt;converter-id&gt;</code>. This can also be an expression of the form
	 * <code>#{...}</code> that evaluates to a <code>javax.faces.convert.Converter</code> instance.
	 */

	String value();
}
