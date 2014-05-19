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

import com.gmarciani.gmparser.models.parser.ParserType;

public final class App {

	public static final class AppUI {		
		public static final String FILE_LOGO = "com/gmarciani/gmparser/views/res/logo.txt";
		public static final String LOGO_PLACEHOLDER = "\nWELCOME TO GMPARSER\n";
		
		public static final Color LOGO_COLOR = Color.YELLOW;
		public static final Color LOGON_COLOR = Color.DEFAULT;
		public static final Color WARNING_COLOR = Color.YELLOW;
		public static final Color EXCEPTION_COLOR = Color.RED;
		public static final Color DEBUG_COLOR = Color.MAGENTA;
	}	
	
	public static final class AppMenus {
		
		public static final class MainMenu {			
			public static final String IDENTIFIER = "main-menu";
			public static final String NAME = "Main Menu";
			public static final String DESCRIPTION = "Main menu description placeholder";
			
			public static final int PARSE = 1;
			public static final int TRANSFORM = 2;
			public static final int CHECK = 3;
			public static final int HELP = 4;
			public static final int QUIT = 5;
			
			public static final String PARSE_DESCRIPTION = "Parse";
			public static final String TRANSFORM_DESCRIPTION = "Transform";
			public static final String CHECK_DESCRIPTION = "Check";
			public static final String HELP_DESCRIPTION = "Help";
			public static final String QUIT_DESCRIPTION = "Quit";			
		}
		
		public static final class ParseMenu {
			public static final String IDENTIFIER = "parse-menu";
			public static final String NAME = "Parse Menu";
			public static final String DESCRIPTION = "Parser menu description placeholder";
			
			public static final int CYK = 1;
			public static final int LL1 = 2;
			
			public static final String CYK_DESCRIPTION = "Cock-Young-Kasami";
			public static final String LL1_DESCRIPTION = "LL(1) Parser";			
		}
		
		public static final class TransformMenu {
			public static final String IDENTIFIER = "transform-menu";
			public static final String NAME = "Transform Menu";
			public static final String DESCRIPTION = "Transform menu description placeholder";
			
			public static final int CHOMSKY_NORMAL_FORM = 1;
			public static final int GREIBACH_NORMAL_FORM = 2;
			
			public static final String CHOMSKY_NORMAL_FORM_DESCRIPTION = "Chomsky Normal Form";
			public static final String GREIBACH_NORMAL_FORM_DESCRIPTION = "Greibach Normal Form";			
		}
		
		public static final class LogonMenu {
			public static final String IDENTIFIER = "logon-menu";
			public static final String NAME = "Logon Menu";
			public static final String DESCRIPTION = "Logon menu description placeholder";
			
			public static final int LOGOFF = 0;
			public static final int LOGON = 1;
			
			public static final String LOGOFF_DESCRIPTION = "Turn log off";
			public static final String LOGON_DESCRIPTION = "Turn log on";
		}
		
	}	
	
	public static final class AppInteractions {
		
		public static final class Grammar {
			public static final String IDENTIFIER = "insert-grammar";
			public static final String NAME = "Insert Grammar";
			public static final String DESCRIPTION = "Insert grammar description placeholder";
		}
		
		public static final class InputString {
			public static final String IDENTIFIER = "insert-input-string";
			public static final String NAME = "Insert Input String";
			public static final String DESCRIPTION = "Insert input string description placeholder";
		}

	}
	
	public static final class AppOptions {
		public static final String DESCRIPTION_VERSION = "GMParser version.";
		public static final String DESCRIPTION_HELP = "GMParser helper.";
		public static final String DESCRIPTION_LOGON = "Turn logging on.";
		public static final String DESCRIPTION_PARSE = "Parse the STRING on GRAMMAR with PARSER.";
		public static final String DESCRIPTION_TRANSFORM = "Transform the GRAMMAR in GRAMMAR-NORMAL-FORM.";
		public static final String DESCRIPTION_CHECK = "Check the GRAMMAR type and form.";
	}	
	
	public static final class AppSettings {
		public static final boolean DEBUG = true;
		public static boolean logon = false;
	}
	
	public static final class AppSample {
		public static final String GRAMMAR = "S->Aa|Bb|Cc;A->Aa|a;B->Bb|b;C->Cc|c";
		public static final String INPUT_STRING = "aaaaabbbbbbccccc";
		public static final ParserType PARSER = ParserType.CYK;
	}
	

}
