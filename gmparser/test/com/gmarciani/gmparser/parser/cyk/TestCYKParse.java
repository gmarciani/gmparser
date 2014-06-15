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

package com.gmarciani.gmparser.parser.cyk;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.parser.cyk.CYKParser;

public class TestCYKParse {
	
	private static final String GRAMMAR_CHOMSKY = "S->CB|FA|FB;A->CS|FD|a;B->FS|CE|b;C->a;D->AA;E->BB;F->b.";
	private static final String GRAMMAR_CHOMSKY_S_EXTENDED = "S->" + Grammar.EPSILON + "|CB|FA|FB;A->CS|FD|a;B->FS|CE|b;C->a;D->AA;E->BB;F->b.";
	private static final String GRAMMAR_NOT_CHOMSKY_EXTENDED = "S->" + Grammar.EPSILON + "|CB|FA|FB|G;A->CS|FD|a;B->FS|CE|b;C->a;D->AA;E->BB;F->b;G->" + Grammar.EPSILON + ".";

	@Test public void parseChosmky() {
		Grammar grammar = Grammar.generateGrammar(GRAMMAR_CHOMSKY);
		boolean recognized = CYKParser.parse(grammar, "aababb");
		boolean unrecognized = CYKParser.parse(grammar, "abcfdg");
		boolean unrecognizedEpsilon = CYKParser.parse(grammar, Grammar.EPSILON.toString());
		
		assertTrue("Uncorrect parsing (should be parsed)", recognized);
		assertFalse("Uncorrect parsing (should not be parsed)", unrecognized);
		assertFalse("Uncorrect parsing (should not be parsed)", unrecognizedEpsilon);
	}
	
	@Test public void parseChomskySExtended() {
		Grammar grammar = Grammar.generateGrammar(GRAMMAR_CHOMSKY_S_EXTENDED);
		boolean recognized = CYKParser.parse(grammar, "aababb");		
		boolean unrecognized = CYKParser.parse(grammar, "abcfdg");	
		boolean recognizedEpsilon = CYKParser.parse(grammar, Grammar.EPSILON.toString());
		
		assertTrue("Uncorrect parsing (should be parsed)", recognized);		
		assertFalse("Uncorrect parsing (should not be parsed)", unrecognized);
		assertTrue("Uncorrect parsing (should be parsed)", recognizedEpsilon);
	}
	
	@Test public void parseNotChomskyExtended() {
		Grammar grammar = Grammar.generateGrammar(GRAMMAR_NOT_CHOMSKY_EXTENDED);
		boolean recognized = CYKParser.parse(grammar, "aababb");		
		boolean unrecognized = CYKParser.parse(grammar, "abcfdg");	
		boolean recognizedEpsilon = CYKParser.parse(grammar, Grammar.EPSILON.toString());
		
		assertTrue("Uncorrect parsing (should be parsed)", recognized);		
		assertFalse("Uncorrect parsing (should not be parsed)", unrecognized);
		assertTrue("Uncorrect parsing (should be parsed)", recognizedEpsilon);
	}

}
