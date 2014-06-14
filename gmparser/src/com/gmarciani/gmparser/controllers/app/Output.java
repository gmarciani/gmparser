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

package com.gmarciani.gmparser.controllers.app;

import static org.fusesource.jansi.Ansi.ansi;

import com.gmarciani.gmparser.controllers.app.Preferences.AppLog;
import com.gmarciani.gmparser.controllers.app.Preferences.AppUI;

/**
 * <p>App output manager.<p> 
 * <p>It manages output format for standard outputs, warnings, exceptions, logon and debug.<p>
 * 
 * @see com.gmarciani.gmparser.controllers.app.App
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public final class Output {
	
	private static Output instance;

	private Output() {}
	
	public static Output getInstance() {
		if (instance == null)
			instance = new Output();
		return instance;
	}
	
	/**
	 * @param message
	 */
	public void onDefault(String message) {
		System.out.println(message);
	}
	
	/**
	 * @param resultMessage
	 */
	public void onResult(String resultMessage) {
		String result = "[gmparser] " + resultMessage;
		System.out.println(ansi().fg(AppUI.RESULT_COLOR).a(result).reset());
	}

	/**
	 * @param logonMessage
	 */
	public void onLogon(String logonMessage) {
		if (AppLog.LOGON) {
			String warning = "[gmparser] " + logonMessage;
			System.out.println(ansi().fg(AppUI.LOGON_COLOR).a(warning).reset());
		}
	}

	/**
	 * @param warningMessage
	 */
	public void onWarning(String warningMessage) {
		String warning = "[WARNING] " + warningMessage;
		System.out.println(ansi().fg(AppUI.WARNING_COLOR).a(warning).reset());
	}

	/**
	 * @param exceptionMessage
	 */
	public void onException(String exceptionMessage) {
		String warning = "[EXCEPTION] " + exceptionMessage;
		System.out.println(ansi().fg(AppUI.EXCEPTION_COLOR).a(warning).reset());
	}

	public void onUnrecognizedArguments(String[] arguments) {
		String unrecognizedArgs = "";
		int uArgsNo = 0;
		for (String arg : arguments) {
			if (arg.charAt(0) != '-') {
				unrecognizedArgs += arg + " ";
				uArgsNo ++;
			}
		}
		
		String warning = "Unrecognized argument" + ((uArgsNo > 1) ? "s" : "") + ": " + unrecognizedArgs;
		
		this.onWarning(warning);
	}

}
