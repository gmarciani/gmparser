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

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarBuilder;
import com.gmarciani.gmparser.models.grammar.analysis.GrammarAnalysis;

/**
 * The grammar-analysis controller. 
 * 
 * @see com.gmarciani.gmparser.controllers.app.App
 * @see com.gmarciani.gmparser.controllers.grammar.GrammarTransformer
 * @see com.gmarciani.gmparser.controllers.grammar.WordParser
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public class GrammarAnalyzer {
	
	private static GrammarAnalyzer instance;

	private GrammarAnalyzer() {}
	
	/**
	 * Returns the grammar analyzer controller singleton instance.
	 * 
	 * @return the controller singleton instance.
	 */
	public synchronized static GrammarAnalyzer getInstance() {
		if (instance == null) {
			instance = new GrammarAnalyzer();
		}
		
		return instance;
	}
	
	/**
	 * Analyzes the target grammar, represented as string.
	 * 
	 * @param strGrammar string representation of the grammar to analyze.
	 * @return grammar analysis report.
	 */
	@SuppressWarnings("static-access")
	public GrammarAnalysis analyze(String strGrammar) {
		Grammar grammar = GrammarBuilder.hasProductions(strGrammar)
				.withAxiom(Grammar.AXIOM)
				.withEmpty(Grammar.EMPTY)
				.create();
		
		return this.analyze(grammar);
	}
	
	/**
	 * Analyzes the target grammar.
	 * 
	 * @param grammar grammar to analyze.
	 * @return grammar analysis report.
	 */
	public GrammarAnalysis analyze(Grammar grammar) {
		return new GrammarAnalysis(grammar);
	}	

}
