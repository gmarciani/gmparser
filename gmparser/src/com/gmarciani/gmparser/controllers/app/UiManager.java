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

import com.gmarciani.gmparser.controllers.app.Preferences.AppInteractions;
import com.gmarciani.gmparser.controllers.app.Preferences.AppMenus;
import com.gmarciani.gmparser.controllers.app.Preferences.AppOptions;
import com.gmarciani.gmparser.views.interaction.Interaction;
import com.gmarciani.gmparser.views.interaction.InteractionBuilder;
import com.gmarciani.gmparser.views.interaction.Interactions;
import com.gmarciani.gmparser.views.menu.Menu;
import com.gmarciani.gmparser.views.menu.MenuBuilder;
import com.gmarciani.gmparser.views.menu.Menus;

/**
 * The command-line interface manager.
 * 
 * @see {@link App}
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
				.hasArgs(3)
				.withValueSeparator(' ')
				.withArgName("GRAMMAR")
				.create("analyze");
		
		Option parse = OptionBuilder.withLongOpt("parse")
				.withDescription(AppOptions.DESCRIPTION_PARSE)
				.hasArgs(3)
				.withValueSeparator(' ')
				.withArgName("GRAMMAR STRING PARSER")
				.create("parse");
		
		Option help = OptionBuilder.withLongOpt("help")
				.withDescription(AppOptions.DESCRIPTION_HELP)
				.hasArg(false)
				.create("help");
		
		Option version = OptionBuilder.withLongOpt("version")
				.withDescription(AppOptions.DESCRIPTION_VERSION)
				.hasArg(false)
				.create();
		
		Option logon = OptionBuilder.withLongOpt("logon")
				.withDescription(AppOptions.DESCRIPTION_LOGON)
				.hasArg(false)
				.create();
		
		OptionGroup optionGroup = new OptionGroup();
		optionGroup.addOption(analyze);
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
		Menu mainMenu = MenuBuilder.hasName(AppMenus.MainMenu.NAME)
				.withDescription(AppMenus.MainMenu.DESCRIPTION)
				.hasChoice(AppMenus.MainMenu.PARSE, AppMenus.MainMenu.PARSE_DESCRIPTION)
				.hasChoice(AppMenus.MainMenu.HELP, AppMenus.MainMenu.HELP_DESCRIPTION)
				.hasChoice(AppMenus.MainMenu.QUIT, AppMenus.MainMenu.QUIT_DESCRIPTION)
				.create();
		
		Menu parseMenu = MenuBuilder.hasName(AppMenus.ParseMenu.NAME)
				.withDescription(AppMenus.ParseMenu.DESCRIPTION)
				.hasChoice(AppMenus.ParseMenu.CYK, AppMenus.ParseMenu.CYK_DESCRIPTION)
				.hasChoice(AppMenus.ParseMenu.LL1, AppMenus.ParseMenu.LL1_DESCRIPTION)
				.create();
		
		Menu logonMenu = MenuBuilder.hasName(AppMenus.LogonMenu.NAME)
				.withDescription(AppMenus.LogonMenu.DESCRIPTION)
				.hasChoice(AppMenus.LogonMenu.LOGOFF, AppMenus.LogonMenu.LOGOFF_DESCRIPTION)
				.hasChoice(AppMenus.LogonMenu.LOGON, AppMenus.LogonMenu.LOGON_DESCRIPTION)
				.create();
		
		Menus menus = new Menus();
		menus.addMenu(mainMenu);
		menus.addMenu(parseMenu);
		menus.addMenu(logonMenu);
		
		return menus;
	}
	
	/**
	 * Builds available interactions.
	 * 
	 * @return interactions entries.
	 */
	@SuppressWarnings("static-access")
	public static Interactions buildInteractions() {
		Interaction grammar = InteractionBuilder.hasName(AppInteractions.Grammar.NAME)
				.withDescription(AppInteractions.Grammar.DESCRIPTION)
				.create();
		
		Interaction inputString = InteractionBuilder.hasName(AppInteractions.InputString.NAME)
				.withDescription(AppInteractions.InputString.DESCRIPTION)
				.create();
		
		Interactions interactions = new Interactions();
		interactions.addInteraction(grammar);
		interactions.addInteraction(inputString);
		
		return interactions;
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
