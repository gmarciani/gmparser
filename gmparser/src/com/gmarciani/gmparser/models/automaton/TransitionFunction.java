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

package com.gmarciani.gmparser.models.automaton;

import java.util.Objects;

import com.bethecoder.ascii_table.ASCIITable;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

public class TransitionFunction {
	
	private Table<State, Character, States> matrix;

	public TransitionFunction(States states, Alphabet alphabet)  {
		this.initializeMatrix(states, alphabet);
	}

	public Table<State, Character, States> getMatrix() {
		return this.matrix;
	}
	
	private void initializeMatrix(States states, Alphabet alphabet) {
		this.matrix = TreeBasedTable.create();
		
		for (State state : states) {
			Character epsilon = Character.toChars(Integer.parseInt("03B5", 16))[0];
			this.matrix.put(state, epsilon, new States());
			for (Character symbol : alphabet)
				this.matrix.put(state, symbol, new States());
		}
			
	}
	
	@Override public String toString() {
		States states = new States();
		states.addAll(this.getMatrix().rowKeySet());
		Alphabet alphabet = new Alphabet();
		alphabet.addAll(this.getMatrix().columnKeySet());
		
		String header[] = new String[alphabet.size() + 1];
		header[0] = "#";
		int c = 1;
		for (Character symbol : alphabet) {			
			header[c] = String.valueOf(symbol);
			c ++;
		}
		
		String data[][] = new String[states.size()][alphabet.size() + 1];
		int r = 0;
		for (State state : states) {
			data[r][0] = state.toString();
			r ++;
		}
		
		for (State sourceState : states) {
			for (Character symbol : alphabet) {
				States destinationStates = this.getMatrix().get(sourceState, symbol);
				int symbolIndex;
				for (symbolIndex = 0; symbolIndex < header.length; symbolIndex ++) {
					if (header[symbolIndex].equals(String.valueOf(symbol)))
						break;
					
				}
				int sourceStateIndex;
				for (sourceStateIndex = 0; sourceStateIndex < data.length; sourceStateIndex ++) {
					if (data[sourceStateIndex][0].equals(sourceState.toString()))
						break;
				}
				
				data[sourceStateIndex][symbolIndex] = destinationStates.toString();
			}
		}
		
		String table = ASCIITable.getInstance().getTable(header, ASCIITable.ALIGN_CENTER, data, ASCIITable.ALIGN_LEFT);
        
        return table;
	}
	
	@Override public boolean equals(Object obj) {
		if (this.getClass() != obj.getClass())
			return false;
		
		TransitionFunction other = (TransitionFunction) obj;
		
		return this.getMatrix().equals(other.getMatrix());
	}
	
	@Override public int hashCode() {
		return Objects.hash(this.getMatrix());
	}

}
