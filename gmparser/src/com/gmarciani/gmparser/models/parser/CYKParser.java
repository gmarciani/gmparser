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

package com.gmarciani.gmparser.models.parser;

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.alphabet.AlphabetType;
import com.gmarciani.gmparser.models.grammar.production.Production;
import com.gmarciani.gmparser.models.grammar.production.Productions;
import com.gmarciani.gmparser.models.parser.matrix.CYKMatrix;

public class CYKParser {

	public CYKParser() {
		// TODO Auto-generated constructor stub
	}

	public synchronized boolean parse(Grammar grammar, String word) {
		CYKMatrix matrix = this.getMatrix(grammar.getProductions(), word);
		return (matrix.get(word.length(), 1).contains(grammar.getAxiom()));
	}
	
	public synchronized CYKMatrix getMatrix(Productions productions, String word) {
		CYKMatrix matrix = new CYKMatrix(word);
		
		for (int j = 1; j <= word.length(); j ++) {
			Character producedTerminal = word.charAt(j - 1);
			for (Production production : productions) {
				if (production.getRight().getSize() == 1
						&& production.getRight().contains(producedTerminal))
					matrix.put(1, j, production.getLeft().getNonTerminalAlphabet());
			}			
		}
		
		for (int i = 2; i <= word.length(); i ++) {
			for (int j = 1; j <= (word.length() - i + 1); j ++) {
				System.out.println("#WORD ("+i+","+j+"): " + word.substring(j - 1, j + i - 1));
				Alphabet target = new Alphabet(AlphabetType.NON_TERMINAL);
				for (int i1 = (i - 1), j1 = j, i2 = 1, j2 = (j + i - 1); 
						i1 >= 1 && j1 == j && i2 <= (i - i1) && j2 >= (j + i1); 
						i1 --, j1 = j, i2 ++, j2 --) {
					System.out.println("\t# " + word.substring(j1 - 1, j1 + i1 - 1) + " " + word.substring(j2 - 1, j2 + i2 - 1));
					Alphabet entry1 = matrix.get(i1, j1);
					Alphabet entry2 = matrix.get(i2, j2);
					Productions prods = productions.getProductionsRightWithinAt(entry1, entry2);
					for (Production prod : prods) {
						target.addAll(prod.getLeft().getNonTerminalAlphabet());
					}
				}
				matrix.put(i, j, target);
			}
		}
		
		return matrix;
	}

}
