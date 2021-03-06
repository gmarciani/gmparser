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

package com.gmarciani.gmparser.models.parser.cyk;

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.production.Production;
import com.gmarciani.gmparser.models.grammar.production.Productions;
import com.gmarciani.gmparser.models.parser.cyk.recognition.CYKMatrix;
import com.gmarciani.gmparser.models.parser.cyk.session.CYKParsingSession;

/**
 * <p>Cocke-Younger-Kasami parser implementation.<p>
 * <p>Every algorithm has been derived from [A. Pettorossi "Automata Theory and Formal Languages (3rd edition)", par. 3.15.1]<p>
 * 
 * @see com.gmarciani.gmparser.models.parser.cyk.session.CYKParsingSession
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public class CYKParser {	
	
	/**
	 * <p>Checks if the specified word can be parsed by the specified grammar.<p>
	 * <p>The algorithm has been derived from [A. Pettorossi "Automata Theory and Formal Languages (3rd edition)", par. 3.15.1]<p>
	 * 
	 * @param grammar the grammar to parse with.
	 * @param word the word to parse.
	 * 
	 * @return true if the specified word can be parsed by the specified grammar; false, otherwise.
	 */
	public static synchronized boolean parse(Grammar grammar, String word) {
		word = (word.length() == 0) ? Grammar.EPSILON.toString() : word;
		CYKMatrix recognitionMatrix = getRecognitionMatrix(grammar, word);
		return (recognitionMatrix.get(word.length(), 1).contains(grammar.getAxiom())); // a word can be parsed if it can be fully produced from the axiom.
	}

	/**
	 * <p>Checks if the specified word can be parsed by the specified grammar.<p>
	 * <p>The algorithm has been derived from [A. Pettorossi "Automata Theory and Formal Languages (3rd edition)", par. 3.15.1]<p>
	 * 
	 * @param grammar the grammar to parse with.
	 * @param word the word to parse.
	 * 
	 * @return the Cocke-Younger-Kasami parsing session for the specified grammar and word.
	 */
	public static synchronized CYKParsingSession parseWithSession(Grammar grammar, String word) {
		word = (word.length() == 0) ? Grammar.EPSILON.toString() : word;
		CYKMatrix recognitionMatrix = getRecognitionMatrix(grammar, word);
		boolean result = (recognitionMatrix.get(word.length(), 1).contains(grammar.getAxiom())); // a word can be parsed if it can be fully produced from the axiom.
		return new CYKParsingSession(grammar, word, recognitionMatrix, result);
	}
	
	/**
	 * <p>Generates the Cocke-Younger-Kasami recognition matrix derived from the specified grammar.<p>
	 * <p>The algorithm has been derived from [A. Pettorossi "Automata Theory and Formal Languages (3rd edition)", par. 3.15.1]<p>
	 * 
	 * @param grammar the grammar.
	 * 
	 * @return the Cocke-Younger-Kasami recognition matrix derived from the specified grammar.
	 */
	public static synchronized CYKMatrix getRecognitionMatrix(Grammar grammar, String word) {
		grammar.toChomskyNormalForm(); // the grammar must be in Chomsky Normal Form.
		CYKMatrix matrix = new CYKMatrix(word); // the structure of the Cocke-Younger-Kasami recognition matrix fully depends on the word to parse.
				
		for (int p = 1; p <= word.length(); p ++) { // every p-th symbol in the word (that is, the substring with length 1 starting at p) can be generated by all of the non terminal symbol that produce it.
			Character producedTerminal = word.charAt(p - 1);
			for (Production production : grammar.getProductions())
				if (production.getRight().getSize() == 1
						&& production.getRight().isContaining(producedTerminal))
					matrix.put(1, p, production.getLeft().getNonTerminalAlphabet());
		}
		
		for (int l = 2; l <= word.length(); l ++) { // every substring of length l starting at p can be generated by all of non terminal symbols that generate its inner substrings.
			for (int p = 1; p <= (word.length() - l + 1); p ++) {
				Alphabet target = new Alphabet();
				for (int l1 = (l - 1), p1 = p, l2 = 1, p2 = (p + l - 1); 
						l1 >= 1 && p1 == p && l2 <= (l - l1) && p2 >= (p + l1); 
						l1 --, p1 = p, l2 ++, p2 --) {
					Alphabet entry1 = matrix.get(l1, p1);
					Alphabet entry2 = matrix.get(l2, p2);
					Productions prods = grammar.getProductions().getProductionsRightIndexedWithin(entry1, entry2);
					for (Production prod : prods)
						target.addAll(prod.getLeft().getNonTerminalAlphabet());
				}
				matrix.put(l, p, target);
			}
		}
		
		return matrix;
	}

}
