package com.gmarciani.gmparser.models.parser;

public enum ParserType {
	
	CYK("Cock-Young-Kasami", "CYK"),
	LL1("LL1", "LL1");	
	
	private String name;
	private String shortName;
	
	private ParserType(String name, String shortName) {
		this.name = name;
		this.shortName = shortName;
	}

	public String getName() {
		return name;
	}

	public String getShortName() {
		return shortName;
	}

}
