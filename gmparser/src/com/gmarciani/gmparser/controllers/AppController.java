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
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.gmarciani.gmparser.controllers.App.AppInteractions;
import com.gmarciani.gmparser.controllers.App.AppMenus;
import com.gmarciani.gmparser.controllers.App.AppSettings;
import com.gmarciani.gmparser.controllers.App.AppUI;
import com.gmarciani.gmparser.controllers.grammar.GrammarChecker;
import com.gmarciani.gmparser.controllers.grammar.GrammarTransformer;
import com.gmarciani.gmparser.controllers.grammar.Parser;
import com.gmarciani.gmparser.controllers.io.IOController;
import com.gmarciani.gmparser.controllers.ui.AppOutput;
import com.gmarciani.gmparser.controllers.ui.Listener;
import com.gmarciani.gmparser.controllers.ui.UiManager;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarBuilder;
import com.gmarciani.gmparser.models.grammar.GrammarForm;
import com.gmarciani.gmparser.models.parser.ParserType;
import com.gmarciani.gmparser.views.interaction.Interactions;
import com.gmarciani.gmparser.views.menu.Menus;

import static org.fusesource.jansi.Ansi.*;

public class AppController {	
	
	private static AppController instance;
	private Options options;
	private Menus menus;	
	private Interactions interactions;	
	
	private Listener output;
	
	private AppController() {
		this.setupCli();
		this.setupListeners();
	}	

	public static AppController getInstance() {
		if (instance == null) {
			instance = new AppController();
		}
		
		return instance;
	}	
	
	//SETUP
	
	private void setupCli() {
		UiManager.installAnsiConsole();
		this.options = UiManager.buildCommandLineOptions();
		this.menus = UiManager.buildMenus();
		this.interactions = UiManager.buildInteractions();
	}	
	
	private void setupListeners() {
		this.output = new AppOutput();
		Parser.setOutput(this.output);
		GrammarTransformer.setOutput(output);
		GrammarChecker.setOutput(output);
	}
	
	public Listener getOutput() {
		return this.output;
	}
	
	public void printWelcome() {
		String welcome = AppUI.LOGO_PLACEHOLDER;
		try {
			welcome = IOController.getFileAsString(AppUI.FILE_LOGO);
			System.out.println(ansi().fg(AppUI.LOGO_COLOR).bold().a(welcome).reset());
		} catch (IOException exc) {
			this.output.onException(exc.getMessage());
			this.playMenu();
		}		
	}

	public void play(String[] args) throws ParseException {
		CommandLineParser cmdParser = new GnuParser();
		CommandLine cmd = cmdParser.parse(this.options, args);
		
		String unrecognizedArguments[] = cmd.getArgs();
		
		if (unrecognizedArguments.length != 0) {
			this.getOutput().onUnrecognizedArguments(unrecognizedArguments);
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
		} else if (cmd.hasOption("transform")) {
			String vals[] = cmd.getOptionValues("transform");
			final GrammarForm grammarForm = GrammarForm.valueOf(vals[0]);
			final String grammar = vals[1];
			this.transform(grammar, grammarForm);
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
		
		switch(choice) {
		case AppMenus.MainMenu.PARSE:
			String grammarToParseWith = this.interactions.run(AppInteractions.Grammar.IDENTIFIER);
			String stringToParse = this.interactions.run(AppInteractions.InputString.IDENTIFIER);			
			ParserType parser = null;
			int pChoice = this.menus.run(AppMenus.ParseMenu.IDENTIFIER);
			switch (pChoice) {
			case AppMenus.ParseMenu.CYK:
				parser = ParserType.CYK;
			case AppMenus.ParseMenu.LL1:
				parser = ParserType.LL1;
			default:
				parser = ParserType.CYK;
				break;
			}			
			AppSettings.logon = (this.menus.run(AppMenus.LogonMenu.IDENTIFIER) == AppMenus.LogonMenu.LOGON) ? true : false;			
			this.parse(grammarToParseWith, stringToParse, parser);
			this.playMenu();
			
		case AppMenus.MainMenu.TRANSFORM:
			String grammarToTransform = this.interactions.run(AppInteractions.Grammar.IDENTIFIER);
			GrammarForm grammarForm = null;
			int gChoice = this.menus.run(AppMenus.TransformMenu.IDENTIFIER);
			switch(gChoice) {
			case AppMenus.TransformMenu.CHOMSKY_NORMAL_FORM:
				grammarForm = GrammarForm.CHOMSKY_NORMAL_FORM;
			case AppMenus.TransformMenu.GREIBACH_NORMAL_FORM:
				grammarForm = GrammarForm.GREIBACH_NORMAL_FORM;
			default:
				grammarForm = GrammarForm.CHOMSKY_NORMAL_FORM;
				break;
			}
			AppSettings.logon = (this.menus.run(AppMenus.LogonMenu.IDENTIFIER) == AppMenus.LogonMenu.LOGON) ? true : false;
			this.transform(grammarToTransform, grammarForm);
			this.playMenu();
			
		case AppMenus.MainMenu.CHECK:
			String grammarToCheck = this.interactions.run(AppInteractions.Grammar.IDENTIFIER);
			AppSettings.logon = (this.menus.run(AppMenus.LogonMenu.IDENTIFIER) == AppMenus.LogonMenu.LOGON) ? true : false;
			this.check(grammarToCheck);
			this.playMenu();
			
		case AppMenus.MainMenu.HELP:
			this.help();
			this.playMenu();
			
		case AppMenus.MainMenu.QUIT:
			this.quit();
			
		default:
			this.output.onWarning("Unrecognized choice");
			this.playMenu();
			break;
		}
	}	

	@SuppressWarnings("static-access")
	private void parse(String strGrammar, String string, ParserType parser) {
		Grammar grammar = GrammarBuilder.hasProductionsAsString(strGrammar, "->", "|", ";")
										.withAxiom('S')
										.withEmpty("e")
										.create();
		
		//parsing
		boolean accepted = Parser.parse(grammar, string, parser);
		
		this.getOutput().onDebug("Grammar in: " + strGrammar + "\nString: " + string + "\nParser: " + parser.getName() + "\nlogon: " + AppSettings.logon + "\nGrammar out: " + grammar);
	}
	
	@SuppressWarnings("static-access")
	private void transform(String strGrammar, GrammarForm grammarForm) {
		Grammar grammar = GrammarBuilder.hasProductionsAsString(strGrammar, "->", "|", ";")
										.withAxiom('S')
										.withEmpty("e")
										.create();
		
		//trasformation
		Grammar transformedGrammar = GrammarTransformer.transform(grammar, grammarForm);
		
		this.getOutput().onDebug("Grammar in: " + strGrammar + "\nGrammar form: " + grammarForm + "\nlogon: " + AppSettings.logon + "\nGrammar out: " + grammar);
	}
	
	@SuppressWarnings("static-access")
	private void check(String strGrammar) {
		Grammar grammar = GrammarBuilder.hasProductionsAsString(strGrammar, "->", "|", ";")
										.withAxiom('S')
										.withEmpty("e")
										.create();
		
		//checking
		GrammarForm grammarForm = GrammarChecker.check(grammar);
		
		this.getOutput().onDebug("Grammar in: " + strGrammar + "\nlogon: " + AppSettings.logon + "\nGrammar out: " + grammar);		
	}
	
	private void help() {
		HelpFormatter helper = new HelpFormatter();
		helper.printHelp("GMParser", this.options);
	}

	private void version() {
		System.out.println("GMParser version: 1.0");
	}

	public void quit() {
		UiManager.uninstallAnsiConsole();
		System.exit(0);
	}	

}
