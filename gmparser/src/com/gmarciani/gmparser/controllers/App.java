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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.gmarciani.gmparser.models.commons.menu.Menus;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.analysis.GrammarAnalysis;
import com.gmarciani.gmparser.models.grammar.transformation.GrammarTransformation;
import com.gmarciani.gmparser.models.parser.ParserType;
import com.gmarciani.gmparser.models.parser.cyk.CYKParser;
import com.gmarciani.gmparser.models.parser.lr.LROneParser;
import com.gmarciani.gmparser.views.UiManager;
import com.gmarciani.gmparser.views.menu.MainMenu;
import com.gmarciani.gmparser.views.menu.ParserMenu;
import com.gmarciani.gmparser.views.menu.TransformationMenu;

import static org.fusesource.jansi.Ansi.*;

/**
 * <p>Main user-app interaction controller.<p>
 * <p>It manages the whole app workflow.<p>
 *  
 * @see com.gmarciani.gmparser.views.UiManager
 * @see com.gmarciani.gmparser.controllers.Output
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public final class App {	
	
	private static App instance;
	
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
		if (instance == null)
			instance = new App();		
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
	 * Sets up the GMParser controllers: Output controller, for single entry-point to output display.
	 */
	private void setupControllers() {
		this.output = Output.getInstance();
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
	 * Shows the GMParser welcome splash screen, based on ASCII art style.
	 */
	public void printWelcome() {
		String welcome = UiManager.getLogo();
		System.out.println(ansi().fg(UiManager.AppUI.LOGO_COLOR).bold().a(welcome).reset());
	}

	/**
	 * <p>The app entry-point method.<p>
	 * <p>This method is activated when no arguments neither options are specified.<p>
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
		} else if (cmd.hasOption("analyze")) {
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
		Grammar grammar = Grammar.generateGrammar(strGrammar);
		this.getOutput().onResult("This is your grammar: " + grammar.toString());		
		GrammarAnalysis analysis = grammar.generateGrammarAnalysis();	
		analysis.setTitle("GRAMMAR ANALYSIS");
		this.getOutput().onResult("Here we are! This is your grammar analysis");		
		this.getOutput().onDefault(analysis.toString());
	}
	
	/**
	 * <p>Executes the specified transformation to the specified grammar, represented as string.<p> 
	 * <p>It also generates a grammar analysis both for input grammar and output grammar.<p>	 * 
	 * <p>Available transformations: 
	 * remove ungenerative symbols (RGS), 
	 * remove unreacheables symbols (RRS), 
	 * remove useless symbols (RUS), 
	 * remove epsilon productions (REP), 
	 * remove unit productions (RUP) 
	 * and generate CHosmky-Normal-Form (CNF).<p>
	 * <p>This method is activated by the command-line option {@code -transform}.<p>
	 * 
	 * @param strGrammar grammar to transform, represented as string.
	 * @param transformation target transformation to run on grammar.
	 */
	private void transform(String strGrammar, GrammarTransformation transformation) {
		Grammar grammar = Grammar.generateGrammar(strGrammar);
		
		this.getOutput().onResult("This is your grammar: " + grammar);
		this.getOutput().onResult("This is your transformation: " + transformation);	
		
		GrammarAnalysis analysisIn = grammar.generateGrammarAnalysis();	
		analysisIn.setTitle("GRAMMAR ANALYSIS: input grammar");		
		
		if (transformation == GrammarTransformation.RGS) {
			grammar.removeUngenerativeSymbols();
		} else if (transformation == GrammarTransformation.RRS) {
			grammar.removeUnreacheableSymbols();
		} else if (transformation == GrammarTransformation.RUS) {
			grammar.removeUselessSymbols();
		} else if (transformation == GrammarTransformation.REP) {
			grammar.removeEpsilonProductions();
		} else if (transformation == GrammarTransformation.RUP) {
			grammar.removeUnitProductions();
		} else if (transformation == GrammarTransformation.CNF) {
			grammar.toChomskyNormalForm();
		}
		
		GrammarAnalysis analysisOut = grammar.generateGrammarAnalysis();
		analysisOut.setTitle("GRAMMAR ANALYSIS: output grammar");
		
		this.getOutput().onResult("Here we are! This is your transformation result");
		this.getOutput().onDefault(analysisIn.toString());
		this.getOutput().onDefault(analysisOut.toString());
	}

	/**
	 * <p>Parses the specified word by the specified parser, according to the specified grammar, represented as a string.<p> 
	 * <p>It shows a grammar analysis for the specified grammar, and a complete parsing session report.<p>
	 * <p>This method is activated by the command-line option {@code -parse}.<p>
	 * 
	 * @param strGrammar grammar to parse with, represented as a string.
	 * @param word word to parse.
	 * @param parserType parser to parse with.
	 */
	private void parse(String strGrammar, String word, ParserType parser) {
		Grammar grammar = Grammar.generateGrammar(strGrammar);
		
		this.getOutput().onResult("This is your grammar: " + grammar);
		this.getOutput().onResult("This is your word: " + word);
		this.getOutput().onResult("This is your parser: " + parser);
		
		if (!grammar.isContextFree() && !grammar.isRegular()) {
			this.getOutput().onWarning("Your grammar is not Context-Free, but " + grammar.getType().getName() + ". Aborting parsing.");
			return;
		}
		
		this.getOutput().onResult("Here we are! This is your parsing session results!");
		
		if (parser.equals(ParserType.CYK))
			this.getOutput().onDefault(CYKParser.parseWithSession(grammar, word).toFormattedParsingSession());
		if (parser.equals(ParserType.LR1))
			this.getOutput().onDefault(LROneParser.parseWithSession(grammar, word).toFormattedParsingSession());
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
	
	/**
	 * <p>Lets the user continue or quit the app workflow after a correct execution or warning/exception.<p>
	 *
	 * @return true if the user wants to continue the workflow; false, otherwise.
	 */
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
	
	/**
	 * <p>Lets the user specify the grammar to analyze, transform, or parse with.<p>
	 * 
	 * @return the input grammar, represented as a string.
	 */
	private String getGrammar() {
		System.out.print("[grammar]> ");
		System.out.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
	    try {
	    	input = br.readLine();
		} catch (NumberFormatException | IOException exc) {
			this.getOutput().onException(exc.getMessage());
		}	    
	    System.out.print("\n");	    
		return input;
	}
	
	/**
	 * <p>Lets the user specify the word to parse.<p>
	 * <p>The empty word is normally specified by an empty input. The user doesn't need to care about any end marker (like $ for LR(1)).<p>
	 * 
	 * @return the word to parse.
	 */
	private String getWord() {
		System.out.print("[word]> ");
		System.out.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
	    try {
	    	input = br.readLine();
		} catch (NumberFormatException | IOException exc) {
			this.getOutput().onException(exc.getMessage());
		}	    
	    System.out.print("\n");	    
		return input;
	}
	
	/**
	 * <p>Lets the user selected the desidered grammar transformation.<p>
	 * <p>Available transformations: 
	 * remove ungenerative symbols (RGS), 
	 * remove unreacheables symbols (RRS), 
	 * remove useless symbols (RUS), 
	 * remove epsilon productions (REP), 
	 * remove unit productions (RUP) 
	 * and generate CHosmky-Normal-Form (CNF).<p>
	 * 
	 * @return the selected grammar transformation.
	 */
	private GrammarTransformation getGrammarTransformation() {
		int choice = this.menus.run(TransformationMenu.IDENTIFIER);
		
		if (choice == TransformationMenu.REMOVE_UNGENERATIVE_SYMBOLS)
			return GrammarTransformation.RGS;
		if (choice == TransformationMenu.REMOVE_UNREACHEABLES_SYMBOLS)
			return GrammarTransformation.RRS;
		if (choice == TransformationMenu.REMOVE_USELESS_SYMBOLS) 
			return GrammarTransformation.RUS;
		if (choice == TransformationMenu.REMOVE_EPSILON_PRODUCTIONS)
			return GrammarTransformation.REP;
		if (choice == TransformationMenu.REMOVE_UNIT_PRODUCTIONS)
			return GrammarTransformation.RUP;
		if (choice == TransformationMenu.GENERATE_CHOMSKY_NORMAL_FORM)
			return GrammarTransformation.CNF;
		return null;
	}
	
	/**
	 * <p>Let the user select the desidered parse type.<p>
	 * <p>Available parsers:
	 * Cock-Younger-Kasami Parser (CYK)
	 * and LR(1) Parser (LR).<p>
	 * 
	 * @return parserType selected parser type.
	 */
	private ParserType getParser() {
		int choice = this.menus.run(ParserMenu.IDENTIFIER);
		
		if (choice == ParserMenu.CYK) {
			return ParserType.CYK;
		} else if (choice == ParserMenu.LR1) {
			return ParserType.LR1;
		} else {
			return null;
		}
	}
	
	/**
	 * <p>Validates the input grammar, represented as a string.<p>
	 * 
	 * @param strGrammar input grammar, represented as a string.
	 * 
	 * @return true if the string correctly represents a grammar; false, otherwise.
	 */
	private boolean validateGrammar(String strGrammar) {
		if (!Grammar.validate(strGrammar)) {
			this.getOutput().onWarning("Grammar syntax error\n");
			return false;
		}		
		return true;		
	}

}
