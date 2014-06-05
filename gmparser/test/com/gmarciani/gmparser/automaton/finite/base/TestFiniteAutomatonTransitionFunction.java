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


package com.gmarciani.gmparser.automaton.finite.base;

import org.junit.Test;

import com.gmarciani.gmparser.models.automaton.finite.TransitionFunction;
import com.gmarciani.gmparser.models.automaton.state.States;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;

public class TestFiniteAutomatonTransitionFunction {

	@Test public void create() {
		Alphabet alphabet = new Alphabet('a', 'b', 'c', 'd');
		States states = new States(1, 2, 3, 4, 5);
		
		TransitionFunction function = new TransitionFunction(states, alphabet);
		
		//System.out.println(function);		
		//System.out.println(function.toFormattedString());
		
	}
	
	@Test public void createEmptyAlphabet() {
		Alphabet alphabet = new Alphabet();	
		States states = new States(1, 2, 3, 4, 5);
		
		TransitionFunction function = new TransitionFunction(states, alphabet);
		
		//System.out.println(function);	
		//System.out.println(function.toFormattedString());
	}

}
