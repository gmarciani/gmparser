package com.gmarciani.gmparser.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOController {
	
	public static String getFileAsString(String path) throws IOException {
		InputStream stream = IOController.class.getClassLoader().getResourceAsStream(path);
		String string = getStringFromInputStream(stream);
		return string;
		//byte[] encoded = Files.readAllBytes(Paths.get(path));
		//return new String(encoded, "UTF-8");
	}
	
	private static String getStringFromInputStream(InputStream is) {
		 
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
 
		String line;
		try {
 
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
 
		} catch (IOException exc) {
			System.out.println(exc.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
 
		return sb.toString();
 
	}

}
