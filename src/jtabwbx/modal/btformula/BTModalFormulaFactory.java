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

import java.util.HashMap;

import javax.swing.JToggleButton.ToggleButtonModel;

import org.antlr.v4.runtime.tree.ParseTree;

import jtabwb.util.ImplementationError;
import jtabwbx.modal.basic.ModalConnective;

/**
 * Factory for modal formulas.
 * 
 * @author Mauro Ferrari
 */
public class BTModalFormulaFactory {

  private HashMap<String, BTModalFormulaProposition> varManager;
  public static final BTModalFormulaProposition FALSE = BTModalFormulaProposition.FALSE;
  public static final BTModalFormulaProposition TRUE = BTModalFormulaProposition.TRUE;

  // Private constructor prevents instantiation from other classes
  public BTModalFormulaFactory() {
    varManager = new HashMap<String, BTModalFormulaProposition>();
    varManager.put(TRUE.getName(), TRUE);
    varManager.put(FALSE.getName(), FALSE);
  }

  public BTModalFormula buildAtomic(String name) {
    BTModalFormulaProposition prop = varManager.get(name);
    if (prop == null) {
      prop = new BTModalFormulaProposition(name);
      varManager.put(name, prop);
    }
    return prop;
  }

  public BTModalFormula buildCompound(ModalConnective mainConnective, BTModalFormula... subformulas) {
    if (subformulas == null) {
      if (mainConnective.arity() != 0)
        throw new ImplementationError("Wrong number of subformulas");
    } else if (subformulas.length != mainConnective.arity())
      throw new ImplementationError("Wrong number of subformulas");

    try {
      BTModalFormulaCompound wff;
      switch (mainConnective) {
      case NOT:
      case BOX:
      case DIA:
        wff = new BTModalFormulaCompound(mainConnective, subformulas[0]);
        break;
      case AND:
      case OR:
      case IMPLIES:
      case EQ:
        wff = new BTModalFormulaCompound(mainConnective, subformulas[0], subformulas[1]);
        break;
      default:
        throw new ImplementationError();
      }
      return wff;
    } catch (ClassCastException e) {
      throw new ImplementationError("Wrong logical constant: " + mainConnective.getName()
          + getClass().toString());
    }
  }

  
  public BTModalFormula buildFrom(ParseTree parseTree){
    BTModalFormulaBuilder builder = new BTModalFormulaBuilder();
    return builder.buildFrom(parseTree);
  }
  
  public String getDescription() {
    return null;
  }

}
