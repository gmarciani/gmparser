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

package com.gmarciani.gmparser.grammar.transformation;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gmarciani.gmparser.models.grammar.Grammar;

public class TestToChomskyNormalForm {
	
	private static final String GRAMMAR_NOT_CHOMSKY = "E->E+T|T;T->TxF|F;F->(E)|a.";

	@Test public void generateChomskyNormalForm() {
		Grammar grammar = Grammar.generateGrammar(GRAMMAR_NOT_CHOMSKY);
		
		assertFalse("Grammar should not be recognized as Chomsky.", grammar.isChomskyNormalForm());
		
		grammar.toChomskyNormalForm();
		
		assertTrue("Grammar should be recognized as Chomsky.", grammar.isChomskyNormalForm());
	}
	
}
