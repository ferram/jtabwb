/*******************************************************************************
 * Copyright (C) 2013, 2015 Mauro Ferrari
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

import jtabwb.engine._AbstractFormula;
import jtabwb.engine._AbstractGoal;
import jtabwb.engine._AbstractRule;

public interface _TraceManager {

	/**
	 * Returns the instance of the rule with the specified name applied to the
	 * specified arguments.
	 * @param name the name of the rule.
	 * @param premise the premise of the rule.
	 * @param mainFormula the main formula of the rule.
	 * @return the rule with the specified name.
	 */
	public _AbstractRule getRuleByName(String name, _AbstractGoal premise,
	    _AbstractFormula mainFormula) throws TraceSupportException;

	//	/**
	//	 * Returns the instance of the clash detection rule with the specified name
	//	 * applied to the specified set.
	//	 * @param name the name of the rule.
	//	 * @param premise the premise of the rule.
	//	 * @return the corensponding instance of a clash-detection rule.
	//	 * @throws TraceSupportException
	//	 */
	//	public _ClashDetectionRule getClashDetectionRule(String name, _AbstractNodeSet premise)
	//	    throws TraceSupportException;
}
