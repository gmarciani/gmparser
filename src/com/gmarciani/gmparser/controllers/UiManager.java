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

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.fusesource.jansi.AnsiConsole;
import org.fusesource.jansi.Ansi.Color;

import com.gmarciani.gmparser.models.commons.menu.Menu;
import com.gmarciani.gmparser.models.commons.menu.MenuBuilder;
import com.gmarciani.gmparser.models.commons.menu.Menus;
import com.gmarciani.gmparser.views.AppMenus.MainMenu;
import com.gmarciani.gmparser.views.AppMenus.ParserMenu;
import com.gmarciani.gmparser.views.AppMenus.TransformationMenu;
import com.gmarciani.gmparser.views.AppOptions;

/**
 * <p>Command-line interface manager.<p>
 * <p>It manages options, menus and interactions <p>
 * 
 * @see com.gmarciani.gmparser.controllers.App
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public final class UiManager {

	/**
	 * <p>Builds available command-line options.<p>
	 * 
	 * @return command-line options
	 */
	@SuppressWarnings("static-access")
	public static Options buildCommandLineOptions() {			
		
		Option analyze = OptionBuilder.withLongOpt("analyze")
				.withDescription(AppOptions.DESCRIPTION_ANALYZE)
				.hasArgs(1)
				.withValueSeparator(' ')
				.withArgName("GRAMMAR")
				.create("a");
		
		Option transform = OptionBuilder.withLongOpt("transform")
				.withDescription(AppOptions.DESCRIPTION_TRANSFORM)
				.hasArgs(2)
				.withValueSeparator(' ')
				.withArgName("GRAMMAR TRANSFORMATION")
				.create("t");
		
		Option parse = OptionBuilder.withLongOpt("parse")
				.withDescription(AppOptions.DESCRIPTION_PARSE)
				.hasArgs(3)
				.withValueSeparator(' ')
				.withArgName("GRAMMAR WORD PARSER")
				.create("p");
		
		Option help = OptionBuilder.withLongOpt("help")
				.withDescription(AppOptions.DESCRIPTION_HELP)
				.hasArg(false)
				.create("h");
		
		Option version = OptionBuilder.withLongOpt("version")
				.withDescription(AppOptions.DESCRIPTION_VERSION)
				.hasArg(false)
				.create("v");
		
		OptionGroup optionGroup = new OptionGroup();
		optionGroup.addOption(analyze);
		optionGroup.addOption(transform);
		optionGroup.addOption(parse);		
		optionGroup.addOption(help);
		optionGroup.addOption(version);
		
		Options options = new Options();	
		options.addOptionGroup(optionGroup);
		
		return options;
	}	
	
	/**
	 * <p>Builds available menus entries.<p>
	 * 
	 * @return menus entries
	 */
	@SuppressWarnings("static-access")
	public static Menus buildMenus() {
		Menu mainMenu = MenuBuilder.hasName(MainMenu.NAME)
				.withDescription(MainMenu.DESCRIPTION)
				.hasChoice(MainMenu.ANALYZE, MainMenu.ANALYZE_DESCRIPTION)
				.hasChoice(MainMenu.TRANSFORM, MainMenu.TRANSFORM_DESCRIPTION)
				.hasChoice(MainMenu.PARSE, MainMenu.PARSE_DESCRIPTION)
				.hasChoice(MainMenu.HELP, MainMenu.HELP_DESCRIPTION)
				.hasChoice(MainMenu.QUIT, MainMenu.QUIT_DESCRIPTION)
				.create();
		
		Menu parserMenu = MenuBuilder.hasName(ParserMenu.NAME)
				.withDescription(ParserMenu.DESCRIPTION)
				.hasChoice(ParserMenu.CYK, ParserMenu.CYK_DESCRIPTION)
				.hasChoice(ParserMenu.LR1, ParserMenu.LR1_DESCRIPTION)
				.create();
		
		Menu transformationMenu = MenuBuilder.hasName(TransformationMenu.NAME)
				.withDescription(TransformationMenu.DESCRIPTION)
				.hasChoice(TransformationMenu.REMOVE_UNGENERATIVE_SYMBOLS, TransformationMenu.REMOVE_UNGENERATIVE_SYMBOLS_DESCRIPTION)
				.hasChoice(TransformationMenu.REMOVE_UNREACHEABLES_SYMBOLS, TransformationMenu.REMOVE_UNREACHEABLES_SYMBOLS_DESCRIPTION)
				.hasChoice(TransformationMenu.REMOVE_USELESS_SYMBOLS, TransformationMenu.REMOVE_USELESS_SYMBOLS_DESCRIPTION)
				.hasChoice(TransformationMenu.REMOVE_EPSILON_PRODUCTIONS, TransformationMenu.REMOVE_EPSILON_PRODUCTIONS_DESCRIPTION)
				.hasChoice(TransformationMenu.REMOVE_UNIT_PRODUCTIONS, TransformationMenu.REMOVE_UNIT_PRODUCTIONS_DESCRIPTION)
				.hasChoice(TransformationMenu.GENERATE_CHOMSKY_NORMAL_FORM, TransformationMenu.GENERATE_CHOMSKY_NORMAL_FORM_DESCRIPTION)
				.create();
		
		Menus menus = new Menus();
		menus.addMenu(mainMenu);
		menus.addMenu(parserMenu);
		menus.addMenu(transformationMenu);
		
		return menus;
	}
	
	/**
	 * Returns the ASCII art GMParser logo.
	 * 
	 * @return the logo, represented as a string.
	 */
	public static String getLogo() {
		final String logo = "\n   ___|   \\  |   _ \\                               \n  |      |\\/ |  |   |  _` |   __|  __|   _ \\   __| \n  |   |  |   |  ___/  (   |  |   \\__ \\   __/  |    \n \\____| _|  _| _|    \\__,_| _|   ____/ \\___| _|    \n";
		
		return logo;
	}

	/**
	 * Installs the ANSI Console for colored command-line interface.
	 */
	public static void installAnsiConsole() {
		AnsiConsole.systemInstall();
	}

	/**
	 * Uninstalls the ANSI Console to set the default system console.
	 */
	public static void uninstallAnsiConsole() {
		AnsiConsole.systemUninstall();
	}
	
	/**
	 * <p>GMParser user-interface preferences, like colors and placeholders.<p>
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
		public static final Color LOGON_COLOR = Color.CYAN;
		public static final Color WARNING_COLOR = Color.YELLOW;
		public static final Color EXCEPTION_COLOR = Color.RED;
	}

}
