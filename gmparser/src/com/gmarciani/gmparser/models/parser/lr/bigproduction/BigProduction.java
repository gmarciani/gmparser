package com.gmarciani.gmparser.models.parser.lr.bigproduction;

import java.util.Objects;

import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.production.Member;
import com.gmarciani.gmparser.models.grammar.production.Production;

public class BigProduction implements Comparable<BigProduction> {
	
	private Production production;
	private Integer dot;
	private Alphabet lookAhead;	
	
	private static final String DOT_SEPARATOR = ".";
	
	public BigProduction(BigProduction bigProduction) {
		this.setProduction(bigProduction.getProduction());
		this.setDot(bigProduction.getDot());
		this.setLookAhead(bigProduction.getLookAhead());
	}
	
	public BigProduction(Production production, Integer dot, Alphabet lookAhead) {
		this.setProduction(production);
		this.setDot(dot);
		this.setLookAhead(lookAhead);
	}

	public BigProduction(Member left, Member right, Integer dot, Alphabet lookAhead) {
		this.setProduction(new Production(left, right));
		this.setDot(dot);
		this.setLookAhead(lookAhead);
	}
	
	public Production getProduction() {
		return this.production;
	}
	
	public void setProduction(Production production) {
		this.production = production;
	}
	
	public Integer getDot() {
		return dot;
	}

	public void setDot(Integer dot) {
		this.dot = dot;
	}

	public Alphabet getLookAhead() {
		return lookAhead;
	}

	public void setLookAhead(Alphabet lookAhead) {
		this.lookAhead = lookAhead;
	}
	
	@Override public String toString() {
		String lhs = this.getProduction().getLeft().toString();
		String rhs = this.getProduction().getRight().toString().substring(0, this.getDot()) + 
				DOT_SEPARATOR + 
				this.getProduction().getRight().toString().substring(this.getDot());
		String lookAhead = this.getLookAhead().toString();
		
		return lhs + Production.MEMBER_SEPARATOR + rhs + "," + lookAhead;
	}	
	
	@Override public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;

		BigProduction other = (BigProduction) obj;
		
		return (this.getProduction().equals(other.getProduction()))
				&& this.getDot().equals(other.getDot())
				&& this.getLookAhead().equals(other.getLookAhead());
	}	
	
	@Override public int compareTo(BigProduction other) {
		int byProduction = this.getProduction().compareTo(other.getProduction());
		if (byProduction == 0)
			return this.getDot().compareTo(other.getDot());
		return byProduction;	
	}
	
	@Override public int hashCode() {
		return Objects.hash(this.getProduction(),
							this.getDot(),
							this.getLookAhead());
	}	

}
