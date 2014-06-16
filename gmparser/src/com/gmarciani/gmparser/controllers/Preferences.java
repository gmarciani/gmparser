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

package com.gmarciani.gmparser.controllers;

import org.fusesource.jansi.Ansi.Color;

/**
 * <p>Static app preferences repo.<p>
 * <p>Preferences content.<p>
 * 
 * @see com.gmarciani.gmparser.controllers.App
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public final class Preferences {
	
	private Preferences() {
		throw new AssertionError();
	}

	protected static final class AppUI {	
		
		private AppUI() {
			throw new AssertionError();
		}
		
		public static final String FILE_LOGO = "com/gmarciani/gmparser/views/res/logo.txt";
		public static final String LOGO_PLACEHOLDER = "\nWELCOME TO GMPARSER\n";
		
		public static final Color LOGO_COLOR = Color.YELLOW;
		public static final Color RESULT_COLOR = Color.GREEN;
		public static final Color LOGON_COLOR = Color.CYAN;
		public static final Color WARNING_COLOR = Color.YELLOW;
		public static final Color EXCEPTION_COLOR = Color.RED;
	}	
	
	protected static final class AppLog {
		
		private AppLog() {
			throw new AssertionError();
		}
		
		public static boolean LOGON = false;
	}		

}
