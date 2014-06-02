package com.gmarciani.gmparser.views.app;

public final class ParserMenu {

	private ParserMenu() {
		throw new AssertionError();
	}
	
	public static final String IDENTIFIER = "parser";
	public static final String NAME = "PARSER";
	public static final String DESCRIPTION = "Choose your parser";
	
	public static final int CYK = 1;
	public static final int LL1 = 2;
	
	public static final String CYK_DESCRIPTION = "Cock-Young-Kasami";
	public static final String LL1_DESCRIPTION = "LL(1) Parser";

}
