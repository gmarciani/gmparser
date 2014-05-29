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
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.production.Production;

public class GrammarCheckerController {
	
	@SuppressWarnings("unused")
	private static Listener output;	
			
	public static void setOutput(Listener listener) {
		output = listener;
	}
	
	public static GrammarForm check(Grammar grammar) {
		
		if (isChomsky(grammar)) 
			return GrammarForm.CHOMSKY_NORMAL_FORM;
		
		if (isGreibach(grammar)) 
			return GrammarForm.GREIBACH_NORMAL_FORM;		
		
		return GrammarForm.UNKNOWN;
	}	
	
	public static boolean isChomsky(Grammar grammar) {
		for (Production production : grammar.getProductions()) {
			if (!isChomsky(production, grammar.getTerminals(), grammar.getNonTerminals()))
				return false;
		}
		
		return true;
	}
	
	//"A->BC|a."
	private static boolean isChomsky(Production production, Alphabet terminals, Alphabet nonTerminals) {
		if (production.isLeftWithin(nonTerminals) && production.getLeft().getSize() == 1
				&& production.isRightWithin(nonTerminals) && production.getRight().getSize() == 2)
			return true;
		
		if (production.isLeftWithin(nonTerminals) && production.getLeft().getSize() == 1
				&& production.isRightWithin(terminals) && production.getRight().getSize() == 1)
			return true;
		
		//aggiungere S->empty e S non sta nel rhs di alcuna produzione
		
		return false;
	}

	public static boolean isGreibach(Grammar grammar) {	
		for (Production production : grammar.getProductions()) {
			if (!isGreibach(production, grammar.getTerminals(), grammar.getNonTerminals()))
				return false;
		}
		
		return true;
	}

	//"A->aB*."
	private static boolean isGreibach(Production production, Alphabet terminals, Alphabet nonTerminals) {
		String regex = "^[";
		for (Character terminal : terminals) {
			regex += terminal;
		}
		regex += "]";
		regex += "[";
		for (Character nonTerminal : nonTerminals) {
			regex += nonTerminal;
		}
		regex += "]*$";
		
		if (production.isLeftWithin(nonTerminals) && production.getLeft().getSize() == 1
				&& production.getRight().matches(regex))
			return true;
		
		//aggiungere S->empty
		
		return false;
	}	

}