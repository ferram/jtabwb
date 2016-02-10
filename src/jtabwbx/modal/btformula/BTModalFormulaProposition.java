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

import jtabwbx.modal.basic.ModalConnective;

/**
 * An instance of this class models an atomic formula (a proposition).
 * 
 * @author Mauro Ferrari
 */
public class BTModalFormulaProposition extends BTModalFormula {

  /**
   * The formula representing the true constant, its name is <code>"true"</code>.
   * 
   */
  public static final BTModalFormulaProposition TRUE = new BTModalFormulaProposition("true");

  /**
   * The formula representing the FALSE constant,  its name is
   * <code>"false"</code>.
   */
  public static final BTModalFormulaProposition FALSE = new BTModalFormulaProposition("false");
  
  private String name;

  /**
   * Builds the atomic formula with the specified name.
   * 
   * @param name the name of the propositional variable
   */
  public BTModalFormulaProposition(String name) {
    this.name = name;
  }

  public boolean isAtomic() {
    return true;
  }

  public boolean isCompound() {
    return false;
  }

  public String getName() {
    return name;
  }

  @Override
  public ModalConnective mainConnective() {
    return null;
  }

  @Override
  public BTModalFormula[] immediateSubformulas() {
    return null;
  }

  @Override
  public String shortName() {
    return "atomic";
  }

  /**
   * Returns <code>true</code> iff this formula proposition and the one
   * referenced by <code>prop</code> have the same name.
   * 
   * @param prop the other formula proposition.
   * @return true iff the two propositions have the same name.
   */
  public boolean equals(BTModalFormulaProposition prop) {
    return name.equals(prop.name);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof BTModalFormulaProposition)
      return this.equals((BTModalFormulaProposition) obj);
    else
      return false;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public BTModalFormula convertToCNF() {
    return this;
  }

}
