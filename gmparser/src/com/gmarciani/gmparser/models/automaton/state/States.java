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

package com.gmarciani.gmparser.models.automaton.state;

import com.bethecoder.ascii_table.ASCIITable;
import com.gmarciani.gmparser.models.commons.set.GSet;

public class States<V> extends GSet<State<V>> {

	private static final long serialVersionUID = -971990098522233593L;

	public States() {
		super();
	}
	
	@SafeVarargs
	public States(States<V> ... states) {
		super();
		for (States<V> s : states)
			this.addAll(s);
	}
	
	@SafeVarargs
	public States(State<V> ... states) {
		super(states);
	}
	
	public State<V> getState(StateId id) {
		for (State<V> state : this)
			if (state.getId().equals(id))
				return state;
		return null;
	}
	
	public State<V> getState(Integer id) {
		return this.getState(new StateId(id));
	}
	
	public GSet<StateId> getIds() {
		GSet<StateId> ids = new GSet<StateId>();
		for (State<V> state : this)
			ids.add(state.getId());
		return ids;
	}
	
	public GSet<V> getValues() {
		GSet<V> values = new GSet<V>();
		for (State<V> state : this)
			values.addAll(state.getValue());
		return values;
	}
	
	public String toExtendedString() {
		String header[] = {"State", "Value"};		
		if (this.isEmpty()) {
			String data[][] = {{"null", "null"}};
			return ASCIITable.getInstance().getTable(header, ASCIITable.ALIGN_CENTER, data, ASCIITable.ALIGN_CENTER);
		}
		
		String data[][] = new String[this.size()][2];
		int r = 0;
		for (State<V> state : this) {
			data[r][0] = String.valueOf(state.getId());
			data[r][1] = String.valueOf(state.getValue());
			r ++;
		}
			
		return ASCIITable.getInstance().getTable(header, ASCIITable.ALIGN_CENTER, data, ASCIITable.ALIGN_CENTER);		
	}

}
