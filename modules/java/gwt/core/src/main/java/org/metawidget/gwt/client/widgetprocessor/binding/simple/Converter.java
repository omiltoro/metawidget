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

package org.metawidget.gwt.client.widgetprocessor.binding.simple;

import com.google.gwt.user.client.ui.Widget;

/**
 * Interface for <code>SimpleBindingProcessor</code> Converters.
 *
 * @author Richard Kennard
 */

public interface Converter<T> {

	//
	// Methods
	//

	/**
	 * Convert the given value (as returned by the given Widget) into the given class
	 *
	 * @param intoClass
	 *            the class to convert into. Useful for handling subclasses (eg. see
	 *            NumberConverter)
	 */

	T convertFromWidget( Widget widget, Object value, Class<?> intoClass );

	/**
	 * Convert the given value to a form that can be displayed by the given Widget.
	 */

	Object convertForWidget( Widget widget, T value );
}
