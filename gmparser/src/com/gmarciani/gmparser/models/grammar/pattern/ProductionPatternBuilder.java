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
import java.util.Map;

import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.production.Production;
import com.gmarciani.gmparser.models.grammar.production.Productions;
import com.gmarciani.gmparser.models.grammar.production.ProductionsBuilder;

public class ProductionPatternBuilder {
	
	private static ProductionPatternBuilder instance = new ProductionPatternBuilder();	
	
	private static Productions patternProductions;
	private static Map<Character, Alphabet> alphabetMap;	

	public ProductionPatternBuilder() {
		patternProductions = new Productions();
		alphabetMap = new HashMap<Character, Alphabet>();
	}
	
	private static void reset() {
		patternProductions.clear();
		alphabetMap.clear();
	}
	
	//[(S,Aa),(A,a)]
	@SuppressWarnings("static-access")
	public static ProductionPatternBuilder hasPattern(Productions productions) {
		Productions created = ProductionsBuilder
				.hasProductions(productions)
				.create();
			
		for (Production prod : created)
			patternProductions.add(prod);
			
		return instance;
	}
		
	//(S,Aa)
	@SuppressWarnings("static-access")
	public static ProductionPatternBuilder hasPattern(Production production) {
		Productions created = ProductionsBuilder
				.hasProduction(production)
				.create();
			
		for (Production prod : created)
			patternProductions.add(prod);
			
		return instance;
	}
		
	//(S,Aa)
	@SuppressWarnings("static-access")
	public static ProductionPatternBuilder hasPattern(String left, String right) {
		Productions created = ProductionsBuilder
				.hasProduction(left, right)
				.create();
			
		for (Production prod : created)
			patternProductions.add(prod);
			
		return instance;
	}	
		
	//S->Aa|a;A->a. (default separators)
	@SuppressWarnings("static-access")
	public static ProductionPatternBuilder hasPattern(String productions) {
		Productions created = ProductionsBuilder
				.hasProductions(productions)
				.create();
			
		for (Production prod : created)
			patternProductions.add(prod);
			
		return instance;
	}
		
	//S->Aa|a;A->a.
	@SuppressWarnings("static-access")
	public static ProductionPatternBuilder hasPattern(String productions, String memberSeparator, String infixSeparator, String productionSeparator, String productionEnder) {
		Productions created = ProductionsBuilder
				.hasProductions(productions, memberSeparator, infixSeparator, productionSeparator, productionEnder)
				.create();
			
		for (Production prod : created)
			patternProductions.add(prod);
			
		return instance;
	}	
	
	public static ProductionPatternBuilder withItem(Character item, Alphabet alphabet) {
		alphabetMap.put(item, alphabet);
		
		return instance;
	}
	
	public static ProductionPattern create() {
		ProductionPattern productionPattern = new ProductionPattern(alphabetMap);
		
		for (Production production : patternProductions) {
			productionPattern.add(production);
		}
		
		reset();
		
		return productionPattern;
	}	

}
