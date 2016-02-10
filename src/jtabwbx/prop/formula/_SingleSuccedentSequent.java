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

import jtabwb.engine._AbstractGoal;
import jtabwbx.prop.basic.FormulaType;

/**
 * Interface modelling a sequent of the form <code>S ==&gt; H</code> where
 * <code>S</code> is a sets of propositional formulas and <code>H</code> is a
 * propositional formula.
 */
public interface _SingleSuccedentSequent extends _AbstractGoal {

  /**
   * Add the specified formula to the left hand side of this sequent.
   * 
   * @param wff the formula to add.
   */
  public abstract void addLeft(Formula wff);

  /**
   * This method add the specified formula in the right hand side of this
   * sequent. Since this class models single conclusion sequent if a formula is
   * already present in the right hand side this method replace the previous
   * formula with the specified one.
   * 
   * @param wff the formula to add in the right hand side of the sequent.
   */
  public abstract void addRight(Formula wff);

  /**
   * Returns a fresh copy of this sequent.
   * 
   * @return a clone of this sequent.
   */
  public abstract _SingleSuccedentSequent clone();

  /**
   * Returns <code>true</code> if this sequent contains the specified formula in
   * the left hand side and false otherwise.
   * 
   * @param wff the formula to search
   * @return <code>true</code> if the right hand side of the sequent contains
   * the specified
   */
  public abstract boolean containsLeft(Formula wff);

  /**
   * Returns the set containg all the formulas in the left hand side of this
   * sequent or <code>null</code> if the left hand side of this sequent is
   * empty.
   * 
   * @return the left-hand side of this sequent or <code>null</code>.
   */
  public abstract Collection<Formula> getAllLeftFormulas();

  /**
   * Returns the collection of all the formulas in the left hand side of this
   * sequent with the specified type or <code>null</code> if the left hand side
   * of this sequent does not contain any formula of the specified type.
   * 
   * @param formulaType the type of the formulas.
   * @return the collection containing all the formulas of the specified type or
   * <code>null</code> if the left hand side of the sequent does not contain any
   * formula of the sepcified type.
   */
  public abstract Collection<Formula> getAllLeftFormulas(FormulaType formulaType);

  /**
   * Returns the formula in the right hand side of this sequent or
   * <code>null</code> if the right hand side of this sequent is empty.
   * 
   * @return the formula on the right or <code>null</code>.
   */
  public abstract Formula getRight();

  /**
   * Returns a formula of the specified type contained in the left hand side of
   * the sequent or <code>null</code> if no formula of the specified type occurs
   * in this sequent.
   * 
   * @param formulaType the type of the formula to be returned
   * @return a formula of the specified type contained in the left hand side of
   * the sequent or <code>null</code>.
   */
  public abstract Formula getLeft(FormulaType formulaType);

  /**
   * Returns a formula from the left hand side of the sequent or
   * <code>null</code> if le theft hand side is empty.
   * 
   * @return a formula or <code>null</code>.
   */
  public abstract Formula getLeft();

  /**
   * Returns the formula in the right hand side of this sequent if the formula
   * has the specified type or <code>null</code> if the formula in the right
   * hand side does not have the specified type.
   * 
   * @param formulaType the type of the formula to be returned
   * @return the formula in the right hand-side of the sequent if it has the
   * specified or <code>null</code> if the type of the formula in the right hand
   * side is different from the specified type.
   */
  public abstract Formula getRightFormulaOfType(FormulaType formulaType);

  /**
   * Returns <code>true</code> iff this is an identity axiom, that is a sequent
   * of the kind <code>S,H ==&gt; H</code>.
   * 
   * @return <code>true</code> iff this is an identity axiom.
   */
  public abstract boolean isIdentityAxiom();

  /**
   * Removes the specified formula from the left hand side of this sequent, if
   * it is present.
   * 
   * @param wff the formula to remove.
   * @return <code>true</code> if this sequent contained the specified element.
   */
  public abstract boolean removeLeft(Formula wff);

  /**
   * Removes the right formula form this sequent.
   * 
   * @return <code>true</code> if this sequent contained a formula in the
   * right-hand side.
   */
  public abstract boolean removeRight();

}