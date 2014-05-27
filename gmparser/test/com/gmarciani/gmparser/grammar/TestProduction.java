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

package com.gmarciani.gmparser.grammar;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.production.Production;

public class TestProduction {

	@Test
	public void testProductionEmpty() {		
		Production prodOne = new Production("S", Grammar.EMPTY);
		Production prodTwo = new Production("S", Grammar.EMPTY + Grammar.EMPTY + Grammar.EMPTY);
		Production prodThree = new Production("S", Grammar.EMPTY + "A");
		Production prodFour = new Production("S", "A" + Grammar.EMPTY);
		Production prodFive = new Production("S", Grammar.EMPTY + "A" + Grammar.EMPTY);
		/*
		System.out.println("PRODUCTION ONE: " + prodOne);
		System.out.println("PRODUCTION TWO: " + prodTwo);
		System.out.println("PRODUCTION THREE: " + prodThree);
		System.out.println("PRODUCTION FOUR: " + prodFour);
		System.out.println("PRODUCTION FIVE: " + prodFive);*/
		
		assertTrue("Uncorrect equality of one or more epsilons",
				prodOne.equals(prodTwo));
		
		assertTrue("Uncorrect equality of mixed epsilons",
				prodThree.equals(prodFour)
				&& prodFour.equals(prodFive));		
	}

}
