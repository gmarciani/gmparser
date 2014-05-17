package com.gmarciani.gmparser.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class IOController {
	
	public static String getFileAsString(String path) throws IOException {
		InputStream stream = IOController.class.getClassLoader().getResourceAsStream(path);
		byte[] encoded = IOUtils.toByteArray(stream);
		return new String(encoded, "UTF-8");
	}

}
