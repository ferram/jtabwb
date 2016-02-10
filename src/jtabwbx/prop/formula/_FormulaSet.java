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
package jtabwbx.prop.formula;

import java.util.Collection;
import java.util.Iterator;

/**
 * Interface for a set of formulas.
 * 
 * @author Mauro Ferrari
 *
 */
public interface _FormulaSet extends Iterable<Formula> {

  /**
   * Add the specified formula to this set and returns <code>true</code> if this
   * set did not already contain the specified formula.
   * 
   * @param wff the formula to be added to this set
   * @return <code>true</code> if this set did not already contain the specified
   * formula
   */
  public abstract boolean add(Formula wff);

  /**
   * Add all the formulas in the specified set to this formula set.
   * 
   * @param other the set of formulas to add to this set.
   */
  public abstract void addAll(_FormulaSet other);

  /**
   * The number of elements in this set.
   * 
   * @return the number of elements in this set.
   */
  public abstract int cardinality();

  /**
   * Returns a fresh copy of this set.
   * 
   * @return a fresh copy of this set.
   */
  public _FormulaSet clone();

  /**
   * Returns <code>true</code> if and only if this set contains the specified
   * formula.
   * 
   * @param swff the formula to search.
   * @return <code>true</code> if and only if this set contains the specified
   * formula.
   */
  public abstract boolean contains(Formula swff);

  /**
   * Returns a collection containing all the formulas in this set or
   * <code>null</code> if this set is empty.
   * 
   * @return the collection of the formula in this set or <code>null</code> if
   * this set is empty.
   */
  public abstract Collection<Formula> getAllFormulas();

  /**
   * Returns the first formula in this set or <code>null</code> if this set is
   * empty.
   * 
   * @return Returns the first formula in this set or <code>null</code> if this
   * set is empty.
   */
  public abstract Formula getFirst();

  /**
   * Returns <code>true</code> if and only if this set is empty.
   * 
   * @return <code>true</code> if and only if this set is empty.
   */
  public boolean isEmpty();

  /**
   * Returns an iterator over the formulas in this set.
   * 
   * @return an iterator over the formulas in this set.
   */
  public abstract Iterator<Formula> iterator();

  /**
   * Returns the specified formula from this set.
   * 
   * @param wff the formula to remove.
   * @return <code>true</code> if this set contained the specified formula.
   */
  public abstract boolean remove(Formula wff);

  /**
   * Returns an array of formulas containing all of the formulas in this set.
   * 
   * @return an array of formulas containing all of the formulas in this set.
   */
  public abstract Formula[] toArray();

}