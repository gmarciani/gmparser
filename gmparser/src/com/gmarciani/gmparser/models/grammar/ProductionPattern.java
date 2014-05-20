package com.gmarciani.gmparser.models.grammar;

import java.util.regex.Pattern;

public class ProductionPattern {
	
	private Pattern leftPattern;
	private Pattern rightPattern;	

	public ProductionPattern() {

	}
	
	public boolean match(Production production) {
		Pattern leftPattern = this.getLeftPattern();
		Pattern rightPattern = this.getRightPattern();
		String left = production.getLeft();
		String right = production.getRight();
		return (left.matches(leftPattern.pattern())
				&& right.matches(rightPattern.pattern()));
	}

	public Character extract(Character symbol, Production production) {
		// TODO Auto-generated method stub
		return null;
	}

	public Pattern getLeftPattern() {
		return this.leftPattern;
	}

	public void setLeftPattern(Pattern leftPattern) {
		this.leftPattern = leftPattern;
	}

	public Pattern getRightPattern() {
		return this.rightPattern;
	}

	public void setRightPattern(Pattern rightPattern) {
		this.rightPattern = rightPattern;
	}

}
