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
package jtabwbx.modal.formula;

import jtabwbx.modal.basic.ModalConnective;
import jtabwbx.modal.basic.ModalFormulaType;

/**
 * An propositional variable.
 * 
 * @author Mauro Ferrari
 */
public final class ModalFormulaProposition extends ModalFormula {

  private String name;
  private final boolean isTrue;
  private final boolean isFalse;
  private int hash;
  private ModalFormulaFactory factory;

  ModalFormulaProposition(ModalFormulaFactory factory, String name, boolean isTrue, boolean isFalse) {
    super();
    this.factory = factory;
    this.name = name;
    this.isTrue = isTrue;
    this.isFalse = isFalse;
    this.hash = name.hashCode();
  }

  @Override
  public ModalFormulaFactory getFactory() {
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
  public boolean isAtomic() {
    return true;
  }

  @Override
  public boolean isCompound() {
    return false;
  }

  @Override
  public ModalConnective mainConnective() {
    return null;
  }

  @Override
  public ModalFormula[] immediateSubformulas() {
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
    return name.equals(((ModalFormulaProposition) other).name);
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
  public boolean containsProposition(ModalFormulaProposition proposition) {
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
  public ModalFormulaType getFormulaType() {
    return ModalFormulaType.ATOMIC_WFF;
  }

}
