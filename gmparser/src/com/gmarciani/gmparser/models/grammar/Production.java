package com.gmarciani.gmparser.models.grammar;

import java.util.LinkedHashSet;
import java.util.Set;

public class Production implements Comparable<Production> {
	
	private String left;
	private String right;
	
	public static final String MEMBER_SEPARATOR = "->";

	public Production() {
		// TODO Auto-generated constructor stub
	}
	
	public Production(String left, String right) {
		this.left = left;
		this.right = right;
	}

	public String getLeft() {
		return this.left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public String getRight() {
		return this.right;
	}

	public void setRight(String right) {
		this.right = right;
	}
	
	public Set<Character> getSymbols() {
		Set<Character> target = new LinkedHashSet<Character>();
		String symbols = this.left + this.right;
		
		for (Character symbol : symbols.toCharArray()) {
			target.add(symbol);
		}
		
		return target;
	}
	
	@Override
	public String toString() {
		String s = this.getLeft() + MEMBER_SEPARATOR + this.getRight();
		return s;
	}
	
	@Override
	public int compareTo(Production other) {
		int byLeft = this.left.compareTo(other.left);
		if (byLeft == 0)
			return this.right.compareTo(other.right);
		return byLeft;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;

		Production other = (Production) obj;
		
		return (this.left.equals(other.left) 
				&& this.right.equals(other.right));
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.left == null) ? 0 : this.left.hashCode());
		result = prime * result + ((this.right == null) ? 0 : this.right.hashCode());
		return result;
	}	

}
