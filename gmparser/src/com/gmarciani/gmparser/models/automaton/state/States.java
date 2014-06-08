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

import com.gmarciani.gmparser.models.commons.set.GSet;

public class States extends GSet<State> implements Comparable<States> {

	private static final long serialVersionUID = -971990098522233593L;

	public States() {
		super();
	}
	
	public States(State ... states) {
		super(states);
	}
	
	public States(State[] ... states) {
		super(states);
	}
	
	public States(States ... states) {
		super(states);
	}
	
	public States(Integer ... ids) {
		super();
		for (Integer id : ids)
			this.add(new State(new StateId(id)));
	}
	
	public boolean add(StateId id) {
		return this.add(new State(id));
	}
	
	public boolean add(Integer id) {
		return this.add(new StateId(id));
	}
	
	public boolean remove(StateId id) {
		State state = this.getState(id);
		if (state != null)
			return this.remove(state);
		return false;
	}
	
	public boolean remove(Integer id) {
		return this.remove(new StateId(id));
	}
	
	public State getState(StateId id) {
		for (State state : this)
			if (state.getId().equals(id))
				return state;
		return null;
	}
	
	public State getState(Integer id) {
		return this.getState(new StateId(id));
	}

	@Override public int compareTo(States other) {
		int bySize = Integer.valueOf(this.size()).compareTo(other.size());
		return bySize;
	}

}
