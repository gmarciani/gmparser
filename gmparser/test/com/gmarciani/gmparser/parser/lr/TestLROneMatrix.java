package com.gmarciani.gmparser.parser.lr;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gmarciani.gmparser.models.automaton.finite.FiniteAutomaton;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarFactory;
import com.gmarciani.gmparser.models.parser.lr.bigproduction.BigProductionGraph;
import com.gmarciani.gmparser.models.parser.lr.bigproduction.Item;
import com.gmarciani.gmparser.models.parser.lr.matrix.LROneMatrix;

public class TestLROneMatrix {
	
	private static final String GRAMMAR = "S->A;A->CC;C->cC|d.";

	@Test public void generate() {
		Grammar grammar = GrammarFactory.getInstance()
				.hasProductions(GRAMMAR)
				.withAxiom(Grammar.AXIOM)
				.withEpsilon(Grammar.EPSILON)
				.create();
		BigProductionGraph bigProduction = new BigProductionGraph(grammar);
		FiniteAutomaton<Item> automaton = bigProduction.powersetConstruction();
		System.out.println(automaton.toExtendedFormattedAutomaton());
		LROneMatrix matrix = new LROneMatrix(grammar, automaton);
		
		System.out.println(matrix.toFormattedFunction());
	}

}
