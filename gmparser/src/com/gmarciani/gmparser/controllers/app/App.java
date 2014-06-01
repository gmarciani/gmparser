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

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.gmarciani.gmparser.controllers.app.Preferences.*;
import com.gmarciani.gmparser.controllers.grammar.GrammarAnalyzer;
import com.gmarciani.gmparser.controllers.grammar.GrammarTransformer;
import com.gmarciani.gmparser.controllers.grammar.WordParser;
import com.gmarciani.gmparser.controllers.io.IOController;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarBuilder;
import com.gmarciani.gmparser.models.grammar.analysis.GrammarAnalysis;
import com.gmarciani.gmparser.models.parser.ParserType;
import com.gmarciani.gmparser.views.interaction.Interactions;
import com.gmarciani.gmparser.views.menu.Menus;

import static org.fusesource.jansi.Ansi.*;

/**
 * The main user-app interaction controller.
 *  
 * @see {@link GrammarAnalyzer}
 * @see {@link GrammarTransformer}
 * @see {@link WordParser}
 * @see {@link UiManager}
 * @see {@link Output}
 * @see {@link Preferences}
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public final class App {	
	
	private static App instance;
	private GrammarAnalyzer analyzer;
	private WordParser parser;
	
	private Options options;
	private Menus menus;	
	private Interactions interactions;	
	
	private Output output;
	
	private App() {
		this.setupCli();
		this.setupControllers();
	}	

	/**
	 * Returns the {@link App} singleton instance.
	 * 
	 * @return the controller singleton instance.
	 */
	public synchronized static App getInstance() {
		if (instance == null) {
			instance = new App();
		}
		
		return instance;
	}	
	
	/**
	 * Sets up the GMParser command-line interface environment.
	 */
	private void setupCli() {
		UiManager.installAnsiConsole();
		this.options = UiManager.buildCommandLineOptions();
		this.menus = UiManager.buildMenus();
		this.interactions = UiManager.buildInteractions();
	}	
	
	/**
	 * Sets up the GMParser controllers: GrammarAnalyzer, GrammarTransformer and WordParser.
	 */
	private void setupControllers() {
		this.output = new Output();
		this.analyzer = GrammarAnalyzer.getInstance();
		this.parser = WordParser.getInstance();
	}
	
	/**
	 * Returns the app output interface.
	 * 
	 * @return the output interface.
	 */
	public Output getOutput() {
		return this.output;
	}
	
	/**
	 * Shows the GMParser welcome splash screen.
	 */
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

	/**
	 * The app entry-method.
	 * 
	 * @param args command-line arguments.
	 * @throws ParseException
	 */
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
			AppLog.logon = true;
		}
		
		if (cmd.hasOption("analyze")) {
			final String vals[] = cmd.getOptionValues("parse");
			final String grammar = vals[0];
			this.analyze(grammar);
		} else if (cmd.hasOption("parse")) {
			final String vals[] = cmd.getOptionValues("parse");
			final String grammar = vals[0];
			final String word = vals[1];
			final ParserType parserType = ParserType.valueOf(vals[2]);
			this.parse(grammar, word, parserType);
		} else if (cmd.hasOption("help")) {
			this.help();
		} else if (cmd.hasOption("version")) {
			this.version();
		}
	}	

	/**
	 * 
	 */
	private void playMenu() {		
		int choice = this.menus.run(AppMenus.MainMenu.IDENTIFIER);
		
		switch(choice) {
		case AppMenus.MainMenu.ANALYZE:
			String grammarToAnalyze = this.interactions.run(AppInteractions.Grammar.IDENTIFIER);
			AppLog.logon = (this.menus.run(AppMenus.LogonMenu.IDENTIFIER) == AppMenus.LogonMenu.LOGON) ? true : false;	
			this.analyze(grammarToAnalyze);
			this.playMenu();
			
		case AppMenus.MainMenu.PARSE:
			String grammarToParseWith = this.interactions.run(AppInteractions.Grammar.IDENTIFIER);
			String stringToParse = this.interactions.run(AppInteractions.InputString.IDENTIFIER);			
			ParserType parser = null;
			int pChoice = this.menus.run(AppMenus.ParseMenu.IDENTIFIER);
			switch (pChoice) {
			case AppMenus.ParseMenu.CYK:
				parser = ParserType.CYK;
			case AppMenus.ParseMenu.LL1:
				parser = ParserType.LR1;
			default:
				parser = ParserType.CYK;
				break;
			}			
			AppLog.logon = (this.menus.run(AppMenus.LogonMenu.IDENTIFIER) == AppMenus.LogonMenu.LOGON) ? true : false;			
			this.parse(grammarToParseWith, stringToParse, parser);
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
	
	/**
	 * @param grammar
	 */
	@SuppressWarnings("static-access")
	private void analyze(String strGrammar) {
		Grammar grammar = GrammarBuilder.hasProductions(strGrammar)
				.withAxiom(Grammar.AXIOM)
				.withEmpty(Grammar.EMPTY)
				.create();
		
		GrammarAnalysis analysis = this.analyzer.analyze(grammar);
		
		this.getOutput().onResult(analysis.toString());
	}

	/**
	 * @param strGrammar
	 * @param word
	 * @param parser
	 */
	@SuppressWarnings("static-access")
	private void parse(String strGrammar, String word, ParserType parser) {
		Grammar grammar = GrammarBuilder.hasProductions(strGrammar)
				.withAxiom(Grammar.AXIOM)
				.withEmpty(Grammar.EMPTY)
				.create();
		
		boolean accepted = this.parser.parse(grammar, word, parser);
		
		this.getOutput().onResult("" + accepted);
	}	
	
	/**
	 * Shows the GMParser helper. This method is activated by the command-line option {@code -help}.
	 */
	private void help() {
		HelpFormatter helper = new HelpFormatter();
		helper.printHelp("GMParser", this.options);
	}

	/**
	 * Shows the GMParser version. This method is activated by the command-line option {@code -version}.
	 */
	private void version() {
		System.out.println("GMParser version: 1.0");
	}

	/**
	 * Quits the app.
	 */
	public void quit() {
		UiManager.uninstallAnsiConsole();
		System.exit(0);
	}	

}
