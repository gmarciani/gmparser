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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.production.Production;

public class ProductionPatternItem implements Comparable<ProductionPatternItem> {

	private Pattern leftPattern;
	private Pattern rightPattern;
	
	private String left;
	private String right;
	
	private Map<Character, Alphabet> alphabetMap;
	
	public static final String MEMBER_SEPARATOR = "->";
	
	public ProductionPatternItem(String left, String right, Map<Character, Alphabet> alphabetMap) {
		this.left = left;
		this.right = right;
		this.alphabetMap = new HashMap<Character, Alphabet>(alphabetMap);
		this.leftPattern = this.generatePattern(left);
		this.rightPattern = this.generatePattern(right);
	}
	
	private Pattern generatePattern(String string) {
		String regex = "^";

		for (Character symbol : string.toCharArray()) {
			if (this.alphabetMap.containsKey(symbol)) {
				String expression = "[";
				Iterator<Character> iter = this.alphabetMap.get(symbol).iterator();
				while (iter.hasNext()) {
					expression += iter.next();
				}
				expression += "]";
				regex += expression;
			} else {
				regex += symbol;
			}
		}
		
		regex += "$";

		Pattern pattern = Pattern.compile(regex);
		return pattern;
	}

	public Pattern getLeftPattern() {
		return this.leftPattern;
	}

	public void setLeftPattern(Pattern leftPattern) {
		this.leftPattern = leftPattern;
	}

	public Pattern getRightPattern() {
		return this.rightPattern;
	}

	public void setRightPattern(Pattern rightPattern) {
		this.rightPattern = rightPattern;
	}
	
	public Alphabet getSymbols() {
		Alphabet target = new Alphabet();
		String symbols = this.left + this.right;
		
		for (Character symbol : symbols.toCharArray()) {
			target.add(symbol);
		}
		
		return target;
	}
	
	public boolean match(Production production) {
		return (production.getLeft().matches(this.getLeftPattern().pattern())
				&& production.getRight().matches(this.getRightPattern().pattern()));
	}
	
	//to check correctness
	public String extract(Character symbol, Production production) {
		Alphabet alphabet = this.alphabetMap.get(symbol);
		String regex = "[";
		for (Character c : alphabet) {
			regex += c;
		}
		regex += "]+";
				
		Matcher extractor = Pattern.compile(regex).matcher(production.getLeft() + Production.MEMBER_SEPARATOR + production.getRight());
		
		if (extractor.find()) {
			return extractor.group();
		}			
			
		return "";
		
	}

	@Override
	public String toString() {
		String s = this.getLeftPattern() + MEMBER_SEPARATOR + this.getRightPattern();
		return s;
	}
	
	@Override
	public int compareTo(ProductionPatternItem other) {
		int byLeft = this.leftPattern.pattern().compareTo(other.leftPattern.pattern());
		if (byLeft == 0)
			return this.rightPattern.pattern().compareTo(other.rightPattern.pattern());
		return byLeft;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;

		ProductionPatternItem other = (ProductionPatternItem) obj;
		
		return (this.leftPattern.equals(other.leftPattern) 
				&& this.rightPattern.equals(other.rightPattern));
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.leftPattern == null) ? 0 : this.leftPattern.hashCode());
		result = prime * result + ((this.rightPattern == null) ? 0 : this.rightPattern.hashCode());
		return result;
	}

	

	

			

}
