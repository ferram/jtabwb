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

import jtabwbx.modal.basic._ModalFormula;

/**
 * The root class for parsed formulas implementation.
 * 
 * @author Mauro Ferrari
 */
public abstract class BTModalFormula implements _ModalFormula {

  static boolean forceParenthesis = true;

  @Override
  public abstract boolean isAtomic();

  @Override
  public abstract boolean isCompound();

  /**
   * If <code>force</code> is <code>true</code> all the subformulas are
   * parenthesised when the string description of the formula is generated.
   * 
   * @param force if <code>true</code> all subformulas are parenthesized.
   */
  public static void forceParenthesis(boolean force) {
    forceParenthesis = force;
  }

  @Override
  public String format() {
    return this.toString();
  }

  public abstract BTModalFormula convertToCNF();

  public abstract BTModalFormula[] immediateSubformulas();

}
