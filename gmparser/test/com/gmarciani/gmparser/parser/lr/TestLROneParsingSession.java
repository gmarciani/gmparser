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


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.parser.lr.LROneParser;
import com.gmarciani.gmparser.models.parser.lr.session.LROneParsingSession;

public class TestLROneParsingSession {
	
	private static final String GRAMMAR_LR1_ONE = "X->S;S->CC;C->cC|d.";
	private static final String GRAMMAR_LR1_ONE_ACCEPTABLE_WORDS[] = {"dd", "cdd", "dcd", "cccdd", "dcccd", "cdcd", "cdcccd", "cccdcd", "cccdcccd"};
	private static final String GRAMMAR_LR1_ONE_NOT_ACCEPTABLE_WORDS[] = {"", "d", "dddd", "cc", "cddd", "dcdd", "dcdc", "ddccc", "dcccdccc", "cdcddd", "abcdfg"};
	
	private static final String GRAMMAR_LR1_TWO = "S->A;A->BA|" + Grammar.EPSILON + ";B->aB|b.";
	private static final String GRAMMAR_LR1_TWO_ACCEPTABLE_WORDS[] = {"", "b", "bbbb", "ab", "aab", "aaab", "abab", "aabaab", "aaabaaab", "aaabbb", "bbbab"};
	private static final String GRAMMAR_LR1_TWO_NOT_ACCEPTABLE_WORDS[] = {"ddd", "cddd", "dcdd", "dcdc", "ddccc", "dcccdccc", "cdcddd", "abcdfg"};
	
	private static final String GRAMMAR_LR1_THREE = "X->S;S->aA|bB;A->cAd|" + Grammar.EPSILON + ";B->" + Grammar.EPSILON + ".";
	private static final String GRAMMAR_LR1_THREE_ACCEPTABLE_WORDS[] = {"b", "a", "acd", "accdd", "acccddd", "accccdddd"};
	private static final String GRAMMAR_LR1_THREE_NOT_ACCEPTABLE_WORDS[] = {"", "ab", "bb", "bbb", "bbbb", "aa", "aaa", "aaaa", "acdb", "aacd", "acdcd", "acdd", "accd"};
	
	private static final String GRAMMAR_EMPTY = "S->" + Grammar.EPSILON + ".";
	private static final String GRAMMAR_EMPTY_ACCEPTABLE_WORDS[] = {""};
	private static final String GRAMMAR_EMPTY_NOT_ACCEPTABLE_WORDS[] = {"ab", "bb", "bbb", "bbbb", "aa", "aaa", "aaaa", "acdb", "aacd", "acdcd", "acdd", "accd"};
	
	private static final String GRAMMAR_NOTLR1_CHOMSKY = "S->AL|BL|BR;A->a;B->b;L->AS|a|b;R->BS|a|b.";
	private static final String GRAMMAR_NOTLR1_CHOMSKY_ACCEPTABLE_WORDS[] = {"aa", "ab", "ba", "bb", "aaaaaa", "aaaaab", "aaaaba", "aaaabb", "babaaa", "babaab", "bababa", "bababb", "bbbbaa", "bbbbab", "bbbbba", "bbbbbb"};
	private static final String GRAMMAR_NOTLR1_CHOMSKY_NOT_ACCEPTABLE_WORDS[] = {"", "abcdefg", "ababaa", "ababab", "ababba", "ababbb"};
	
	private static final String GRAMMAR_NOTLR1_NOTCHOMSKY = "S->aL|bL|bR;L->aS|a|b;R->bS|a|b.";
	private static final String GRAMMAR_NOTLR1_NOTCHOMSKY_ACCEPTABLE_WORDS[] = {"aa", "ab", "ba", "bb", "aaaaaa", "aaaaab", "aaaaba", "aaaabb", "babaaa", "babaab", "bababa", "bababb", "bbbbaa", "bbbbab", "bbbbba", "bbbbbb"};
	private static final String GRAMMAR_NOTLR1_NOTCHOMSKY_NOT_ACCEPTABLE_WORDS[] = {"", "abcdefg", "ababaa", "ababab", "ababba", "ababbb"};

	@Test public void test() {
		List<String> strGrammars = new ArrayList<String>();
		strGrammars.add(GRAMMAR_LR1_ONE);
		strGrammars.add(GRAMMAR_LR1_TWO);
		strGrammars.add(GRAMMAR_LR1_THREE);
		strGrammars.add(GRAMMAR_EMPTY);
		strGrammars.add(GRAMMAR_NOTLR1_CHOMSKY);
		strGrammars.add(GRAMMAR_NOTLR1_NOTCHOMSKY);
		
		Map<String, String[]> acceptableWords = new HashMap<String, String[]>();
		acceptableWords.put(GRAMMAR_LR1_ONE, GRAMMAR_LR1_ONE_ACCEPTABLE_WORDS);
		acceptableWords.put(GRAMMAR_LR1_TWO, GRAMMAR_LR1_TWO_ACCEPTABLE_WORDS);
		acceptableWords.put(GRAMMAR_LR1_THREE, GRAMMAR_LR1_THREE_ACCEPTABLE_WORDS);
		acceptableWords.put(GRAMMAR_EMPTY, GRAMMAR_EMPTY_ACCEPTABLE_WORDS);
		acceptableWords.put(GRAMMAR_NOTLR1_CHOMSKY, GRAMMAR_NOTLR1_CHOMSKY_ACCEPTABLE_WORDS);
		acceptableWords.put(GRAMMAR_NOTLR1_NOTCHOMSKY, GRAMMAR_NOTLR1_NOTCHOMSKY_ACCEPTABLE_WORDS);
		
		Map<String, String[]> notAcceptableWords = new HashMap<String, String[]>();
		notAcceptableWords.put(GRAMMAR_LR1_ONE, GRAMMAR_LR1_ONE_NOT_ACCEPTABLE_WORDS);
		notAcceptableWords.put(GRAMMAR_LR1_TWO, GRAMMAR_LR1_TWO_NOT_ACCEPTABLE_WORDS);
		notAcceptableWords.put(GRAMMAR_LR1_THREE, GRAMMAR_LR1_THREE_NOT_ACCEPTABLE_WORDS);
		notAcceptableWords.put(GRAMMAR_EMPTY, GRAMMAR_EMPTY_NOT_ACCEPTABLE_WORDS);
		notAcceptableWords.put(GRAMMAR_NOTLR1_CHOMSKY, GRAMMAR_NOTLR1_CHOMSKY_NOT_ACCEPTABLE_WORDS);
		notAcceptableWords.put(GRAMMAR_NOTLR1_NOTCHOMSKY, GRAMMAR_NOTLR1_NOTCHOMSKY_NOT_ACCEPTABLE_WORDS);
		
		for (String strGrammar : strGrammars)
			for (String word : acceptableWords.get(strGrammar))
				this.saveSession(LROneParser.parseWithSession(Grammar.generateGrammar(strGrammar), word));
		
		for (String strGrammar : strGrammars)
			for (String word : notAcceptableWords.get(strGrammar))
				this.saveSession(LROneParser.parseWithSession(Grammar.generateGrammar(strGrammar), word));	
	}
	
	private void saveSession(LROneParsingSession session) {
		String fileName = "LR(1) " + ((session.getResult()) ? "accepts " : "doesn't accept ") + session.getWord() + " by " + session.getGrammar().getProductions().toString() + ".txt";
		try {			  
			File file = new File("/home/giacomo/gmparser-tests/LR(1)/" + fileName);
			if (!file.exists())
				file.createNewFile(); 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(session.toFormattedParsingSession());
			bw.close();  
		} catch (IOException exc) {
			exc.printStackTrace();
		}		
	}

}
