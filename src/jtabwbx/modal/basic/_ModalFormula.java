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

import jtabwb.engine._AbstractFormula;

/**
 * Interface describing modal formulas on the set of connectives
 * {@link ModalConnective}.
 * 
 * @author Mauro Ferrari
 */
public interface _ModalFormula extends _AbstractFormula {

  /**
   * Returns <code>true</code> iff this formula is atomic.
   * 
   * @return <code>true</code> iff this formula is atomic.
   */
  public abstract boolean isAtomic();

  /**
   * Returns <code>true</code> iff this formula is compound.
   * 
   * @return <code>true</code> iff this formula is compound.
   */
  public abstract boolean isCompound();

  /**
   * The main logical operator of this formula.
   * 
   * @return the main connective of this formula.
   */
  public ModalConnective mainConnective();

  /**
   * The immediate subformulas of this formula.
   * 
   * @return the subformulas of this formula.
   */
  public _ModalFormula[] immediateSubformulas();

}
