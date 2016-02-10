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
import java.util.LinkedList;

import jtabwb.util.ImplementationError;
import jtabwbx.prop.basic.FormulaType;

/**
 * Implementation of the {@link _SingleSuccedentSequent} interface using lists
 * to store left formulas.
 * 
 * @author Mauro Ferrari
 */
public class SingleSuccedentSequentOnLists implements _SingleSuccedentSequent, Cloneable {

  private FormulaSetOnList[] left;
  private Formula right;

  /**
   * Builds an empty sequent.
   */
  public SingleSuccedentSequentOnLists() {
    left = new FormulaSetOnList[FormulaType.values().length];
  }

  @Override
  public Collection<Formula> getAllLeftFormulas() {
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
  public Collection<Formula> getAllLeftFormulas(FormulaType formulaType) {
    int setIndex = formulaType.ordinal();
    if (left[setIndex] == null)
      return null;
    else
      return left[setIndex].getAllFormulas();
  }

  @Override
  public Formula getRight() {
    return right;
  }

  @Override
  public void addLeft(Formula wff) {
    FormulaType type = FormulaType.getFormulaType(wff);
    int index = type.ordinal();
    if (left[index] == null) {
      left[index] = new FormulaSetOnList();
      left[index].add(wff);
    } else if (!left[index].contains(wff))
      left[index].add(wff);
  }

  @Override
  public void addRight(Formula wff) {
    right = wff;
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

  @Override
  public Formula getLeft(FormulaType formulaType) {
    return getLeftFormula(formulaType.ordinal());
  }
  
  @Override
  public Formula getLeft() {
    for (LinkedList<Formula> list : left)
      if (list != null)
        return list.getFirst();
    return null;
  }

  @Override
  public Formula getRightFormulaOfType(FormulaType formulaType) {
    if (right == null)
      return null;

    if (formulaType == FormulaType.getFormulaType(right))
      return right;
    else
      return null;
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
  public boolean removeRight() {
    if (right != null) {
      right = null;
      return true;
    } else
      return false;
  }

  @Override
  public boolean isIdentityAxiom() {
    if (right == null)
      return false;
    FormulaType type = FormulaType.getFormulaType(right);
    int index = type.ordinal();
    return left[index] != null && left[index].contains(right);
  }

  @Override
  public boolean containsLeft(Formula wff) {
    FormulaType type = FormulaType.getFormulaType(wff);
    int index = type.ordinal();
    return left[index] != null && left[index].contains(wff);
  }

  @Override
  public SingleSuccedentSequentOnLists clone() {
    try{
    SingleSuccedentSequentOnLists cloned = (SingleSuccedentSequentOnLists)super.clone();
    cloned.left = new FormulaSetOnList[FormulaType.values().length];
    for (int i = 0; i < left.length; i++)
      if (this.left[i] != null)
        cloned.left[i] = this.left[i].clone();
    cloned.right = this.right;
    return cloned;
    } catch (CloneNotSupportedException e){
      throw new ImplementationError("Clone not supported:" + e.getMessage());
    }
  }

  @Override
  public String format() {
    return this.toString();
  }

  @Override
  public String toString() {
    String str = "";
    for (int i = 0; i < FormulaType.values().length; i++)
      if (left[i] != null)
        str += left[i].toString();

    return str + "==>\n" + (right == null ? "" : right.toString());
  }

}
