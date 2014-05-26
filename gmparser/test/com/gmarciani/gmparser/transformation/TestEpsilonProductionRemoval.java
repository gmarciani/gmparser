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

package com.gmarciani.gmparser.transformation;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gmarciani.gmparser.controllers.grammar.GrammarTransformer;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarBuilder;

public class TestEpsilonProductionRemoval {
	
	private static final String GRAMMAR_WITH_EPSILON_PRODUCTIONS = "S->A;A->" + Grammar.EMPTY + ".";
	private static final String GRAMMAR_WITHOUT_EPSILON_PRODUCTIONS = "S->A|" + Grammar.EMPTY + ".";

	@SuppressWarnings("static-access")
	@Test
	public void testRemoveEpsilonProductions() {
		Grammar grammar = GrammarBuilder
				.hasProductions(GRAMMAR_WITH_EPSILON_PRODUCTIONS)
				.create();
		
		//System.out.println("EPSILONS: " + grammar);
		
		GrammarTransformer.removeEpsilonProductions(grammar);
		
		//System.out.println("WITHOUT EPSILONS: " + grammar);
		
		Grammar shouldBe = GrammarBuilder
				.hasProductions(GRAMMAR_WITHOUT_EPSILON_PRODUCTIONS)
				.create();	
		
		//System.out.println("SHOULD BE: " + shouldBe);
		
		assertTrue("Incorrect removal of epsilon productions", 
				grammar.equals(shouldBe));
	}
}