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

package org.metawidget.integrationtest.struts.allwidgets.plugin;

import java.util.Date;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.metawidget.integrationtest.shared.allwidgets.model.AllWidgets.NestedWidgets;
import org.metawidget.integrationtest.struts.allwidgets.converter.NestedWidgetsConverter;

/**
 * @author Richard Kennard
 */

public class AllWidgetsPlugIn
	implements PlugIn {

	//
	// Public methods
	//

	@Override
	public void init( ActionServlet servlet, ModuleConfig config ) {

		DateConverter converterDate = new DateConverter();
		converterDate.setPattern( "E MMM dd HH:mm:ss z yyyy" );
		ConvertUtils.register( converterDate, Date.class );

		ConvertUtils.register( new NestedWidgetsConverter(), NestedWidgets.class );
	}

	@Override
	public void destroy() {

		// Do nothing
	}
}