package com.gmarciani.gmparser.grammar.base;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gmarciani.gmparser.models.commons.set.GSet;
import com.gmarciani.gmparser.models.grammar.validator.Grammar;
import com.gmarciani.gmparser.models.grammar.validator.NonTerminal;
import com.gmarciani.gmparser.models.grammar.validator.Production;
import com.gmarciani.gmparser.models.grammar.validator.Terminal;

public class TestValidation {
	
	private static final String VALID_PRODUCTION = "";
	private static final String NON_VALID_PRODUCTION = "";
	private static final String VALID_GRAMMAR = "";
	private static final String NON_VALID_GRAMMAR = "";
	
	private static final GSet<Character> NON_TERMINALS = new GSet<Character>('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');

	@Test public void validateGrammar() {
		fooGrammar(VALID_GRAMMAR);
		fooGrammar(NON_VALID_GRAMMAR);
	}
	
	@Test public void validateProduction() {
		fooProduction(VALID_PRODUCTION);
		fooProduction(NON_VALID_PRODUCTION);
	}

	
	@Test public void validateSymbols() {
		for (Character symbol : NON_TERMINALS)
			fooTerminalSymbol(symbol);
		
		for (Character symbol : NON_TERMINALS)
			fooNonTerminalSymbol(symbol);
	}
	
	private void fooGrammar(@Grammar String grammar) {
		
	}
	
	private void fooProduction(@Production String production) {
		
	}
	
	private void fooNonTerminalSymbol(@NonTerminal Character symbol) {
		
	}
	
	private void fooTerminalSymbol(@Terminal Character symbol) {
		
	}

}
