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
final class FormulaNot extends AbstractCompoundFormula {

  private static Formula NullFormula = new Formula() {

    @Override
    public int getIndex() {
      return -1;
    }

    @Override
    public Formula calculateBooleanSimplification() {
      return this;
    }

    @Override
    public Formula applySubstitution(PropositionalSubstitution subst) {
      return this;
    }

    @Override
    public Formula applySubstitution(Substitution subst) {
      return this;
    }

    @Override
    public Formula applyIntuitionisticPartialSubstitution(Substitution subst) {
      return this;
    }

    @Override
    public boolean containsTrue() {
      return false;
    }

    @Override
    public boolean containsFalse() {
      return false;
    }

    @Override
    public boolean isIntuitionisticLocalFormula() {
      return false;
    }

    @Override
    public boolean containsProposition(FormulaProposition name) {
      return false;
    }

    @Override
    public boolean isAtomic() {
      return false;
    }

    @Override
    public boolean isCompound() {
      return false;
    }

    @Override
    public FormulaType getFormulaType() {
      return null;
    }

    @Override
    public PropositionalConnective mainConnective() {
      return null;
    }

    @Override
    public Formula[] immediateSubformulas() {
      return null;
    }

    @Override
    public String shortName() {
      return "NullFormula";
    }

    @Override
    public String format() {
      return this.toString();
    }

    @Override
    public String toString() {
      return "NullFormula, used to unify unary and binary formulae methods";
    }

    @Override
    public boolean isFalse() {
      return false;
    }

    @Override
    public boolean isTrue() {
      return false;
    }

    @Override
    public FormulaFactory getFactory() {
      return null;
    }

  };

  protected FormulaNot(FormulaFactory formulaFactory, Formula subFormula) {
    super(formulaFactory, PropositionalConnective.NOT, new Formula[] { subFormula }, subFormula,
        NullFormula);
  }

  @Override
  protected int computeHashCode(Formula[] subFormulas) {
    int result = mainConnective().hashCode();
    result = 31 * result + left.hashCode();
    result = 31 * result + 1;
    return result;
  }

  @Override
  protected Formula computeBooleanSimplification() {
    Formula newSub = left.calculateBooleanSimplification();
    if (newSub.isTrue())
      return formulaFactory.getFalse();
    if (newSub.isFalse())
      return formulaFactory.getTrue();
    return formulaFactory.buildCompound(mainConnective(), newSub);
  }

  @Override
  public String toString() {
    String subf = "";
    if (left.isAtomic())
      subf = left.toString();
    else if (left.mainConnective() != PropositionalConnective.NOT)
      subf = "(" + left.toString() + ")";
    else
      subf = left.toString();
    return mainConnective().toString() + subf;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    return left == ((FormulaNot) o).left;
  }

  @Override
  protected Formula computePartialSubstitution(Substitution subst) {
    return this;
  }

  @Override
  public FormulaType getFormulaType() {
    return FormulaType.NOT_WFF;
  }

}
