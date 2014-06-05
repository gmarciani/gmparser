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

package com.gmarciani.gmparser.models.parser.cyk.matrix;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.bethecoder.ascii_table.ASCIITable;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.alphabet.AlphabetType;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

public class CYKMatrix {
	
	private Table<Integer, Integer, Alphabet> matrix;
	private String word;

	public CYKMatrix(String word) {
		this.setWord(word);
		this.initializeMatrix(word);		
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
		
		target.addAll(this.getMatrix().rowKeySet());
		
		return target;
	}
	
	public List<Integer> getWordPositions() {
		List<Integer> target = new ArrayList<Integer>();
		
		target.addAll(this.getMatrix().columnKeySet());
		
		return target;
		
	}
	
	private void initializeMatrix(String word) {
		int size = (word.length() == 0) ? 1 : word.length();
		this.matrix = TreeBasedTable.create();
		
		for (int r = 1; r <= size; r ++) {
			for (int c = 1; c <= size; c ++) {
				this.matrix.put(r, c, new Alphabet(AlphabetType.NON_TERMINAL));
			}
		}
	}
	
	public Alphabet get(int row, int column) {
		return this.getMatrix().get(row, column);
	}
	
	public boolean put(int row, int column, Character nonTerminal) {
		return this.getMatrix().get(row, column).add(nonTerminal);
	}
	
	public boolean put(int row, int column, Alphabet nonTerminals) {
		return this.getMatrix().get(row, column).addAll(nonTerminals);
	}
	
	public String toFormattedString() {
		List<Integer> lengths = new LinkedList<Integer>();
		lengths.addAll(this.getMatrix().rowKeySet());
		List<Character> symbols = new LinkedList<Character>();
		for (Character symbol : this.getWord().toCharArray())
			symbols.add(symbol);
		
		String header[] = new String[symbols.size() + 1];
		header[0] = "#";
		int c = 1;
		for (Character symbol : symbols) {
			header[c] = String.valueOf(symbol);
			c ++;
		}
		
		String data[][] = new String[lengths.size()][symbols.size() + 1];
		int r = 0;
		for (Integer length : lengths) {
			data[r][0] = String.valueOf(length);
			r ++;
		}
		
		for (Table.Cell<Integer, Integer, Alphabet> cell : this.getMatrix().cellSet()) {
			int symbolPosition = cell.getColumnKey();
			int length = cell.getRowKey();
			Alphabet alphabet = cell.getValue();
			
			data[length - 1][symbolPosition] = alphabet.toString();			
		}
		
		String table = ASCIITable.getInstance().getTable(header, ASCIITable.ALIGN_CENTER, data, ASCIITable.ALIGN_LEFT);
        
        return table;
	}
	
	@Override public String toString() {
		return "CYKMatrix(" + this.getMatrix() + ")";
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
