/*******************************************************************************
 * Copyright (C) 2013, 2016 Mauro Ferrari
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
package jtabwb.launcher;

/**
 * An error in the format of the formula description file.
 * @author Mauro Ferrari
 */
public class ProblemDescriptionException extends Exception {

	private static final long serialVersionUID = 1L;

	public ProblemDescriptionException() {
		super();
	}

	public ProblemDescriptionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProblemDescriptionException(String message) {
		super(message);
	}

	public ProblemDescriptionException(Throwable cause) {
		super(cause);
	}

}
