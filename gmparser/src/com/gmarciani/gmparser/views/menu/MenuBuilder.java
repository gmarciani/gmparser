/*	The MIT License (MIT)
 *
 *	Copyright (c) 2014 Giacomo Marciani
 *	
 *	Permission is hereby granted, free of charge, to any person obtaining a copy
 *	of this software and associated documentation files (the "Software"), to deal
 *	in the Software without restriction, including without limitation the rights
 *	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *	copies of the Software, and to permit persons to whom the Software is
 *	furnished to do so, subject to the following conditions:
 *	
 *	The above copyright notice and this permission notice shall be included in all
 *	copies or substantial portions of the Software.
 *	
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *	SOFTWARE.
*/

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
