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

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.production.Production;

public class ProductionPattern  extends TreeSet<ProductionPatternItem> {
	
	private static final long serialVersionUID = 6971391542009941434L;
	
	private Map<Character, Alphabet> alphabetMap;	
	
	public ProductionPattern(Map<Character, Alphabet> alphabetMap) {
		super();
		this.alphabetMap = alphabetMap;
	}
	
	public Map<Character, Alphabet> getAlphabetMap() {
		return this.alphabetMap;
	}

	public void setAlphabetMap(Map<Character, Alphabet> alphabetMap) {
		this.alphabetMap = alphabetMap;
	}
	
	public boolean add(Production production) {
		String left = production.getLeft();
		String right = production.getRight();
		ProductionPatternItem pattern = new ProductionPatternItem(left, right, this.alphabetMap);
		
		return this.add(pattern);
	}
	
	public boolean match(Production production) {
		for (ProductionPatternItem pattern : this) {
			if (pattern.match(production))
				return true;
		}
		
		return false;
	}

	public String extract(Character symbol, Production production) {
		Set<ProductionPatternItem> patternsForSymbol = this.getPatternForSymbol(symbol);
		for (ProductionPatternItem pattern : patternsForSymbol) {
			if (pattern.match(production)){
				String extracted = pattern.extract(symbol, production);
				if (!extracted.equals(""))
					return extracted;
			}
		}
		return "";
	}
	
	

	private Set<ProductionPatternItem> getPatternForSymbol(Character symbol) {
		Set<ProductionPatternItem> target = new TreeSet<ProductionPatternItem>();
		for (ProductionPatternItem pattern : this) {
			if (pattern.getSymbols().contains(symbol))
				target.add(pattern);
		}
		
		return target;
	}	

}
