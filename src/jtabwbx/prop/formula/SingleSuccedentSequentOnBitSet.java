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

import java.util.BitSet;
import java.util.Collection;
import java.util.LinkedList;

import jtabwb.util.ImplementationError;
import jtabwbx.prop.basic.FormulaType;

/**
 * Implementation of the {@link _Sequent} interface on {@link Formula} using a
 * BitSet to represent the left-hand side of the sequent.
 * 
 * @author Mauro Ferrari
 */
public class SingleSuccedentSequentOnBitSet implements _SingleSuccedentSequent, Cloneable {

  private FormulaFactory factory;
  private BitSet leftSide;
  private LinkedList<Formula>[] leftFormulas; // Left formulas collected by
                                              // FormulaType
  private Formula rightSide;

  @SuppressWarnings("unchecked")
  public SingleSuccedentSequentOnBitSet(FormulaFactory factory) {
    leftSide = new BitSet(factory.numberOfGeneratedFormulas());
    leftFormulas = new LinkedList[FormulaType.values().length];
    rightSide = null;
  }

  @Override
  public void addLeft(Formula wff) {
    int idx = wff.getIndex();

    if (!leftSide.get(idx)) {
      leftSide.set(idx); // set the bit
      int type_idx = FormulaType.getFormulaType(wff).ordinal();
      if (leftFormulas[type_idx] == null)
        leftFormulas[type_idx] = new LinkedList<Formula>();
      leftFormulas[type_idx].add(wff);
    }
  }

  @Override
  public void addRight(Formula wff) {
    rightSide = wff;
  }

  public static SingleSuccedentSequentOnBitSet buildArraySequent(FormulaFactory factory,
      Collection<Formula> leftFormulas, Formula rightFormula) {
    SingleSuccedentSequentOnBitSet seq = new SingleSuccedentSequentOnBitSet(factory);

    if (leftFormulas != null)
      for (Formula wff : leftFormulas)
        seq.addLeft(wff);
    seq.addRight(rightFormula);
    return seq;
  }

  @SuppressWarnings("unchecked")
  @Override
  public SingleSuccedentSequentOnBitSet clone() {
    try {
      SingleSuccedentSequentOnBitSet result = (SingleSuccedentSequentOnBitSet) super.clone();
      result.leftSide = (BitSet) this.leftSide.clone();
      result.leftFormulas = (LinkedList<Formula>[]) new LinkedList[FormulaType.values().length];
      for (int i = 0; i < this.leftFormulas.length; i++)
        if (this.leftFormulas[i] != null)
          result.leftFormulas[i] = (LinkedList<Formula>) this.leftFormulas[i].clone();

      result.rightSide = this.rightSide;
      return result;
    } catch (CloneNotSupportedException e) {
      throw new ImplementationError(e.getMessage());
    }

  }

  /**
   * Returns true if this sequent is contained in the one specifed as agrument;
   * this holds if <code>this</code> and <code>other</code> have the same
   * formula in the right-hand side and the set of formulas in the left-hand
   * side of <code>this</code> includes the set of formulas in the left-hand
   * side of <code>other</code>.
   * 
   * @param other the other sequent.
   * @return true if this sequent is contained in the other.
   */
  public boolean contains(SingleSuccedentSequentOnBitSet other) {
    if (this.rightSide != other.rightSide)
      return false;
    for (int i = other.leftSide.nextSetBit(0); i >= 0; i = other.leftSide.nextSetBit(i + 1)) {
      if (!leftSide.get(i))
        return false;
    }
    return true;
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
    Collection<Formula> coll = new LinkedList<Formula>();
    for (LinkedList<Formula> set : leftFormulas)
      if (set != null)
        coll.addAll(set);
    return coll.size() == 0 ? null : coll;
  }

  @Override
  public Collection<Formula> getAllLeftFormulas(FormulaType formulaType) {
    return leftFormulas[formulaType.ordinal()];
  }

  @Override
  public Formula getLeft() {
    int idx = leftSide.nextSetBit(0);
    if (idx == -1)
      return null;
    else
      return factory.getByIndex(idx);
  }

  @Override
  public Formula getLeft(FormulaType formulaType) {
    LinkedList<Formula> list = leftFormulas[formulaType.ordinal()];
    if (list == null)
      return null;
    else
      return list.getFirst();
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

  /**
   * Returns <code>true</code> if this sequent is contained in the other.
   * 
   * @param other the other sequent.
   * @return true if this sequent is contained in the other.
   */
  public boolean isContained(SingleSuccedentSequentOnBitSet other) {
    if (this.rightSide != other.rightSide)
      return false;
    for (int i = leftSide.nextSetBit(0); i >= 0; i = leftSide.nextSetBit(i + 1)) {
      if (!other.leftSide.get(i))
        return false;
    }
    return true;
  }

  @Override
  public boolean isIdentityAxiom() {
    if (rightSide == null)
      return false;
    else
      return leftSide.get(rightSide.getIndex());
  }

  @Override
  public boolean removeLeft(Formula wff) {
    int idx = wff.getIndex();
    if (leftSide.get(idx)) {
      leftSide.set(idx, false);
      int type_idx = FormulaType.getFormulaType(wff).ordinal();
      LinkedList<Formula> list = leftFormulas[type_idx];
      list.remove(wff);
      if (list.size() == 0)
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
    for (int i = 0; i < FormulaType.values().length; i++)
      if (leftFormulas[i] != null)
        str += leftFormulas[i].toString();

    return str + "==>\n" + (rightSide == null ? "" : rightSide.toString());
  }

  /*
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.leftSide == null) ? 0 : this.leftSide.hashCode());
    result = prime * result + ((this.rightSide == null) ? 0 : this.rightSide.hashCode());
    return result;
  }

  /*
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SingleSuccedentSequentOnBitSet other = (SingleSuccedentSequentOnBitSet) obj;
    if (this.leftSide == null) {
      if (other.leftSide != null)
        return false;
    } else if (!this.leftSide.equals(other.leftSide))
      return false;
    if (this.rightSide == null) {
      if (other.rightSide != null)
        return false;
    } else if (this.rightSide != other.rightSide)
      return false;
    return true;
  }

}
