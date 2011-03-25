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

package org.metawidget.config;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Pattern;

import org.metawidget.util.simple.ObjectUtils;

/**
 * @author Richard Kennard
 */

public class TestInspectorConfig {

	//
	// Public statics
	//

	public static final int	CONSTANT_VALUE	= 42;

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

	private boolean			mFailDuringConstruction;

	//
	// Public methods
	//

	public void setList( List<Object> list ) {

		mList = list;
	}

	public void setSet( Set<Object> set ) {

		mSet = set;
	}

	public void setInt( int anInt ) {

		mInt = anInt;
	}

	public void setConstant( int constant ) {

		mConstant = constant;
	}

	public void setExternalConstant( int externalConstant ) {

		mExternalConstant = externalConstant;
	}

	public void setBoolean( boolean aBoolean ) {

		mBoolean = aBoolean;
	}

	public void setPattern( Pattern pattern ) {

		mPattern = pattern;
	}

	public void setInputStream( InputStream inputStream ) {

		mInputStream = inputStream;
	}

	public void setResourceBundle( ResourceBundle resourceBundle ) {

		mResourceBundle = resourceBundle;
	}

	public void setStringArray( String[] stringArray ) {

		mStringArray = stringArray;
	}

	public void setEnum( FooEnum anEnum ) {

		mEnum = anEnum;
	}

	/**
	 * @param failDuringConstruction
	 */

	public void setFailDuringConstruction( boolean failDuringConstruction ) {

		mFailDuringConstruction = true;
	}

	/**
	 * @param date
	 */

	public void setDate( Date date ) {

		// Test unsupported types
	}

	public void setNoParameters() {

		throw new UnsupportedOperationException( "Called setNoParameters" );
	}

	@Override
	public boolean equals( Object that ) {

		if ( this == that ) {
			return true;
		}

		if ( that == null ) {
			return false;
		}

		if ( getClass() != that.getClass() ) {
			return false;
		}

		if ( !ObjectUtils.nullSafeEquals( mList, ( (TestInspectorConfig) that ).mList ) ) {
			return false;
		}

		if ( !ObjectUtils.nullSafeEquals( mSet, ( (TestInspectorConfig) that ).mSet ) ) {
			return false;
		}

		if ( !ObjectUtils.nullSafeEquals( mInt, ( (TestInspectorConfig) that ).mInt ) ) {
			return false;
		}

		if ( !ObjectUtils.nullSafeEquals( mBoolean, ( (TestInspectorConfig) that ).mBoolean ) ) {
			return false;
		}

		if ( !ObjectUtils.nullSafeEquals( mPattern, ( (TestInspectorConfig) that ).mPattern ) ) {
			return false;
		}

		if ( !ObjectUtils.nullSafeEquals( mInputStream, ( (TestInspectorConfig) that ).mInputStream ) ) {
			return false;
		}

		if ( !ObjectUtils.nullSafeEquals( mResourceBundle, ( (TestInspectorConfig) that ).mResourceBundle ) ) {
			return false;
		}

		if ( !ObjectUtils.nullSafeEquals( mStringArray, ( (TestInspectorConfig) that ).mStringArray ) ) {
			return false;
		}

		if ( !ObjectUtils.nullSafeEquals( mFailDuringConstruction, ( (TestInspectorConfig) that ).mFailDuringConstruction ) ) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {

		int hashCode = 1;
		hashCode = 31 * hashCode + ObjectUtils.nullSafeHashCode( mList );
		hashCode = 31 * hashCode + ObjectUtils.nullSafeHashCode( mSet );
		hashCode = 31 * hashCode + ObjectUtils.nullSafeHashCode( mInt );
		hashCode = 31 * hashCode + ObjectUtils.nullSafeHashCode( mBoolean );
		hashCode = 31 * hashCode + ObjectUtils.nullSafeHashCode( mPattern );
		hashCode = 31 * hashCode + ObjectUtils.nullSafeHashCode( mInputStream );
		hashCode = 31 * hashCode + ObjectUtils.nullSafeHashCode( mResourceBundle );
		hashCode = 31 * hashCode + ObjectUtils.nullSafeHashCode( mStringArray );
		hashCode = 31 * hashCode + ObjectUtils.nullSafeHashCode( mFailDuringConstruction );

		return hashCode;
	}

	//
	// Protected methods
	//

	protected List<Object> getList() {

		return mList;
	}

	protected Set<Object> getSet() {

		return mSet;
	}

	protected int getConstant() {

		return mConstant;
	}

	protected int getExternalConstant() {

		return mExternalConstant;
	}

	protected boolean isBoolean() {

		return mBoolean;
	}

	protected int getInt() {

		return mInt;
	}

	protected Pattern getPattern() {

		return mPattern;
	}

	protected InputStream getInputStream() {

		return mInputStream;
	}

	protected ResourceBundle getResourceBundle() {

		return mResourceBundle;
	}

	protected String[] getStringArray() {

		return mStringArray;
	}

	protected FooEnum getEnum() {

		return mEnum;
	}

	protected boolean isFailDuringConstruction() {

		return mFailDuringConstruction;
	}

	//
	// Inner class
	//

	static enum FooEnum {

		FOO, BAR, BAZ
	}
}