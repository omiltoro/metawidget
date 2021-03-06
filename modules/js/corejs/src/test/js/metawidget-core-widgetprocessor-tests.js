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

( function() {

	'use strict';

	describe( "The IdProcessor", function() {

		it( "assigns ids to widgets", function() {

			var processor = new metawidget.widgetprocessor.IdProcessor();

			var widget = simpleDocument.createElement( 'input' );
			var mw = {};
			processor.processWidget( widget, "property", {
				name: "foo"
			}, mw );
			expect( widget.toString() ).toBe( 'input id="foo"' );

			// With subpath

			widget = simpleDocument.createElement( 'input' );
			mw.path = 'foo.bar';
			processor.processWidget( widget, "property", {
				name: "baz"
			}, mw );
			expect( widget.toString() ).toBe( 'input id="fooBarBaz"' );

			// With root

			widget = simpleDocument.createElement( 'input' );
			mw.path = 'foo.bar';
			processor.processWidget( widget, "entity", {}, mw );
			expect( widget.toString() ).toBe( 'input id="fooBar"' );
		} );

		it( "does not reassigns ids", function() {

			var processor = new metawidget.widgetprocessor.IdProcessor();

			var widget = simpleDocument.createElement( 'input' );
			widget.setAttribute( 'id', 'do-not-touch' );
			var mw = {};
			processor.processWidget( widget, "property", {
				name: "foo"
			}, mw );
			expect( widget.toString() ).toBe( 'input id="do-not-touch"' );
		} );
	} );

	describe( "The RequiredAttributeProcessor", function() {

		it( "assigns the required attribute to widgets", function() {

			var processor = new metawidget.widgetprocessor.RequiredAttributeProcessor();

			var widget = simpleDocument.createElement( 'input' );
			var mw = {};

			processor.processWidget( widget, "property", {}, mw );
			expect( widget.hasAttribute( 'required' ) ).toBe( false );

			processor.processWidget( widget, "property", {
				required: "false"
			}, mw );
			expect( widget.hasAttribute( 'required' ) ).toBe( false );

			processor.processWidget( widget, "property", {
				required: "true"
			}, mw );
			expect( widget.getAttribute( 'required' ) ).toBe( 'required' );
		} );
	} );

	describe( "The SimpleBindingProcessor", function() {

		it( "processes widgets and binds them", function() {

			var processor = new metawidget.widgetprocessor.SimpleBindingProcessor();
			var attributes = {
				name: "foo",
			};
			var mw = {
				toInspect: {
					foo: "fooValue",
					bar: "barValue",
					baz: "bazValue",
					boolean: true,
					select: false
				},
				path: "testPath"
			};

			processor.onStartBuild( mw );

			// Inputs

			var widget = simpleDocument.createElement( 'input' );
			widget.setAttribute( 'id', 'fooId' );
			processor.processWidget( widget, "property", attributes, mw );
			expect( widget.toString() ).toBe( 'input id="fooId" name="fooId"' );
			expect( widget.value ).toBe( 'fooValue' );

			// Buttons

			attributes = {
				name: "bar"
			};
			widget = simpleDocument.createElement( 'button' );
			processor.processWidget( widget, "property", attributes, mw );
			expect( widget.toString() ).toBe( 'button' );
			expect( widget.onclick.toString() ).toContain( 'return mw.toInspect[attributes.name]();' );

			// Outputs

			widget = simpleDocument.createElement( 'output' );
			processor.processWidget( widget, "property", attributes, mw );
			expect( widget.toString() ).toBe( 'output' );
			expect( widget.innerHTML ).toBe( 'barValue' );

			// Enums

			attributes = {
				name: "baz",
				enum: [ "bazValue1", "bazValue", "bazValue3" ],
				enumTitles: [ "1", "2", "3" ]
			};
			widget = simpleDocument.createElement( 'output' );
			processor.processWidget( widget, "property", attributes, mw );
			expect( widget.toString() ).toBe( 'output' );
			expect( widget.innerHTML ).toBe( '2' );

			attributes = {
				name: "baz",
				enum: [ "bazValue1", "bazValue", "bazValue3" ],
				enumTitles: []
			};
			processor.processWidget( widget, "property", attributes, mw );
			expect( widget.toString() ).toBe( 'output' );
			expect( widget.innerHTML ).toBe( 'bazValue' );

			// Textareas

			attributes = {
				name: "baz"
			};
			widget = simpleDocument.createElement( 'textarea' );
			processor.processWidget( widget, "property", attributes, mw );
			expect( widget.toString() ).toBe( 'textarea' );
			expect( widget.innerHTML ).toBe( 'bazValue' );

			// Checkboxes

			attributes = {
				name: "boolean"
			};
			widget = simpleDocument.createElement( 'input' );
			widget.setAttribute( 'type', 'checkbox' );
			processor.processWidget( widget, "property", attributes, mw );
			expect( widget.toString() ).toBe( 'input type="checkbox"' );
			expect( widget.checked ).toBe( true );

			// Select boxes

			attributes = {
				name: "select"
			};
			widget = simpleDocument.createElement( 'select' );
			processor.processWidget( widget, "property", attributes, mw );
			expect( widget.toString() ).toBe( 'select' );
			expect( widget.value ).toBe( false );

			// Root-level

			widget = simpleDocument.createElement( 'output' );
			processor.processWidget( widget, "entity", {}, mw );
			expect( widget.toString() ).toBe( 'output' );
			expect( widget.innerHTML ).toBe( mw.toInspect );
		} );

		it( "supports nested widgets", function() {

			var element = simpleDocument.createElement( 'div' );
			var mw = new metawidget.Metawidget( element, {
				layout: new metawidget.layout.SimpleLayout()
			} );

			mw.toInspect = {
				nested: {
					"nestedFoo": "nestedFooValue"
				}
			};
			mw.buildWidgets();

			expect( element.childNodes[0].toString() ).toBe( 'div id="nested"' );
			expect( element.childNodes[0].childNodes[0].toString() ).toBe( 'input type="text" id="nestedNestedFoo" name="nestedNestedFoo"' );
			expect( element.childNodes[0].childNodes[0].value ).toBe( 'nestedFooValue' );
			expect( element.childNodes[0].childNodes.length ).toBe( 1 );
			expect( element.childNodes.length ).toBe( 1 );

			element.childNodes[0].childNodes[0].value = 'nestedFooValue1';
			mw.getWidgetProcessor( function( testInstanceOf ) {

				return testInstanceOf instanceof metawidget.widgetprocessor.SimpleBindingProcessor;
			} ).save( mw );
			expect( mw.toInspect.nested.nestedFoo ).toBe( 'nestedFooValue1' );
		} );

		it( "supports paths", function() {

			var element = simpleDocument.createElement( 'div' );
			var mw = new metawidget.Metawidget( element, {
				layout: new metawidget.layout.SimpleLayout()
			} );

			mw.toInspect = {
				firstname: 'bar'
			};
			mw.path = 'toInspect.firstname';
			mw.buildWidgets();

			expect( element.childNodes[0].toString() ).toBe( 'input type="text" id="toInspectFirstname" name="toInspectFirstname"' );
			expect( element.childNodes[0].value ).toBe( 'bar' );
			expect( element.childNodes.length ).toBe( 1 );

			element.childNodes[0].value = 'baz';

			// Should set properties

			mw.getWidgetProcessor( function( testInstanceOf ) {

				return testInstanceOf instanceof metawidget.widgetprocessor.SimpleBindingProcessor;
			} ).save( mw );
			expect( mw.toInspect.firstname ).toBe( 'baz' );

			// Should not set sub-properties

			mw.toInspect = {
				firstname: {}
			}
			mw.getWidgetProcessor( function( testInstanceOf ) {

				return testInstanceOf instanceof metawidget.widgetprocessor.SimpleBindingProcessor;
			} ).save( mw );
			expect( mw.toInspect.firstname.firstname ).toNotBe( 'baz' );

			// Should support read-only top-level

			mw.toInspect = 'Bar'
			delete mw.path;

			mw.buildWidgets();

			expect( element.childNodes[0].toString() ).toBe( 'input type="text"' );
			expect( element.childNodes[0].value ).toBe( 'Bar' );
			expect( element.childNodes.length ).toBe( 1 );

			element.childNodes[0].value = 'Baz';

			mw.getWidgetProcessor( function( testInstanceOf ) {

				return testInstanceOf instanceof metawidget.widgetprocessor.SimpleBindingProcessor;
			} ).save( mw );
			expect( mw.toInspect ).toBe( 'Bar' );
		} );

		it( "creates children just-in-time", function() {

			var element = simpleDocument.createElement( 'div' );
			var mw = new metawidget.Metawidget( element, {
				inspector: function() {

					return {
						properties: {
							firstname: {
								type: 'string'
							}
						}
					};
				},
				layout: new metawidget.layout.SimpleLayout()
			} );

			mw.toInspect = {};
			mw.path = 'toInspect.jit'
			mw.buildWidgets();

			expect( element.childNodes[0].toString() ).toBe( 'input type="text" id="toInspectJitFirstname" name="toInspectJitFirstname"' );
			expect( element.childNodes[0].value ).toBeUndefined();
			expect( element.childNodes.length ).toBe( 1 );

			element.childNodes[0].value = 'baz';

			mw.getWidgetProcessor( function( testInstanceOf ) {

				return testInstanceOf instanceof metawidget.widgetprocessor.SimpleBindingProcessor;
			} ).save( mw );
			expect( mw.toInspect.jit.firstname ).toBe( 'baz' );
		} );
	} );
} )();