/*******************************************************************************
 * Copyright (C) 2013, 2014 Mauro Ferrari
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option)
 * any later version.
 *  
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package jtabwb.tracesupport;

import java.io.PrintStream;

import ferram.messages.MessageManager;


/**
 * The class managing messages of the engine.
 * @author Mauro Ferrari
 */
class TraceSupportMessageManager {

	private static PrintStream out = System.out;
	private static final String INFO_TAB = "   ";
	private static MessageManager manager = new MessageManager("jtabwb.tracesupport.messages");

	static void printMessage(String key, Object... args) {
		out.println(manager.getMsg(key, args));
	}

	static void print(String key, Object... args) {
		String str = null;
		if (key == null) {
			str = buildStringOf(args, " ");
		} else
			str = manager.getMsg(key, args);
		out.print(str);
	}

	static void iterationStepInfo(String key, Object... args) {
		String str = null;
		if (key == null) {
			str = buildStringOf(args, " ");
		} else
			str = manager.getMsg(key, args);
		out.println(INFO_TAB + str);
	}

	static void println(String key, Object... args) {
		String str = null;
		if (key == null) {
			str = buildStringOf(args, " ");
		} else
			str = manager.getMsg(key, args);
		out.println(str);
	}

	public static String getMsg(String key, Object... args) {
		return manager.getMsg(key, args);
	}

	public static String getMsg(String key) {
		return manager.getMsg(key);
	}

	/**
	 * Returns the list consisting of the string describing the elements of
	 * <code>a</code> separated by the specified <code>separator</code>. If the
	 * specified array is <code>null</code> the method returns the empty string.
	 * @param a the array.
	 * @param separator the string to use as separator.
	 * @return the string.
	 */
	static String buildStringOf(Object[] a, String separator) {
		if (a == null)
			return "";

		String str = "";
		for (int i = 0; i < a.length; i++)
			str += a[i].toString() + (i < a.length - 1 ? separator : "");
		return str;
	}
}
