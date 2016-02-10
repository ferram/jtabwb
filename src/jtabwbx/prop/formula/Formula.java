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
import jtabwbx.prop.basic._PropositionalFormula;

/**
 * Formula of this package are realized by objects implementing this interface.
 * 
 * @author Lorenzo Bossi
 */
public abstract class Formula implements _PropositionalFormula {

  private int index;
  int size;

  Formula() {
  }

  final void setIndex(int index) {
    this.index = index;
  }

  /**
   * Returns the index for this formula.
   * 
   * @return the index of this formula.
   */
  public int getIndex() {
    return index;
  }

  /**
   * The subformulas of this formula.
   * 
   * @return the subformulas of this formula.
   */
  public abstract Formula[] immediateSubformulas();

  /**
   * Returns the formula obtained by applying boolean simplifications on this
   * formula. This method is performs caching and optimisation,
   * 
   * @return the simplified formula.
   */
  public abstract Formula calculateBooleanSimplification();

  /**
   * Returns the formula obtained by applying the specified substitution on
   * propositional formulas.
   * 
   * @param subst the substitution to apply.
   * @return the formula obtained applying the substitution.
   */
  public abstract Formula applySubstitution(PropositionalSubstitution subst);

  /**
   * Returns the formula obtained by applying the specified substitution.
   * 
   * @param subst the substitution to apply.
   * @return the formula obtained applying the substitution.
   */
  public abstract Formula applySubstitution(Substitution subst);

  /**
   * Returns the formula obtained by applying the specified substitution as
   * intuitionistic partial substitution.
   * 
   * @param subst the substitution to apply.
   * @return the formula obtained applying the substitution.
   */
  public abstract Formula applyIntuitionisticPartialSubstitution(Substitution subst);

  /**
   * Returns <code>true</code> iff this formula contains the propositional
   * constant TRUE as subfromula.
   * 
   * @return <code>true</code> iff this formula contains the propositional
   * constant TRUE.
   */
  public abstract boolean containsTrue();

  /**
   * Returns <code>true</code> iff this formula contains the propositional
   * constant FALSE as subformula.
   * 
   * @return <code>true</code> iff this formula contains the propositional
   * constant FALSE.
   */
  public abstract boolean containsFalse();

  /**
   * Returns <code>true</code> iff this formula contains the specified
   * propositional formula as subformula.
   * 
   * @param name the propositional formula.
   * @return <code>true</code> iff this formula contains the specified
   * proposition.
   */
  abstract boolean containsProposition(FormulaProposition name);

  /**
   * Returns <code>true</code> iff this formula is the propositional constant
   * FALSE.
   * 
   * @return <code>true</code> iff this formula is the propositional constant
   * FALSE.
   */
  public abstract boolean isFalse();

  /**
   * Returns <code>true</code> iff this is the propositional constant TRUE.
   * 
   * @return <code>true</code> iff this is the propositional constant TRUE.
   */
  public abstract boolean isTrue();

  /**
   * TODO: doc Returns <code>true</code> iff this is a local formula.
   * 
   * @return <code>true</code> iff this is a local formula.
   */
  public abstract boolean isIntuitionisticLocalFormula();

  /**
   * Returns the factory used to build this formula.
   * 
   * @return the factory used to build this formula.
   */
  public abstract FormulaFactory getFactory();

  /**
   * Returns the formula type of this formula.
   * 
   * @return the formula type.
   */
  public abstract FormulaType getFormulaType();

  /**
   * Returns the size of this formula.
   * 
   * @return the size of this formula.
   */
  public int size() {
    return size;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.index;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Formula other = (Formula) obj;
    if (this.index != other.index)
      return false;
    return true;
  }

}
