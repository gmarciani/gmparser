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

package com.gmarciani.gmparser.grammar.analysis;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarFactory;

public class TestGrammarNormalForm {
	
	private static final String GRAMMAR_CHOMSKY = "E->EA|TB|LC|a;A->PT;P->+;T->TB|LC|a;B->MF;M->x;F->LC|a;C->ER;L->(;R->).";
	private static final String GRAMMAR_NOT_CHOMSKY = "E->E+T|T;T->TxF|F;F->(E)|a.";
	
	private static final String GRAMMAR_GREIBACH = "S->aA|a;A->aA|a|bS|b.";
	private static final String GRAMMAR_NOT_GREIBACH = "S->SA|A|a;A->aA|Aab.";
	
		
	@Test public void isChomskyNormalForm() {
		Grammar grammarChomsky = GrammarFactory.getInstance()
				.hasProductions(GRAMMAR_CHOMSKY)
				.withAxiom('E')
				.withEpsilon(Grammar.EPSILON)
				.create();
		
		Grammar grammarNotChomsky = GrammarFactory.getInstance()
				.hasProductions(GRAMMAR_NOT_CHOMSKY)
				.withAxiom('E')
				.withEpsilon(Grammar.EPSILON)
				.create();
		/*
		System.out.println("CHOMSKY: " + grammarChomsky);
		System.out.println("NOT CHOMSKY: " + grammarNotChomsky);*/
		
		assertTrue("Uncorrect Chomsky Normal Form recognition (should be recognized)", 
				(grammarChomsky.isChomskyNormalForm()));
		
		assertFalse("Uncorrect Chomsky Normal Form recognition (should not be recognized)", 
				(grammarNotChomsky.isChomskyNormalForm()));
	}

	@Test public void isGreibachNormalForm() {
		Grammar grammarGreibach = GrammarFactory.getInstance()
				.hasProductions(GRAMMAR_GREIBACH)
				.withAxiom('S')
				.withEpsilon(Grammar.EPSILON)
				.create();
		
		Grammar grammarNotGreibach = GrammarFactory.getInstance()
				.hasProductions(GRAMMAR_NOT_GREIBACH)
				.withAxiom('S')
				.withEpsilon(Grammar.EPSILON)
				.create();
		/*
		System.out.println("GREIBACH: " + grammarGreibach);
		System.out.println("NOT GREIBACH: " + grammarNotGreibach);*/
		
		assertTrue("Uncorrect Greibach Normal Form recognition (should be recognized)", 
				(grammarGreibach.isGreibachNormalForm()));
		
		assertFalse("Uncorrect Greibach Normal Form recognition (should not be recognized)", 
				(grammarNotGreibach.isGreibachNormalForm()));
	}	

}
