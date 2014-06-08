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

public class State implements Comparable<State> {
	
	private final StateId id;
	private boolean isInitial;
	private boolean isFinal;

	public State(StateId id) {
		this.id = id;
		this.setIsInitial(false);
		this.setIsFinal(false);
	}
	
	public State(Integer id) {
		this(new StateId(id));
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
	
	public boolean isFinal() {
		return this.isFinal;
	}
	
	public boolean isInitial() {
		return this.isInitial;
	}	
	
	@Override public String toString() {
		String string = "Q";
		if (this.isInitial())
			string = "^" + string; /*Character.toChars(Integer.parseInt("0303", 16))[0];*/
		if (this.isFinal())
			string += "*"; /*Character.toChars(Integer.parseInt("02DF", 16))[0];*/
		string += this.getId();
		return string;
	}
	
	@Override public boolean equals(Object obj) {
		if (this.getClass() != obj.getClass())
			return false;
		
		State other = (State) obj;
		
		return this.getId() == other.getId();
	}
	
	@Override public int compareTo(State other) {
		return this.getId().compareTo(other.getId());
	}
	
	@Override public int hashCode() {
		return Objects.hash(this.getId());
	}

}
