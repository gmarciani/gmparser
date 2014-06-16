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

package com.gmarciani.gmparser.models.parser.lr.action;

import java.util.Objects;

/**
 * @author Giacomo Marciani
 * @version 1.0
 */
public class Action implements Comparable<Action> {
	
	private final ActionType type;
	private final Integer value;

	public Action(ActionType type, Integer value) {
		this.type = type;
		this.value = (value != null) ? value : -1;
	}
	
	public ActionType getType() {
		return this.type;
	}
	
	public Integer getValue() {
		return this.value;
	}
	
	public boolean isActionType(ActionType type) {
		return this.getType().equals(type);
	}
	
	@Override public String toString() {
		return "(" + this.getType().getShortName() + ((this.getValue() != -1) ? "," + this.getValue() : "") + ")";
	}

	@Override public int compareTo(Action other) {
		int byType = this.getType().compareTo(other.getType());
		int byValue = this.getValue().compareTo(other.getValue());
		if (byType == 0)
			return byValue;
		return byType;
	}
	
	@Override public boolean equals(Object obj) {
		if (this.getClass() != obj.getClass())
			return false;
		
		Action other = (Action) obj;
		
		return (this.getType().equals(other.getType())
				&& this.getValue().equals(other.getValue()));
	}
	
	@Override public int hashCode() {
		return Objects.hash(this.getType(), this.getValue());
	}	

}
