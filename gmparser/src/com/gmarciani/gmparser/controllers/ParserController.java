package com.gmarciani.gmparser.controllers;

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarForm;
import com.gmarciani.gmparser.models.grammar.GrammarType;
import com.gmarciani.gmparser.models.parser.ParserType;

public class ParserController {
	
	public static Grammar parseGrammar(String strGrammar) {
		return new Grammar();
	}

	public static boolean parse(Grammar grammar, String string, ParserType parser) {
		return false;
	}

	public static GrammarType checkGrammarType(Grammar grammar) {
		return GrammarType.UNKNOWN;
	}

	public static GrammarForm checkGrammarForm(Grammar grammar) {
		return GrammarForm.UNKNOWN;
	}

}
