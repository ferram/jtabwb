/*******************************************************************************
 * Copyright (C) 2013, 2016 Mauro Ferrari
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package jtabwb.launcher;


/**
 * Signals a problem during the execution of the method.
 * {@link _InitialGoalBuilder#buildInitialNodeSet(ProblemDescription)}
 * @author Mauro Ferrari
 */
public class InitialGoalBuilderException extends Exception {

	/**
	 * Constructs an exception with <code>null</code> as message string.
	 */
	public InitialGoalBuilderException() {
		super();
	}

	/**
	 * Constructs an exception with the specified message string.
	 * @param message the message string.
	 */
	public InitialGoalBuilderException(String message) {
		super(message);
	}

}
