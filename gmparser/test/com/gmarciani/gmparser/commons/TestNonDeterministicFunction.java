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

import org.junit.Test;

import com.gmarciani.gmparser.models.commons.function.NonDeterministicFunction;
import com.gmarciani.gmparser.models.commons.set.GSet;

public class TestNonDeterministicFunction {
	
	@Test public void createCompleteAndRemoveAllXY() {
		System.out.println("#createCompleteAndRemoveAllXY");
		GSet<Character> domainX = new GSet<Character>('a', 'b', 'c');
		GSet<Character> domainY = new GSet<Character>('d', 'e', 'f');
		
		NonDeterministicFunction<Character, Character, String> function = new NonDeterministicFunction<Character, Character, String>(domainX, domainY);
		for (Character x : domainX) {
			for (Character y : domainY) {
				function.add(x, y, "" + x + y);
				function.add(x, y, "" + y + x);
			}				
		}			
		
		function.removeAllForXY('b', 'e');
		
		System.out.println(function);
		System.out.println(function.toFormattedFunction());
	}
	
	@Test public void createCompleteAndRemoveAllX() {
		System.out.println("#createCompleteAndRemoveAllX");
		GSet<Character> domainX = new GSet<Character>('a', 'b', 'c');
		GSet<Character> domainY = new GSet<Character>('d', 'e', 'f');
		
		NonDeterministicFunction<Character, Character, String> function = new NonDeterministicFunction<Character, Character, String>(domainX, domainY);
		for (Character x : domainX) {
			for (Character y : domainY) {
				function.add(x, y, "" + x + y);
				function.add(x, y, "" + y + x);
			}				
		}
		
		function.removeAllForX('b');
		
		System.out.println(function);
		System.out.println(function.toFormattedFunction());
	}
	
	@Test public void createCompleteAndRemoveAllY() {
		System.out.println("#createCompleteAndRemoveAllY");
		GSet<Character> domainX = new GSet<Character>('a', 'b', 'c');
		GSet<Character> domainY = new GSet<Character>('d', 'e', 'f');
		
		NonDeterministicFunction<Character, Character, String> function = new NonDeterministicFunction<Character, Character, String>(domainX, domainY);
		for (Character x : domainX) {
			for (Character y : domainY) {
				function.add(x, y, "" + x + y);
				function.add(x, y, "" + y + x);
			}				
		}
		
		function.removeAllForY('e');
		
		System.out.println(function);
		System.out.println(function.toFormattedFunction());
	}
	
	@Test public void createComplete() {
		System.out.println("#createComplete");
		GSet<Character> domainX = new GSet<Character>('a', 'b', 'c');
		GSet<Character> domainY = new GSet<Character>('d', 'e', 'f');
		
		NonDeterministicFunction<Character, Character, String> function = new NonDeterministicFunction<Character, Character, String>(domainX, domainY);
		for (Character x : domainX) {
			for (Character y : domainY) {
				function.add(x, y, "" + x + y);
				function.add(x, y, "" + y + x);
			}				
		}
		
		System.out.println(function);
		System.out.println(function.toFormattedFunction());
	}
	
	@Test public void createCompleteByTriples() {
		System.out.println("#createCompleteByTriples");
		GSet<Character> domainX = new GSet<Character>('a', 'b', 'c');
		GSet<Character> domainY = new GSet<Character>('d', 'e', 'f');
		
		NonDeterministicFunction<Character, Character, String> function = new NonDeterministicFunction<Character, Character, String>();
		for (Character x : domainX) {
			for (Character y : domainY) {
				function.add(x, y, "" + x + y);
				function.add(x, y, "" + y + x);
			}				
		}
		
		System.out.println(function);
		System.out.println(function.toFormattedFunction());
	}
	
	@Test public void createIncompleteCodomain() {
		System.out.println("#createIncompleteCodomain");
		GSet<Character> domainX = new GSet<Character>('a', 'b', 'c');
		GSet<Character> domainY = new GSet<Character>('d', 'e', 'f');
		
		NonDeterministicFunction<Character, Character, String> function = new NonDeterministicFunction<Character, Character, String>(domainX, domainY);
		function.add('a', 'b', "ab");
		
		System.out.println(function);
		System.out.println(function.toFormattedFunction());
	}

	@Test public void createCompleteDomainEmptyCodomain() {
		System.out.println("#createCompleteDomainEmptyCodomain");
		GSet<Character> domainX = new GSet<Character>('a', 'b', 'c');
		GSet<Character> domainY = new GSet<Character>('d', 'e', 'f');
		
		NonDeterministicFunction<Character, Character, String> function = new NonDeterministicFunction<Character, Character, String>(domainX, domainY);
		
		System.out.println(function);
		System.out.println(function.toFormattedFunction());
	}
	
	@Test public void createEmptyDomainX() {
		System.out.println("#createEmptyDomainX");
		GSet<Character> domainX = new GSet<Character>();
		GSet<Character> domainY = new GSet<Character>('d', 'e', 'f');
		
		NonDeterministicFunction<Character, Character, String> function = new NonDeterministicFunction<Character, Character, String>(domainX, domainY);
		
		System.out.println(function);
		System.out.println(function.toFormattedFunction());
	}
	
	@Test public void createEmptyDomainY() {
		System.out.println("#createEmptyDomainY");
		GSet<Character> domainX = new GSet<Character>('a', 'b', 'c');
		GSet<Character> domainY = new GSet<Character>();
		
		NonDeterministicFunction<Character, Character, String> function = new NonDeterministicFunction<Character, Character, String>(domainX, domainY);
		
		System.out.println(function);
		System.out.println(function.toFormattedFunction());
	}
	
	@Test public void createEmptyDomainXY() {
		System.out.println("#createEmptyDomainXY");
		GSet<Character> domainX = new GSet<Character>();
		GSet<Character> domainY = new GSet<Character>();
		
		NonDeterministicFunction<Character, Character, String> function = new NonDeterministicFunction<Character, Character, String>(domainX, domainY);
		
		System.out.println(function);
		System.out.println(function.toFormattedFunction());
	}

}
