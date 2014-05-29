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

package com.gmarciani.gmparser.parser;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarBuilder;
import com.gmarciani.gmparser.models.parser.CYKParser;
import com.gmarciani.gmparser.models.parser.matrix.CYKMatrix;

public class TestCYKParser {
	
	private static final String GRAMMAR = "S->CB|FA|FB;A->CS|FD|a;B->FS|CE|b;C->a;D->AA;E->BB;F->b.";
	private static final String WORD = "aababb";

	@SuppressWarnings("static-access")
	@Test
	public void testParse() {
		Grammar grammar = GrammarBuilder.hasProductions(GRAMMAR)
				.withAxiom(Grammar.AXIOM)
				.withEmpty(Grammar.EMPTY)
				.create();
		
		CYKParser parser = new CYKParser();
		CYKMatrix matrix = parser.getMatrix(grammar.getProductions(), WORD);
		
		System.out.println(matrix);
	}

}
