/*******************************************************************************
 * Copyright (C) 2013, 2016 Mauro Ferrari This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this program; if not, see
 * <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package jtabwbx.prop.formula;

import jtabwbx.prop.basic.FormulaType;
import jtabwbx.prop.basic.PropositionalConnective;

/**
 * @author Lorenzo Bossi
 */
final class FormulaImplies extends AbstractCompoundFormula {

	protected FormulaImplies(FormulaFactory formulaFactory, Formula left, Formula right) {
		super(formulaFactory, PropositionalConnective.IMPLIES, new Formula[] { left, right }, left,
		    right);
	}

	@Override
	protected int computeHashCode(Formula[] subFormulas) {
		int result = mainConnective().hashCode();
		int leftHash = this.subFormulas[0].hashCode();
		int rightHash = this.subFormulas[1].hashCode();
		result = 31 * result + leftHash;
		result = 31 * result + rightHash;
		return result;
	}

	@Override
	protected Formula computeBooleanSimplification() {
		Formula newLeft = left.calculateBooleanSimplification();
		Formula newRight = right.calculateBooleanSimplification();

    if (newLeft.equals(newRight))
      return formulaFactory.getTrue();
		if (newLeft.isTrue())
			return newRight;
		if (newLeft.isFalse())
			return formulaFactory.getTrue();
		if (newRight.isTrue())
			return newRight;
		if (newRight.isFalse() && formulaFactory.translateNot)
		    return formulaFactory.buildCompound(PropositionalConnective.NOT, newLeft);
		
		return formulaFactory.buildCompound(mainConnective(), newLeft, newRight);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		FormulaImplies that = (FormulaImplies) o;
		return left == that.left && right == that.right;
	}

	@Override
	protected Formula computePartialSubstitution(Substitution subst) {
		return this;
	}
	
	@Override
  public FormulaType getFormulaType() {
    return FormulaType.IMPLIES_WFF;
  }



}
