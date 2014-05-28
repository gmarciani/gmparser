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

package com.gmarciani.gmparser.models.parser.matrix;

import java.util.HashMap;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.alphabet.AlphabetType;

public class CYKMatrix extends HashMap<Pair<Integer, Integer>, Alphabet>{

	private static final long serialVersionUID = -4089291025909944905L;

	public CYKMatrix(String word) {
		super();
		this.build(word);
	}
	
	public Alphabet get(int row, int column) {
		Pair<Integer, Integer> coordinates = new ImmutablePair<Integer, Integer>(row, column);
		return (this.get(coordinates));
	}
	
	public boolean put(Character nonTerminal, int row, int column) {
		Pair<Integer, Integer> coordinates = new ImmutablePair<Integer, Integer>(row, column);
		return (this.get(coordinates).add(nonTerminal));
	}
	
	private void build(String word) {
		int size = word.length();
		
		for (int row = 0; row < size; row ++) {
			for (int column = 0; column < size; column ++) {
				Pair<Integer, Integer> coordinate = new ImmutablePair<Integer, Integer>(row, column);
				this.put(coordinate, new Alphabet(AlphabetType.NON_TERMINAL));
			}
		}
	}

}
