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

import jtabwb.engine._AbstractGoal;
import jtabwbx.prop.basic.FormulaType;

/**
 * Interface modeling a sequent of the form <code>S ==&gt; T</code> where
 * <code>S</code> and <code>T</code> are sets of propositional formulas.
 */
public interface _Sequent extends _AbstractGoal {

  /**
   * Returns the set containg all the formulas in the left hand side of this
   * sequent or <code>null</code> if the left hand side of this sequent is
   * empty.
   * 
   * @return the left-hand side of this sequent or <code>null</code>.
   */
  public abstract Collection<Formula> getLeftFormulas();

  /**
   * Returns the collection of all the formulas in the left hand side of this
   * sequent with the specified type or <code>null</code> if the left hand side
   * of this sequent does not contain any formula of the specified type.
   * 
   * @param formulaType the type of the formulas.
   * @return the collection containing all the formulas of the specified type or
   * <code>null</code> if the left hand side of the sequent does not contain any
   * formula of the specified type.
   */
  public abstract Collection<Formula> getLeftFormulas(FormulaType formulaType);

  /**
   * Returns the set containg all the formulas in the right hand side of this
   * sequent or <code>null</code> if the right hand side of this sequent is
   * empty.
   * 
   * @return the left-hand side of this sequent or <code>null</code>.
   */
  public abstract Collection<Formula> getRightFormulas();

  /**
   * Returns the collection of all the formulas in the right hand side of this
   * sequent with the specified type or <code>null</code> if the right hand side
   * of this sequent does not contain any formula of the specified type.
   * 
   * @param formulaType the type of the formulas.
   * @return the collection containing all the formulas of the specified type or
   * <code>null</code> if the left hand side of the sequent does not contain any
   * formula of the specified type.
   */
  public abstract Collection<Formula> getRightFormulas(FormulaType formulaType);

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
   * Returns a formula of the specified type contained in the left hand side of
   * the sequent or <code>null</code> if no formula of the specified type occurs
   * in this sequent.
   * 
   * @param formulaType the type of the formula to be returned.
   * @return a formula of the specified type contained in the left hand side of
   * the sequent or <code>null</code>.
   */
  public abstract Formula getLeft(FormulaType formulaType);

  /**
   * Returns the formula in the right hand side of this sequent or
   * <code>null</code> if the formula in the right hand side does not have the
   * specified type.
   * 
   * @param formulaType the type of the formula to be returned.
   * @return the formula in the right hand-side of the the sequent or
   * <code>null</code>.
   */
  public abstract Formula getRight(FormulaType formulaType);

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
   * @param wff the formula to remove.
   * @return <code>true</code> if this sequent contained a formula in the
   * right-hand side.
   */
  public abstract boolean removeRight(Formula wff);

  /**
   * Returns <code>true</code> iff this is an identity axiom, that is a sequent
   * of the kind <code>S,H ==&gt; H</code>.
   * 
   * @return <code>true</code> iff this is an identity axiom.
   */
  public abstract boolean isIdentityAxiom();

  /**
   * Returns <code>true</code> if this sequent contains the specified formula in
   * the left hand side and <code>false</code> otherwise.
   *
   * @param wff the formula to search
   * @return <code>true</code> if the left hand side of this sequent contains
   * the specified
   */
  public abstract boolean containsLeft(Formula wff);

  /**
   * Returns <code>true</code> if this sequent contains the specified formula in
   * the right hand side and <code>false</code> otherwise.
   * 
   * @param wff the formula to search
   * @return <code>true</code> if the right hand side of this sequent contains
   * the specified
   */
  public abstract boolean containsRight(Formula wff);

  /**
   * Returns <code>true</code> if this sequent contains at least a formula of
   * the specified type in the right-hand side.
   * 
   * @param type the type of the formula.
   * @return <code>true</code> if this sequent contains at least a formula of
   * the specified type in the right-hand side.
   */
  public boolean containsRight(FormulaType type);

  /**
   * Returns <code>true</code> if this sequent contains at least a formula of
   * the specified type in the left-hand side.
   * 
   * @param type the type of the formula.
   * @return <code>true</code> if this sequent contains at least a formula of
   * the specified type in the left-hand side.
   */
  public boolean containsLeft(FormulaType type);

  /**
   * Returns a fresh copy of this sequent.
   * 
   * @return a clone of this sequent.
   */
  public abstract _Sequent clone();

  /**
   * Returns <code>true</code> iff the left-hand side of this sequent is empty.
   * 
   * @return <code>true</code> iff the right-hand side of this sequent is empty
   */
  public boolean isLeftSideEmpty();

  /**
   * Returns <code>true</code> iff the right-hand side of this sequent is empty.
   * 
   * @return <code>true</code> iff the right-hand side of this sequent is empty
   */
  public boolean isRightSideEmpty();

  /**
   * Returns <code>true</code> iff this sequent is empty.
   * 
   * @return <code>true</code> iff this sequent is empty
   */
  public boolean isEmpty();

  public void stablePart();

  public void clearLeft();

  public void clearRight();

  public void addLeft(Collection<Formula> formulas);

  public void addRight(Collection<Formula> formulas);

  public Iterator<Formula> leftSideIterator();

  public Iterator<Formula> rigtSideIterator();

}