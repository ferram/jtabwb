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

import java.util.HashMap;

import jtabwbx.prop.basic.PropositionalConnective;
import jtabwbx.prop.btformula.BTFormula;
import jtabwbx.prop.btformula.BTFormulaCompound;
import jtabwbx.prop.btformula.BTFormulaProposition;

/**
 * Factory for this implementation of propositional formulas based on the set of
 * logical connectives specified by {@link PropositionalConnective}.
 * 
 * @author Mauro Ferrari
 */
public class BTPropositionalFormulaFactory {

  private HashMap<String, BTFormulaProposition> varManager;
  public static final BTFormulaProposition FALSE = BTFormulaProposition.FALSE;
  public static final BTFormulaProposition TRUE = BTFormulaProposition.TRUE;

  // Private constructor prevents instantiation from other classes
  public BTPropositionalFormulaFactory() {
    varManager = new HashMap<String, BTFormulaProposition>();
    varManager.put(TRUE.getName(), TRUE);
    varManager.put(FALSE.getName(), FALSE);
  }

  public BTFormula buildAtomic(String name) {
    BTFormulaProposition prop = varManager.get(name);
    if (prop == null) {
      prop = new BTFormulaProposition(name);
      varManager.put(name, prop);
    }
    return prop;
  }

  public BTFormula buildCompound(PropositionalConnective mainConnective, BTFormula... subformulas) {
    if (subformulas == null) {
      if (mainConnective.arity() != 0)
        throw new ImplementationError("Wrong number of subformulas");
    } else if (subformulas.length != mainConnective.arity())
      throw new ImplementationError("Wrong number of subformulas");

    try {
      BTFormulaCompound wff;
      switch (mainConnective) {
      case NOT:
        wff = new BTFormulaCompound(mainConnective, subformulas[0]);
        break;
      case AND:
      case OR:
      case IMPLIES:
      case EQ:
        wff = new BTFormulaCompound(mainConnective, subformulas[0], subformulas[1]);
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

  public String getDescription() {
    return null;
  }

}
