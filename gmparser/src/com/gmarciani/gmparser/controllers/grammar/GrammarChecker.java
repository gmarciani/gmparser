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

import java.util.LinkedHashSet;
import java.util.Set;

import com.gmarciani.gmparser.controllers.ui.Listener;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarForm;

public class GrammarChecker {
	
	private static Listener output;	
		
	private static final String PATTERN_CHOMSKY_NORMAL_FORM = "A->BC|a";
	private static final String PATTERN_GREIBACH_NORMAL_FORM = "A->aB*";
	
	public static void setOutput(Listener listener) {
		output = listener;
	}
	
	public static GrammarForm check(Grammar grammar) {
		output.onDebug("GrammarChecker.check()");
		
		if (checkChomsky(grammar)) 
			return GrammarForm.CHOMSKY_NORMAL_FORM;
		
		if (checkGreibach(grammar)) 
			return GrammarForm.GREIBACH_NORMAL_FORM;		
		
		return GrammarForm.UNKNOWN;
	}	
	
	public static boolean checkChomsky(Grammar grammar) {
		output.onDebug("GrammarChecker.checkChomsky()");
		return checkGrammar(grammar, PATTERN_CHOMSKY_NORMAL_FORM);
	}
	
	public static boolean checkGreibach(Grammar grammar) {		
		output.onDebug("GrammarChecker.checkGreibach()");
		return checkGrammar(grammar, PATTERN_GREIBACH_NORMAL_FORM);
	}	
	
	public static boolean checkGrammar(Grammar grammar, String productionRegExp) {
		output.onDebug("GrammarChecker.check()");
		
		return true;
	}
	
	public static boolean checkProduction(Grammar grammar, Character nonTerminal, String sentential, String productionRegExp) {
		output.onDebug("GrammarChecker.checkProduction()");
		Set<String> prods = parseProductionRegExp(productionRegExp);

		return true;
	}
	
	public static Set<String> parseProductionRegExp(String productionRegExp) {
		output.onDebug("GrammarChecker.parseProductionRegExp()");
		Set<String> setProds = new LinkedHashSet<String>();
		
		String prodsDiff[] = productionRegExp.split(";");
		for (String prodsSame : prodsDiff) {
			String prodsSameSplit[] = prodsSame.split("->");
			Character nT = prodsSameSplit[0].toCharArray()[0];
			String prods[] = prodsSameSplit[1].split("\\|");
			for (String prod : prods) {
				setProds.add(nT + "->" + prod);
			}			
		}
		
		return setProds;
	}

	

}
