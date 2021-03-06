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

package org.metawidget.integrationtest.faces.allwidgets.converter;

import java.util.Date;

import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

/**
 * Converts Dates to a String representation.
 *
 * @author Richard Kennard
 */

@FacesConverter( forClass = Date.class )
public class DateConverter
	extends DateTimeConverter {

	//
	// Constructor
	//

	public DateConverter() {

		setPattern( "E MMM dd HH:mm:ss z yyyy" );
	}
}
