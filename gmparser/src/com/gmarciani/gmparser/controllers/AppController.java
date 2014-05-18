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


import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.fusesource.jansi.Ansi.Color;
import org.fusesource.jansi.AnsiConsole;

import com.bethecoder.ascii_table.ASCIITable;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarForm;
import com.gmarciani.gmparser.models.grammar.GrammarType;
import com.gmarciani.gmparser.models.parser.ParserType;
import com.gmarciani.gmparser.views.interaction.Interaction;
import com.gmarciani.gmparser.views.interaction.InteractionBuilder;
import com.gmarciani.gmparser.views.interaction.Interactions;
import com.gmarciani.gmparser.views.menu.Menu;
import com.gmarciani.gmparser.views.menu.MenuBuilder;
import com.gmarciani.gmparser.views.menu.Menus;

import static org.fusesource.jansi.Ansi.*;

public class AppController {	
	
	private static AppController instance;
	private Options options;
	private Menus menus;	
	private Interactions interactions;
	
	
	public static final class AppUI {		
		public static final String FILE_LOGO = "com/gmarciani/gmparser/views/res/logo.txt";
		public static final String LOGO_PLACEHOLDER = "\nWELCOME TO GMPARSER\n";
		
		public static final Color LOGO_COLOR = Color.YELLOW;
		public static final Color WARNING_COLOR = Color.RED;
		public static final Color DEBUG_COLOR = Color.MAGENTA;
	}	
	
	public static final class AppMenus {
		
		public static final class MainMenu {			
			public static final String IDENTIFIER = "main-menu";
			public static final String NAME = "Main Menu";
			public static final String DESCRIPTION = "Main menu description placeholder";
			
			public static final int PARSE = 1;
			public static final int CHECK = 2;
			public static final int HELP = 3;
			public static final int QUIT = 4;
			
			public static final String PARSE_DESCRIPTION = "Parse";
			public static final String CHECK_DESCRIPTION = "Check";
			public static final String HELP_DESCRIPTION = "Help";
			public static final String QUIT_DESCRIPTION = "Quit";			
		}
		
		public static final class ParserMenu {				
			public static final String IDENTIFIER = "parser-menu";
			public static final String NAME = "Parser Menu";
			public static final String DESCRIPTION = "Parser menu description placeholder";
			
			public static final int CYK = 1;
			public static final int LL1 = 2;
			
			public static final String CYK_DESCRIPTION = "Cock-Young-Kasami";
			public static final String LL1_DESCRIPTION = "LL(1) Parser";			
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
	
	private AppController() {
		AnsiConsole.systemInstall();
		this.buildCommandLineOptions();
		this.buildMenus();
		this.buildInteractions();
	}

	public static AppController getInstance() {
		if (instance == null) {
			instance = new AppController();
		}
		
		return instance;
	}	
	
	@SuppressWarnings("static-access")
	private void buildCommandLineOptions() {			
		
		Option parse = OptionBuilder.withLongOpt("parse")
				.withDescription(AppOptions.DESCRIPTION_PARSE)
				.hasArgs(3)
				.withValueSeparator(' ')
				.withArgName("GRAMMAR STRING PARSER")
				.create("parse");
		
		Option check = OptionBuilder.withLongOpt("check")
				.withDescription(AppOptions.DESCRIPTION_CHECK)
				.hasArg()
				.withArgName("GRAMMAR")
				.create("check");
		
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
		optionGroup.addOption(parse);
		optionGroup.addOption(check);
		optionGroup.addOption(help);
		optionGroup.addOption(version);
		
		this.options = new Options();	
		this.options.addOptionGroup(optionGroup);
		this.options.addOption(logon);
	}	
	
	@SuppressWarnings("static-access")
	private void buildMenus() {
		Menu mainMenu = MenuBuilder.hasName("Main Menu")
				.withDescription(AppMenus.MainMenu.DESCRIPTION)
				.hasChoice(AppMenus.MainMenu.PARSE, AppMenus.MainMenu.PARSE_DESCRIPTION)
				.hasChoice(AppMenus.MainMenu.CHECK, AppMenus.MainMenu.CHECK_DESCRIPTION)
				.hasChoice(AppMenus.MainMenu.HELP, AppMenus.MainMenu.HELP_DESCRIPTION)
				.hasChoice(AppMenus.MainMenu.QUIT, AppMenus.MainMenu.QUIT_DESCRIPTION)
				.create();
		
		Menu parserMenu = MenuBuilder.hasName("Parser Menu")
				.withDescription(AppMenus.ParserMenu.DESCRIPTION)
				.hasChoice(AppMenus.ParserMenu.CYK, AppMenus.ParserMenu.CYK_DESCRIPTION)
				.hasChoice(AppMenus.ParserMenu.LL1, AppMenus.ParserMenu.LL1_DESCRIPTION)
				.create();
		
		Menu logonMenu = MenuBuilder.hasName("Logon Menu")
				.withDescription(AppMenus.LogonMenu.DESCRIPTION)
				.hasChoice(AppMenus.LogonMenu.LOGOFF, AppMenus.LogonMenu.LOGOFF_DESCRIPTION)
				.hasChoice(AppMenus.LogonMenu.LOGON, AppMenus.LogonMenu.LOGON_DESCRIPTION)
				.create();
		
		this.menus = new Menus();
		this.menus.addMenu(mainMenu);
		this.menus.addMenu(parserMenu);
		this.menus.addMenu(logonMenu);
	}
	
	@SuppressWarnings("static-access")
	private void buildInteractions() {
		Interaction grammar = InteractionBuilder.hasName(AppInteractions.Grammar.NAME)
				.withDescription(AppInteractions.Grammar.DESCRIPTION)
				.create();
		
		Interaction inputString = InteractionBuilder.hasName(AppInteractions.InputString.NAME)
				.withDescription(AppInteractions.InputString.DESCRIPTION)
				.create();
		
		this.interactions = new Interactions();
		this.interactions.addInteraction(grammar);
		this.interactions.addInteraction(inputString);
	}
	
	public void printWelcome() {
		String welcome = AppUI.LOGO_PLACEHOLDER;
		try {
			welcome = IOController.getFileAsString(AppUI.FILE_LOGO);
			System.out.println(ansi().fg(AppUI.LOGO_COLOR).bold().a(welcome).reset());
		} catch (IOException exc) {
			this.printDebug(exc.getMessage());
			this.playMenu();
		}		
	}

	public void play(String[] args) throws ParseException {
		CommandLineParser cmdParser = new GnuParser();
		CommandLine cmd = cmdParser.parse(this.options, args);
		
		String unrecognizedArguments[] = cmd.getArgs();
		
		if (unrecognizedArguments.length != 0) {
			this.unrecognizedArguments(unrecognizedArguments);
			this.quit();
		}
		
		if (cmd.getOptions().length == 0) {
			this.playMenu();
		}
		
		if (cmd.hasOption("logon")) {
			AppSettings.logon = true;
		}
		
		if (cmd.hasOption("parse")) {
			String vals[] = cmd.getOptionValues("parse");
			final String grammar = vals[0];
			final String string = vals[1];
			final ParserType parser = ParserType.valueOf(vals[2]);
			this.parse(grammar, string, parser);
		} else if (cmd.hasOption("check")) {
			String vals[] = cmd.getOptionValues("check");
			final String grammar = vals[0];
			this.check(grammar);
		} else if (cmd.hasOption("help")) {
			this.help();
		} else if (cmd.hasOption("version")) {
			this.version();
		}
	}	

	private void playMenu() {
		
		int choice = this.menus.run(AppMenus.MainMenu.IDENTIFIER);
		
		if (choice == AppMenus.MainMenu.PARSE) {
			String grammar = this.interactions.run(AppInteractions.Grammar.IDENTIFIER);
			String string = this.interactions.run(AppInteractions.InputString.IDENTIFIER);
			ParserType parser = this.getParserType();
			AppSettings.logon = (this.menus.run(AppMenus.LogonMenu.IDENTIFIER) == 1) ? true : false;
			this.parse(grammar, string, parser);
			this.playMenu();
		} else if (choice == AppMenus.MainMenu.CHECK) {
			String grammar = this.interactions.run(AppInteractions.Grammar.IDENTIFIER);
			AppSettings.logon = (this.menus.run(AppMenus.LogonMenu.IDENTIFIER) == 1) ? true : false;
			this.check(grammar);
			this.playMenu();
		} else if (choice == AppMenus.MainMenu.HELP) {
			this.help();
			this.playMenu();
		} else if (choice == AppMenus.MainMenu.QUIT) {
			this.quit();
		} else {
			this.printWarning("Unrecognized choice");
			this.playMenu();
		}
	}	

	private ParserType getParserType() {
		return AppSample.PARSER;
	}

	private void parse(String strGrammar, String string, ParserType parser) {
		Grammar grammar = ParserController.parseGrammar(strGrammar);
		boolean accepted = ParserController.parse(grammar, string, parser);
		System.out.println("Grammar: " + strGrammar + "\nString: " + string + "\nParser: " + parser.getName() + "\nlogon: " + AppSettings.logon + "\naccepted: " + accepted);
	}
	
	private void check(String strGrammar) {
		Grammar grammar = ParserController.parseGrammar(strGrammar);
		GrammarType grammarType = ParserController.checkGrammarType(grammar);
		GrammarForm grammarForm = ParserController.checkGrammarForm(grammar);
		System.out.println("Grammar: " + strGrammar + "\nlogon: " + AppSettings.logon + "\nGrammarType: " + grammarType + "\nGrammarForm: " + grammarForm);		
	}
	
	private void help() {
		HelpFormatter helper = new HelpFormatter();
		helper.printHelp("GMParser", this.options);
	}

	private void version() {
		System.out.println("GMParser version: 1.0");
	}

	public void quit() {
		AnsiConsole.systemUninstall();
		System.exit(0);
	}
	
	public void printWarning(String message) {
		String warning = "[WARNING] " + message;
		System.out.println(ansi().fg(AppUI.WARNING_COLOR).a(warning).reset());
	}
	
	public void printDebug(String message) {
		if (AppSettings.DEBUG) System.out.println(ansi().fg(AppUI.DEBUG_COLOR).a(message).reset());
	}
	
	public void printTable(String[] header, String[][] data) {
		ASCIITable.getInstance().printTable(header, 0, data, 0);
	}

	public void unrecognizedArguments(String[] args) {
		String unrecognizedArgs = "";
		int uArgsNo = 0;
		for (String arg : args) {
			if (arg.charAt(0) != '-') {
				unrecognizedArgs += arg + " ";
				uArgsNo ++;
			}
		}
		
		String warning = "Unrecognized argument" + ((uArgsNo > 1) ? "s" : "") + ": " + unrecognizedArgs;
		
		this.printWarning(warning);		
	}

}
