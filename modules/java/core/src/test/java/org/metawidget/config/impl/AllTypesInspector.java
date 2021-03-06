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

package org.metawidget.config.impl;

import java.io.InputStream;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Pattern;

import org.metawidget.config.impl.AllTypesInspectorConfig.FooEnum;
import org.metawidget.inspector.iface.Inspector;

/**
 * @author Richard Kennard
 */

public class AllTypesInspector
	implements Inspector {

	//
	// Private members
	//

	private List<Object>	mList;

	private Set<Object>		mSet;

	private int				mInt;

	private int				mConstant;

	private int				mExternalConstant;

	private boolean			mBoolean;

	private Pattern			mPattern;

	private InputStream		mInputStream;

	private ResourceBundle	mResourceBundle;

	private String[]		mStringArray;

	private FooEnum			mEnum;

	//
	// Constructor
	//

	public AllTypesInspector( AllTypesInspectorConfig config ) {

		if ( config.isFailDuringConstruction() ) {
			throw new RuntimeException( "Failed during construction" );
		}

		mList = config.getList();
		mSet = config.getSet();
		mInt = config.getInt();
		mConstant = config.getConstant();
		mExternalConstant = config.getExternalConstant();
		mBoolean = config.isBoolean();
		mPattern = config.getPattern();
		mInputStream = config.getInputStream();
		mResourceBundle = config.getResourceBundle();
		mStringArray = config.getStringArray();
		mEnum = config.getEnum();
	}

	/**
	 * @param config
	 */

	public AllTypesInspector( NoEqualsInspectorConfig config ) {

		// Do nothing
	}

	/**
	 * @param config
	 */

	public AllTypesInspector( NoHashCodeInspectorConfig config ) {

		// Do nothing
	}

	/**
	 * @param config
	 */

	public AllTypesInspector( UnbalancedEqualsInspectorConfig config ) {

		// Do nothing
	}

	/**
	 * @param config
	 */

	public AllTypesInspector( NoEqualsSubclassInspectorConfig config ) {

		// Do nothing
	}

	/**
	 * @param config
	 */

	public AllTypesInspector( NoEqualsHasMethodsSubclassInspectorConfig config ) {

		// Do nothing
	}

	/**
	 * @param config
	 */

	public AllTypesInspector( DumbHashCodeInspectorConfig config ) {

		// Do nothing
	}

	//
	// Public methods
	//

	public String inspect( Object toInspect, String type, String... names ) {

		return null;
	}

	public List<Object> getList() {

		return mList;
	}

	public Set<Object> getSet() {

		return mSet;
	}

	public int getInt() {

		return mInt;
	}

	public int getConstant() {

		return mConstant;
	}

	public int getExternalConstant() {

		return mExternalConstant;
	}

	public boolean isBoolean() {

		return mBoolean;
	}

	public Pattern getPattern() {

		return mPattern;
	}

	public InputStream getInputStream() {

		return mInputStream;
	}

	public ResourceBundle getResourceBundle() {

		return mResourceBundle;
	}

	public String[] getStringArray() {

		return mStringArray;
	}

	public FooEnum getEnum() {

		return mEnum;
	}
}
