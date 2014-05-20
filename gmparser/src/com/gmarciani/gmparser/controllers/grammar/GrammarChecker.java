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

package com.gmarciani.gmparser.controllers.grammar;


import com.gmarciani.gmparser.controllers.ui.Listener;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarForm;
import com.gmarciani.gmparser.models.grammar.Production;
import com.gmarciani.gmparser.models.grammar.ProductionPattern;
import com.gmarciani.gmparser.models.grammar.ProductionPatternBuilder;

public class GrammarChecker {
	
	private static Listener output;	
		
	private static final String PATTERN_CHOMSKY_NORMAL_FORM = "A->BC|a";
	private static final String PATTERN_GREIBACH_NORMAL_FORM = "A->aB*";
	
	public static void setOutput(Listener listener) {
		output = listener;
	}
	
	public static GrammarForm check(Grammar grammar) {
		
		if (checkChomsky(grammar)) 
			return GrammarForm.CHOMSKY_NORMAL_FORM;
		
		if (checkGreibach(grammar)) 
			return GrammarForm.GREIBACH_NORMAL_FORM;		
		
		return GrammarForm.UNKNOWN;
	}	
	
	@SuppressWarnings("static-access")
	public static boolean checkChomsky(Grammar grammar) {
		ProductionPattern pattern = ProductionPatternBuilder
				.hasPatternAsString(PATTERN_CHOMSKY_NORMAL_FORM, "->", "|")
				.withItem('A', grammar.getNonTerminals())
				.withItem('B', grammar.getNonTerminals())
				.withItem('C', grammar.getNonTerminals())
				.withItem('a', grammar.getTerminals())
				.create();
		
		return checkGrammar(grammar, pattern);
	}
	
	@SuppressWarnings("static-access")
	public static boolean checkGreibach(Grammar grammar) {	
		ProductionPattern pattern = ProductionPatternBuilder
				.hasPatternAsString(PATTERN_GREIBACH_NORMAL_FORM, "->", "|")
				.withItem('A', grammar.getNonTerminals())
				.withItem('B', grammar.getNonTerminals())
				.withItem('a', grammar.getTerminals())
				.create();
		
		return checkGrammar(grammar, pattern);
	}	
	
	public static boolean checkGrammar(Grammar grammar, ProductionPattern pattern) {
		for (Production production : grammar.getProductions()) {
			if (!checkProduction(production, pattern))
				return false;
		}
		
		return true;
	}
	
	private static boolean checkProduction(Production production, ProductionPattern pattern) {	
		return (pattern.match(production));
	}	

}
