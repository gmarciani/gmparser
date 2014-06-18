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

import com.gmarciani.gmparser.models.commons.set.GSet;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.analysis.NormalForm;
import com.gmarciani.gmparser.models.grammar.analysis.Type;

/**
 * <p>Set of productions model.<p>
 * 
 * @see com.gmarciani.gmparser.models.grammar.Grammar 
 * @see com.gmarciani.gmparser.models.grammar.production.Production
 * @see com.gmarciani.gmparser.models.grammar.production.Member
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

	/**
	 * Creates a new set of productions.
	 */
	public Productions() {
		super();
	}
	
	/**
	 * Creates a new set of productions as a copy of the specified set of productions.
	 * 
	 * @param productions the set of productions to copy.
	 */
	public Productions(Productions productions) {
		super(productions);
	}
	
	/**
	 * Returns the subset of productions with LHS/RHS members equals to the specified members.
	 * 
	 * @param left the LHS member.
	 * @param right the RHS member.
	 * 
	 * @return the subset of productions with LHS/RHS members equals to the specified members.
	 */
	public Productions getProductionsEqual(Member left, Member right) {
		Productions target = new Productions();
		for (Production production : this)
			if (production.getLeft().equals(left)
					&& production.getRight().equals(right))
				target.add(production);
		return target;
	}
	
	/**
	 * Returns the subset of productions with LHS member equals to the specified member.
	 * 
	 * @param left the LHS member.
	 * 
	 * @return the subset of productions with LHS member equals to the specified member.
	 */
	public Productions getProductionsLeftEqual(Member left) {
		Productions target = new Productions();
		for (Production production : this)
			if (production.getLeft().equals(left))
				target.add(production);
		return target;
	}
	
	/**
	 * Returns the subset of productions with RHS member equals to the specified member.
	 * 
	 * @param right the RHS member.
	 * 
	 * @return the subset of productions with RHS member equals to the specified member.
	 */
	public Productions getProductionsRightEqual(Member right) {
		Productions target = new Productions();
		for (Production production : this)
			if (production.getRight().equals(right))
				target.add(production);
		return target;
	}
	
	/**
	 * Returns the subset of productions with both LHS and RHS members within the specified alphabet.
	 * 
	 * @param alphabet the alphabet.
	 * 
	 * @return the subset of productions with both the LHS and RHS members within the specified alphabet.
	 */
	public Productions getProductionsWithin(Alphabet alphabet) {
		Productions target = new Productions();		
		for (Production production : this)
			if (production.isWithin(alphabet))
				target.add(production);		
		return target;
	}
	
	/**
	 * Returns the subset of productions with LHS member within the specified alphabet.
	 * 
	 * @param alphabet the alphabet
	 * 
	 * @return the subset of productions with LHS member within the specified alphabet.
	 */
	public Productions getProductionsLeftWithin(Alphabet alphabet) {
		Productions target = new Productions();		
		for (Production production : this)
			if (production.getLeft().isWithin(alphabet))
				target.add(production);		
		return target;
	}
	
	/**
	 * Returns the subset of productions with RHS member within the specified alphabet.
	 * 
	 * @param alphabet the alphabet.
	 * 
	 * @return the subset of productions with RHS member within the specified alphabet.
	 */
	public Productions getProductionsRightWithin(Alphabet alphabet) {
		Productions target = new Productions();		
		for (Production production : this)
			if (production.getRight().isWithin(alphabet))
				target.add(production);		
		return target;
	}
	
	/**
	 * Returns the subset of productions with both the LHS and RHS member containing at least one symbol of the specified alphabet.
	 * 
	 * @param alphabet the alphabet.
	 * 
	 * @return the subset of productions with both the LHS and RHS member containing at least one symbol of the specified alphabet.
	 */
	public Productions getProductionsContaining(Alphabet alphabet) {
		Productions target = new Productions();		
		for (Production production : this)
			if (production.isContaining(alphabet))
				target.add(production);		
		return target;
	}
	
	/**
	 * Returns the subset of productions with the LHS member containing at least one symbol of the specified alphabet.
	 * 
	 * @param alphabet the alphabet.
	 * 
	 * @return the subset of productions with the LHS member containing at least one symbol of the specified alphabet.
	 */
	public Productions getProductionsLeftContaining(Alphabet alphabet) {
		Productions target = new Productions();	
		for (Production production : this)
			if (production.getLeft().isContaining(alphabet))
				target.add(production);		
		return target;
	}
	
	/**
	 * Returns the subset of productions with the RHS member containing at least one symbol of the specified alphabet.
	 * 
	 * @param alphabet the alphabet.
	 * 
	 * @return the subset of productions with the RHS member containing at least one symbol of the specified alphabet.
	 */
	public Productions getProductionsRightContaining(Alphabet alphabet) {
		Productions target = new Productions();		
		for (Production production : this)
			if (production.getRight().isContaining(alphabet))
				target.add(production);		
		return target;
	}
	
	/**
	 * Returns the subset of productions with both the LHS and RHS member containing at least one occurrence of the specified symbol.
	 * 
	 * @param symbol the symbol.
	 * 
	 * @return the subset of productions with both the LHS and RHS member containing at least one occurrence of the specified symbol.
	 */
	public Productions getProductionsContaining(Character symbol) {
		Productions target = new Productions();		
		for (Production production : this)
			if (production.isContaining(symbol))
				target.add(production);
		return target;
	}
	
	/**
	 * Returns the subset of productions with LHS member containing at least one occurrence of the specified symbol.
	 * 
	 * @param symbol the symbol.
	 * 
	 * @return the subset of productions with LHS member containing at least one occurrence of the specified symbol.
	 */
	public Productions getProductionsLeftContaining(Character symbol) {
		Productions target = new Productions();		
		for (Production production : this) 
			if (production.getLeft().isContaining(symbol))
				target.add(production);		
		return target;
	}
	
	/**
	 * Returns the subset of productions with RHS member containing at least one occurrence of the specified symbol.
	 * 
	 * @param symbol the symbol.
	 * 
	 * @return the subset of productions with RHS member containing at least one occurrence of the specified symbol.
	 */
	public Productions getProductionsRightContaining(Character symbol) {
		Productions target = new Productions();		
		for (Production production : this)
			if (production.getRight().isContaining(symbol))
				target.add(production);		
		return target;
	}
	
	/**
	 * Returns the subset of productions with the specified mapping of alphabet occurrences.
	 * 
	 * @param alphabets the alphabets.
	 * 
	 * @return the subset of productions with the specified mapping of alphabet occurrences.
	 */
	public Productions getProductionsRightIndexedWithin(Alphabet ... alphabets) {
		Productions target = new Productions();		
		for (Production production : this) {
			String rhs = production.getRight().getValue();
			boolean match = true;
			for (int index = 0; index < alphabets.length; index ++) {
				Alphabet alphabet = alphabets[index];
				match = (alphabet.contains(rhs.charAt(index)) ? match : false);
				if (!match)
					break;
			}			
			if (match)
				target.add(production);
		}				
		return target;
	}
	
	/**
	 * Returns the set of productions type, with respect to the Chomsky hierarchy.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * @param terminals the terminal alphabet.
	 * 
	 * @return the set of productions type, with respect to the Chomsky hierarchy.
	 */
	public Type getType(Alphabet nonTerminals, Alphabet terminals) {
		if (this.areRegular(nonTerminals, terminals))
			return Type.REGULAR;		
		if (this.areContextFree(nonTerminals, terminals))
			return Type.CONTEXT_FREE;		
		if (this.areContextSensitive(nonTerminals, terminals))
			return Type.CONTEXT_SENSITIVE;		
		if (this.areUnrestricted(nonTerminals, terminals))
			return Type.UNRESTRICTED;		
		return Type.UNKNOWN;
	}
	
	/**
	 * Checks if the set of productions is unrestricted.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * @param terminals the terminal alphabet.
	 * 
	 * @return true if the set of productions is unrestricted; false, otherwise.
	 */
	public boolean areUnrestricted(Alphabet nonTerminals, Alphabet terminals) {
		for (Production production : this)
			if (!production.isUnrestricted(nonTerminals, terminals))
				return false;
		return true;
	}
	
	/**
	 * Checks if the set of productions is context-sensitive.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * @param terminals the terminal alphabet.
	 * 
	 * @return true if the set of productions is context-sensitive; false, otherwise.
	 */
	public boolean areContextSensitive(Alphabet nonTerminals, Alphabet terminals) {
		for (Production production : this)
			if (!production.isContextSensitive(nonTerminals, terminals))
				return false;
		return true;
	}
	
	/**
	 * Checks if the set of productions is context-free.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * @param terminals the terminal alphabet.
	 * 
	 * @return true if the set of productions is context-free; false, otherwise.
	 */
	public boolean areContextFree(Alphabet nonTerminals, Alphabet terminals) {
		for (Production production : this)
			if (!production.isContextFree(nonTerminals, terminals))
				return false;
		return true;
	}
	
	/**
	 * Checks if the set of productions is regular.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * @param terminals the terminal alphabet.
	 * 
	 * @return true if the set of productions is regular; false, otherwise.
	 */
	public boolean areRegular(Alphabet nonTerminals, Alphabet terminals) {
		for (Production production : this)
			if (!production.isRegular(nonTerminals, terminals))
				return false;
		return true;
	}
	
	/**
	 * Checks if the set of productions is left linear.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * @param terminals the terminal alphabet.
	 * 
	 * @return true if the set of productions is left linear; false, otherwise.
	 */
	public boolean areRegularLeftLinear(Alphabet nonTerminals, Alphabet terminals) {
		for (Production production : this)
			if (!production.isRegularLeftLinear(nonTerminals, terminals))
				return false;
		return true;
	}
	
	/**
	 * Checks if the set of productions is right linear.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * @param terminals the terminal alphabet.
	 * 
	 * @return true if the set of productions is right linear; false, otherwise.
	 */
	public boolean areRegularRightLinear(Alphabet nonTerminals, Alphabet terminals) {
		for (Production production : this)
			if (!production.isRegularRightLinear(nonTerminals, terminals))
				return false;
		return true;
	}	
	
	/**
	 * Checks the normal forms of the set of productions.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * @param terminals the terminal alphabet.
	 * 
	 * @return the normal forms of the set of productions.
	 */
	public Set<NormalForm> getNormalForm(Alphabet nonTerminals, Alphabet terminals) {
		Set<NormalForm> target = new HashSet<NormalForm>();		
		if (this.areChomskyNormalForm(nonTerminals, terminals))
			target.add(NormalForm.CHOMSKY_NORMAL_FORM);		
		if (this.areGreibachNormalForm(nonTerminals, terminals))
			target.add(NormalForm.GREIBACH_NORMAL_FORM);		
		return target;
	}
	
	/**
	 * Checks if the set of productions is in Chomsky Normal Form.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * @param terminals the terminal alphabet.
	 * 
	 * @return true if the set of productions is in Chomsky Normal Form; false, otherwise.
	 */
	public boolean areChomskyNormalForm(Alphabet nonTerminals, Alphabet terminals) {
		for (Production production : this)
			if (!production.isChomskyNormalForm(nonTerminals, terminals))
				return false;
		return true;
	}
	
	/**
	 * Checks if the set of productions is in Greibach Normal Form.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * @param terminals the terminal alphabet.
	 * 
	 * @return true if the set of productions is in Greibach Normal Form; false, otherwise.
	 */
	public boolean areGreibachNormalForm(Alphabet nonTerminals, Alphabet terminals) {
		for (Production production : this)
			if (!production.isGreibachNormalForm(nonTerminals, terminals))
				return false;
		return true;
	}
	
	/**
	 * Returns the subset of epsilon productions.
	 * 
	 * @return the subset of epsilon productions.
	 */
	public Productions getEpsilonProductions() {
		Productions target = new Productions();		
		for (Production production : this)
			if (production.isEpsilonProduction())
				target.add(production);
		return target;
	}
	
	/**
	 * Returns the subset of unit productions.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * 
	 * @return the subset of unit productions.
	 */
	public Productions getUnitProductions(Alphabet nonTerminals) {
		Productions target = new Productions();		
		for (Production production : this)
			if (production.isUnitProduction(nonTerminals))
				target.add(production);
		return target;
	}
	
	/**
	 * Returns the subset of trivial unit productions.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * 
	 * @return the subset of trivial unit productions.
	 */
	public Productions getTrivialUnitProductions(Alphabet nonTerminals) {
		Productions target = new Productions();		
		for (Production production : this)
			if (production.isTrivialUnitProduction(nonTerminals))
				target.add(production);
		return target;
	}
	
	/**
	 * Returns the subset of non trivial unit productions.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * 
	 * @return the subset of non trivial unit productions.
	 */
	public Productions getNonTrivialUnitProductions(Alphabet nonTerminals) {
		Productions target = new Productions();		
		for (Production production : this)
			if (production.isNonTrivialUnitProduction(nonTerminals))
				target.add(production);
		return target;
	}
	
	/**
	 * Returns the subset of nullables symbols in the set of productions.
	 * 
	 * @return the subset of nullables symbols in the set of productions.
	 */
	public Alphabet getNullables() {
		Alphabet target = new Alphabet();		
		for (Production production : this)
			if (production.isEpsilonProduction())
				target.addAll(production.getLeft().getNonTerminalAlphabet());		
		for (Production production : this)
			if (target.containsAll(production.getRight().getAlphabet()))
				target.addAll(production.getLeft().getNonTerminalAlphabet());		
		return target;
	}
	
	/**
	 * Returns the subset of ungenerative symbols in the set of productions.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * @param terminals the terminal alphabet.
	 * 
	 * @return the subset of ungenerative symbols in the set of productions.
	 */
	public Alphabet getUngeneratives(Alphabet nonTerminals, Alphabet terminals) {
		Alphabet target = new Alphabet(nonTerminals);		
		Alphabet generativeTerminals = new Alphabet();
		Alphabet generativeAlphabet = new Alphabet(terminals, generativeTerminals);				
		boolean loop = true;
		while(loop) {
			loop = false;							
			generativeAlphabet.addAll(generativeTerminals);					
			for (Production production : this)
				if (production.getRight().isWithin(generativeAlphabet))
					loop = generativeTerminals.addAll(production.getLeft().getNonTerminalAlphabet()) ? true : loop;			
		}						
		target.removeAll(generativeTerminals);		
		return target;
	}
	
	/**
	 * Returns the subset of unreacheable symbols in the set of productions.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * @param terminals the terminal alphabet.
	 * @param axiom the axiom.
	 * 
	 * @return the subset of unreacheable symbols in the set of productions.
	 */
	public Alphabet getUnreacheables(Alphabet nonTerminals, Alphabet terminals, Character axiom) {
		Alphabet target = new Alphabet(nonTerminals);		
		Alphabet reacheableTerminals = new Alphabet();
		Alphabet reacheableNonTerminals = new Alphabet();	
		reacheableNonTerminals.add(axiom);				
		boolean loop = true;
		while(loop) {
			loop = false;					
			for (Production production : this) {	
				if (production.getLeft().isWithin(reacheableNonTerminals))
					loop = reacheableNonTerminals.addAll(production.getRight().getNonTerminalAlphabet()) ? true : loop;				
				if (production.getLeft().isWithin(reacheableNonTerminals))
					reacheableTerminals.addAll(production.getRight().getTerminalAlphabet());
			}
		}					
		target.removeAll(reacheableNonTerminals);		
		return target;
	}
	
	/**
	 * Returns the subset of useless symbols in the set of productions.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * @param terminals the terminal alphabet.
	 * @param axiom the axiom.
	 * 
	 * @return the subset of useless symbols in the set of productions.
	 */
	public Alphabet getUseless(Alphabet nonTerminals, Alphabet terminals, Character axiom) {
		Alphabet target = new Alphabet();		
		Alphabet ungeneratives = this.getUngeneratives(nonTerminals, terminals);
		Alphabet unreacheables = this.getUnreacheables(nonTerminals, terminals, axiom);		
		target.addAll(ungeneratives);
		target.addAll(unreacheables);		
		return target;
	}
	
	/**
	 * Returns the alphabet of the set of productions.
	 * 
	 * @return the alphabet of the set of productions.
	 */
	public Alphabet getAlphabet() {
		Alphabet target = new Alphabet();		
		target.addAll(this.getNonTerminalAlphabet());
		target.addAll(this.getTerminalAlphabet());				
		return target;
	}
	
	/**
	 * Returns the non terminal alphabet of the set of productions.
	 * 
	 * @return the non terminal alphabet of the set of productions.
	 */
	public Alphabet getNonTerminalAlphabet() {
		Alphabet target = new Alphabet();		
		target.addAll(this.getLeftNonTerminalAlphabet());
		target.addAll(this.getRightNonTerminalAlphabet());		
		return target;
	}
	
	/**
	 * Returns the terminal alphabet of the set of productions.
	 * 
	 * @return the terminal alphabet of the set of productions.
	 */
	public Alphabet getTerminalAlphabet() {
		Alphabet target = new Alphabet();		
		target.addAll(this.getRightTerminalAlphabet());		
		return target;
	}
	
	/**
	 * Returns the non terminal alphabet of the LHS members of the set of productions.
	 * 
	 * @return the non terminal alphabet of the LHS members of the set of productions.
	 */
	public Alphabet getLeftNonTerminalAlphabet() {
		Alphabet target = new Alphabet();		
		for (Production production : this)
			target.addAll(production.getLeft().getNonTerminalAlphabet());
		return target;
	}
	
	/**
	 * Returns the non terminal alphabet of the RHS members of the set of productions.
	 * 
	 * @return the non terminal alphabet of the RHS members of the set of productions.
	 */
	public Alphabet getRightNonTerminalAlphabet() {
		Alphabet target = new Alphabet();		
		for (Production production : this)
			target.addAll(production.getRight().getNonTerminalAlphabet());
		return target;
	}
	
	/**
	 * Returns the terminal alphabet of the RHS members of the set of productions.
	 * 
	 * @return the terminal alphabet of the RHS members of the set of productions.
	 */
	public Alphabet getRightTerminalAlphabet() {
		Alphabet target = new Alphabet();		
		for (Production production : this)
			target.addAll(production.getRight().getTerminalAlphabet());		
		return target;
	}	

	@Override public String toString() {
		String s = "{";
		Iterator<Character> nonTerminals = this.getLeftNonTerminalAlphabet().iterator();
		while(nonTerminals.hasNext()) {
			Character nonTerminal = nonTerminals.next();
			s += nonTerminal + Production.MEMBER_SEPARATOR;
			Iterator<Production> productionsForNonTerminal = this.getProductionsLeftContaining(nonTerminal).iterator();
			while(productionsForNonTerminal.hasNext()) {
				String sentential = productionsForNonTerminal.next().getRight().getValue();
				s += sentential + ((productionsForNonTerminal.hasNext()) ? INFIX_SEPARATOR : "");
			}
			s += ((nonTerminals.hasNext()) ? PRODUCTION_SEPARATOR : "");
		}		
		s += "}";		
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
