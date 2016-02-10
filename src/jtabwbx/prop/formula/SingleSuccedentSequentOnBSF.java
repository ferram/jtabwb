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

import jtabwb.util.ImplementationError;
import jtabwbx.prop.basic.FormulaType;

/**
 * Implementation of the {@link _Sequent} interface on {@link Formula} using a
 * {@link BitSetOfFormulas} to represent the left-hand side.
 * 
 * @author Mauro Ferrari
 */
public class SingleSuccedentSequentOnBSF implements _SingleSuccedentSequent, Cloneable {

  private FormulaFactory formulaFactory;
  private BitSetOfFormulas leftSide;
  private BitSetOfFormulas[] leftFormulas;
  private Formula rightSide;

  public SingleSuccedentSequentOnBSF(FormulaFactory factory) {
    this.formulaFactory = factory;
    this.leftSide = new BitSetOfFormulas(factory);
    this.leftFormulas = new BitSetOfFormulas[FormulaType.values().length];
    this.rightSide = null;
  }

  @Override
  public void addLeft(Formula wff) {
    int idx = wff.getIndex();
    if (!leftSide.get(idx)) {
      leftSide.set(idx); // set the bit
      int type_idx = FormulaType.getFormulaType(wff).ordinal();
      if (leftFormulas[type_idx] == null)
        leftFormulas[type_idx] = new BitSetOfFormulas(formulaFactory);
      leftFormulas[type_idx].add(wff);
    }

  }

  @Override
  public void addRight(Formula wff) {
    rightSide = wff;
  }

  public void clearLeft() {
    leftSide.clear();
    for (int i = 0; i < leftFormulas.length; i++)
      leftFormulas[i] = null;
  }

  public void clearRight() {
    rightSide = null;
  }

  @Override
  public SingleSuccedentSequentOnBSF clone() {
    try {
      SingleSuccedentSequentOnBSF result = (SingleSuccedentSequentOnBSF) super.clone();
      result.formulaFactory = this.formulaFactory;
      result.leftSide = this.leftSide.clone();
      result.rightSide = this.rightSide;
      result.leftFormulas = new BitSetOfFormulas[FormulaType.values().length];
      for (int i = 0; i < leftFormulas.length; i++)
        if (this.leftFormulas[i] != null)
          result.leftFormulas[i] = this.leftFormulas[i].clone();
      return result;
    } catch (CloneNotSupportedException e) {
      throw new ImplementationError(e.getMessage());
    }
  }

  @Override
  public boolean containsLeft(Formula wff) {
    return leftSide.get(wff.getIndex());
  }

  @Override
  public String format() {
    return this.toString();
  }

  @Override
  public Collection<Formula> getAllLeftFormulas() {
    return leftSide.getAllFormulas();
  }

  @Override
  public Collection<Formula> getAllLeftFormulas(FormulaType formulaType) {
    if (leftFormulas[formulaType.ordinal()] == null)
      return null;
    else
      return leftFormulas[formulaType.ordinal()].getAllFormulas();
  }

  public FormulaFactory getFormulaFactory() {
    return formulaFactory;
  }

  @Override
  public Formula getLeft() {
    return leftSide.getFirst();
  }

  @Override
  public Formula getLeft(FormulaType formulaType) {
    if (leftFormulas[formulaType.ordinal()] == null)
      return null;
    else
      return leftFormulas[formulaType.ordinal()].getFirst();
  }

  @Override
  public Formula getRight() {
    return rightSide;
  }

  @Override
  public Formula getRightFormulaOfType(FormulaType formulaType) {
    if (rightSide != null && FormulaType.getFormulaType(rightSide) == formulaType)
      return rightSide;
    else
      return null;
  }

  @Override
  public boolean isIdentityAxiom() {
    if (rightSide == null)
      return false;
    else
      return leftSide.get(rightSide.getIndex());
  }

  public boolean leftSideIsEmpty() {
    return leftSide.cardinality() == 0;
  }

  /**
   * Returns the bitset of formulas representing the left-hand side of this
   * sequent; if the left hand side is empty this method returns an empty
   * bitset.
   * 
   * @return the bitset of formulas representig the left side of the sequent
   */
  public BitSetOfFormulas leftSide() {
    return leftSide.clone();
  }

  public int leftSideCardinality() {
    return leftSide.cardinality();
  }

  @Override
  public boolean removeLeft(Formula wff) {
    int idx = wff.getIndex();
    if (leftSide.get(idx)) {
      leftSide.set(idx, false);
      int type_idx = wff.getFormulaType().ordinal();
      BitSetOfFormulas set = leftFormulas[type_idx];
      set.remove(wff);
      if (set.cardinality() == 0)
        leftFormulas[type_idx] = null;
      return true;
    } else
      return false;
  }

  public boolean removeRight() {
    if (rightSide != null) {
      rightSide = null;
      return true;
    } else
      return false;
  }

  @Override
  public String toString() {
    String str = "";
    Collection<Formula> lf = leftSide.getAllFormulas();
    str += lf == null ? "" : lf.toString();

    return str + "==>\n" + (rightSide == null ? "" : rightSide.toString());
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.leftSide == null) ? 0 : this.leftSide.hashCode());
    result = prime * result + ((this.rightSide == null) ? 0 : this.rightSide.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SingleSuccedentSequentOnBSF other = (SingleSuccedentSequentOnBSF) obj;
    if (this.leftSide == null) {
      if (other.leftSide != null)
        return false;
    } else if (!this.leftSide.equals(other.leftSide))
      return false;
    if (this.rightSide == null) {
      if (other.rightSide != null)
        return false;
    } else if (!this.rightSide.equals(other.rightSide))
      return false;
    return true;
  }

}
