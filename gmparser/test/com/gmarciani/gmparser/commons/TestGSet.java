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

package com.gmarciani.gmparser.commons;

import static org.junit.Assert.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.junit.Test;

import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.automaton.state.StateId;
import com.gmarciani.gmparser.models.commons.set.GSet;

public class TestGSet {

	@Test public void create() {
		Character symbols[] = {'a', 'b', 'c'};
		Character pSymbolsOne[] = {'a'};
		Character pSymbolsTwo[] = {'b', 'c'};
		
		GSet<Character> setOne = new GSet<Character>();
		setOne.add('a');
		setOne.add('b');
		setOne.add('c');		
		
		GSet<Character> setTwo = new GSet<Character>('a');
		setTwo.add('b');
		setTwo.add('c');
		
		GSet<Character> setThree = new GSet<Character>('a', 'b', 'c');
		
		GSet<Character> setFour = new GSet<Character>(symbols);		
		
		GSet<Character> setFive = new GSet<Character>(pSymbolsOne, pSymbolsTwo);
		
		GSet<Character> setSix = new GSet<Character>(setOne, setTwo, setThree);
		
		GSet<Character> setSeven = new GSet<Character>(setOne);
		
		assertEquals("Uncorrect GSet creation. Should be equals: " + setOne + " and " + setTwo, setOne, setTwo);
		assertEquals("Uncorrect GSet creation. Should be equals: " + setTwo + " and " + setThree, setTwo, setThree);
		assertEquals("Uncorrect GSet creation. Should be equals: " + setThree + " and " + setFour, setThree, setFour);
		assertEquals("Uncorrect GSet creation. Should be equals: " + setFour + " and " + setFive, setFour, setFive);
		assertEquals("Uncorrect GSet creation. Should be equals: " + setFive + " and " + setSix, setFive, setSix);
		assertEquals("Uncorrect GSet creation. Should be equals: " + setSix + " and " + setSeven, setSix, setSeven);
	}
	
	@Test public void add() {
		GSet<Character> set = new GSet<Character>();
		
		List<Character> list = new ArrayList<Character>();
		list.add('a');
		list.add('b');
		list.add('c');		
		
		for (Character c : list)
			assertTrue("Uncorrect GSet insertion. Should be added: " + c + " in " + set, set.add(c));
		
		for (Character c : list)
			assertFalse("Uncorrect GSet insertion. Should not be added: " + c + " in " + set, set.add(c));		
	}
	
	@Test public void addCustom() {
		State<Object> stateOne = new State<Object>(new StateId(1));
		State<Object> stateTwo = new State<Object>(new StateId(2));
		State<Object> stateThree = new State<Object>(new StateId(3));
		State<Object> stateOneTwo = new State<Object>(new StateId(1, 2));
		
		GSet<State<Object>> set = new GSet<State<Object>>();
		
		List<State<Object>> list = new ArrayList<State<Object>>();
		list.add(stateOne);
		list.add(stateTwo);
		list.add(stateThree);
		list.add(stateOneTwo);
		
		for (State<Object> state : list)
			assertTrue("Uncorrect GSet insertion. Should be added: " + state + " in " + set, set.add(state));
		
		for (State<Object> state : list)
			assertFalse("Uncorrect GSet insertion. Should not be added: " + state + " in " + set, set.add(state));		
	}
	
	@Test public void iterate() {
		GSet<Integer> set = new GSet<Integer>(1, 2, 3);
		
		for (int n : set)
			if (n < 100)
				set.add(n *2);
		
		Queue<Integer> queue = new ArrayDeque<Integer>();
		queue.addAll(set);
		while(!queue.isEmpty()) {
			int n = queue.poll();
			if (n < 100) {
				queue.add(n*2);
				set.add(n);
			}
		}
		
	}

}
