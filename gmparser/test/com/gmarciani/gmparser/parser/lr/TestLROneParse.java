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

package com.gmarciani.gmparser.parser.lr;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.parser.lr.LROneParser;

public class TestLROneParse {
	
	private static final String GRAMMAR_LR1_ONE = "X->S;S->CC;C->cC|d.";
	private static final String GRAMMAR_LR1_TWO = "S->A;A->BA|" + Grammar.EPSILON + ";B->aB|b.";
	private static final String GRAMMAR_LR1_THREE = "X->S;S->aA|bB;A->cAd|" + Grammar.EPSILON + ";B->" + Grammar.EPSILON + ".";
	
	private static final String GRAMMAR_EMPTY = "S->" + Grammar.EPSILON + ".";
	
	private static final String GRAMMAR_NOTLR1_CHOMSKY = "S->AL|BL|BR;A->a;B->b;L->AS|a|b;R->BS|a|b.";
	private static final String GRAMMAR_NOTLR1_NOTCHOMSKY = "S->aL|bL|bR;L->aS|a|b;R->bS|a|b.";
	
	@Test public void parseLR1One() {
		Grammar grammar = Grammar.generateGrammar(GRAMMAR_LR1_ONE); //(c*d)(c*d)
		
		String acceptableWords[] = {"dd", "cdd", "dcd", "cccdd", "dcccd", "cdcd", "cdcccd", "cccdcd", "cccdcccd"};
		String notAcceptableWords[] = {"", "d", "dddd", "cc", "cddd", "dcdd", "dcdc", "ddccc", "dcccdccc", "cdcddd", "abcdfg"};
		
		for (String word : acceptableWords)
			assertTrue("Uncorrect LR(1) parsing. Should be parsed: " + word, LROneParser.parse(grammar, word));		
		for (String word : notAcceptableWords)
			assertFalse("Uncorrect LR(1) parsing. Should not be parsed: " + word, LROneParser.parse(grammar, word));
	}
	
	@Test public void parseLR1Two() {
		Grammar grammar = Grammar.generateGrammar(GRAMMAR_LR1_TWO); //(a*b)*
		
		String acceptableWords[] = {"", "b", "bbbb", "ab", "aab", "aaab", "abab", "aabaab", "aaabaaab", "aaabbb$", "bbbab"};
		String notAcceptableWords[] = {"ddd", "cddd", "dcdd", "dcdc", "ddccc", "dcccdccc", "cdcddd", "abcdfg"};
		
		for (String word : acceptableWords)
			assertTrue("Uncorrect LR(1) parsing. Should be parsed: " + word, LROneParser.parse(grammar, word));		
		for (String word : notAcceptableWords)
			assertFalse("Uncorrect LR(1) parsing. Should not be parsed: " + word, LROneParser.parse(grammar, word));
	}
	
	@Test public void parseLR1Three() {
		Grammar grammar = Grammar.generateGrammar(GRAMMAR_LR1_THREE); //((a(c{n}d{n})* + b)
		
		String acceptableWords[] = {"b", "a", "acd", "accdd", "acccddd", "accccdddd"};
		String notAcceptableWords[] = {"", "ab", "bb", "bbb", "bbbb", "aa", "aaa", "aaaa", "acdb", "aacd", "acdcd", "acdd", "accd"};
		
		for (String word : acceptableWords)
			assertTrue("Uncorrect LR(1) parsing. Should be parsed: " + word, LROneParser.parse(grammar, word));		
		for (String word : notAcceptableWords)
			assertFalse("Uncorrect LR(1) parsing. Should not be parsed: " + word, LROneParser.parse(grammar, word));
	}
	
	@Test public void parseEmpty() {
		Grammar grammar = Grammar.generateGrammar(GRAMMAR_EMPTY); //empty		
		
		String acceptableWords[] = {""};
		String notAcceptableWords[] = {"ab", "bb", "bbb", "bbbb", "aa", "aaa", "aaaa", "acdb", "aacd", "acdcd", "acdd", "accd"};
		
		for (String word : acceptableWords)
			assertTrue("Uncorrect LR(1) parsing. Should be parsed: " + word, LROneParser.parse(grammar, word));		
		for (String word : notAcceptableWords)
			assertFalse("Uncorrect LR(1) parsing. Should not be parsed: " + word, LROneParser.parse(grammar, word));
	}
	
	@Test public void parseNotLR1Chosmky() {
		Grammar grammar = Grammar.generateGrammar(GRAMMAR_NOTLR1_CHOMSKY); //(aa+ba+bb)*(aa+ab+ba+bb)
		
		assertFalse("Uncorrect LR1 parsing. Should not be parsed, because not LR1 grammar.", LROneParser.isLROneGrammar(grammar));
		
		String acceptableWords[] = {"aa", "ab", "ba", "bb", "aaaaaa", "aaaaab", "aaaaba", "aaaabb", "babaaa", "babaab", "bababa", "bababb", "bbbbaa", "bbbbab", "bbbbba", "bbbbbb"};
		String notAcceptableWords[] = {"", "abcdefg", "ababaa", "ababab", "ababba", "ababbb"};
		
		for (String word : acceptableWords)
			assertFalse("Uncorrect LR(1) parsing. Should be parsed, because not LR1 grammar: " + word, LROneParser.parse(grammar, word));		
		for (String word : notAcceptableWords)
			assertFalse("Uncorrect LR(1) parsing. Should not be parsed, because not LR1 grammar: " + word, LROneParser.parse(grammar, word));
	}
	
	@Test public void parseNotLR1NotChosmky() {
		Grammar grammar = Grammar.generateGrammar(GRAMMAR_NOTLR1_NOTCHOMSKY); //(aa+ba+bb)*(aa+ab+ba+bb)
		
		assertFalse("Uncorrect LR1 parsing. Should not be parsed, because not LR1 grammar.", LROneParser.isLROneGrammar(grammar));		
		
		String acceptableWords[] = {"aa", "ab", "ba", "bb", "aaaaaa", "aaaaab", "aaaaba", "aaaabb", "babaaa", "babaab", "bababa", "bababb", "bbbbaa", "bbbbab", "bbbbba", "bbbbbb"};
		String notAcceptableWords[] = {"", "abcdefg", "ababaa", "ababab", "ababba", "ababbb"};
		
		for (String word : acceptableWords)
			assertFalse("Uncorrect LR(1) parsing. Should be parsed, because not LR1 grammar: " + word, LROneParser.parse(grammar, word));		
		for (String word : notAcceptableWords)
			assertFalse("Uncorrect LR(1) parsing. Should not be parsed, because not LR1 grammar: " + word, LROneParser.parse(grammar, word));
	}

}
