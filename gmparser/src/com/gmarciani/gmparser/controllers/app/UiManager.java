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

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.fusesource.jansi.AnsiConsole;

import com.gmarciani.gmparser.views.app.AppOptions;
import com.gmarciani.gmparser.views.app.MainMenu;
import com.gmarciani.gmparser.views.app.ParserMenu;
import com.gmarciani.gmparser.views.app.TransformationMenu;
import com.gmarciani.gmparser.views.menu.Menu;
import com.gmarciani.gmparser.views.menu.MenuBuilder;
import com.gmarciani.gmparser.views.menu.Menus;

/**
 * <p>Command-line interface manager.<p>
 * <p>It manages options, menus and interactions <p>
 * 
 * @see com.gmarciani.gmparser.controllers.app.App
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public final class UiManager {

	/**
	 * Builds available command-line options.
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
		
		Option logon = OptionBuilder.withLongOpt("logon")
				.withDescription(AppOptions.DESCRIPTION_LOGON)
				.hasArg(false)
				.create("l");
		
		OptionGroup optionGroup = new OptionGroup();
		optionGroup.addOption(analyze);
		optionGroup.addOption(transform);
		optionGroup.addOption(parse);		
		optionGroup.addOption(help);
		optionGroup.addOption(version);
		
		Options options = new Options();	
		options.addOptionGroup(optionGroup);
		options.addOption(logon);
		
		return options;
	}	
	
	/**
	 * Builds available menus entries.
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
				.create();
		
		Menus menus = new Menus();
		menus.addMenu(mainMenu);
		menus.addMenu(parserMenu);
		menus.addMenu(transformationMenu);
		
		return menus;
	}
	
	public static String getLogo() {
		final String logo = "\n   ___|   \\  |   _ \\                               \n  |      |\\/ |  |   |  _` |   __|  __|   _ \\   __| \n  |   |  |   |  ___/  (   |  |   \\__ \\   __/  |    \n \\____| _|  _| _|    \\__,_| _|   ____/ \\___| _|    \n";
		
		return logo;
	}
	
	public static String makeBold(String string) {
		return "\033[0;1m " + string + " \033[0;0m";
	}
	
	public static String getBullet() {
		return "\u0700 ";
	}

	/**
	 * Installs ANSI Console for colored command-line interface.
	 */
	public static void installAnsiConsole() {
		AnsiConsole.systemInstall();
	}

	/**
	 * Uninstalls ANSI Console to set the default system console.
	 */
	public static void uninstallAnsiConsole() {
		AnsiConsole.systemUninstall();
	}

}
