package com.gmarciani.gmparser.models.grammar;

public enum Extension {
	
	S_EXTENDED("S-Extended"),
	EXTENDED("Extended"),
	NONE("None");
	
	private String name;
	
	private Extension(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override public String toString() {
		return this.getName();
	}

}
