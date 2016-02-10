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

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import jtabwb.util.ImplementationError;
import jtabwbx.prop.basic.FormulaType;

/**
 * Implementation of the {@link _Sequent} interface using lists to store left
 * and right formulas. A sequent has the form <code>S ==&gt; T</code> where
 * <code>S</code> and <code>T</code> are sets of propositional formulas.
 * 
 * @author Mauro Ferrari
 */
public class SequentOnLists implements _Sequent, Cloneable {

  private FormulaSetOnList[] left;
  private FormulaSetOnList[] right;

  /**
   * Builds an empty sequent.
   */
  public SequentOnLists() {
    left = new FormulaSetOnList[FormulaType.values().length];
    right = new FormulaSetOnList[FormulaType.values().length];
  }

  @Override
  public Collection<Formula> getLeftFormulas() {
    _FormulaSet set = new FormulaSetOnList();
    for (_FormulaSet s : left)
      if (s != null)
        set.addAll(s);
    if (set.cardinality() == 0)
      return null;
    else
      return set.getAllFormulas();
  }

  @Override
  public Collection<Formula> getLeftFormulas(FormulaType formulaType) {
    int setIndex = formulaType.ordinal();
    if (left[setIndex] == null)
      return null;
    else
      return left[setIndex].getAllFormulas();
  }

  @Override
  public Collection<Formula> getRightFormulas() {
    _FormulaSet set = new FormulaSetOnList();
    for (_FormulaSet s : right)
      if (s != null)
        set.addAll(s);
    if (set.cardinality() == 0)
      return null;
    else
      return set.getAllFormulas();
  }

  @Override
  public Collection<Formula> getRightFormulas(FormulaType formulaType) {
    int setIndex = formulaType.ordinal();
    if (right[setIndex] == null)
      return null;
    else
      return right[setIndex].getAllFormulas();
  }

  @Override
  public void addLeft(Formula wff) {
    FormulaType type = FormulaType.getFormulaType(wff);
    int index = type.ordinal();
    if (left[index] == null) {
      left[index] = new FormulaSetOnList();
      left[index].add(wff);
    } else
      left[index].add(wff);
  }

  @Override
  public void addRight(Formula wff) {
    int index = FormulaType.getFormulaType(wff).ordinal();
    if (right[index] == null) {
      right[index] = new FormulaSetOnList();
      right[index].add(wff);
    } else
      right[index].add(wff);
  }

  /*
   * Returns the first formula from the set with the specified index or null if
   * such a set is null. return
   */
  private Formula getLeftFormula(int index) {
    if (left[index] != null)
      return left[index].getFirst();
    else
      return null;
  }

  /*
   * @see jpintp.g3i.sequents._Sequent#getLeftFormulaOfType(jpintp.g3i.sequents.
   * FormulaType)
   */
  @Override
  public Formula getLeft(FormulaType formulaType) {
    return getLeftFormula(formulaType.ordinal());
  }

  /*
   * Returns the first formula from the set with the specified index or null if
   * such a set is null. return
   */
  private Formula getRightFormula(int index) {
    if (right[index] != null)
      return right[index].getFirst();
    else
      return null;
  }

  /*
   * @see
   * jpintp.g3i.sequents._Sequent#getRightFormulaOfType(jpintp.g3i.sequents.
   * FormulaType)
   */
  @Override
  public Formula getRight(FormulaType formulaType) {
    return getRightFormula(formulaType.ordinal());
  }

  @Override
  public boolean isRightSideEmpty() {
    for (FormulaSetOnList set : right)
      if (set != null)
        return false;
    return true;
  }

  @Override
  public boolean isLeftSideEmpty() {
    for (FormulaSetOnList set : left)
      if (set != null)
        return false;
    return true;
  }

  @Override
  public boolean isEmpty() {
    return isLeftSideEmpty() && isLeftSideEmpty();
  }

  @Override
  public boolean removeLeft(Formula wff) {
    FormulaType type = FormulaType.getFormulaType(wff);
    int index = type.ordinal();
    if (left[index] != null) {
      boolean result = left[index].remove(wff);
      if (left[index].cardinality() == 0)
        left[index] = null;
      return result;
    } else
      return false;
  }

  @Override
  public boolean removeRight(Formula wff) {
    FormulaType type = FormulaType.getFormulaType(wff);
    int index = type.ordinal();
    if (right[index] != null) {
      boolean result = right[index].remove(wff);
      if (right[index].cardinality() == 0)
        right[index] = null;
      return result;
    } else
      return false;
  }

  @Override
  public boolean isIdentityAxiom() {
    for (FormulaType type : FormulaType.values()) {
      int index = type.ordinal();
      if (left[index] != null && right[index] != null)
        for (Formula rw : right[index])
          if (left[index].contains(rw))
            return true;
    }
    return false;
  }

  @Override
  public boolean containsLeft(Formula wff) {
    FormulaType type = FormulaType.getFormulaType(wff);
    int index = type.ordinal();
    return left[index] != null && left[index].contains(wff);
  }

  public boolean containsLeft(FormulaType type) {
    return left[type.ordinal()] != null;
  }

  @Override
  public boolean containsRight(Formula wff) {
    FormulaType type = FormulaType.getFormulaType(wff);
    int index = type.ordinal();
    return right[index] != null && right[index].contains(wff);
  }

  public boolean containsRight(FormulaType type) {
    return right[type.ordinal()] != null;
  }

  @Override
  public SequentOnLists clone() {
    try {
      SequentOnLists cloned = (SequentOnLists) super.clone();
      // left 
      cloned.left = new FormulaSetOnList[FormulaType.values().length];
      for (int i = 0; i < left.length; i++)
        if (this.left[i] != null)
          cloned.left[i] = this.left[i].clone();

      // right
      cloned.right = new FormulaSetOnList[FormulaType.values().length];
      for (int i = 0; i < right.length; i++)
        if (this.right[i] != null)
          cloned.right[i] = this.right[i].clone();
      return cloned;
    } catch (CloneNotSupportedException e) {
      throw new ImplementationError("Clone not supported: " + e.getMessage());
    }
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
    SequentOnLists other = (SequentOnLists) obj;

    return Arrays.equals(this.left, other.left) && Arrays.equals(this.right, other.right);
  }

  @Override
  public String format() {
    return this.toString();
  }

  @Override
  public String toString() {
    String lstr = "";
    for (int i = 0; i < FormulaType.values().length; i++)
      if (left[i] != null)
        lstr += left[i].toString();

    String rstr = "";
    for (int i = 0; i < FormulaType.values().length; i++)
      if (right[i] != null)
        rstr += right[i].toString();

    return lstr + "==>\n" + rstr;
  }

  @Override
  public void stablePart() {
    this.clearRight();
  }

  @Override
  public void clearLeft() {
    for (int i = 0; i < left.length; i++)
      left[i] = null;
  }

  @Override
  public void clearRight() {
    for (int i = 0; i < right.length; i++)
      right[i] = null;
  }

  @Override
  public void addLeft(Collection<Formula> formulas) {
    for (Formula formula : formulas)
      this.addRight(formula);
  }

  @Override
  public void addRight(Collection<Formula> formulas) {
    for (Formula formula : formulas)
      this.addRight(formula);
  }

  @Override
  public Iterator<Formula> leftSideIterator() {
    return getLeftFormulas().iterator();
  }

  @Override
  public Iterator<Formula> rigtSideIterator() {
    return getRightFormulas().iterator();
  }
  
}
