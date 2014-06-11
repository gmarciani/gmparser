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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import com.gmarciani.gmparser.models.commons.set.GSet;
import com.gmarciani.gmparser.models.grammar.NormalForm;
import com.gmarciani.gmparser.models.grammar.Type;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.alphabet.AlphabetType;

/**
 * LHS/RHS member of a production.
 * 
 * @see com.gmarciani.gmparser.models.grammar.Grammar 
 * @see com.gmarciani.gmparser.models.grammar.production.Production
 * @see com.gmarciani.gmparser.models.grammar.production.Member
 * @see com.gmarciani.gmparser.models.alphabet.Alphabet
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public class Productions extends GSet<Production> {

	private static final long serialVersionUID = 9062194540654802369L;
	
	public static final String MEMBER_SEPARATOR = Production.MEMBER_SEPARATOR; 
	public static final String INFIX_SEPARATOR = "|";
	public static final String PRODUCTION_SEPARATOR = ";";
	public static final String PRODUCTION_ENDER = ".";

	public Productions() {
		super();
	}
	
	public Productions(Production ... productions) {
		super();
		for (Production production : productions)
			super.add(production);
	}
	
	public Productions(Productions ... productions) {
		super();
		for (Productions production : productions)
			super.addAll(production);
	}
		
	public boolean add(Member left, Member right) {
		Production production = new Production(left, right);
		return this.add(production);
	}
	
	public boolean remove(Member left, Member right) {
		Production production = new Production(left, right);
		return this.remove(production);
	}
	
	public Productions getProductionsWithLeft(Member left) {
		Productions target = new Productions();
		for (Production production : this)
			if (production.getLeft().equals(left))
				target.add(production);
		return target;
	}
	
	public Productions getProductionsWithRight(Member right) {
		Productions target = new Productions();
		for (Production production : this)
			if (production.getRight().equals(right))
				target.add(production);
		return target;
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
	
	public Productions getProductionsContaining(Alphabet alphabet) {
		Productions target = new Productions();
		
		for (Production production : this) {
			if (production.isContaining(alphabet))
				target.add(production);
		}
		
		return target;
	}
	
	public Productions getProductionsLeftContaining(Alphabet alphabet) {
		Productions target = new Productions();
		
		for (Production production : this) {
			if (production.isLeftContaining(alphabet))
				target.add(production);
		}
		
		return target;
	}
	
	public Productions getProductionsRightContaining(Alphabet alphabet) {
		Productions target = new Productions();
		
		for (Production production : this) {
			if (production.isRightContaining(alphabet))
				target.add(production);
		}
		
		return target;
	}
	
	public Productions getProductionsContaining(Character symbol) {
		Productions target = new Productions();
		
		for (Production production : this) {
			if (production.isContaining(symbol))
				target.add(production);
		}
		
		return target;
	}
	
	public Productions getProductionsLeftContaining(Character symbol) {
		Productions target = new Productions();
		
		for (Production production : this) {
			if (production.isLeftContaining(symbol))
				target.add(production);
		}
		
		return target;
	}
	
	public Productions getProductionsRightContaining(Character symbol) {
		Productions target = new Productions();
		
		for (Production production : this) {
			if (production.isRightContaining(symbol))
				target.add(production);
		}
		
		return target;
	}
	
	public Productions getProductionsForNonTerminal(Character nonTerminal) {
		Productions target = new Productions();
		
		for (Production production : this) {
			if (production.isLeftContaining(nonTerminal));
				target.add(production);
		}
		
		return target;
	}
	
	public Productions getProductionsRightWithinAt(Alphabet ... entries) {
		Productions target = new Productions();
		
		for (Production production : this) {
			String rhs = production.getRight().getValue();
			boolean match = true;
			for (int index = 0; index < entries.length; index ++) {
				Alphabet alphabet = entries[index];
				match = (alphabet.contains(rhs.charAt(index)) ? match : false);
				if (!match)
					break;
			}
			
			if (match)
				target.add(production);
		}		
		
		return target;
	}
	
	public Type getType(Alphabet nonTerminals, Alphabet terminals) {
		if (this.isRegular(nonTerminals, terminals))
			return Type.REGULAR;
		
		if (this.isContextFree(nonTerminals, terminals))
			return Type.CONTEXT_FREE;
		
		if (this.isContextSensitive(nonTerminals, terminals))
			return Type.CONTEXT_SENSITIVE;
		
		if (this.isUnrestricted(nonTerminals, terminals))
			return Type.UNRESTRICTED;
		
		return Type.UNKNOWN;
	}
	
	public boolean isUnrestricted(Alphabet nonTerminals, Alphabet terminals) {
		for (Production production : this) {
			if (!production.isUnrestricted(nonTerminals, terminals))
				return false;
		}
		
		return true;
	}
	
	public boolean isContextSensitive(Alphabet nonTerminals, Alphabet terminals) {
		for (Production production : this) {
			if (!production.isContextSensitive(nonTerminals, terminals))
				return false;
		}
		
		return true;
	}
	
	public boolean isContextFree(Alphabet nonTerminals, Alphabet terminals) {
		for (Production production : this) {
			if (!production.isContextFree(nonTerminals, terminals))
				return false;
		}
		
		return true;
	}
	
	public boolean isRegular(Alphabet nonTerminals, Alphabet terminals) {
		for (Production production : this) {
			if (!production.isRegular(nonTerminals, terminals))
				return false;
		}
		
		return true;
	}
	
	public boolean isRegularLeftLinear(Alphabet nonTerminals, Alphabet terminals) {
		for (Production production : this) {
			if (!production.isRegularLeftLinear(nonTerminals, terminals))
				return false;
		}
		
		return true;
	}
	
	public boolean isRegularRightLinear(Alphabet nonTerminals, Alphabet terminals) {
		for (Production production : this) {
			if (!production.isRegularRightLinear(nonTerminals, terminals))
				return false;
		}
		
		return true;
	}	
	
	public Set<NormalForm> getNormalForm(Alphabet nonTerminals, Alphabet terminals) {
		Set<NormalForm> target = new HashSet<NormalForm>();
		
		if (this.isChomskyNormalForm(nonTerminals, terminals))
			target.add(NormalForm.CHOMSKY_NORMAL_FORM);
		
		if (this.isGreibachNormalForm(nonTerminals, terminals))
			target.add(NormalForm.GREIBACH_NORMAL_FORM);
		
		return target;
	}
	
	public boolean isChomskyNormalForm(Alphabet nonTerminals, Alphabet terminals) {
		for (Production production : this) {
			if (!production.isChomskyNormalForm(nonTerminals, terminals))
				return false;
		}
		
		return true;
	}
	
	public boolean isGreibachNormalForm(Alphabet nonTerminals, Alphabet terminals) {
		for (Production production : this) {
			if (!production.isGreibachNormalForm(nonTerminals, terminals))
				return false;
		}
		
		return true;
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
				target.addAll(production.getLeft().getNonTerminalAlphabet());
			}
				
		}
		
		for (Production production : this) {
			if (target.containsAll(production.getRight().getAlphabet())) {
				target.addAll(production.getLeft().getNonTerminalAlphabet());
			}
				
		}
		
		return target;
	}
	
	public Alphabet getUngeneratives(Alphabet nonTerminals, Alphabet terminals) {
		Alphabet target = new Alphabet(nonTerminals);
		
		Alphabet generativeTerminals = new Alphabet(AlphabetType.NON_TERMINAL);
		Alphabet generativeAlphabet = new Alphabet(terminals, generativeTerminals);
				
		boolean loop = true;
		while(loop) {
			loop = false;		
					
			generativeAlphabet.addAll(generativeTerminals);
					
			for (Production production : this) {
				if (production.isRightWithin(generativeAlphabet)) {
					loop = generativeTerminals.addAll(production.getLeft().getNonTerminalAlphabet()) ? true : loop;
				}		
			}			
		}		
				
		target.removeAll(generativeTerminals);
		
		return target;
	}
	
	public Alphabet getUnreacheables(Alphabet nonTerminals, Alphabet terminals, Character axiom) {
		Alphabet target = new Alphabet(nonTerminals);
		
		Alphabet reacheableTerminals = new Alphabet(AlphabetType.TERMINAL);
		Alphabet reacheableNonTerminals = new Alphabet(AlphabetType.NON_TERMINAL);	
		reacheableNonTerminals.add(axiom);
				
		boolean loop = true;
		while(loop) {
			loop = false;
					
			for (Production production : this) {	
				if (production.isLeftWithin(reacheableNonTerminals))
					loop = reacheableNonTerminals.addAll(production.getRight().getNonTerminalAlphabet()) ? true : loop;
						
				if (production.isLeftWithin(reacheableNonTerminals))
					reacheableTerminals.addAll(production.getRight().getTerminalAlphabet());
			}
		}			
		
		target.removeAll(reacheableNonTerminals);
		
		return target;
	}
	
	public Alphabet getUseless(Alphabet nonTerminals, Alphabet terminals, Character axiom) {
		Alphabet target = new Alphabet(AlphabetType.NON_TERMINAL);
		
		Alphabet ungeneratives = this.getUngeneratives(nonTerminals, terminals);
		Alphabet unreacheables = this.getUnreacheables(nonTerminals, terminals, axiom);
		
		target.addAll(ungeneratives);
		target.addAll(unreacheables);
		
		return target;
	}
	
	public Set<String> getRightForNonTerminal(Character nonTerminal) {
		Set<String> target = new ConcurrentSkipListSet<String>();
		
		for (Production production : this) {
			if (production.isLeftContaining(nonTerminal))
				target.add(production.getRight().getValue());
		}
		
		return target;
	}
	
	public Alphabet getAlphabet() {
		Alphabet target = new Alphabet();
		
		target.addAll(this.getNonTerminalAlphabet());
		target.addAll(this.getTerminalAlphabet());		
		
		return target;
	}
	
	public Alphabet getNonTerminalAlphabet() {
		Alphabet target = new Alphabet(AlphabetType.NON_TERMINAL);
		
		target.addAll(this.getLeftNonTerminalAlphabet());
		target.addAll(this.getRightNonTerminalAlphabet());
		
		return target;
	}
	
	public Alphabet getTerminalAlphabet() {
		Alphabet target = new Alphabet(AlphabetType.TERMINAL);
		
		target.addAll(this.getRightTerminalAlphabet());
		
		return target;
	}
	
	public Alphabet getLeftNonTerminalAlphabet() {
		Alphabet target = new Alphabet(AlphabetType.NON_TERMINAL);
		
		for (Production production : this) {
			target.addAll(production.getLeft().getNonTerminalAlphabet());
		}
		
		return target;
	}
	
	public Alphabet getRightNonTerminalAlphabet() {
		Alphabet target = new Alphabet(AlphabetType.NON_TERMINAL);
		
		for (Production production : this) {
			target.addAll(production.getRight().getNonTerminalAlphabet());
		}
		
		return target;
	}
	
	public Alphabet getRightTerminalAlphabet() {
		Alphabet target = new Alphabet(AlphabetType.TERMINAL);
		
		for (Production production : this) {
			target.addAll(production.getRight().getTerminalAlphabet());
		}
		
		return target;
	}	

	@Override public String toString() {
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
	
	@Override public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		Productions other = (Productions) obj;
		
		boolean byProductions = (this.containsAll(other) && other.containsAll(this));
		
		return byProductions;
	}
	
}
