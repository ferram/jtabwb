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
 * An atomic propositional formula.
 * 
 * @author Lorenzo Bossi
 */
public final class FormulaProposition extends Formula {

  private String name;
  private final boolean isTrue;
  private final boolean isFalse;
  private int hash;
  private boolean parentesised;
  private FormulaFactory factory;

  FormulaProposition(FormulaFactory factory, String name, boolean isTrue, boolean isFalse) {
    super();
    this.factory = factory;
    this.name = name;
    this.isTrue = isTrue;
    this.isFalse = isFalse;
    this.hash = name.hashCode();
    this.parentesised = false;
  }

  @Override
  public FormulaFactory getFactory() {
    return factory;
  }

  public String getName() {
    return name;
  }

  @Override
  public String shortName() {
    return "propositional variable";
  }

  @Override
  public Formula calculateBooleanSimplification() {
    return this;
  }

  @Override
  public Formula applySubstitution(PropositionalSubstitution subst) {
    Formula newF = subst.get(this);
    if (newF != null)
      return newF;
    return this;
  }

  @Override
  public Formula applySubstitution(Substitution subst) {
    Formula newF = subst.get(this);
    if (newF != null)
      return newF;
    return this;
  }

  @Override
  public Formula applyIntuitionisticPartialSubstitution(Substitution subst) {
    Formula newF = subst.get(this);
    if (newF != null)
      return newF;
    return this;
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
  public Formula[] immediateSubformulas() {
    return null;
  }

  @Override
  public int hashCode() {
    return hash;
  }

  @Override
  public boolean equals(Object other) {
    if (other.getClass() != this.getClass())
      return false;
    return name.equals(((FormulaProposition) other).name);
  }

  @Override
  public String format() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public boolean containsProposition(FormulaProposition proposition) {
    return this == proposition;
  }

  @Override
  public boolean containsTrue() {
    return isTrue;
  }

  @Override
  public boolean containsFalse() {
    return isFalse;
  }

  /**
   * Returns <code>true</code> iff this propositional formula represents FALSE.
   * 
   * @return <code>true</code> iff this propositional formula represents FALSE.
   */
  public boolean isFalse() {
    return this.isFalse;
  }

  /**
   * Returns <code>true</code> iff this propositional formula represents TRUE.
   * 
   * @return <code>true</code> iff this propositional formula represents TRUE.
   */
  public boolean isTrue() {
    return this.isTrue;
  }

  @Override
  public boolean isIntuitionisticLocalFormula() {
    if (isTrue)
      return false;
    else
      return true;
  }

  @Override
  public FormulaType getFormulaType() {
    return FormulaType.ATOMIC_WFF;
  }

}
