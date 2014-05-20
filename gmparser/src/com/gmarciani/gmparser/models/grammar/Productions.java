package com.gmarciani.gmparser.models.grammar;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;


public class Productions extends LinkedHashSet<Production> {

	private static final long serialVersionUID = 9062194540654802369L;
	
	public static final String MEMBER_SEPARATOR = Production.MEMBER_SEPARATOR; 
	public static final String INFIX_SEPARATOR = "|";
	public static final String PRODUCTION_SEPARATOR = ";";

	public Productions() {
		super();
	}
	
	public boolean add(String left, String right) {
		Production production = new Production(left, right);
		return this.add(production);
	}
	
	public void restrictTo(Set<Production> restricted) {
		Iterator<Production> iter = this.iterator();
		while (iter.hasNext()) {
			Production production = iter.next();
			if (!restricted.contains(production))
				iter.remove();
		}
	}
	
	public Set<Production> getProductionsWithSymbolsIn(Set<Character> symbols) {
		Set<Production> target = new LinkedHashSet<Production>();
		
		for (Production production : this) {
			if (production.isWithin(symbols))
				target.add(production);
		}
		
		return target;
	}
	
	public Set<Production> getProductionsFor(String nonTerminal) {
		Set<Production> target = new LinkedHashSet<Production>();
		
		for (Production production : this) {
			if (production.getLeft().equals(nonTerminal))
				target.add(production);
		}
		
		return target;
	}
	
	public Set<String> getSententialsFor(String nonTerminal) {
		Set<String> target = new LinkedHashSet<String>();
		
		for (Production production : this) {
			if (production.getLeft().equals(nonTerminal))
				target.add(production.getRight());
		}
		
		return target;
	}
	
	public Set<String> getNonTerminals() {
		Set<String> target = new LinkedHashSet<String>();
		
		for (Production production : this) {
			target.add(production.getLeft());
		}
		
		return target;
	}

	@Override
	public String toString() {
		String s = "[";
		
		Iterator<String> nonTerminals = this.getNonTerminals().iterator();
		while(nonTerminals.hasNext()) {
			String nonTerminal = nonTerminals.next();
			s += nonTerminal + Production.MEMBER_SEPARATOR;
			Iterator<String> sententials = this.getSententialsFor(nonTerminal).iterator();
			while(sententials.hasNext()) {
				String sentential = sententials.next();
				s += sentential + ((sententials.hasNext()) ? INFIX_SEPARATOR : "");
			}
			s += ((nonTerminals.hasNext()) ? PRODUCTION_SEPARATOR : "");
		}
		
		s += "]";
		
		return s;
	}	
	/*
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		Productions other = (Productions) obj;
		
		return this.equals(other.productions);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this == null) ? 0 : this.hashCode());
		return result;
	}
	*/

}
