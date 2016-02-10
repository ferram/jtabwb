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

import java.util.Collection;
import java.util.Iterator;

import jtabwbx.modal.formula.ModalFormula;

/**
 *  Interface to be implemented by sets of modal formulas. 
 */ 
public interface _ModalFormulaSet extends Iterable<ModalFormula> {

  /**
   * Add the specified formula to this set and returns <code>true</code> if this
   * set did not already contain the specified formula.
   * 
   * @param wff the formula to be added to this set
   * @return <code>true</code> if this set did not already contain the specified
   * formula
   */
  public abstract boolean add(ModalFormula wff);

  public abstract void addAll(_ModalFormulaSet other);

  public abstract int cardinality();

  public _ModalFormulaSet clone();

  public abstract boolean contains(ModalFormula swff);

  public abstract Collection<ModalFormula> getAllFormulas();

  public abstract ModalFormula getFirst();

  public boolean isEmpty();

  public abstract Iterator<ModalFormula> iterator();

  public abstract boolean remove(ModalFormula wff);

  public abstract ModalFormula[] toArray();

}