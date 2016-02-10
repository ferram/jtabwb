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
class FormulaAnd extends AbstractCompoundFormula {

  protected FormulaAnd(FormulaFactory formulaFactory, Formula left, Formula right) {
    super(formulaFactory, PropositionalConnective.AND, new Formula[] { left, right }, left, right);
  }

  @Override
  Formula computeBooleanSimplification() {

    Formula newLeft = left.calculateBooleanSimplification();
    Formula newRight = right.calculateBooleanSimplification();

    if (newLeft.equals(newRight))
      return newLeft;
    if (newLeft.isTrue())
      return newRight;
    if (newLeft.isFalse())
      return newLeft;
    if (newRight.isTrue())
      return newLeft;
    if (newRight.isFalse())
      return newRight;
    return formulaFactory.buildCompound(mainConnective(), newLeft, newRight);
  }

  @Override
  protected Formula computePartialSubstitution(Substitution subst) {
    return formulaFactory.buildCompound(mainConnective(),
        left.applyIntuitionisticPartialSubstitution(subst),
        right.applyIntuitionisticPartialSubstitution(subst));
  }

  @Override
  public FormulaType getFormulaType() {
    return FormulaType.AND_WFF;
  }

}
