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

public class TestGrammarType {
	
	private static final String GRAMMAR_UNRESTRICTED = "S->AB|AbC|aa|c;A->aa;B->bbCb|bC;Cb->abc;C->c.";
	private static final String GRAMMAR_CONTEXT_SENSITIVE = "S->Aa|bB|c;Aa->aBc;B->bcA.";	
	private static final String GRAMMAR_CONTEXT_FREE = "S->AB|a|b;A->a;B->b.";
	private static final String GRAMMAR_REGULAR_LEFT_LINEAR = "S->Aa|Ab|c;A->Aa|c.";
	private static final String GRAMMAR_REGULAR_RIGHT_LINEAR = "S->aA|bA|c;A->aA|c.";
	
	@Test public void check() {
		Grammar grammarUnrestricted = GrammarFactory.getInstance()
				.hasProductions(GRAMMAR_UNRESTRICTED)
				.withAxiom(Grammar.AXIOM)
				.withEpsilon(Grammar.EPSILON)
				.create();
		
		Grammar grammarContextSensitive = GrammarFactory.getInstance()
				.hasProductions(GRAMMAR_CONTEXT_SENSITIVE)
				.withAxiom(Grammar.AXIOM)
				.withEpsilon(Grammar.EPSILON)
				.create();
		
		Grammar grammarContextFree = GrammarFactory.getInstance()
				.hasProductions(GRAMMAR_CONTEXT_FREE)
				.withAxiom(Grammar.AXIOM)
				.withEpsilon(Grammar.EPSILON)
				.create();
		
		Grammar grammarRegularLeftLinear = GrammarFactory.getInstance()
				.hasProductions(GRAMMAR_REGULAR_LEFT_LINEAR)
				.withAxiom(Grammar.AXIOM)
				.withEpsilon(Grammar.EPSILON)
				.create();
		
		Grammar grammarRegularRightLinear = GrammarFactory.getInstance()
				.hasProductions(GRAMMAR_REGULAR_RIGHT_LINEAR)
				.withAxiom(Grammar.AXIOM)
				.withEpsilon(Grammar.EPSILON)
				.create();
		
		assertTrue("Uncorrect grammar type recognition (should be Unrestricted)", 
				(grammarUnrestricted.isUnrestricted()));
		
		assertTrue("Uncorrect grammar type recognition (should be Context-Sensitive)", 
				(grammarContextSensitive.isContextSensitive()));
	
		assertTrue("Uncorrect grammar type recognition (should be Context-Free)", 
				(grammarContextFree.isContextFree()));
		
		assertTrue("Uncorrect grammar type recognition (should be Regular Left-Linear)", 
				(grammarRegularLeftLinear.isRegular()
						&& grammarRegularLeftLinear.isRegularLeftLinear()));
		
		assertTrue("Uncorrect grammar type recognition (should be Regular Right-Linear)", 
				(grammarRegularRightLinear.isRegular()
						&& grammarRegularRightLinear.isRegularRightLinear()));
	}

}
