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

package com.gmarciani.gmparser;

import static org.junit.Assert.*;


import org.junit.Test;

import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.pattern.ProductionPattern;
import com.gmarciani.gmparser.models.grammar.pattern.ProductionPatternBuilder;
import com.gmarciani.gmparser.models.grammar.production.Production;

public class TestProductionPattern {

	@SuppressWarnings("static-access")
	@Test
	public void testProductionPatternBuilding() {
		Alphabet charsetA = new Alphabet();
		Alphabet charsetB = new Alphabet();
		Alphabet charsetC = new Alphabet();
		
		charsetA.add('a');
		charsetA.add('b');
		charsetA.add('c');
		
		charsetB.add('d');
		charsetB.add('e');
		charsetB.add('f');
		
		charsetC.add('g');
		charsetC.add('h');
		charsetC.add('i');
		
		ProductionPattern pattern = ProductionPatternBuilder
				.hasPattern("A->B+C.")
				.withItem('A', charsetA)
				.withItem('B', charsetB)
				.withItem('C', charsetC)
				.create();
		
		Production productionToMatchTrue = new Production("b", "defedg");
		Production productionToMatchFalse = new Production("b", "gdefed");
		
		assertTrue("Incorrect production pattern matching (should be matched)",
				(pattern.match(productionToMatchTrue)));
		
		assertFalse("Incorrect production pattern matching (should not be matched)",
				(pattern.match(productionToMatchFalse)));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testProductionPatternExtraction() {
		Alphabet charsetA = new Alphabet();
		Alphabet charsetB = new Alphabet();
		Alphabet charsetC = new Alphabet();
		
		charsetA.add('a');
		charsetA.add('b');
		charsetA.add('c');
		
		charsetB.add('d');
		charsetB.add('e');
		charsetB.add('f');
		
		charsetC.add('g');
		charsetC.add('h');
		charsetC.add('i');
		
		ProductionPattern pattern = ProductionPatternBuilder
				.hasPattern("A->B+C.")
				.withItem('A', charsetA)
				.withItem('B', charsetB)
				.withItem('C', charsetC)
				.create();
		
		Production productionToExtractFrom = new Production("c", "defedg");
		String shouldBeExtracted = "defed";
		
		String extracted = pattern.extract('B', productionToExtractFrom);
		
		System.out.println(extracted);
		
		assertTrue("Incorrect production pattern extraction",
				(extracted.equals(shouldBeExtracted)));
	}

}
