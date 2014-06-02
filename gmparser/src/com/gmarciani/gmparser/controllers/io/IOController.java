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

package com.gmarciani.gmparser.controllers.io;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

/**
 * I/O stream controller, for general I/O operations.
 * 
 * @see com.gmarciani.gmparser.controllers.app.App
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public class IOController {
	
	/**
	 * <p>Returns a string representation of the file that resides at the specified {@code path}.<p>
	 * <p>This method is used, for example, to retrive the ASCII-art representation of the GMParser logo for the welcome splash screen.<p>
	 * 
	 * @param path the relative (to the project) path of the file.
	 * @return string representation of the specified file content.
	 * @throws IOException
	 */
	public static String getFileAsString(String path) throws IOException {
		InputStream stream = IOController.class.getClassLoader().getResourceAsStream(path);
		byte[] encoded = IOUtils.toByteArray(stream);
		return new String(encoded, "UTF-8");
	}

}
