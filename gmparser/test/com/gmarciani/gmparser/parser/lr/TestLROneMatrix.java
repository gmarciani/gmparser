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
	
	private static final String GRAMMAR_ONE = "S->A;A->CC;C->cC|d.";
	private static final String GRAMMAR_TWO = "S->A;A->BA|"+Grammar.EPSILON+";B->aB|b.";

	@Test public void generateOne() {
		Grammar grammar = GrammarFactory.getInstance()
				.hasProductions(GRAMMAR_ONE)
				.withEpsilon(Grammar.EPSILON)
				.create();
		System.out.println("#GRAMMAR: " + grammar);
		BigProductionGraph bigProduction = new BigProductionGraph(grammar);
		System.out.println(bigProduction.toExtendedFormattedAutomaton());
		FiniteAutomaton<Item> automaton = bigProduction.powersetConstruction();
		System.out.println(automaton.toExtendedFormattedAutomaton());
		LROneMatrix matrix = new LROneMatrix(grammar, automaton);
		
		System.out.println(matrix.toExtendedFormattedMatrix());
	}
	
	@Test public void generateTwo() {
		Grammar grammar = GrammarFactory.getInstance()
				.hasProductions(GRAMMAR_TWO)
				.withEpsilon(Grammar.EPSILON)
				.create();
		System.out.println("#GRAMMAR: " + grammar);
		BigProductionGraph bigProduction = new BigProductionGraph(grammar);
		System.out.println(bigProduction.toExtendedFormattedAutomaton());
		FiniteAutomaton<Item> automaton = bigProduction.powersetConstruction();
		System.out.println(automaton.toExtendedFormattedAutomaton());
		LROneMatrix matrix = new LROneMatrix(grammar, automaton);
		
		System.out.println(matrix.toExtendedFormattedMatrix());
	}

}
