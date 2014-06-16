package com.gmarciani.gmparser.views.menu;

public final class ParserMenu {

	private ParserMenu() {
		throw new AssertionError();
	}
	
	public static final String IDENTIFIER = "parser";
	public static final String NAME = "PARSER";
	public static final String DESCRIPTION = "Choose your parser";
	
	public static final int CYK = 1;
	public static final int LR1 = 2;
	
	public static final String CYK_DESCRIPTION = "Cock-Younger-Kasami";
	public static final String LR1_DESCRIPTION = "LR(1) Parser";

}
