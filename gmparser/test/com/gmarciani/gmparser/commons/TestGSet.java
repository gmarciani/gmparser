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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import com.gmarciani.gmparser.models.commons.set.GSet;

public class TestGSet {

	@Test public void create() {		
		GSet<Character> setOne = new GSet<Character>();
		setOne.add('a');
		setOne.add('b');
		setOne.add('c');		
		
		GSet<Character> setTwo = new GSet<Character>('a');
		setTwo.add('b');
		setTwo.add('c');
		
		GSet<Character> setThree = new GSet<Character>();
		setThree.add('a');
		setThree.add('b');
		setThree.add('c');
		
		GSet<Character> setFour = new GSet<Character>(setOne);
		
		assertEquals("Uncorrect GSet creation. Should be equals: " + setOne + " and " + setTwo, setOne, setTwo);
		assertEquals("Uncorrect GSet creation. Should be equals: " + setTwo + " and " + setThree, setTwo, setThree);
		assertEquals("Uncorrect GSet creation. Should be equals: " + setThree + " and " + setFour, setThree, setFour);
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

}
