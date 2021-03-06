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

package org.metawidget.integrationtest.shared.allwidgets.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * Models an entity that tests all available widgets.
 * <p>
 * The fields all have default values to test data binding.
 * 
 * @author Richard Kennard
 */

public class AllWidgets
	implements Serializable {

	//
	// Private members
	//

	private String				mTextbox							= "Textbox";

	private String				mLimitedTextbox						= "Limited Textbox";

	private String				mTextarea							= "Textarea";

	private String				mPassword							= "Password";

	private byte				mBytePrimitive						= Byte.MAX_VALUE;

	private Byte				mByteObject							= Byte.MIN_VALUE;

	private short				mShortPrimitive						= Short.MAX_VALUE;

	private Short				mShortObject						= Short.MIN_VALUE;

	private int					mIntPrimitive						= Integer.MAX_VALUE;

	private Integer				mIntegerObject						= Integer.MIN_VALUE;

	private int					mRangedInt							= 32;

	private int					mRangedInteger						= 33;

	private long				mLongPrimitive						= 42;

	private Long				mLongObject							= Long.valueOf( 43 );

	private float				mFloatPrimitive						= 4.2f;

	private Float				mFloatObject						= Float.valueOf( 4.3f );

	private double				mDoublePrimitive					= 42.2d;

	private Double				mDoubleObject						= Double.valueOf( 43.3d );

	private char				mCharPrimitive						= 'A';

	private Character			mCharacterObject					= 'Z';

	/**
	 * Test boolean widgets.
	 * <p>
	 * An unforunate situation exists in HTML whereby an unchecked checkbox POSTs nothing back. This
	 * means the backing object must default to false.
	 */

	private boolean				mBooleanPrimitive					= false;

	private Boolean				mBooleanObject						= Boolean.TRUE;

	private String				mDropdown							= "dropdown1";

	private String				mDropdownWithLabels					= "dropdown2";

	private byte				mNotNullDropdown;

	private String				mNotNullObjectDropdown				= "dropdown3";

	private NestedWidgets		mNestedWidgets						= new NestedWidgets();

	private NestedWidgets		mReadOnlyNestedWidgets				= new NestedWidgets();

	private NestedWidgets		mNestedWidgetsDontExpand			= new NestedWidgets();

	private NestedWidgets		mReadOnlyNestedWidgetsDontExpand	= new NestedWidgets();

	/**
	 * Use a deprecated API to avoid a dependency on java.util.Calendar, which GWT doesn't currently
	 * support.
	 */

	@SuppressWarnings( "deprecation" )
	private Date				mDate								= new Date( 75, 3, 9, 1, 0, 0 );

	private String				mHidden								= "Hidden";

	private String				mReadOnly							= "Read Only";

	private Collection<String>	mCollection							= new ArrayList<String>( Arrays.asList( "element1", "element2" ) );

	//
	// Constructor
	//

	public AllWidgets() {

		// Test two levels of nesting

		mNestedWidgets.setFurtherNestedWidgets( new NestedWidgets() );
	}

	//
	// Public methods
	//

	public String getTextbox() {

		return mTextbox;
	}

	public void setTextbox( String textbox ) {

		mTextbox = textbox;
	}

	public String getLimitedTextbox() {

		return mLimitedTextbox;
	}

	public void setLimitedTextbox( String limitedTextbox ) {

		mLimitedTextbox = limitedTextbox;
	}

	public String getTextarea() {

		return mTextarea;
	}

	public void setTextarea( String textarea ) {

		mTextarea = textarea;
	}

	public String getPassword() {

		return mPassword;
	}

	public void setPassword( String password ) {

		mPassword = password;
	}

	public byte getBytePrimitive() {

		return mBytePrimitive;
	}

	public void setBytePrimitive( byte bytePrimitive ) {

		mBytePrimitive = bytePrimitive;
	}

	public Byte getByteObject() {

		return mByteObject;
	}

	public void setByteObject( Byte object ) {

		mByteObject = object;
	}

	public short getShortPrimitive() {

		return mShortPrimitive;
	}

	public void setShortPrimitive( short shortPrimitive ) {

		mShortPrimitive = shortPrimitive;
	}

	public Short getShortObject() {

		return mShortObject;
	}

	public void setShortObject( Short object ) {

		mShortObject = object;
	}

	public int getIntPrimitive() {

		return mIntPrimitive;
	}

	public void setIntPrimitive( int intPrimitive ) {

		mIntPrimitive = intPrimitive;
	}

	public Integer getIntegerObject() {

		return mIntegerObject;
	}

	public void setIntegerObject( Integer integerObject ) {

		mIntegerObject = integerObject;
	}

	public int getRangedInt() {

		return mRangedInt;
	}

	public void setRangedInt( int rangedInt ) {

		mRangedInt = rangedInt;
	}

	public Integer getRangedInteger() {

		return mRangedInteger;
	}

	public void setRangedInteger( Integer rangedInteger ) {

		mRangedInteger = rangedInteger;
	}

	public long getLongPrimitive() {

		return mLongPrimitive;
	}

	public void setLongPrimitive( long longPrimitive ) {

		mLongPrimitive = longPrimitive;
	}

	public Long getLongObject() {

		return mLongObject;
	}

	public void setLongObject( Long longObject ) {

		mLongObject = longObject;
	}

	public float getFloatPrimitive() {

		return mFloatPrimitive;
	}

	public void setFloatPrimitive( float floatPrimitive ) {

		mFloatPrimitive = floatPrimitive;
	}

	public Float getFloatObject() {

		return mFloatObject;
	}

	public void setFloatObject( Float object ) {

		mFloatObject = object;
	}

	public double getDoublePrimitive() {

		return mDoublePrimitive;
	}

	public void setDoublePrimitive( double doublePrimitive ) {

		mDoublePrimitive = doublePrimitive;
	}

	public Double getDoubleObject() {

		return mDoubleObject;
	}

	public void setDoubleObject( Double object ) {

		mDoubleObject = object;
	}

	public char getCharPrimitive() {

		return mCharPrimitive;
	}

	public void setCharPrimitive( char charPrimitive ) {

		mCharPrimitive = charPrimitive;
	}

	public Character getCharacterObject() {

		return mCharacterObject;
	}

	public void setCharacterObject( Character aCharacterObject ) {

		mCharacterObject = aCharacterObject;
	}

	public boolean isBooleanPrimitive() {

		return mBooleanPrimitive;
	}

	public void setBooleanPrimitive( boolean booleanPrimitive ) {

		mBooleanPrimitive = booleanPrimitive;
	}

	public Boolean getBooleanObject() {

		return mBooleanObject;
	}

	public void setBooleanObject( Boolean object ) {

		mBooleanObject = object;
	}

	public String getDropdown() {

		return mDropdown;
	}

	public void setDropdown( String dropdown ) {

		mDropdown = dropdown;
	}

	public String getDropdownWithLabels() {

		return mDropdownWithLabels;
	}

	public void setDropdownWithLabels( String dropdownWithLabels ) {

		mDropdownWithLabels = dropdownWithLabels;
	}

	public byte getNotNullDropdown() {

		return mNotNullDropdown;
	}

	public void setNotNullDropdown( byte notNullDropdown ) {

		mNotNullDropdown = notNullDropdown;
	}

	public String getNotNullObjectDropdown() {

		return mNotNullObjectDropdown;
	}

	public void setNotNullObjectDropdown( String notNullObjectDropdown ) {

		mNotNullObjectDropdown = notNullObjectDropdown;
	}

	public NestedWidgets getNestedWidgets() {

		return mNestedWidgets;
	}

	public void setNestedWidgets( NestedWidgets nestedWidgets ) {

		mNestedWidgets = nestedWidgets;
	}

	public NestedWidgets getReadOnlyNestedWidgets() {

		return mReadOnlyNestedWidgets;
	}

	public void setReadOnlyNestedWidgets( NestedWidgets readOnlyNestedWidgets ) {

		mReadOnlyNestedWidgets = readOnlyNestedWidgets;
	}

	public NestedWidgets getNestedWidgetsDontExpand() {

		return mNestedWidgetsDontExpand;
	}

	public void setNestedWidgetsDontExpand( NestedWidgets nestedWidgetsDontExpand ) {

		mNestedWidgetsDontExpand = nestedWidgetsDontExpand;
	}

	public NestedWidgets getReadOnlyNestedWidgetsDontExpand() {

		return mReadOnlyNestedWidgetsDontExpand;
	}

	public Date getDate() {

		return mDate;
	}

	public void setDate( Date date ) {

		mDate = date;
	}

	public String getHidden() {

		return mHidden;
	}

	public void setHidden( String hidden ) {

		mHidden = hidden;
	}

	public String getReadOnly() {

		return mReadOnly;
	}

	public void setReadOnly( String readOnly ) {

		mReadOnly = readOnly;
	}

	/**
	 * The ArrayList is defined as a 'Collection' at the getter/setter level, to test 'actual'
	 * versus 'declared' types.
	 */

	public Collection<String> getCollection() {

		return mCollection;
	}

	public void setCollection( Collection<String> collection ) {

		mCollection = collection;
	}

	public void doAction() {

		// Throw an Exception so that unit tests can catch it

		throw new RuntimeException( "doAction called" );
	}

	//
	// Inner class
	//

	public static class NestedWidgets
		implements Serializable {

		//
		// Private members
		//

		private String				mNestedTextbox1		= "Nested Textbox 1";

		private String				mNestedTextbox2		= "Nested Textbox 2";

		private NestedWidgets		mFurtherNestedWidgets;

		//
		// Public methods
		//

		public String getNestedTextbox1() {

			return mNestedTextbox1;
		}

		public void setNestedTextbox1( String nestedTextbox1 ) {

			mNestedTextbox1 = nestedTextbox1;
		}

		public String getNestedTextbox2() {

			return mNestedTextbox2;
		}

		public void setNestedTextbox2( String nestedTextbox2 ) {

			mNestedTextbox2 = nestedTextbox2;
		}

		public NestedWidgets getFurtherNestedWidgets() {

			return mFurtherNestedWidgets;
		}

		public void setFurtherNestedWidgets( NestedWidgets furtherNestedWidgets ) {

			mFurtherNestedWidgets = furtherNestedWidgets;
		}

		@Override
		public String toString() {

			return mNestedTextbox1 + ", " + mNestedTextbox2;
		}
	}
}
