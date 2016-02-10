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
package jtabwb.engine;

/**
 * Provides some details about the rule; the method {@link #getDetails()} is
 * invoked by the engine when it works in verbose mode to provide some extra
 * information about the rule application implementing this interface.
 * @author Mauro Ferrari
 */
public interface _RuleWithDetails {

	/**
	 * A string providing some extra information about the rule application.
	 * @return the string describing some details about this rule application.
	 */
	public String getDetails();
}
