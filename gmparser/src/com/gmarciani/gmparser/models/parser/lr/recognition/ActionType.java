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

package com.gmarciani.gmparser.models.parser.lr.recognition;

/**
 * <p>LR(1) parser action type enumeration model.<p>
 * <p>Available action types are: 
 * shift, 
 * reduce,
 * goto and
 * accept.<p>
 * 
 * @see com.gmarciani.gmparser.models.parser.lr.recognition.Action
 * @see com.gmarciani.gmparser.models.parser.lr.recognition.LROneMatrix
 * @see com.gmarciani.gmparser.models.parser.lr.LROneParser
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public enum ActionType {
	
	SHIFT("Shift", "sh"),
	REDUCE("Reduce", "red"),
	GOTO("Goto", "go"),
	ACCEPT("Accept", "acc");
	
	private String name;
	private String shortName;
	
	private ActionType(String name, String shortName) {
		this.name = name;
		this.shortName = shortName;
	}

	/**
	 * Returns the action type name.
	 * 
	 * @return the action type name.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the action type short name.
	 * 
	 * @return the action type short name.
	 */
	public String getShortName() {
		return this.shortName;
	}
	
	@Override public String toString() {
		return this.getShortName();
	}

}
