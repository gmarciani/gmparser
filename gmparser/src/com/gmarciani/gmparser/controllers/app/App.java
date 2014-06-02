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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
import com.gmarciani.gmparser.models.grammar.analysis.GrammarAnalysis;
import com.gmarciani.gmparser.models.grammar.transformation.GrammarTransformation;
import com.gmarciani.gmparser.models.parser.ParserType;
import com.gmarciani.gmparser.views.app.MainMenu;
import com.gmarciani.gmparser.views.app.ParserMenu;
import com.gmarciani.gmparser.views.app.TransformationMenu;
import com.gmarciani.gmparser.views.menu.Menus;

import static org.fusesource.jansi.Ansi.*;

/**
 * <p>Main user-app interaction controller.<p>
 * <p>It manages the whole app flow.<p>
 *  
 * @see com.gmarciani.gmparser.controllers.grammar.GrammarAnalyzer
 * @see com.gmarciani.gmparser.controllers.grammar.WordParser
 * @see com.gmarciani.gmparser.controllers.app.UiManager
 * @see com.gmarciani.gmparser.controllers.app.Output
 * @see com.gmarciani.gmparser.controllers.app.Preferences
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public final class App {	
	
	private static App instance;
	private GrammarAnalyzer analyzer;
	private GrammarTransformer transformer;
	private WordParser parser;
	
	private Options options;
	private Menus menus;		
	
	private Output output;
	
	private App() {
		this.setupCli();
		this.setupControllers();
	}	

	/**
	 * Returns the app controller singleton instance.
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
	}	
	
	/**
	 * Sets up the GMParser controllers: GrammarAnalyzer, GrammarTransformer and WordParser.
	 */
	private void setupControllers() {
		this.output = Output.getInstance();
		this.analyzer = GrammarAnalyzer.getInstance();
		this.transformer = GrammarTransformer.getInstance();
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
			boolean loop = true;
			while(loop) {
				this.playMenu();
				loop = this.getContinued();
			}				
		}
		
		if (cmd.hasOption("logon")) {
			AppLog.LOGON = true;
		}
		
		if (cmd.hasOption("analyze")) {
			final String vals[] = cmd.getOptionValues("analyze");
			final String grammar = vals[0];
			if (!this.validateGrammar(grammar))				
				return;
			this.analyze(grammar);
		} else if (cmd.hasOption("transform")) {
			final String vals[] = cmd.getOptionValues("transform");			
			final String grammar = vals[0];
			final GrammarTransformation transformation = GrammarTransformation.valueOf(vals[1]);
			if (!this.validateGrammar(grammar))				
				return;
			this.transform(grammar, transformation);
		} else if (cmd.hasOption("parse")) {
			final String vals[] = cmd.getOptionValues("parse");
			final String grammar = vals[0];
			final String word = vals[1];
			final ParserType parserType = ParserType.valueOf(vals[2]);
			if (!this.validateGrammar(grammar))				
				return;
			this.parse(grammar, word, parserType);
		} else if (cmd.hasOption("help")) {
			this.printWelcome();
			this.help();
		} else if (cmd.hasOption("version")) {
			this.version();
		}
		
		this.quit();
	}	

	/**
	 * Plays the main menu.
	 */
	private void playMenu() {		
		int choice = this.menus.run(MainMenu.IDENTIFIER);
		
		if (choice == MainMenu.ANALYZE) {
			String grammar = this.getGrammar();
			if (!this.validateGrammar(grammar))				
				return;
			this.analyze(grammar);
		} else if (choice == MainMenu.TRANSFORM) {
			String grammar = this.getGrammar();
			GrammarTransformation transformation = this.getGrammarTransformation();
			if (!this.validateGrammar(grammar))				
				return;
			this.transform(grammar, transformation);
		} else if (choice == MainMenu.PARSE) {
			String grammar = this.getGrammar();
			String word = this.getWord();
			ParserType parser = this.getParser();
			if (!this.validateGrammar(grammar))				
				return;
			this.parse(grammar, word, parser);
		} else if (choice == MainMenu.HELP) {
			this.help();
		} else if (choice == MainMenu.QUIT) {
			this.quit();
		} else {
			this.getOutput().onWarning("Unrecognized choice\n");
		}
	}	
	
	/**
	 * <p>Analyzes the specified grammar, represented as string.<p> 
	 * <p>This method is activated by the command-line option {@code -analyze}.<p>
	 * 
	 * @param strGrammar grammar to analyze, represented as string.
	 */
	private void analyze(String strGrammar) {
		GrammarAnalysis analysis = this.analyzer.analyze(strGrammar);
		
		this.getOutput().onResult("Here we are! This is your grammar analysis");		
		this.getOutput().onDefault(analysis.toString());
	}
	
	/**
	 * <p>Executes the specified transformation to the specified grammar, represented as string.<p> 
	 * <p>This method is activated by the command-line option {@code -transform}.<p>
	 * 
	 * @param strGrammar grammar to transform, represented as string.
	 * @param transformation target transformation.
	 */
	private void transform(String strGrammar, GrammarTransformation transformation) {
		GrammarAnalysis analysisIn = this.analyzer.analyze(strGrammar);
		analysisIn.setTitle("GRAMMAR ANALYSIS: input grammar");
		
		Grammar grammarOut = this.transformer.transform(strGrammar, transformation);
		GrammarAnalysis analysisOut = this.analyzer.analyze(grammarOut);
		analysisOut.setTitle("GRAMMAR ANALYSIS: output grammar");
		
		this.getOutput().onResult("Here we are! This is your transformation (" + transformation + ")");
		this.getOutput().onDefault(analysisIn.toString());
		this.getOutput().onDefault(analysisOut.toString());
	}

	/**
	 * <p>Parses the specified word by the specified parse, according to the specified grammar, represented as string.<p> 
	 * <p>This method is activated by the command-line option {@code -parse}.<p>
	 * 
	 * @param strGrammar grammar to parse with, represented as string.
	 * @param word word to parse.
	 * @param parser parser type to parse with.
	 */
	private void parse(String strGrammar, String word, ParserType parser) {		
		boolean accepted = this.parser.parse(strGrammar, word, parser);
		
		this.getOutput().onResult("Here we are! This is your parsing result");
		this.getOutput().onResult("" + accepted);
	}	
	
	/**
	 * <p>Shows the GMParser helper.<p> 
	 * <p>This method is activated by the command-line option {@code -help}.<p>
	 */
	private void help() {
		HelpFormatter helper = new HelpFormatter();
		helper.printHelp("gmparser", this.options);
		System.out.print("\n");
		System.out.flush();
	}

	/**
	 * <p>Shows the GMParser version.<p> 
	 * <p>This method is activated by the command-line option {@code -version}.<p>
	 */
	private void version() {
		System.out.println("GMParser version: 1.0");
		System.out.flush();
	}

	/**
	 * Quits the app.
	 */
	public void quit() {
		this.getOutput().onResult("Good bye!");
		UiManager.uninstallAnsiConsole();
		System.exit(0);
	}	
	
	private boolean getContinued() {
		System.out.print("[continue? (y/n)]> ");
		System.out.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
	    try {
	    	input = br.readLine();
		} catch (NumberFormatException | IOException e) {
			
		}
	    
	    System.out.print("\n");
	  
		return (input.equals("y") ? true : false);
	}
	
	private String getGrammar() {
		System.out.print("[grammar]> ");
		System.out.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
	    try {
	    	input = br.readLine();
		} catch (NumberFormatException | IOException e) {
			
		}
	    
	    System.out.print("\n");
	    
		return input;
	}
	
	private String getWord() {
		System.out.print("[word]> ");
		System.out.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
	    try {
	    	input = br.readLine();
		} catch (NumberFormatException | IOException e) {
			
		}
	    
	    System.out.print("\n");
	    
		return input;
	}
	
	private GrammarTransformation getGrammarTransformation() {
		int choice = this.menus.run(TransformationMenu.IDENTIFIER);
		
		if (choice == TransformationMenu.REMOVE_UNGENERATIVE_SYMBOLS) {
			return GrammarTransformation.REMOVE_UNGENERATIVE_SYMBOLS;
		} else if (choice == TransformationMenu.REMOVE_UNREACHEABLES_SYMBOLS) {
			return GrammarTransformation.REMOVE_UNREACHEABLES_SYMBOLS;
		} else if (choice == TransformationMenu.REMOVE_USELESS_SYMBOLS) {
			return GrammarTransformation.REMOVE_USELESS_SYMBOLS;
		} else if (choice == TransformationMenu.REMOVE_EPSILON_PRODUCTIONS) {
			return GrammarTransformation.REMOVE_EPSILON_PRODUCTIONS;
		} else if (choice == TransformationMenu.REMOVE_UNIT_PRODUCTIONS) {
			return GrammarTransformation.REMOVE_UNIT_PRODUCTIONS;
		} else {
			return null;
		}
	}
	
	private ParserType getParser() {
		int choice = this.menus.run(ParserMenu.IDENTIFIER);
		
		if (choice == ParserMenu.CYK) {
			return ParserType.CYK;
		} else if (choice == ParserMenu.LL1) {
			return ParserType.LR1;
		} else {
			return null;
		}
	}
	
	private boolean validateGrammar(String strGrammar) {
		if (!Grammar.validate(strGrammar)) {
			this.getOutput().onWarning("Grammar syntax error\n");
			return false;
		}
		
		return true;		
	}

}
