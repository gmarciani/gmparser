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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarBuilder;
import com.gmarciani.gmparser.models.grammar.production.Production;
import com.gmarciani.gmparser.models.grammar.production.Productions;

public class TestGrammar {

	@SuppressWarnings("static-access")
	@Test
	public void testGrammarBuilding() {
		Productions productions = new Productions();
		
		Production prodOne = new Production("S","Aa");
		Production prodTwo = new Production("S","a");
		Production prodThree = new Production("A","a");
		Production prodFour = new Production("A", Grammar.EMPTY_STRING);
		
		productions.add(prodOne);
		productions.add(prodTwo);
		productions.add(prodThree);
		productions.add(prodFour);
		
		
		Grammar grammarOne = GrammarBuilder
				.hasProductions(productions)
				.withAxiom('S')
				.withEmpty(Grammar.EMPTY_STRING)
				.create();
		
		Grammar grammarTwo = GrammarBuilder
				.hasProduction(prodOne)
				.hasProduction(prodTwo)
				.hasProduction(prodThree)
				.hasProduction(prodFour)
				.withAxiom('S')
				.withEmpty(Grammar.EMPTY_STRING)
				.create();
		
		Grammar grammarThree = GrammarBuilder
				.hasProduction(prodOne.getLeft(), prodOne.getRight())
				.hasProduction(prodTwo.getLeft(), prodTwo.getRight())
				.hasProduction(prodThree.getLeft(), prodThree.getRight())
				.hasProduction(prodFour.getLeft(), prodFour.getRight())
				.withAxiom('S')
				.withEmpty(Grammar.EMPTY_STRING)
				.create();
		
		Grammar grammarFour = GrammarBuilder
				.hasProductions("S->Aa|a;A->a|" + Grammar.EMPTY_STRING + ".")
				.withAxiom('S')
				.withEmpty(Grammar.EMPTY_STRING)
				.create();
				
		Map<String, Grammar> grammars = new HashMap<String, Grammar>();
		grammars.put("GrammarOne", grammarOne);
		grammars.put("GrammarTwo", grammarTwo);
		grammars.put("GrammarThree", grammarThree);
		grammars.put("GrammarFour", grammarFour);
		
		for (Map.Entry<String, Grammar> i : grammars.entrySet()) {
			for (Map.Entry<String, Grammar> j : grammars.entrySet()) {
				assertTrue("Incorrect grammar building: " + i.getKey() + " different from " + j.getKey(),
						i.getValue().equals(j.getValue()));
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testGrammarInput() {
		String input = getInput();
		
		Grammar grammar = GrammarBuilder
				.hasProductions(input)
				.withAxiom('S')
				.withEmpty(Grammar.EMPTY_STRING)
				.create();
		
		System.out.println(grammar);
		
	}
	
	private String getInput() {
		System.out.print("Grammar: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
	    try {
	    	input = br.readLine();
		} catch (NumberFormatException | IOException e) {
			
		}
	    System.out.println("\n");
		return input;
	}

}
