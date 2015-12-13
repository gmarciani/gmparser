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

import java.util.Collection;
import java.util.Objects;

import com.gmarciani.gmparser.models.commons.set.GSet;

/**
 * <p>The automaton state model.<p>
 * <p>A state is identified by a unique id, and contains a collection of values. A state can be marked as final, initial or none.<p>
 * 
 * @see com.gmarciani.gmparser.models.automaton.FiniteAutomaton
 * @see com.gmarciani.gmparser.models.automaton.TransitionGraph
 * @see com.gmarciani.gmparser.models.automaton.state.States
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public class State<V> implements Comparable<State<V>> {
	
	private final Integer id;
	private boolean isInitial;
	private boolean isFinal;
	private GSet<V> value;
	
	/**
	 * Creates a new state with the specified state id and value.
	 * 
	 * @param id the state id
	 * @param value the state value.
	 */
	public State(Integer id, V value) {
		this.id = id;
		this.value = (value != null) ? new GSet<V>(value) : new GSet<V>();
		this.setIsInitial(false);
		this.setIsFinal(false);
	}
	
	/**
	 * Creates a new state with the specified state id and empty value.
	 * 
	 * @param id the state id.
	 */
	public State(Integer id) {
		this.id = id;
		this.value = new GSet<V>();
		this.setIsInitial(false);
		this.setIsFinal(false);
	}
	
	/**
	 * Creates a new state with the specified state id and collection of values.
	 * 
	 * @param id the state id.
	 * @param values the collection of values.
	 */
	public State(Integer id, Collection<V> values) {
		this(id);
		for (V value : values)
			this.getValue().add(value);
	}
	
	/**
	 * Returns the state id.
	 * 
	 * @return the state id.
	 */
	public Integer getId() {
		return this.id;
	}
	
	/**
	 * Sets if the state is initial or not.
	 * 
	 * @param isInitial true if the state is initial; false, otherwise.
	 */
	public void setIsInitial(boolean isInitial) {
		this.isInitial = isInitial;
	}
	
	/**
	 * Sets if the state is final or not.
	 * 
	 * @param isFinal true if the state is final; false, otherwise.
	 */
	public void setIsFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
	
	/**
	 * Sets the state as non-final and non-initial state.
	 */
	public void setNormal() {
		this.setIsInitial(false);
		this.setIsFinal(false);
	}
	
	/**
	 * Returns the state value.
	 * 
	 * @return the state value.
	 */
	public GSet<V> getValue() {
		return this.value;
	}
	
	/**
	 * Sets the new state value.
	 * 
	 * @param value the new state value.
	 */
	public void setValue(GSet<V> value) {
		this.value = value;
	}
	
	/**
	 * Returns true if the state is a final state; false, otherwise.
	 * 
	 * @return true if the state is a final state; false, otherwise.
	 */
	public boolean isFinal() {
		return this.isFinal;
	}
	
	/**
	 * Returns true if the state is an initial state; false, otherwise.
	 * 
	 * @return true if the state is an initial state; false, otherwise.
	 */
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
