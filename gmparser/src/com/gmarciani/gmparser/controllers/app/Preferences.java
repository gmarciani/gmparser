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

package com.gmarciani.gmparser.controllers.app;

import org.fusesource.jansi.Ansi.Color;

/**
 * Static app preferences repo.
 * 
 * @see {@link App}
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public final class Preferences {
	
	private Preferences() {
		throw new AssertionError();
	}

	/**
	 * Description
	 * 
	 * @see {@link App}
	 * @see {@link Preferences}
	 * 
	 * @author Giacomo Marciani
	 * @version 1.0
	 */
	public static final class AppUI {	
		
		private AppUI() {
			throw new AssertionError();
		}
		
		public static final String FILE_LOGO = "com/gmarciani/gmparser/views/res/logo.txt";
		public static final String LOGO_PLACEHOLDER = "\nWELCOME TO GMPARSER\n";
		
		public static final Color LOGO_COLOR = Color.YELLOW;
		public static final Color RESULT_COLOR = Color.GREEN;
		public static final Color LOGON_COLOR = Color.DEFAULT;
		public static final Color WARNING_COLOR = Color.YELLOW;
		public static final Color EXCEPTION_COLOR = Color.RED;
		public static final Color DEBUG_COLOR = Color.MAGENTA;
	}	
	
	/**
	 * Description
	 * 
	 * @see {@link App}
	 * @see {@link Preferences}
	 * 
	 * @author Giacomo Marciani
	 * @version 1.0
	 */
	public static final class AppMenus {
		
		private AppMenus() {
			throw new AssertionError();
		}
		
		/**
		 * Description
		 * 
		 * @see {@link App}
		 * @see {@link Preferences}
		 * 
		 * @author Giacomo Marciani
		 * @version 1.0
		 */
		public static final class MainMenu {
			
			private MainMenu() {
				throw new AssertionError();
			}
			
			public static final String IDENTIFIER = "main-menu";
			public static final String NAME = "Main Menu";
			public static final String DESCRIPTION = "Main menu description placeholder";
			
			public static final int ANALYZE = 1;
			public static final int PARSE = 2;
			public static final int HELP = 4;
			public static final int QUIT = 5;
			
			public static final String ANALYZE_DESCRIPTION = "Analyze";
			public static final String PARSE_DESCRIPTION = "Parse";		
			public static final String HELP_DESCRIPTION = "Help";
			public static final String QUIT_DESCRIPTION = "Quit";			
		}
		
		/**
		 * Description
		 * 
		 * @see {@link App}
		 * @see {@link Preferences}
		 * 
		 * @author Giacomo Marciani
		 * @version 1.0
		 */
		public static final class ParseMenu {
			
			private ParseMenu() {
				throw new AssertionError();
			}
			
			public static final String IDENTIFIER = "parse-menu";
			public static final String NAME = "Parse Menu";
			public static final String DESCRIPTION = "Parser menu description placeholder";
			
			public static final int CYK = 1;
			public static final int LL1 = 2;
			
			public static final String CYK_DESCRIPTION = "Cock-Young-Kasami";
			public static final String LL1_DESCRIPTION = "LL(1) Parser";			
		}
		
		/**
		 * Description
		 * 
		 * @see {@link App}
		 * @see {@link Preferences}
		 * 
		 * @author Giacomo Marciani
		 * @version 1.0
		 */
		public static final class LogonMenu {
			
			private LogonMenu() {
				throw new AssertionError();
			}
			
			public static final String IDENTIFIER = "logon-menu";
			public static final String NAME = "Logon Menu";
			public static final String DESCRIPTION = "Logon menu description placeholder";
			
			public static final int LOGOFF = 0;
			public static final int LOGON = 1;
			
			public static final String LOGOFF_DESCRIPTION = "Turn log off";
			public static final String LOGON_DESCRIPTION = "Turn log on";
		}
		
	}	
	
	/**
	 * Description
	 * 
	 * @see {@link App}
	 * @see {@link Preferences}
	 * 
	 * @author Giacomo Marciani
	 * @version 1.0
	 */
	public static final class AppInteractions {
		
		private AppInteractions() {
			throw new AssertionError();
		}
		
		/**
		 * Description
		 * 
		 * @see {@link App}
		 * @see {@link Preferences}
		 * 
		 * @author Giacomo Marciani
		 * @version 1.0
		 */
		public static final class Grammar {
			
			private Grammar() {
				throw new AssertionError();
			}
			public static final String IDENTIFIER = "insert-grammar";
			public static final String NAME = "YOUR GRAMMAR";
			public static final String DESCRIPTION = "Insert your grammar (pattern: (lhs->rhs)+(;lhs->rhs).)";
		}
		
		/**
		 * Description
		 * 
		 * @see {@link App}
		 * @see {@link Preferences}
		 * 
		 * @author Giacomo Marciani
		 * @version 1.0
		 */
		public static final class InputString {
			
			private InputString() {
				throw new AssertionError();
			}
			
			public static final String IDENTIFIER = "insert-input-string";
			public static final String NAME = "YOUR WORD";
			public static final String DESCRIPTION = "Insert your word";
		}

	}
	
	/**
	 * Description
	 * 
	 * @see {@link App}
	 * @see {@link Preferences}
	 * 
	 * @author Giacomo Marciani
	 * @version 1.0
	 */
	public static final class AppOptions {
		
		private AppOptions() {
			throw new AssertionError();
		}
		
		public static final String DESCRIPTION_VERSION = "GMParser version.";
		public static final String DESCRIPTION_HELP = "GMParser helper.";
		public static final String DESCRIPTION_LOGON = "Turn logging on.";
		public static final String DESCRIPTION_ANALYZE = "Analyze the GRAMMAR.";
		public static final String DESCRIPTION_PARSE = "Parse the WORD on GRAMMAR with PARSER.";
		
	}	
	
	/**
	 * Description
	 * 
	 * @see {@link App}
	 * @see {@link Preferences}
	 * 
	 * @author Giacomo Marciani
	 * @version 1.0
	 */
	public static final class AppLog {
		
		private AppLog() {
			throw new AssertionError();
		}
		
		public static final boolean DEBUG = true;
		public static boolean logon = false;
	}	

}
