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

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarBuilder;
import com.gmarciani.gmparser.models.grammar.Production;
import com.gmarciani.gmparser.models.grammar.Productions;

public class TestGrammarBuilding {
	
	private static final int NON_TERMINALS = 5;
	private static final int TERMINALS = 5;
	private static final int PRODUCTIONS = 5;
	private static final int PRODUCTION_LENGHT = 3;
	
	private static final String EMPTY_STRING = Grammar.EMPTY_STRING;
	
	private static final String MEMBER_SEPARATOR = "->";
	private static final String INFIX_SEPARATOR = "|";
	private static final String PRODUCTION_SEPARATOR = ";";

	@Test
	public void testGrammarBuildings() {
		Map<String, Grammar> grammars = new HashMap<String, Grammar>();
		
		Set<Character> nonTerminals = getRandomNonTerminals(NON_TERMINALS);
		Set<Character> terminals = getRandomTerminals(TERMINALS);	
		
		Character axiom = getRandomCharacter(nonTerminals);
		String empty = EMPTY_STRING;
		
		Productions productions = getRandomProductions(nonTerminals, terminals, PRODUCTIONS, PRODUCTION_LENGHT);	
		
		Grammar grammarProduction = buildGrammarProductionAsProduction(productions, axiom, empty);
		grammars.put("Production (as Production)", grammarProduction);
		Grammar grammarLeftRightString = buildGrammarProductionAsLeftRightString(productions, axiom, empty);
		grammars.put("Production (as String)", grammarLeftRightString);
		Grammar grammarString = buildGrammarProductionsAsString(productions, axiom, empty);
		grammars.put("Productions (as String)", grammarString);
		
		for (Map.Entry<String, Grammar> entry : grammars.entrySet()) {
			String modality = entry.getKey();
			Grammar grammar = entry.getValue();
			
			for (String left : productions.getNonTerminals()) {
				for (String right : productions.getSententialsFor(left)) {
					String production = left + MEMBER_SEPARATOR + right;
					assertTrue("Grammar built with " + modality + " doesn't contain the production: " + production, 
							grammar.getProductions().getSententialsFor(left).contains(right));
				}			
			}
		}		
		
		System.out.println("#INPUT");
		System.out.println("- NON TERMINAL ALPHABET: " + nonTerminals);
		System.out.println("- TERMINAL ALPHABET: " + terminals);
		System.out.println("- AXIOM: " + axiom);
		System.out.println("- EMPTY: " + empty);
		System.out.println("- PRODUCTIONS: " + productions);
		
		System.out.println("\n");		

		for (Map.Entry<String, Grammar> entry : grammars.entrySet()) {
			String modality = entry.getKey();
			Grammar grammar = entry.getValue();
			
			System.out.println("#OUTPUT " + modality);
			System.out.println(grammar);
			System.out.println("\n");
		}
	}	

	@SuppressWarnings("static-access")
	private Grammar buildGrammarProductionAsProduction(Productions productions, Character axiom, String empty) {
		GrammarBuilder builder = GrammarBuilder.withAxiom(axiom).withEmpty(empty);
		
		for (Production production : productions) {
			builder.hasProduction(production);
		}
		
		Grammar grammar = builder.create();
		
		return grammar;
	}
	
	@SuppressWarnings("static-access")
	private Grammar buildGrammarProductionAsLeftRightString(Productions productions, Character axiom, String empty) {
		GrammarBuilder builder = GrammarBuilder.withAxiom(axiom).withEmpty(empty);
		
		for (Production production : productions) {
			String left = production.getLeft();
			String right = production.getRight();
			
			builder.hasProduction(left, right);
		}
		
		Grammar grammar = builder.create();
		
		return grammar;
	}
	
	@SuppressWarnings("static-access")
	private Grammar buildGrammarProductionsAsString(Productions productions, Character axiom, String empty) {
		GrammarBuilder builder = GrammarBuilder.withAxiom(axiom).withEmpty(empty);
		
		String string = "";
		
		Iterator<String> iLefts = productions.getNonTerminals().iterator();
		while (iLefts.hasNext()) {
			String left = iLefts.next();
			string += left + MEMBER_SEPARATOR;
			Iterator<String> iRights = productions.getSententialsFor(left).iterator();
			while (iRights.hasNext()) {
				String right = iRights.next();
				string += right;
				if (iRights.hasNext())
					string += INFIX_SEPARATOR;
			}
			if (iLefts.hasNext())
				string += PRODUCTION_SEPARATOR;
		}
		
		Grammar grammar = builder.hasProductionsAsString(string, MEMBER_SEPARATOR, INFIX_SEPARATOR, PRODUCTION_SEPARATOR)
				.create();
		
		return grammar;
	}	

	private Productions getRandomProductions(Set<Character> nonTerminals, Set<Character> terminals, int n, int lenght) {
		Productions productions = new Productions();
		
		Set<Character> alphabet = new LinkedHashSet<Character>();
		alphabet.addAll(nonTerminals);
		alphabet.addAll(terminals);
		
		for (int i = 1; i <= n; i++) {
			String left = getRandomString(nonTerminals, 1);			
			String right = getRandomString(alphabet, lenght);
			
			productions.add(left, right);
		}
		
		return productions;
	}

	private Set<Character> getRandomNonTerminals(int n) {
		Set<Character> nonTerminals = new LinkedHashSet<Character>();
		
		Random r = new Random();
		
		for (int i = 1; i <= n; i++) {
			Character c = (char)(r.nextInt(26) + 'A');
			nonTerminals.add(c);
		}
		
		return nonTerminals;
	}
	
	private Set<Character> getRandomTerminals(int n) {
		Set<Character> terminals = new LinkedHashSet<Character>();
		
		Random r = new Random();
		
		for (int i = 1; i <= n; i++) {
			Character c = (char)(r.nextInt(26) + 'a');
			terminals.add(c);
		}
		
		return terminals;
	}		
	
	private String getRandomString(Set<Character> alphabet, int length) {
		String s = "";
		
		for (int i = 1; i <= length; i++) {
			Character c = getRandomCharacter(alphabet);
			s += String.valueOf(c);
		}
		
		return s;
	}
	
	private Character getRandomCharacter(Set<Character> alphabet) {
		Random r = new Random();
		int item = r.nextInt(alphabet.size());
		Character c = (Character) alphabet.toArray()[item];
		
		return c;
	}	

}
