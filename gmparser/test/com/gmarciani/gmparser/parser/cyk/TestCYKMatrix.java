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

import org.junit.Test;

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.parser.cyk.CYKParser;
import com.gmarciani.gmparser.models.parser.cyk.matrix.CYKMatrix;

public class TestCYKMatrix {
	
	private static final String GRAMMAR_CHOMSKY = "S->CB|FA|FB;A->CS|FD|a;B->FS|CE|b;C->a;D->AA;E->BB;F->b.";
	private static final String GRAMMAR_CHOMSKY_S_EXTENDED = "S->" + Grammar.EPSILON + "|CB|FA|FB;A->CS|FD|a;B->FS|CE|b;C->a;D->AA;E->BB;F->b.";
	private static final String GRAMMAR_NOT_CHOMSKY_EXTENDED = "S->" + Grammar.EPSILON + "|CB|FA|FB|G;A->CS|FD|a;B->FS|CE|b;C->a;D->AA;E->BB;F->b;G->" + Grammar.EPSILON + ".";
	private static final String GRAMMAR_CHOMSKY_EMPTY = "S->" + Grammar.EPSILON + ".";
	
	@Test public void createChomsky() {
		Grammar grammar = Grammar.generateGrammar(GRAMMAR_CHOMSKY);
		CYKMatrix matrixRecognized = CYKParser.getRecognitionMatrix(grammar, "aababb");
		System.out.println(matrixRecognized.toFormattedMatrix());
		CYKMatrix matrixUnrecognized = CYKParser.getRecognitionMatrix(grammar, "abcdfg");
		System.out.println(matrixUnrecognized.toFormattedMatrix());
		CYKMatrix matrixEpsilon = CYKParser.getRecognitionMatrix(grammar, Grammar.EPSILON.toString());
		System.out.println(matrixEpsilon.toFormattedMatrix());
	}
	
	@Test public void createChomskySExtended() {
		Grammar grammar = Grammar.generateGrammar(GRAMMAR_CHOMSKY_S_EXTENDED);
		CYKMatrix matrixRecognized = CYKParser.getRecognitionMatrix(grammar, "aababb");
		System.out.println(matrixRecognized.toFormattedMatrix());
		CYKMatrix matrixUnrecognized = CYKParser.getRecognitionMatrix(grammar, "abcdfg");
		System.out.println(matrixUnrecognized.toFormattedMatrix());
		CYKMatrix matrixEpsilon = CYKParser.getRecognitionMatrix(grammar, Grammar.EPSILON.toString());
		System.out.println(matrixEpsilon.toFormattedMatrix());
	}
	
	@Test public void createNotChomskyExtended() {
		Grammar grammar = Grammar.generateGrammar(GRAMMAR_NOT_CHOMSKY_EXTENDED);
		CYKMatrix matrixRecognized = CYKParser.getRecognitionMatrix(grammar, "aababb");
		System.out.println(matrixRecognized.toFormattedMatrix());
		CYKMatrix matrixUnrecognized = CYKParser.getRecognitionMatrix(grammar, "abcdfg");
		System.out.println(matrixUnrecognized.toFormattedMatrix());
		CYKMatrix matrixEpsilon = CYKParser.getRecognitionMatrix(grammar, Grammar.EPSILON.toString());
		System.out.println(matrixEpsilon.toFormattedMatrix());
	}
	
	@Test public void parseChosmkyEmpty() {
		Grammar grammar = Grammar.generateGrammar(GRAMMAR_CHOMSKY_EMPTY);
		CYKMatrix matrixRecognized = CYKParser.getRecognitionMatrix(grammar, Grammar.EPSILON.toString());
		System.out.println(matrixRecognized.toFormattedMatrix());
		CYKMatrix matrixUnrecognized = CYKParser.getRecognitionMatrix(grammar, "abcdfg");
		System.out.println(matrixUnrecognized.toFormattedMatrix());
	}

}
