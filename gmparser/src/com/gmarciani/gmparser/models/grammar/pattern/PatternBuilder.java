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

package com.gmarciani.gmparser.models.grammar.pattern;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class PatternBuilder {
	
	private static PatternBuilder instance = new PatternBuilder();
	
	private static String string;
	private static Map<Character, Set<Character>> map;	

	public PatternBuilder() {
		string = "";
		map = new HashMap<Character, Set<Character>>();
	}
	
	private static void reset() {
		string = "";
		map.clear();
	}
	
	public static PatternBuilder hasPattern(String pattern) {
		string = pattern;
		
		return instance;
	}
	
	public static PatternBuilder withItem(Character patternItem, Set<Character> alphabet) {
		map.put(patternItem, alphabet);
		
		return instance;
	}
	
	public static Pattern create() {
		String reg = "";
		
		for (Character symbol : string.toCharArray()) {
			if (map.containsKey(symbol)) {
				String expression = "[";
				Iterator<Character> iter = map.get(symbol).iterator();
				while (iter.hasNext()) {
					expression += iter.next();
				}
				expression += "]";
				reg += expression;
			} else {
				reg += symbol;
			}
		}
		
		reset();
		
		Pattern pattern = Pattern.compile(reg);
		return pattern;
	}

}
