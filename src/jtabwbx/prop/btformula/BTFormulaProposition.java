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
package jtabwbx.prop.btformula;

import jtabwbx.prop.basic.PropositionalConnective;


/**
 * Implementation of a propositional variable (an atomic formula).
 * @author Mauro Ferrari
 */
public class BTFormulaProposition extends BTFormula {

	/**
	 * The formula representing the true constant, its name is <code>"true"</code>.
	 * 
	 */
	public static final BTFormulaProposition TRUE = new BTFormulaProposition("true");

	/**
	 * The formula representing the FALSE constant,  its name is
	 * <code>"false"</code>.
	 */
	public static final BTFormulaProposition FALSE = new BTFormulaProposition("false");
	private String name;

	public BTFormulaProposition(String name) {
		this.name = name;
	}

	@Override
	public boolean isAtomic() {
		return true;
	}

	@Override
	public boolean isCompound() {
		return false;
	}

	@Override
	public PropositionalConnective mainConnective() {
		return null;
	}

	@Override
	public BTFormula[] immediateSubformulas() {
		return null;
	}

	public String getName() {
		return name;
	}

	@Override
	public String shortName() {
		return "propositional variable";
	}

	@Override
	public String format() {
		return name;
	}

	/**
	 * Two propositional formulas are equals if they have the same name.
	 * @param obj the object to compare with this propositional formula.
	 * @return <code>true</code> if and only if the propositional formulas have
	 * the same name.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		return this.name.equals(((BTFormulaProposition) obj).name);
	}

	/**
	 * Returns the string representation of the formula.
	 * @return string representing the formula.
	 */
	@Override
	public String toString() {
			return name;
	}
}
