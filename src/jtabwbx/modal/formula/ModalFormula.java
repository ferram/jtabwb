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

import jtabwbx.modal.basic.ModalFormulaType;
import jtabwbx.modal.basic._ModalFormula;

/**
 * A modal formula.
 * 
 * @author Mauro Ferrari
 */
public abstract class ModalFormula implements _ModalFormula {

  private int index;
  int size;

  ModalFormula() {
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
  public abstract ModalFormula[] immediateSubformulas();

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
   * @param prop the formula proposition.
   * @return <code>true</code> iff this formula contains the specified
   * proposition.
   */
  abstract boolean containsProposition(ModalFormulaProposition prop);

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
   * Returns the factory used to build this formula.
   * 
   * @return the factory used to build this formula.
   */
  public abstract ModalFormulaFactory getFactory();

  /**
   * Returns the formula type of this formula.
   * 
   * @return the formula type.
   */
  public abstract ModalFormulaType getFormulaType();

  /**
   * Returns the size of this formula.
   * 
   * @return the size of this formula.
   */
  public int size() {
    return size;
  }
}
