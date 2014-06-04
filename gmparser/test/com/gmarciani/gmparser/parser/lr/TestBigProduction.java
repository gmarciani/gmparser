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

package com.gmarciani.gmparser.parser.lr;

import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.production.Production;
import com.gmarciani.gmparser.models.parser.lr.bigproduction.BigProduction;

public class TestBigProduction {

	@Test public void create() {
		BigProduction bigProduction = new BigProduction(new Production("S", "ABcdEF"), 1, new Alphabet("cd$"));
		//System.out.println(bigProduction);
	}
	
	@Test public void sort() {
		BigProduction bigProductionOne = new BigProduction(new Production("A", "ABC"), 0, new Alphabet("cd$"));
		BigProduction bigProductionTwo = new BigProduction(new Production("A", "ABC"), 1, new Alphabet("cd$"));
		BigProduction bigProductionThree = new BigProduction(new Production("B", "ABC"), 0, new Alphabet("cd$"));
		BigProduction bigProductionFour = new BigProduction(new Production("B", "ABC"), 1, new Alphabet("cd$"));
		BigProduction bigProductionFive = new BigProduction(new Production("A", "XYZ"), 0, new Alphabet("cd$"));
		BigProduction bigProductionSix = new BigProduction(new Production("A", "XYZ"), 1, new Alphabet("cd$"));
		BigProduction bigProductionSeven = new BigProduction(new Production("B", "XYZ"), 0, new Alphabet("cd$"));
		BigProduction bigProductionEight = new BigProduction(new Production("B", "XYZ"), 1, new Alphabet("cd$"));
		
		SortedSet<BigProduction> set = new TreeSet<BigProduction>();
		set.add(bigProductionOne);
		set.add(bigProductionTwo);
		set.add(bigProductionThree);
		set.add(bigProductionFour);
		set.add(bigProductionFive);
		set.add(bigProductionSix);
		set.add(bigProductionSeven);
		set.add(bigProductionEight);

		//System.out.println(set);
	}

}
