package com.gmarciani.gmparser.views.menu;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Menus implements Serializable {

	private static final long serialVersionUID = -6320704459207698679L;
	
	private Map<String, Menu> menus = new HashMap<String, Menu>();
	
	public Menus addMenu(Menu menu) {
		String menuIdentifier = getMenuIdentifier(menu);
		menus.put(menuIdentifier, menu);
		return this;
	}
	
	private String getMenuIdentifier(Menu menu) {
		String name = menu.getName();
		String identifier = name.toLowerCase().replaceAll(" ", "-");
		return identifier;
	}

	public int run(String menuIdentifier) {
		Menu menu = menus.get(menuIdentifier);
		return menu.run();
	}

}
