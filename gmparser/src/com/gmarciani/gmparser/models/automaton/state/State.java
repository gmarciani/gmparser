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

import java.util.Objects;
import java.util.Set;

import com.gmarciani.gmparser.models.commons.set.GSet;

public class State<V> implements Comparable<State<V>> {
	
	private final StateId id;
	private boolean isInitial;
	private boolean isFinal;
	private GSet<V> value;
	
	public State(StateId id, V value) {
		this.id = id;
		this.value = (value != null) ? new GSet<V>(value) : new GSet<V>();
		this.setIsInitial(false);
		this.setIsFinal(false);
	}
	
	public State(Set<StateId> ids, V value) {
		this(new StateId(ids), value);
	}
	
	public State(Set<StateId> ids, Set<V> values) {
		this(new StateId(ids));
		this.getValue().addAll(values);
	}
	
	public State(States<V> states) {
		this(states.getIds(), states.getValues());
	}

	public State(StateId id) {
		this(id, null);
	}	
	
	public State(Set<StateId> ids) {
		this(new StateId(ids));
	}
	
	public StateId getId() {
		return this.id;
	}	
	
	public void setIsInitial(boolean isInitial) {
		this.isInitial = isInitial;
	}
	
	public void setIsFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
	
	public void setNormal() {
		this.setIsInitial(false);
		this.setIsFinal(false);
	}
	
	public GSet<V> getValue() {
		return this.value;
	}
	
	public void setValue(GSet<V> value) {
		this.value = value;
	}
	
	public boolean isFinal() {
		return this.isFinal;
	}
	
	public boolean isInitial() {
		return this.isInitial;
	}	
	
	@Override public String toString() {
		String string = "Q";
		if (this.isInitial())
			string = "^" + string;
		if (this.isFinal())
			string += "*";
		string += this.getId();
		return string;
	}
	
	public String toExtendedString() {
		return this.toString() + "[" + this.getValue() + "]";
	}
	
	@Override public boolean equals(Object obj) {
		if (this.getClass() != obj.getClass())
			return false;
		
		State<?> other = (State<?>) obj;
		
		return this.getId().equals(other.getId());
	}
	
	@Override public int compareTo(State<V> other) {
		return this.getId().compareTo(other.getId());
	}
	
	@Override public int hashCode() {
		return Objects.hash(this.getId());
	}

}
