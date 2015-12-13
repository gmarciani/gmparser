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

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.gmarciani.gmparser.models.commons.function.Function;
import com.gmarciani.gmparser.models.commons.function.NonDeterministicFunction;
import com.gmarciani.gmparser.models.commons.set.GSet;

public class TestNonDeterministicFunction {
	
	@Test public void createCompleteFunction() {
		System.out.println("#createCompleteFunction");
		GSet<Character> domainX = new GSet<Character>();
		domainX.add('a');
		domainX.add('b');
		domainX.add('c');
		GSet<Integer> domainY = new GSet<Integer>();
		domainY.add(1);
		domainY.add(2);
		domainY.add(3);
		GSet<String> domainZ = new GSet<String>();
		
		for (Character c : domainX) {
			for (Integer n : domainY) {
				for (int i = 1; i <= n; i ++)
					domainZ.add(StringUtils.repeat(c, i));
			}
		}
		
		Function<Character, Integer, String> function = new NonDeterministicFunction<Character, Integer, String>(domainX, domainY, domainZ);
		
		for (Character c : domainX) {
			for (Integer n : domainY) {
				for (int i = 1; i <= n; i ++)
					function.add(c, n, StringUtils.repeat(c, i));
			}
		}
				
		
		System.out.println(function);
		System.out.println(function.toFormattedFunction());
	}
	
	@Test public void createCompleteAndRemoveAllX() {
		System.out.println("#createCompleteAndRemoveAllX");
		GSet<Character> domainX = new GSet<Character>();
		domainX.add('a');
		domainX.add('b');
		domainX.add('c');
		GSet<Integer> domainY = new GSet<Integer>();
		domainY.add(1);
		domainY.add(2);
		domainY.add(3);
		GSet<String> domainZ = new GSet<String>();
		
		for (Character c : domainX) {
			for (Integer n : domainY) {
				for (int i = 1; i <= n; i ++)
					domainZ.add(StringUtils.repeat(c, i));
			}
		}
		
		Function<Character, Integer, String> function = new NonDeterministicFunction<Character, Integer, String>(domainX, domainY, domainZ);
		
		for (Character c : domainX) {
			for (Integer n : domainY) {
				for (int i = 1; i <= n; i ++)
					function.add(c, n, StringUtils.repeat(c, i));
			}
		}
		
		function.removeAllForX('b');
		
		System.out.println(function);
		System.out.println(function.toFormattedFunction());
	}
	
	@Test public void createCompleteAndRemoveAllY() {
		System.out.println("#createCompleteAndRemoveAllY");
		GSet<Character> domainX = new GSet<Character>();
		domainX.add('a');
		domainX.add('b');
		domainX.add('c');
		GSet<Integer> domainY = new GSet<Integer>();
		domainY.add(1);
		domainY.add(2);
		domainY.add(3);
		GSet<String> domainZ = new GSet<String>();
		
		for (Character c : domainX) {
			for (Integer n : domainY) {
				for (int i = 1; i <= n; i ++)
					domainZ.add(StringUtils.repeat(c, i));
			}
		}
		
		Function<Character, Integer, String> function = new NonDeterministicFunction<Character, Integer, String>(domainX, domainY, domainZ);
		
		for (Character c : domainX) {
			for (Integer n : domainY) {
				for (int i = 1; i <= n; i ++)
					function.add(c, n, StringUtils.repeat(c, i));
			}
		}
		
		function.removeAllForY(2);
		
		System.out.println(function);
		System.out.println(function.toFormattedFunction());
	}
	
	@Test public void createCompleteAndRemoveAllXY() {
		System.out.println("#createCompleteAndRemoveAllXY");
		GSet<Character> domainX = new GSet<Character>();
		domainX.add('a');
		domainX.add('b');
		domainX.add('c');
		GSet<Integer> domainY = new GSet<Integer>();
		domainY.add(1);
		domainY.add(2);
		domainY.add(3);
		GSet<String> domainZ = new GSet<String>();
		
		for (Character c : domainX) {
			for (Integer n : domainY) {
				for (int i = 1; i <= n; i ++)
					domainZ.add(StringUtils.repeat(c, i));
			}
		}
		
		Function<Character, Integer, String> function = new NonDeterministicFunction<Character, Integer, String>(domainX, domainY, domainZ);
		
		for (Character c : domainX) {
			for (Integer n : domainY) {
				for (int i = 1; i <= n; i ++)
					function.add(c, n, StringUtils.repeat(c, i));
			}
		}
		
		function.removeAllForXY('b', 2);
		
		System.out.println(function);
		System.out.println(function.toFormattedFunction());
	}
	
	@Test public void createIncompleteFunction() {
		System.out.println("#createIncompleteFunction");
		GSet<Character> domainX = new GSet<Character>();
		domainX.add('a');
		domainX.add('b');
		domainX.add('c');
		GSet<Integer> domainY = new GSet<Integer>();
		domainY.add(1);
		domainY.add(2);
		domainY.add(3);
		GSet<String> domainZ = new GSet<String>();
		
		for (Character c : domainX)
			for (Integer n : domainY)
				domainZ.add(StringUtils.repeat(c, n));
		
		Function<Character, Integer, String> function = new NonDeterministicFunction<Character, Integer, String>(domainX, domainY, domainZ);
		
		function.add('a', 1, "a");
		function.add('b', 2, "bb");
		function.add('c', 3, "ccc");
		
		System.out.println(function);
		System.out.println(function.toFormattedFunction());
	}

	@Test public void createEmptyFunction() {
		System.out.println("#createEmptyFunction");
		GSet<Character> domainX = new GSet<Character>();
		domainX.add('a');
		domainX.add('b');
		domainX.add('c');
		GSet<Integer> domainY = new GSet<Integer>();
		domainY.add(1);
		domainY.add(2);
		domainY.add(3);
		GSet<String> domainZ = new GSet<String>();
		
		Function<Character, Integer, String> function = new NonDeterministicFunction<Character, Integer, String>(domainX, domainY, domainZ);
		
		System.out.println(function);
		System.out.println(function.toFormattedFunction());
	}
	
	@Test public void createEmptyDomainX() {
		System.out.println("#createEmptyDomainX");
		GSet<Character> domainX = new GSet<Character>();
		GSet<Integer> domainY = new GSet<Integer>();
		domainY.add(1);
		domainY.add(2);
		domainY.add(3);
		GSet<String> domainZ = new GSet<String>();
		
		Function<Character, Integer, String> function = new NonDeterministicFunction<Character, Integer, String>(domainX, domainY, domainZ);
		
		System.out.println(function);
		System.out.println(function.toFormattedFunction());
	}
	
	@Test public void createEmptyDomainY() {
		System.out.println("#createEmptyDomainY");
		GSet<Character> domainX = new GSet<Character>();
		domainX.add('a');
		domainX.add('b');
		domainX.add('c');
		GSet<Integer> domainY = new GSet<Integer>();
		GSet<String> domainZ = new GSet<String>();
		
		Function<Character, Integer, String> function = new NonDeterministicFunction<Character, Integer, String>(domainX, domainY, domainZ);
		
		System.out.println(function);
		System.out.println(function.toFormattedFunction());
	}
	
	@Test public void createEmptyDomainXY() {
		System.out.println("#createEmptyDomainXY");
		GSet<Character> domainX = new GSet<Character>();
		GSet<Integer> domainY = new GSet<Integer>();
		GSet<String> domainZ = new GSet<String>();
		
		Function<Character, Integer, String> function = new NonDeterministicFunction<Character, Integer, String>(domainX, domainY, domainZ);
		
		System.out.println(function);
		System.out.println(function.toFormattedFunction());
	}

}
