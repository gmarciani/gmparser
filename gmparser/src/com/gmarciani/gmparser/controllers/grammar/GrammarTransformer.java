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

package com.gmarciani.gmparser.controllers.grammar;

import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.collections.CollectionUtils;

import com.gmarciani.gmparser.controllers.ui.Listener;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarForm;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.alphabet.AlphabetType;
import com.gmarciani.gmparser.models.grammar.production.Production;

public class GrammarTransformer {
	
	@SuppressWarnings("unused")
	private static Listener output;	
	
	public static void setOutput(Listener listener) {
		output = listener;
	}

	public static Grammar transform(Grammar grammar, GrammarForm grammarForm) {
		return null;
	}
	
	public static void removeUngenerativeSymbols(Grammar grammar) {	
		
		//Vn'={}
		Alphabet acceptedNonTerminals = new Alphabet(AlphabetType.NON_TERMINAL);
		
		//(Vt U Vn')
		Alphabet acceptedAlphabet = new Alphabet();
		acceptedAlphabet.addAll(grammar.getTerminals());
		acceptedAlphabet.addAll(acceptedNonTerminals);
		
		boolean loop = true;
		while(loop) {
			loop = false;		
			
			acceptedAlphabet.addAll(acceptedNonTerminals);
			
			for (Production production : grammar.getProductions()) {
				if (production.isRightWithin(acceptedAlphabet)) {
					loop = acceptedNonTerminals.add(production.getLeftNonTerminals());
				}		
			}			
		}		
		
		grammar.retainAllProductionsWithin(acceptedAlphabet);
	}
	
	//to check!
	public static void removeUnreacheableSymbols(Grammar grammar) {		
		//Vt'={}
		Alphabet acceptedTerminals = new Alphabet(AlphabetType.TERMINAL);
		//Vn'={S}
		Alphabet acceptedNonTerminals = new Alphabet(AlphabetType.NON_TERMINAL);	
		acceptedNonTerminals.add(grammar.getAxiom());
		
		boolean loop = true;
		while(loop) {
			loop = false;
			
			for (Production production : grammar.getProductions()) {	
				if (production.isLeftWithin(acceptedNonTerminals)
						&& acceptedNonTerminals.containsAll(production.getRightNonTerminals()))
					loop = acceptedNonTerminals.add(production.getRightNonTerminals());
				
				if (production.isLeftWithin(acceptedNonTerminals))
					acceptedTerminals.add(production.getRightTerminals());
			}
		}		
		
		Alphabet acceptedAlphabet = new Alphabet();
		acceptedAlphabet.addAll(acceptedTerminals);
		acceptedAlphabet.addAll(acceptedNonTerminals);
		
		grammar.retainAllProductionsWithin(acceptedAlphabet);
	}
	
	public static void removeUselessSymbols(Grammar grammar) {
		removeUngenerativeSymbols(grammar);
		System.out.println("#grammar without ungeneratives: " + grammar);
		removeUnreacheableSymbols(grammar);
		System.out.println("#grammar without unreacheables: " + grammar);
	}
	
	public static void removeEpsilonProductions(Grammar grammar) {
		Alphabet nullables = grammar.getNullables();	
		
		Iterator<Production> iterProductions = grammar.getProductions().iterator();
		while(iterProductions.hasNext()) {
			Production production = iterProductions.next();
			if (!CollectionUtils.intersection(production.getRightNonTerminals(), nullables).isEmpty()) {
				Character lhs = production.getLeft();
				String rhs = production.getRight().replaceAll(nullables.getUnionRegex(), Grammar.EMPTY);
				Production productionWithoutNullables = new Production(lhs, rhs);
				grammar.addProduction(productionWithoutNullables);
			}
		}
		
		Iterator<Production> iterEpsilonProductions = grammar.getEpsilonProductions().iterator();
		while(iterEpsilonProductions.hasNext()) {
			Production epsilonProduction = iterEpsilonProductions.next();
			grammar.removeProduction(epsilonProduction);
		}
		
		if (nullables.contains(grammar.getAxiom()))
			grammar.addProduction(grammar.getAxiom(), Grammar.EMPTY);
	}
	
	public static void removeUnitProductions(Grammar grammar) {
		removeEpsilonProductions(grammar);
		
		Iterator<Production> iterTrivialUnitProductions = grammar.getTrivialUnitProductions().iterator();
		while(iterTrivialUnitProductions.hasNext()) {
			Production trivialUnitProduction = iterTrivialUnitProductions.next();
			grammar.removeProduction(trivialUnitProduction);
		}
		
		Queue<Production> queue = new ConcurrentLinkedQueue<Production>(grammar.getNonTrivialUnitProductions());
		while(!queue.isEmpty()) {
			Production nonTrivialUnitProduction = queue.poll();
			Character lhs = nonTrivialUnitProduction.getLeft();
			Character rhs = nonTrivialUnitProduction.getRight().charAt(0);
			
			Set<String> unfoldings = grammar.getSententialsForNonTerminal(rhs);
			for (String unfolding : unfoldings)
				grammar.addProduction(lhs, unfolding);
			
			grammar.removeProduction(nonTrivialUnitProduction);
			
			Iterator<Production> iterGeneratedTrivialUnitProductions = grammar.getTrivialUnitProductions().iterator();
			while(iterGeneratedTrivialUnitProductions.hasNext()) {
				Production trivialUnitProduction = iterGeneratedTrivialUnitProductions.next();
				grammar.removeProduction(trivialUnitProduction);
			}			
			
			queue.addAll(grammar.getNonTrivialUnitProductions());
		}
	}	
	
	public static void toChomskyNormalForm(Grammar grammar) {
		removeEpsilonProductions(grammar);
		System.out.println("#grammar without epsilons: " + grammar);
		removeUnitProductions(grammar);
		System.out.println("#grammar without units: " + grammar);	
		
		boolean emptyGeneration = grammar.getNullables().contains(grammar.getAxiom());
		
		System.out.println("#empty word in language: " + emptyGeneration);
		
		boolean loop = true;
		while(loop) {
			loop = false;
			
			Iterator<Production> iterProductions = grammar.getProductions().iterator();
			while(iterProductions.hasNext()) {
				Production production = iterProductions.next();
				if (production.getRightSize() > 2) {
					System.out.println("#production too long: " + production);
					loop = true;
					Character newNonTerminal = grammar.getNewNonTerminal();
					System.out.println("#new non terminal: " + newNonTerminal);
					Production reducedProductionOne = new Production();
					reducedProductionOne.setLeft(production.getLeft());
					reducedProductionOne.setRight(production.getRight().substring(0, 1) + newNonTerminal);
					
					System.out.println("#reduced production one: " + reducedProductionOne);
					
					Production reducedProductionTwo = new Production();
					reducedProductionTwo.setLeft(newNonTerminal);
					reducedProductionTwo.setRight(production.getRight().substring(1));
					
					System.out.println("#reduced production two: " + reducedProductionTwo);
					
					grammar.removeProduction(production);
					grammar.addProduction(reducedProductionOne);
					grammar.addProduction(reducedProductionTwo);
					
					System.out.println("#grammar: " + grammar);
				}
			}
		}
		
		loop = true;
		while(loop) {
			loop = false;
			
			Iterator<Production> iterProductions = grammar.getProductions().iterator();
			while(iterProductions.hasNext()) {
				Production production = iterProductions.next();
				if (production.getRightSize() > 1
						&& !production.getRightTerminals().isEmpty()) {
					
					System.out.println("#production to promote: " + production);
					loop = true;
					Character newNonTerminal = grammar.getNewNonTerminal();
					System.out.println("#new non terminal: " + newNonTerminal);
					Character terminal = production.getRightTerminals().first();
					System.out.println("#terminal to promote: " + terminal);
					
					Production promotionProductionOne = new Production();
					promotionProductionOne.setLeft(production.getLeft());
					promotionProductionOne.setRight(production.getRight().replace(terminal, newNonTerminal));
					
					System.out.println("#promotion production one: " + promotionProductionOne);
					
					Production promotionProductionTwo = new Production();
					promotionProductionTwo.setLeft(newNonTerminal);
					promotionProductionTwo.setRight("" + terminal);
					
					System.out.println("#promotion production two: " + promotionProductionTwo);
					
					grammar.removeProduction(production);
					grammar.addProduction(promotionProductionOne);
					grammar.addProduction(promotionProductionTwo);
					
					System.out.println("#grammar: " + grammar);
					
					
					
				}
			}
		}
		
		if (emptyGeneration)
			grammar.addProduction(grammar.getAxiom(), grammar.getEmpty());		
	}
	
}
