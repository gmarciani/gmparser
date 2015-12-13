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
package com.gmarciani.gmparser.automaton.base;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.commons.set.GSet;

public class TestState {

	@Test public void equal() {
		State<Object> stateOne = new State<Object>(1);
		State<Object> stateTwo = new State<Object>(2);
		
		assertEquals("Uncorrect state equality. Should be equals: " + stateOne + " and " + stateOne, 
				stateOne, stateOne);
		assertNotEquals("Uncorrect state equality. Should not be equals: " + stateOne + " and " + stateTwo, 
				stateOne, stateTwo);
	}
	
	@Test public void represent() {
		State<String> stateOne = new State<String>(1, "one");
		State<String> stateTwo = new State<String>(2, "two");
		
		GSet<State<String>> states = new GSet<State<String>>();
		states.add(stateOne);
		states.add(stateTwo);
		
		for (State<String> state : states)
			System.out.println(state);
		
		for (State<String> state : states)
			System.out.println(state.toExtendedString());
	}

}
