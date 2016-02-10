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
import java.util.Iterator;
import java.util.LinkedList;

import jtabwb.util.ImplementationError;
import jtabwbx.prop.basic.FormulaType;

/**
 * Implementation of the {@link _Sequent} interface using an array of integers
 * to represents the sequent. TODO: doc
 * 
 * @author Mauro Ferrari
 */
public class SequentOnBitSet implements _Sequent, Cloneable {

  private static int NUMBER_OF_FORMULA_TYPE = FormulaType.values().length;

  private FormulaFactory factory;
  private BitSet leftside;
  private BitSet rightside;
  private LinkedList<Formula>[] leftFormulas;
  private LinkedList<Formula>[] rightFormulas;

  @SuppressWarnings("unchecked")
  public SequentOnBitSet(FormulaFactory factory) {
    this.factory = factory;
    leftside = new BitSet(factory.numberOfGeneratedFormulas());
    rightside = new BitSet(factory.numberOfGeneratedFormulas());
    leftFormulas = new LinkedList[NUMBER_OF_FORMULA_TYPE];
    rightFormulas = new LinkedList[NUMBER_OF_FORMULA_TYPE];
  }

  @Override
  public Collection<Formula> getLeftFormulas() {
    Collection<Formula> coll = new LinkedList<Formula>();
    for (LinkedList<Formula> set : leftFormulas)
      if (set != null)
        coll.addAll(set);
    return coll.size() == 0 ? null : coll;
  }

  @Override
  public Collection<Formula> getLeftFormulas(FormulaType formulaType) {
    return leftFormulas[formulaType.ordinal()];
  }

  @Override
  public Collection<Formula> getRightFormulas() {
    Collection<Formula> coll = new LinkedList<Formula>();
    for (LinkedList<Formula> set : rightFormulas)
      if (set != null)
        coll.addAll(set);
    return coll.size() == 0 ? null : coll;
  }

  @Override
  public Collection<Formula> getRightFormulas(FormulaType formulaType) {
    return rightFormulas[formulaType.ordinal()];
  }

  @Override
  public void addLeft(Formula wff) {
    int idx = wff.getIndex();
    if (!this.leftside.get(idx)) {
      this.leftside.set(idx); // set the bit
      int type_idx = wff.getFormulaType().ordinal();
      if (leftFormulas[type_idx] == null)
        leftFormulas[type_idx] = new LinkedList<Formula>();
      leftFormulas[type_idx].add(wff);
    }
  }

  @Override
  public void addRight(Formula wff) {
    int idx = wff.getIndex();
    if (!this.rightside.get(idx)) {
      this.rightside.set(idx); // set the bit
      int type_idx = wff.getFormulaType().ordinal();
      if (rightFormulas[type_idx] == null)
        rightFormulas[type_idx] = new LinkedList<Formula>();
      rightFormulas[type_idx].add(wff);
    }
  }

  @Override
  public Formula getLeft(FormulaType formulaType) {
    LinkedList<Formula> list = leftFormulas[formulaType.ordinal()];
    return list == null ? null : list.getFirst();
  }

  @Override
  public Formula getRight(FormulaType formulaType) {
    LinkedList<Formula> list = rightFormulas[formulaType.ordinal()];
    return list == null ? null : list.getFirst();
  }

  @Override
  public boolean removeLeft(Formula wff) {
    int idx = wff.getIndex();
    if (leftside.get(idx)) {
      leftside.set(idx, false);
      int type_idx = wff.getFormulaType().ordinal();
      LinkedList<Formula> list = leftFormulas[type_idx];
      list.remove(wff);
      if (list.size() == 0)
        leftFormulas[type_idx] = null;
      return true;
    } else
      return false;
  }

  @Override
  public boolean removeRight(Formula wff) {
    int idx = wff.getIndex();
    if (rightside.get(idx)) {
      rightside.set(idx, false);
      int type_idx = wff.getFormulaType().ordinal();
      LinkedList<Formula> list = rightFormulas[type_idx];
      list.remove(wff);
      if (list.size() == 0)
        rightFormulas[type_idx] = null;
      return true;
    } else
      return false;
  }

  @Override
  public boolean isIdentityAxiom() {
    return leftside.intersects(rightside);
  }

  @Override
  public boolean containsLeft(Formula wff) {
    return leftside.get(wff.getIndex());
  }

  public boolean containsLeft(FormulaType type) {
    return leftFormulas[type.ordinal()] != null;
  }

  @Override
  public boolean containsRight(Formula wff) {
    return rightside.get(wff.getIndex());
  }

  public boolean containsRight(FormulaType type) {
    return rightFormulas[type.ordinal()] != null;
  }

  @Override
  public boolean isLeftSideEmpty() {
    return leftside.isEmpty();
  }

  @Override
  public boolean isRightSideEmpty() {
    return rightside.isEmpty();
  }

  @Override
  public boolean isEmpty() {
    return leftside.isEmpty() && rightside.isEmpty();
  }

  @SuppressWarnings("unchecked")
  @Override
  public SequentOnBitSet clone() {
    try {
      SequentOnBitSet cloned = (SequentOnBitSet) super.clone();
      cloned.leftside = (BitSet) this.leftside.clone();
      cloned.rightside = (BitSet) this.rightside.clone();
      cloned.leftFormulas = new LinkedList[FormulaType.values().length];
      cloned.rightFormulas = new LinkedList[FormulaType.values().length];
      for (int i = 0; i < this.leftFormulas.length; i++)
        if (this.leftFormulas[i] != null)
          cloned.leftFormulas[i] = (LinkedList<Formula>) this.leftFormulas[i].clone();
      for (int i = 0; i < this.rightFormulas.length; i++)
        if (this.rightFormulas[i] != null)
          cloned.rightFormulas[i] = (LinkedList<Formula>) this.rightFormulas[i].clone();

      return cloned;
    } catch (CloneNotSupportedException e) {
      throw new ImplementationError("Clone not supported: " + e.getMessage());
    }
  }

  @Override
  public String format() {
    return this.toString();
  }

  @Override
  public String toString() {
    return toString(this.getLeftFormulas()) + "==>\n" + toString(this.getRightFormulas());
  }

  private String toString(Collection<Formula> list) {
    if (list == null)
      return "";
    Iterator<Formula> it = list.iterator();
    String str = it.next().format();
    while (it.hasNext())
      str += ", " + it.next().format();
    return str;
  }

  @Override
  public void stablePart() {
    this.clearRight();
  }

  @Override
  public void clearLeft() {
    leftside.clear();
    for (int i = 0; i < leftFormulas.length; i++)
      leftFormulas[i] = null;
  }

  @Override
  public void clearRight() {
    rightside.clear();
    for (int i = 0; i < rightFormulas.length; i++)
      rightFormulas[i] = null;
  }

  @Override
  public void addLeft(Collection<Formula> formulas) {
    for (Formula formula : formulas)
      this.addLeft(formula);

  }

  @Override
  public void addRight(Collection<Formula> formulas) {
    for (Formula formula : formulas)
      this.addRight(formula);

  }

  public FormulaFactory getFormulaFactory() {
    return factory;
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
