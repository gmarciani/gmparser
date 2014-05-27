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

package com.gmarciani.gmparser.models.grammar.production;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.alphabet.AlphabetType;

public class Productions extends ConcurrentSkipListSet<Production> {

	private static final long serialVersionUID = 9062194540654802369L;
	
	public static final String MEMBER_SEPARATOR = Production.MEMBER_SEPARATOR; 
	public static final String INFIX_SEPARATOR = "|";
	public static final String PRODUCTION_SEPARATOR = ";";
	public static final String PRODUCTION_ENDER = ".";

	public Productions() {
		super();
	}
	
	public Productions(Productions productions) {
		super(productions);
	}
		
	public boolean add(Member left, Member right) {
		Production production = new Production(left, right);
		return this.add(production);
	}
	
	public boolean remove(Member left, Member right) {
		Production production = new Production(left, right);
		return this.remove(production);
	}
	
	public Productions getProductionsWithin(Alphabet alphabet) {
		Productions target = new Productions();
		
		for (Production production : this) {
			if (production.isWithin(alphabet))
				target.add(production);
		}
		
		return target;
	}
	
	public Productions getProductionsLeftWithin(Alphabet alphabet) {
		Productions target = new Productions();
		
		for (Production production : this) {
			if (production.isLeftWithin(alphabet))
				target.add(production);
		}
		
		return target;
	}
	
	public Productions getProductionsRightWithin(Alphabet alphabet) {
		Productions target = new Productions();
		
		for (Production production : this) {
			if (production.isRightWithin(alphabet))
				target.add(production);
		}
		
		return target;
	}
	
	public Productions getProductionsForNonTerminal(Character nonTerminal) {
		Productions target = new Productions();
		
		for (Production production : this) {
			if (production.getLeft().contains(nonTerminal));
				target.add(production);
		}
		
		return target;
	}
	
	public Productions getEpsilonProductions() {
		Productions target = new Productions();
		
		for (Production production : this) {
			if (production.isEpsilonProduction())
				target.add(production);
		}
		
		return target;
	}
	
	public Productions getUnitProductions(Alphabet nonTerminals) {
		Productions target = new Productions();
		
		for (Production production : this) {
			if (production.isUnitProduction(nonTerminals))
				target.add(production);
		}
		
		return target;
	}
	
	public Productions getTrivialUnitProductions(Alphabet nonTerminals) {
		Productions target = new Productions();
		
		for (Production production : this) {
			if (production.isTrivialUnitProduction(nonTerminals))
				target.add(production);
		}
		
		return target;
	}
	
	public Productions getNonTrivialUnitProductions(Alphabet nonTerminals) {
		Productions target = new Productions();
		
		for (Production production : this) {
			if (production.isNonTrivialUnitProduction(nonTerminals))
				target.add(production);
		}
		
		return target;
	}
	
	public Alphabet getNullables() {
		Alphabet target = new Alphabet(AlphabetType.NON_TERMINAL);
		
		for (Production production : this) {
			if (production.isEpsilonProduction()) {
				target.add(production.getLeft().getNonTerminalAlphabet());
			}
				
		}
		
		for (Production production : this) {
			if (target.containsAll(production.getRight().getAlphabet())) {
				target.add(production.getLeft().getNonTerminalAlphabet());
			}
				
		}
		
		return target;
	}
	
	public Set<String> getRightForNonTerminal(Character nonTerminal) {
		Set<String> target = new ConcurrentSkipListSet<String>();
		
		for (Production production : this) {
			if (production.getLeft().contains(nonTerminal))
				target.add(production.getRight().getValue());
		}
		
		return target;
	}
	
	public Alphabet getAlphabet() {
		Alphabet target = new Alphabet();
		
		target.add(this.getNonTerminalAlphabet());
		target.add(this.getTerminalAlphabet());		
		
		return target;
	}
	
	public Alphabet getNonTerminalAlphabet() {
		Alphabet target = new Alphabet(AlphabetType.NON_TERMINAL);
		
		target.add(this.getLeftNonTerminalAlphabet());
		target.add(this.getRightNonTerminalAlphabet());
		
		return target;
	}
	
	public Alphabet getTerminalAlphabet() {
		Alphabet target = new Alphabet(AlphabetType.TERMINAL);
		
		target.add(this.getRightTerminalAlphabet());
		
		return target;
	}
	
	public Alphabet getLeftNonTerminalAlphabet() {
		Alphabet target = new Alphabet(AlphabetType.NON_TERMINAL);
		
		for (Production production : this) {
			target.add(production.getLeft().getNonTerminalAlphabet());
		}
		
		return target;
	}
	
	public Alphabet getRightNonTerminalAlphabet() {
		Alphabet target = new Alphabet(AlphabetType.NON_TERMINAL);
		
		for (Production production : this) {
			target.add(production.getRight().getNonTerminalAlphabet());
		}
		
		return target;
	}
	
	public Alphabet getRightTerminalAlphabet() {
		Alphabet target = new Alphabet(AlphabetType.TERMINAL);
		
		for (Production production : this) {
			target.add(production.getRight().getTerminalAlphabet());
		}
		
		return target;
	}	

	@Override
	public String toString() {
		String s = "[";
		
		Iterator<Character> nonTerminals = this.getLeftNonTerminalAlphabet().iterator();
		while(nonTerminals.hasNext()) {
			Character nonTerminal = nonTerminals.next();
			s += nonTerminal + Production.MEMBER_SEPARATOR;
			Iterator<String> sententials = this.getRightForNonTerminal(nonTerminal).iterator();
			while(sententials.hasNext()) {
				String sentential = sententials.next();
				s += sentential + ((sententials.hasNext()) ? INFIX_SEPARATOR : "");
			}
			s += ((nonTerminals.hasNext()) ? PRODUCTION_SEPARATOR : "");
		}
		
		s += "]";
		
		return s;
	}	
	
}
