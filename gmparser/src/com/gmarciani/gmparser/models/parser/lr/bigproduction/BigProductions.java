package com.gmarciani.gmparser.models.parser.lr.bigproduction;

import com.gmarciani.gmparser.models.commons.set.GSet;
import com.gmarciani.gmparser.models.grammar.Grammar;

public class BigProductions extends GSet<BigProductionGraph> {

	private static final long serialVersionUID = -5923668264014276090L;

	public BigProductions() {
		// TODO Auto-generated constructor stub
	}
	
	public static BigProductions generate(Grammar grammar) {
		BigProductions bigProductions = new BigProductions();
		
		return bigProductions;
	}

}
