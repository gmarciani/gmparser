package com.gmarciani.gmparser.controllers;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarForm;
import com.gmarciani.gmparser.models.grammar.GrammarType;
import com.gmarciani.gmparser.models.parser.ParserType;

public class AppController {
	
	private static final boolean DEBUG = false;
	private static boolean logon = false;
	
	private static AppController instance;
	private Options options;
	
	private static final String DESCRIPTION_VERSION = "GMParser version.";
	private static final String DESCRIPTION_HELP = "GMParser helper.";
	private static final String DESCRIPTION_LOGON = "Turn logging on.";
	private static final String DESCRIPTION_PARSE = "Parse the STRING on GRAMMAR with PARSER.";
	private static final String DESCRIPTION_CHECK = "Check the GRAMMAR type and form.";
	
	private AppController() {
		buildOptions();
	}
	
	public static AppController getInstance() {
		if (instance == null) {
			instance = new AppController();
		}
		
		return instance;
	}
	
	@SuppressWarnings("static-access")
	private void buildOptions() {			
		
		Option parse = OptionBuilder.withLongOpt("parse")
				.withDescription(DESCRIPTION_PARSE)
				.hasArgs(3)
				.withValueSeparator(' ')
				.withArgName("GRAMMAR STRING PARSER")
				.create("p");
		
		Option check = OptionBuilder.withLongOpt("check")
				.withDescription(DESCRIPTION_CHECK)
				.hasArg()
				.withArgName("GRAMMAR")
				.create("c");
		
		Option help = OptionBuilder.withLongOpt("help")
				.withDescription(DESCRIPTION_HELP)
				.create("h");
		
		Option version = OptionBuilder.withLongOpt("version")
				.withDescription(DESCRIPTION_VERSION)
				.create("v");
		
		Option logon = OptionBuilder.withLongOpt("logon")
				.withDescription(DESCRIPTION_LOGON)
				.create("l");
		
		OptionGroup optionGroup = new OptionGroup();
		optionGroup.addOption(parse);
		optionGroup.addOption(check);
		optionGroup.addOption(help);
		optionGroup.addOption(version);
		
		this.options = new Options();	
		this.options.addOptionGroup(optionGroup);
		this.options.addOption(logon);
	}
	
	private static final String LOGO = "";

	public void printWelcome() {
		System.out.println(LOGO);
	}	

	public void play(String[] args) throws ParseException {
		CommandLineParser cmdParser = new GnuParser();
		CommandLine cmd = cmdParser.parse(this.options, args);
		
		if(cmd.hasOption("logon")) {
			logon = true;
		}
		
		if (cmd.hasOption("parse")) {
			String vals[] = cmd.getOptionValues("parse");
			final String grammar = vals[0];
			final String string = vals[1];
			final ParserType parser = ParserType.valueOf(vals[2]);
			parse(grammar, string, parser);
		} else if (cmd.hasOption("check")) {
			String vals[] = cmd.getOptionValues("check");
			final String grammar = vals[0];
			check(grammar);
		} else if (cmd.hasOption("help")) {
			help();
		} else if (cmd.hasOption("version")) {
			version();
		} else {
			unrecognizedCommands();
		}
	}	

	private void parse(String strGrammar, String string, ParserType parser) {
		Grammar grammar = ParserController.parseGrammar(strGrammar);
		boolean accepted = ParserController.parse(grammar, string, parser);
		System.out.println("Grammar: " + strGrammar + "\nString: " + string + "\nParser: " + parser.getName() + "\nlogon: " + logon + "\naccepted: " + accepted);
	}
	
	private void check(String strGrammar) {
		Grammar grammar = ParserController.parseGrammar(strGrammar);
		GrammarType grammarType = ParserController.checkGrammarType(grammar);
		GrammarForm grammarForm = ParserController.checkGrammarForm(grammar);
		System.out.println("Grammar: " + strGrammar + "\nlogon: " + logon + "\nGrammarType: " + grammarType + "\nGrammarForm: " + grammarForm);		
	}
	
	private void help() {
		HelpFormatter helper = new HelpFormatter();
		helper.printHelp("GMParser", this.options);
	}

	private void version() {
		System.out.println("GMParser version: 1.0");
	}

	public void quit() {
		System.exit(0);
	}

	private void unrecognizedCommands() {
		printWarning("Unrecognized commands");
	}	
	
	public void printWarning(String message) {
		System.out.println("[WARNING] " + message);
	}
	
	public void printException(ParseException exc) {
		System.out.println("[ERROR] " + exc.getMessage());
	}
	
	public void printDebug(String message) {
		if (DEBUG) System.out.println(message);
	}

}
