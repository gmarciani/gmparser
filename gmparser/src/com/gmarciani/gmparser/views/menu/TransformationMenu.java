/*	The MIT License (MIT)
 *
 *	Copyright (c) 2014 Giacomo Marciani
 *	
 *	Permission is hereby granted, free of charge, to any person obtaining a copy
 *	of this software and associated documentation files (the "Software"), to deal
 *	in the Software without restriction, including without limitation the rights
 *	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *	copies of the Software, and to permit persons to whom the Software is
 *	furnished to do so, subject to the following conditions:
 *	
 *	The above copyright notice and this permission notice shall be included in all
 *	copies or substantial portions of the Software.
 *	
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *	SOFTWARE.
*/

package com.gmarciani.gmparser.views.menu;

public class TransformationMenu {

	private TransformationMenu() {
		throw new AssertionError();
	}
	
	public static final String IDENTIFIER = "transformation";
	public static final String NAME = "TRANSFORMATION";
	public static final String DESCRIPTION = "Choose your transformation";
	
	public static final int GENERATE_CHOMSKY_NORMAL_FORM = 1;
	public static final int REMOVE_UNGENERATIVE_SYMBOLS = 2;
	public static final int REMOVE_UNREACHEABLES_SYMBOLS = 3;
	public static final int REMOVE_USELESS_SYMBOLS = 4;
	public static final int REMOVE_EPSILON_PRODUCTIONS = 5;
	public static final int REMOVE_UNIT_PRODUCTIONS = 6;		
	
	public static final String GENERATE_CHOMSKY_NORMAL_FORM_DESCRIPTION = "Generate Chomsky-Normal-Form";
	public static final String REMOVE_UNGENERATIVE_SYMBOLS_DESCRIPTION = "Remove ungenerative symbols";
	public static final String REMOVE_UNREACHEABLES_SYMBOLS_DESCRIPTION = "Remove unreacheables symbols";
	public static final String REMOVE_USELESS_SYMBOLS_DESCRIPTION = "Remove useless symbols";
	public static final String REMOVE_EPSILON_PRODUCTIONS_DESCRIPTION = "Remove epsilon productions";
	public static final String REMOVE_UNIT_PRODUCTIONS_DESCRIPTION = "Remove unit productions";	

}
