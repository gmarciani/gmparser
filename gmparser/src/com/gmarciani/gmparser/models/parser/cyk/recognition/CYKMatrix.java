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

package com.gmarciani.gmparser.models.parser.cyk.recognition;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.bethecoder.ascii_table.ASCIITable;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

/**
 * <p>The recognition matrix for the Cocke-Younger-Kasami parser.<p>
 * 
 * @see com.gmarciani.gmparser.model.parser.cyk.CYKParser
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
/**
 * @author giacomo
 *
 */
public class CYKMatrix {
	
	private Table<Integer, Integer, Alphabet> matrix;
	private String word;

	/**
	 * Creates a new Cocke-Younger-Kasami recognition matrix for the specified word to parse.
	 * 
	 * @param word the word.
	 */
	public CYKMatrix(String word) {
		this.word = word;
		this.generate();		
	}
	
	/**
	 * Returns the Cocke-Younger-Kasami recognition matrix.
	 * 
	 * @return the Cocke-Younger-Kasami recognition matrix.
	 */
	public Table<Integer, Integer, Alphabet> getMatrix() {
		return this.matrix;
	}
	
	/**
	 * Returns the word to parse.
	 * 
	 * @return the word to parse.
	 */
	public String getWord() {
		return this.word;
	}
	
	/**
	 * Returns the columns key set for the Cocke-Younger-Kasami recognition matrix.
	 * 
	 * @return the columns key set for the Cocke-Younger-Kasami recognition matrix.
	 */
	public List<Integer> getWordLenghts() {
		List<Integer> target = new ArrayList<Integer>();		
		target.addAll(this.getMatrix().rowKeySet());		
		return target;
	}
	
	/**
	 * Returns the rows key set for the Cocke-Younger-Kasami recognition matrix.
	 * 
	 * @return the rows key set for the Cocke-Younger-Kasami recognition matrix.
	 */
	public List<Integer> getWordPositions() {
		List<Integer> target = new ArrayList<Integer>();		
		target.addAll(this.getMatrix().columnKeySet());		
		return target;
		
	}
	
	/**
	 * Generates the Cocke-Younger-Kasami recognition matrix.
	 */
	private void generate() {
		int size = (this.getWord().length() == 0) ? 1 : this.getWord().length();
		this.matrix = TreeBasedTable.create();
		
		for (int r = 1; r <= size; r ++) {
			for (int c = 1; c <= size; c ++) {
				this.matrix.put(r, c, new Alphabet());
			}
		}
	}	
	
	/**
	 * Returns the non terminal alphabet stored in correspondence of the specified row and column.
	 * 
	 * @param row the row index.
	 * @param column the column index.
	 * 
	 * @return the non terminal alphabet stored in correspondence of the specified row and column.
	 */
	public Alphabet get(int row, int column) {
		return this.getMatrix().get(row, column);
	}
	
	/**
	 * Adds to the recognition matrix the specified non terminal alphabet, in correspondence of the specified row and column index.
	 * 
	 * @param row the row index.
	 * @param column the column index.
	 * @param nonTerminals the non terminal alphabet.
	 * 
	 * @return true if the non terminal alphabet has been added; false, otherwise.
	 */
	public boolean put(int row, int column, Alphabet nonTerminals) {
		return this.getMatrix().get(row, column).addAll(nonTerminals);
	}
	
	public String toFormattedMatrix() {
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
