package com.gmarciani.gmparser;


import org.apache.commons.cli.ParseException;

import com.gmarciani.gmparser.controllers.AppController;

public class App {

	public static void main(String[] args) {	
		AppController app = AppController.getInstance();
		app.printWelcome();
		try {
			app.play(args);
		} catch (ParseException exc) {
			app.printWarning(exc.getMessage());
			app.quit();
		}	
	}	

}
