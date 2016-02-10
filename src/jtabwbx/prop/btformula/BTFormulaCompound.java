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
 ******************************************************************************/
package jtabwbx.prop.btformula;

import java.util.Arrays;

import jtabwb.util.CaseNotImplementedImplementationError;
import jtabwbx.prop.basic.PropositionalConnective;

/**
 * Implementation of a propositional compound formula, that is a formula
 * obtained by combining sub-formulas by means of a propositional connective.
 * 
 * @author Mauro Ferrari
 */
public class BTFormulaCompound extends BTFormula {

  protected PropositionalConnective mainConnective;
  protected BTFormula[] subformulas;

  /**
   * Builds the compound formula with the specified binary infix connective and
   * the specified sub-formulas. No check is made that the specified connective
   * is binary.
   * 
   * @param mainConnective a binary connective.
   * @param left the left sub-formula.
   * @param right the right sub-formula.
   */
  public BTFormulaCompound(PropositionalConnective mainConnective, BTFormula left, BTFormula right) {
    this.subformulas = new BTFormula[] { left, right };
    this.mainConnective = mainConnective;
  }

  /**
   * Builds the compound formula with the specified unary connective and the
   * specified sub-formula. No check is made that the specified connective is
   * unary.
   * 
   * @param mainConnective a unary connective.
   * @param subformula the sub-formula.
   */
  public BTFormulaCompound(PropositionalConnective mainConnective, BTFormula subformula) {
    this.subformulas = new BTFormula[] { subformula };
    this.mainConnective = mainConnective;
  }

  /*
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;

    return (this.mainConnective == ((BTFormulaCompound) obj).mainConnective)
        && (Arrays.equals(this.subformulas, ((BTFormulaCompound) obj).subformulas));
  }

  @Override
  public BTFormula[] immediateSubformulas() {
    return subformulas;
  }

  @Override
  public PropositionalConnective mainConnective() {
    return mainConnective;
  }

  @Override
  public boolean isAtomic() {
    return false;
  }

  @Override
  public boolean isCompound() {
    return true;
  }

  @Override
  public String shortName() {
    return mainConnective.getName() + "-formula";
  }

  @Override
  public String format() {
    return toString();
  }

  /**
   * Returns the string representation of the formula.
   * 
   * @return string representing the formula.
   */
  @Override
  public String toString() {
    switch (mainConnective) {
    case NOT: {
      BTFormula left = subformulas[0];
      String subf = "";
      if (left.isAtomic())
        subf = left.toString();
      else {
        switch (left.mainConnective()) {
        case NOT:
          subf = "(" + left.toString() + ")";
          break;
        default:
          subf = left.toString();
        }
      }
      return mainConnective().toString() + subf;
    }
    case AND:
    case OR:
    case EQ:
    case IMPLIES: {
      String str = "";
      BTFormula[] subformulas = immediateSubformulas();
      String[] subformulasString = new String[subformulas.length];
      for (int i = 0; i < subformulasString.length; i++)
        subformulasString[i] = subformulas[i].toString();
      for (int i = 0; i < subformulasString.length; i++)
        str +=
            subformulasString[i]
                + (i < subformulasString.length - 1 ? " " + mainConnective.toString() + " " : "");

      return "(" + str + ")";
    }
    default:
      throw new CaseNotImplementedImplementationError(mainConnective.getName());
    }

  }
}
