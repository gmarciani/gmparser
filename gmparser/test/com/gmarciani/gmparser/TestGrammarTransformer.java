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

package com.gmarciani.gmparser;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gmarciani.gmparser.controllers.grammar.GrammarTransformer;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarBuilder;

public class TestGrammarTransformer {
	
	private static final String GRAMMAR_WITH_UNGENERATIVE_SYMBOLS = "S->XY|a;X->a.";
	private static final String GRAMMAR_WITHOUT_UNGENERATIVE_SYMBOLS = "S->a;X->a.";
	
	private static final String GRAMMAR_WITH_UNREACHEABLE_SYMBOLS = "S->XY|a;X->a.";
	private static final String GRAMMAR_WITHOUT_UNREACHEABLE_SYMBOLS = "S->a.";
	
	private static final String GRAMMAR_WITH_USELESS_SYMBOLS = "S->XY|XZ|a;X->a;Y->YZ.";
	private static final String GRAMMAR_WITHOUT_USELESS_SYMBOLS = "S->a.";	
	
	private static final String GRAMMAR_WITH_EPSILON_PRODUCTIONS = "S->A;A" + Grammar.EMPTY + ".";
	private static final String GRAMMAR_WITHOUT_EPSILON_PRODUCTIONS = "S->" + Grammar.EMPTY + ".";
	
	private static final String GRAMMAR_WITH_UNIT_PRODUCTIONS = "S->AS|A;A->B|a;B->A|S|b.";
	private static final String GRAMMAR_WITHOUT_UNIT_PRODUCTIONS = "S->AS|a|b;A->AS|a|b.";	
	
	private static final String GRAMMAR_WITH_LEFT_RECURSION = "S->SA|a;A->a.";
	private static final String GRAMMAR_WITHOUT_LEFT_RECURSION = "S->aZ|a;Z->AZ|a;A->a.";		

	@SuppressWarnings("static-access")
	@Test
	public void testRemoveUngenerativeSymbols() {
		Grammar grammar = GrammarBuilder
				.hasProductions(GRAMMAR_WITH_UNGENERATIVE_SYMBOLS)
				.create();
		
		GrammarTransformer.removeUngenerativeSymbols(grammar);
		
		Grammar shouldBe = GrammarBuilder
				.hasProductions(GRAMMAR_WITHOUT_UNGENERATIVE_SYMBOLS)
				.create();
		
		assertTrue("Incorrect removal of ungenerative symbols", 
				grammar.getProductions().equals(shouldBe.getProductions()));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void removeUnreacheableSymbols() {
		Grammar grammar = GrammarBuilder
				.hasProductions(GRAMMAR_WITH_UNREACHEABLE_SYMBOLS)
				.create();
		
		GrammarTransformer.removeUnreacheableSymbols(grammar);
		
		Grammar shouldBe = GrammarBuilder
				.hasProductions(GRAMMAR_WITHOUT_UNREACHEABLE_SYMBOLS)
				.create();
		
		assertTrue("Incorrect removal of unreacheable symbols", 
				grammar.getProductions().equals(shouldBe.getProductions()));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void removeUselessSymbols() {
		Grammar grammar = GrammarBuilder
				.hasProductions(GRAMMAR_WITH_USELESS_SYMBOLS)
				.create();
		
		GrammarTransformer.removeUselessSymbols(grammar);
		
		Grammar shouldBe = GrammarBuilder
				.hasProductions(GRAMMAR_WITHOUT_USELESS_SYMBOLS)
				.create();
		
		assertTrue("Incorrect removal of useless symbols", 
				grammar.getProductions().equals(shouldBe.getProductions()));
	}
	
	/*
	@SuppressWarnings("static-access")
	@Test
	public void testRemoveEpsilonProductions() {
		Grammar grammar = GrammarBuilder
				.hasProductions(GRAMMAR_WITH_EPSILON_PRODUCTIONS)
				.create();
		
		GrammarTransformer.removeEpsilonProductions(grammar);
		
		Grammar shouldBe = GrammarBuilder
				.hasProductions(GRAMMAR_WITHOUT_EPSILON_PRODUCTIONS)
				.create();
		
		assertTrue("Incorrect removal of epsilon productions", 
				grammar.getProductions().equals(shouldBe.getProductions()));
	}

	@SuppressWarnings("static-access")
	@Test
	public void testRemoveUnitProductions() {
		Grammar grammar = GrammarBuilder
				.hasProductions(GRAMMAR_WITH_UNIT_PRODUCTIONS)
				.create();
		
		GrammarTransformer.removeUnitProductions(grammar);
		
		Grammar shouldBe = GrammarBuilder
				.hasProductions(GRAMMAR_WITHOUT_UNIT_PRODUCTIONS)
				.create();
		
		assertTrue("Incorrect removal of unit productions", 
				grammar.getProductions().equals(shouldBe.getProductions()));
	}	
	
	@SuppressWarnings("static-access")
	@Test
	public void testRemoveLeftRecursion() {
		Grammar grammar = GrammarBuilder
				.hasProductions(GRAMMAR_WITH_LEFT_RECURSION)
				.create();
		
		GrammarTransformer.removeUnitProductions(grammar);
		
		Grammar shouldBe = GrammarBuilder
				.hasProductions(GRAMMAR_WITHOUT_LEFT_RECURSION)
				.create();
		
		assertTrue("Incorrect removal of left recursion", 
				grammar.getProductions().equals(shouldBe.getProductions()));
	}
	*/

}
