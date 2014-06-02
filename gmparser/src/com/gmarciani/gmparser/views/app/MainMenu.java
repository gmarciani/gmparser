package com.gmarciani.gmparser.views.app;

public final class MainMenu {

	private MainMenu() {
		throw new AssertionError();
	}
	
	public static final String IDENTIFIER = "main";
	public static final String NAME = "MAIN";
	public static final String DESCRIPTION = "Functions";
	
	public static final int ANALYZE = 1;
	public static final int TRANSFORM = 2;
	public static final int PARSE = 3;
	public static final int HELP = 4;
	public static final int QUIT = 5;
	
	public static final String ANALYZE_DESCRIPTION = "Analyze";
	public static final String TRANSFORM_DESCRIPTION = "Transform";
	public static final String PARSE_DESCRIPTION = "Parse";		
	public static final String HELP_DESCRIPTION = "Help";
	public static final String QUIT_DESCRIPTION = "Quit";

}
