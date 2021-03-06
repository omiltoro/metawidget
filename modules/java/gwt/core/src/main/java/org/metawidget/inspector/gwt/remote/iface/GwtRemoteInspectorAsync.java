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

package org.metawidget.inspector.gwt.remote.iface;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * GWT asynchronous AJAX interface to <code>GwtRemoteInspectorImpl</code> servlet.
 * <p>
 * Note: this interface is purely for the AJAX call. It is not related to
 * <code>GwtInspectorAsync</code>.
 * <p>
 * <strong>This interface is designed to work 'out of the box' for most cases. However, use of
 * Serializable as a parameter type is not optimal for GWT. We recommend deriving your own interface
 * with your own business-model-specific base class instead.</strong>
 *
 * @author Richard Kennard
 */

public interface GwtRemoteInspectorAsync {

	//
	// Methods
	//

	void inspect( Serializable toInspect, String type, String[] names, AsyncCallback<String> callback );
}
