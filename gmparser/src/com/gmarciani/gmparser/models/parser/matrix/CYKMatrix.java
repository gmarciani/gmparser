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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.alphabet.AlphabetType;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class CYKMatrix {
	
	private Table<Integer, Integer, Alphabet> matrix;
	private String word;

	public CYKMatrix(String word) {
		this.setWord(word);
		this.initializeMatrix();		
	}
	
	public Table<Integer, Integer, Alphabet> getMatrix() {
		return this.matrix;
	}
	
	public String getWord() {
		return this.word;
	}
	
	private void setWord(String word) {
		this.word = word;
	}
	
	public List<Integer> getWordLenghts() {
		List<Integer> target = new ArrayList<Integer>();
		
		target.addAll(this.matrix.rowKeySet());
		
		return target;
	}
	
	public List<Integer> getWordPositions() {
		List<Integer> target = new ArrayList<Integer>();
		
		target.addAll(this.matrix.columnKeySet());
		
		return target;
		
	}
	
	private void initializeMatrix() {
		int size = this.getWord().length();
		this.matrix = HashBasedTable.create(size, size);
		
		for (int r = 1; r <= size; r ++) {
			for (int c = 1; c <= size; c ++) {
				this.matrix.put(r, c, new Alphabet(AlphabetType.NON_TERMINAL));
			}
		}
	}
	
	public Alphabet get(int row, int column) {
		return this.matrix.get(row, column);
	}
	
	public boolean put(int row, int column, Character nonTerminal) {
		return this.matrix.get(row, column).add(nonTerminal);
	}
	
	public boolean put(int row, int column, Alphabet nonTerminals) {
		return this.matrix.get(row, column).addAll(nonTerminals);
	}
	
	@Override public String toString() {
		String target = "CYKMatrix(";
		
		Iterator<Table.Cell<Integer, Integer, Alphabet>> iter = this.matrix.cellSet().iterator();
		while(iter.hasNext()) {
			Table.Cell<Integer, Integer, Alphabet> cell = iter.next();
			target += "(" + cell.getRowKey() + ";" + cell.getColumnKey() + ")" + cell.getValue();
			target += (iter.hasNext() ? "," : "");
		}
		
		target += ")";
		
		return target;
	}
	
	@Override public boolean equals(Object obj) {
		if (this.getClass() != obj.getClass())
			return false;
		
		CYKMatrix other = (CYKMatrix) obj;
		
		return (this.getWord().equals(other.getWord())
				&& this.getMatrix().equals(other.getMatrix()));
	}
	
	@Override public int hashCode() {
		return Objects.hash(this.getWord(), this.getMatrix());
	}

}
