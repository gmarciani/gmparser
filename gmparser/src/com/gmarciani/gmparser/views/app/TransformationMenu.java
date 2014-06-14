package com.gmarciani.gmparser.views.app;

public class TransformationMenu {

	private TransformationMenu() {
		throw new AssertionError();
	}
	
	public static final String IDENTIFIER = "transformation";
	public static final String NAME = "TRANSFORMATION";
	public static final String DESCRIPTION = "Choose your transformation";
	
	public static final int REMOVE_UNGENERATIVE_SYMBOLS = 1;
	public static final int REMOVE_UNREACHEABLES_SYMBOLS = 2;
	public static final int REMOVE_USELESS_SYMBOLS = 3;
	public static final int REMOVE_EPSILON_PRODUCTIONS = 4;
	public static final int REMOVE_UNIT_PRODUCTIONS = 5;	
	public static final int GENERATE_CHOMSKY_NORMAL_FORM = 6;
	
	public static final String REMOVE_UNGENERATIVE_SYMBOLS_DESCRIPTION = "Remove ungenerative symbols";
	public static final String REMOVE_UNREACHEABLES_SYMBOLS_DESCRIPTION = "Remove unreacheables symbols";
	public static final String REMOVE_USELESS_SYMBOLS_DESCRIPTION = "Remove useless symbols";
	public static final String REMOVE_EPSILON_PRODUCTIONS_DESCRIPTION = "Remove epsilon productions";
	public static final String REMOVE_UNIT_PRODUCTIONS_DESCRIPTION = "Remove unit productions";
	public static final String GENERATE_CHOMSKY_NORMAL_FORM_DESCRIPTION = "Generate Chomsky-Normal-Form";

}
