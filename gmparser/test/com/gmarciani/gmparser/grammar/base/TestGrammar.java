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

package com.gmarciani.gmparser.grammar.base;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarFactory;
import com.gmarciani.gmparser.models.grammar.production.Production;
import com.gmarciani.gmparser.models.grammar.production.Productions;

public class TestGrammar {

	@Test public void create() {		
		
		Production prodOne = new Production("S","Aa");
		Production prodTwo = new Production("S","a");
		Production prodThree = new Production("A","a");
		Production prodFour = new Production("A", Grammar.EPSILON);
		
		Productions productions = new Productions();
		productions.add(prodOne);
		productions.add(prodTwo);
		productions.add(prodThree);
		productions.add(prodFour);		
		
		Grammar grammarOne = GrammarFactory.getInstance()
				.hasProductions(productions)
				.withAxiom('S')
				.withEpsilon(Grammar.EPSILON)
				.create();
		
		Grammar grammarTwo = GrammarFactory.getInstance()
				.hasProduction(prodOne)
				.hasProduction(prodTwo)
				.hasProduction(prodThree)
				.hasProduction(prodFour)
				.withAxiom('S')
				.withEpsilon(Grammar.EPSILON)
				.create();
		
		Grammar grammarThree = GrammarFactory.getInstance()
				.hasProduction(prodOne.getLeft(), prodOne.getRight())
				.hasProduction(prodTwo.getLeft(), prodTwo.getRight())
				.hasProduction(prodThree.getLeft(), prodThree.getRight())
				.hasProduction(prodFour.getLeft(), prodFour.getRight())
				.withAxiom('S')
				.withEpsilon(Grammar.EPSILON)
				.create();
		
		Grammar grammarFour = GrammarFactory.getInstance()
				.hasProductions("S->Aa|a;A->a|" + Grammar.EPSILON + ".")
				.withAxiom('S')
				.withEpsilon(Grammar.EPSILON)
				.create();
		
		assertTrue("Uncorrect grammar building",
				grammarOne.equals(grammarTwo)
				&& grammarTwo.equals(grammarThree)
				&& grammarThree.equals(grammarFour));
	}
}
