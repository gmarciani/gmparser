package com.gmarciani.gmparser.controllers.grammar;

import com.gmarciani.gmparser.controllers.app.App;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.analysis.GrammarAnalysis;

/**
 * The grammar-analysis controller. 
 * 
 * @see {@link App}
 * @see {@link GrammarTransformer}
 * @see {@link WordParser}
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public class GrammarAnalyzer {
	
	private static GrammarAnalyzer instance;

	private GrammarAnalyzer() {}
	
	/**
	 * Returns the {@link GrammarAnalyzer} singleton instance.
	 * 
	 * @return the controller singleton instance.
	 */
	public synchronized static GrammarAnalyzer getInstance() {
		if (instance == null) {
			instance = new GrammarAnalyzer();
		}
		
		return instance;
	}
	
	public GrammarAnalysis analyze(Grammar grammar) {
		return new GrammarAnalysis(grammar);
	}

}
