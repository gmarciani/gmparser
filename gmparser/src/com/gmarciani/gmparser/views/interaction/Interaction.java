package com.gmarciani.gmparser.views.interaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.bethecoder.ascii_table.ASCIITable;

public class Interaction {

	private String name;
	private String description;
	
	public Interaction() {
		
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
	
	public String run() {
		this.show();
		System.out.println("input: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
	    try {
	    	input = br.readLine();
		} catch (NumberFormatException | IOException e) {
			
		}
	    System.out.println("\n");
		return input;
	}
	
	private void show() {		
		String header[] = {this.getName()};
		String data[][] = {{this.getDescription()}};
		
		ASCIITable.getInstance().printTable(header, -1, data, -1);
	}
	
	@Override
	public String toString() {
		String s = "Interaction(" + this.getName() + " | " + this.getDescription() + ")";
		return s;
	}
}
