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

package com.gmarciani.gmparser.grammar;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gmarciani.gmparser.controllers.grammar.GrammarChecker;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarBuilder;

public class TestGrammarForm {
	
	private static final String GRAMMAR_CHOMSKY = "E->EA|TB|LC|a;A->PT;P->+;T->TB|LC|a;B->MF;M->x;F->LC|a;C->ER;L->(;R->).";
	private static final String GRAMMAR_NOT_CHOMSKY = "E->E+T|T;T->TxF|F;F->(E)|a.";
	
	private static final String GRAMMAR_GREIBACH = "S->aA|a;A->aA|a|bS|b.";
	private static final String GRAMMAR_NOT_GREIBACH = "S->SA|A|a;A->aA|Aab.";
	
		
	@SuppressWarnings("static-access")
	@Test
	public void testChomskyNormalForm() {
		Grammar grammarChomsky = GrammarBuilder.hasProductions(GRAMMAR_CHOMSKY)
				.withAxiom('E')
				.withEmpty(Grammar.EMPTY)
				.create();
		
		Grammar grammarNotChomsky = GrammarBuilder.hasProductions(GRAMMAR_NOT_CHOMSKY)
				.withAxiom('E')
				.withEmpty(Grammar.EMPTY)
				.create();
		/*
		System.out.println("CHOMSKY: " + grammarChomsky);
		System.out.println("NOT CHOMSKY: " + grammarNotChomsky);*/
		
		assertTrue("Uncorrect Chomsky Normal Form recognition (should be recognized)", 
				(GrammarChecker.isChomsky(grammarChomsky)));
		
		assertFalse("Uncorrect Chomsky Normal Form recognition (should not be recognized)", 
				(GrammarChecker.isChomsky(grammarNotChomsky)));
	}

	@SuppressWarnings("static-access")
	@Test
	public void testGreibachNormalForm() {
		Grammar grammarGreibach = GrammarBuilder.hasProductions(GRAMMAR_GREIBACH)
				.withAxiom('S')
				.withEmpty(Grammar.EMPTY)
				.create();
		
		Grammar grammarNotGreibach = GrammarBuilder.hasProductions(GRAMMAR_NOT_GREIBACH)
				.withAxiom('S')
				.withEmpty(Grammar.EMPTY)
				.create();
		/*
		System.out.println("GREIBACH: " + grammarGreibach);
		System.out.println("NOT GREIBACH: " + grammarNotGreibach);*/
		
		assertTrue("Uncorrect Greibach Normal Form recognition (should be recognized)", 
				(GrammarChecker.isGreibach(grammarGreibach)));
		
		assertFalse("Uncorrect Greibach Normal Form recognition (should not be recognized)", 
				(GrammarChecker.isGreibach(grammarNotGreibach)));
	}	

}
