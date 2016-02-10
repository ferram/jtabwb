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
package jtabwbx.modal.btformula;

import jtabwb.util.CaseNotImplementedImplementationError;
import jtabwbx.modal.basic.ModalConnective;

/**
 * An instance of this class models a compound propositional formula.
 * 
 * @author Mauro Ferrari
 */
public class BTModalFormulaCompound extends BTModalFormula {

  private ModalConnective mainConnective;
  private BTModalFormula[] subformulas;

  /**
   * Build an instance of a compound formula with a binary main connective.
   * 
   * @param mainConnective the connective of the formula.
   * @param left the left sub-formula.
   * @param right the right sub-formula.
   */
  public BTModalFormulaCompound(ModalConnective mainConnective, BTModalFormula left,
      BTModalFormula right) {
    this.subformulas = new BTModalFormula[] { left, right };
    this.mainConnective = mainConnective;
  }

  /**
   * Build an instance of a compound formula with an unary main connective.
   * 
   * @param mainConnective the connective of the formula.
   * @param sub the main sub-formula.
   */
  public BTModalFormulaCompound(ModalConnective mainConnective, BTModalFormula sub) {
    this.subformulas = new BTModalFormula[] { sub };
    this.mainConnective = mainConnective;
  }

  @Override
  public String shortName() {
    return mainConnective.getName() + "-formula";
  }

  @Override
  public BTModalFormula[] immediateSubformulas() {
    return subformulas;
  }

  @Override
  public ModalConnective mainConnective() {
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
  public BTModalFormula convertToCNF() {
    switch (mainConnective) {
    case AND:
      return new BTModalFormulaCompound(ModalConnective.AND, subformulas[0].convertToCNF(),
          subformulas[1].convertToCNF());
    case OR: {// A OR B = NOT ( NOT A AND NOT B)
      BTModalFormula left = buildNegModuloDoubleNegation(subformulas[0].convertToCNF());
      BTModalFormula right = buildNegModuloDoubleNegation(subformulas[1].convertToCNF());
      return new BTModalFormulaCompound(ModalConnective.NOT, new BTModalFormulaCompound(
          ModalConnective.AND, left, right));
    }
    case IMPLIES: { // A IMPLIES B = NOT A OR B = NOT (A AND NOT B)
      BTModalFormula left = subformulas[0].convertToCNF();
      BTModalFormula right = buildNegModuloDoubleNegation(subformulas[1].convertToCNF());
      return new BTModalFormulaCompound(ModalConnective.NOT, new BTModalFormulaCompound(
          ModalConnective.AND, left, right));
    }
    case EQ: { // A EQ B = (A IMPLIES B) AND (B IMPLIES A) = (NOT A OR B) AND (NOT B OR A)
      //  = NOT ( A OR NOT B)  AND NOT (B AND NOT A)
      BTModalFormula Acnf = subformulas[0].convertToCNF();
      BTModalFormula Bcnf = subformulas[1].convertToCNF();
      BTModalFormula AimpliesB =
          new BTModalFormulaCompound(ModalConnective.NOT, new BTModalFormulaCompound(
              ModalConnective.AND, Acnf, buildNegModuloDoubleNegation(Bcnf)));
      BTModalFormula BimpliesA =
          new BTModalFormulaCompound(ModalConnective.NOT, new BTModalFormulaCompound(
              ModalConnective.AND, Bcnf, buildNegModuloDoubleNegation(Acnf)));
      return new BTModalFormulaCompound(ModalConnective.AND, AimpliesB, BimpliesA);
    }
    case NOT:
      return buildNegModuloDoubleNegation(subformulas[0].convertToCNF());
    case BOX:
      return new BTModalFormulaCompound(ModalConnective.BOX, subformulas[0].convertToCNF());
    case DIA:
      return new BTModalFormulaCompound(ModalConnective.NOT, new BTModalFormulaCompound(
          ModalConnective.BOX, buildNegModuloDoubleNegation(subformulas[0].convertToCNF())));
    default:
      throw new CaseNotImplementedImplementationError(mainConnective.getName());
    }
  }

  // returns NOT A if A is not a negated formula and A otherwise
  private BTModalFormula buildNegModuloDoubleNegation(BTModalFormula wff) {
    if (wff.isCompound() && wff.mainConnective() == ModalConnective.NOT)
      return ((BTModalFormula) wff.immediateSubformulas()[0]);
    else
      return new BTModalFormulaCompound(ModalConnective.NOT, wff);
  }

  @Override
  public String toString() {
    String str = "";
    switch (mainConnective) {
    case BOX:
    case DIA: {
      BTModalFormula left = subformulas[0];
      String subf = "";
      if (left.isAtomic())
        subf = " " + left.toString();
      else {
        switch (left.mainConnective()) {
        case DIA:
        case BOX:
        case NOT:
          subf = "(" + left.toString() + ")";
          break;
        default:
          subf = left.toString();
        }
      }
      str = mainConnective().toString() + subf;
    }
      break;
    case NOT: {
      BTModalFormula left = subformulas[0];
      String subf = "";
      if (left.isAtomic())
        subf = left.toString();
      else {
        switch (left.mainConnective()) {
        case DIA:
        case BOX:
        case NOT:
          subf = "(" + left.toString() + ")";
          break;
        default:
          subf = left.toString();
        }
      }
      str = mainConnective().toString() + subf;
    }
      break;
    case AND:
    case OR:
    case IMPLIES:
    case EQ: {
      BTModalFormula[] subformulas = immediateSubformulas();
      String[] subformulasString = new String[subformulas.length];
      for (int i = 0; i < subformulasString.length; i++)
        subformulasString[i] = subformulas[i].toString();
      for (int i = 0; i < subformulasString.length; i++)
        str +=
            subformulasString[i]
                + (i < subformulasString.length - 1 ? " " + mainConnective.toString() + " " : "");
      str = "(" + str + ")";
    }
      break;
    default:
      throw new CaseNotImplementedImplementationError(mainConnective.getName());
    }
    return str;
  }

}
