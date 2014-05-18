package com.gmarciani.gmparser.views.menu;

import java.util.HashMap;
import java.util.Map;


public final class MenuBuilder {
	
	private static MenuBuilder instance = new MenuBuilder();
	
	private static String menuName;
	private static String menuDescription;
	private static Map<Integer, String> choices = new HashMap<Integer, String>();

	private MenuBuilder() {
		
	}
	
	private static void reset() {
		menuName = null;
		menuDescription = null;
		choices.clear();
	}
	
	public static MenuBuilder hasName(String name) {
		menuName = name;
		return instance;
	}
	
	public static MenuBuilder withDescription(String description) {
		menuDescription = description;
		return instance;
	}
	
	public static MenuBuilder hasChoice(int key, String choice) {
		choices.put(key, choice);
		return instance;
	}
	
	public static Menu create() {
		Menu menu = new Menu();
		menu.setName(menuName);
		menu.setDescription(menuDescription);
		for (Map.Entry<Integer, String> choice : choices.entrySet()) {
			menu.addChoice(choice.getKey(), choice.getValue());
		}
		
		MenuBuilder.reset();
		
		return menu;
	}
	
}
