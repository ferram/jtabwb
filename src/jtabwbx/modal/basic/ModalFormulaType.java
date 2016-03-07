/*******************************************************************************
 * Copyright (C) 2013, 2016 Mauro Ferrari
 *  
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *  
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package jtabwbx.modal.basic;

import jtabwb.util.CaseNotImplementedImplementationError;
import jtabwbx.modal.formula.ModalFormula;

/**
 * The types of propositional formulas represented by formulas implemented by
 * {@link ModalFormula}. The type is determined by the main connective of the
 * formula.
 * 
 * @author Mauro Ferrari
 */
public enum ModalFormulaType {
  /**
   * Atomic formula.
   */
  ATOMIC_WFF,
  /**
   * Conjunctive formula.
   */
  AND_WFF,
  /**
   * Disjunctive formula.
   */
  OR_WFF,
  /**
   * Implicative formula.
   */
  IMPLIES_WFF,
  /**
   * Negated formula.
   */
  NOT_WFF,
  /**
   * Equivalence formula.
   */
  EQ_WFF,
  /**
   * Box prefixed formula.
   */
  BOX_WFF,
  /**
   * Dia (diamond) prefixed formula.
   */
  DIA_WFF;

  /**
   * Returns the type for the specified formula.
   * 
   * @param wff the formula.
   * @return the type of the formula.
   */
  public static ModalFormulaType getFormulaType(ModalFormula wff) {
    if (wff.isAtomic())
      return ATOMIC_WFF;
    switch (wff.mainConnective()) {
    case AND:
      return AND_WFF;
    case OR:
      return OR_WFF;
    case IMPLIES:
      return IMPLIES_WFF;
    case NOT:
      return NOT_WFF;
    case EQ:
      return EQ_WFF;
    case BOX:
      return BOX_WFF;
    case DIA:
      return DIA_WFF;
    default:
      throw new CaseNotImplementedImplementationError(wff.mainConnective().getName());
    }
  }
}
