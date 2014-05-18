package com.gmarciani.gmparser.views.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.bethecoder.ascii_table.ASCIITable;

public class Menu {
	
	private String name;
	private String description;	
	private Map<Integer, String> choices = new HashMap<Integer, String>();
	
	public Menu() {
		
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	

	public Map<Integer, String> getChoices() {
		return this.choices;
	}

	public void setChoices(Map<Integer, String> choices) {
		this.choices = choices;
	}
	
	public void addChoice(int key, String choice) {
		this.choices.put(key, choice);
	}
	
	public int run() {
		this.show();
		System.out.println("choice: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    int choice = -1;
	    try {
			choice = Integer.parseInt(br.readLine());
		} catch (NumberFormatException | IOException e) {
			
		}
	    System.out.println("\n");
		return choice;
	}
	
	private void show() {		
		String header[] = {this.getName(), this.getDescription()};
		String data[][] = new String[this.getChoices().size()][2];
		int c = 0;
		for (Map.Entry<Integer, String> choice : this.getChoices().entrySet()) {
			data[c][0] = choice.getKey().toString();
			data[c][1] = choice.getValue();
			c++;
		}	
		
		ASCIITable.getInstance().printTable(header, -1, data, -1);
	}
	
	@Override
	public String toString() {
		String s = "Menu(" + this.getName() + " | " + this.getDescription() + " | " + this.getChoices() + ")";
		return s;
	}

}
