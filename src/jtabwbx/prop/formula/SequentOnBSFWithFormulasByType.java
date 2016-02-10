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

import jtabwb.util.ImplementationError;
import jtabwbx.prop.basic.FormulaType;

/**
 * Implementation of the {@link _Sequent} interface using
 * {@link BitSetOfFormulas} to represent the left-hand and the right-hand sides.
 * 
 * @author Mauro Ferrari
 */
public class SequentOnBSFWithFormulasByType implements _Sequent, Cloneable {

  private FormulaFactory formulaFactory;

  private BitSetOfFormulas leftside;
  private BitSetOfFormulas rightside;
  private BitSetOfFormulas[] leftFormulas;
  private BitSetOfFormulas[] rightFormulas;

  public SequentOnBSFWithFormulasByType(FormulaFactory formulaFactory) {
    this.formulaFactory = formulaFactory;
    this.leftside = new BitSetOfFormulas(formulaFactory);
    this.rightside = new BitSetOfFormulas(formulaFactory);
    this.leftFormulas = new BitSetOfFormulas[FormulaType.values().length];
    this.rightFormulas = new BitSetOfFormulas[FormulaType.values().length];
  }

  @Override
  public void addLeft(Formula wff) {
    int idx = wff.getIndex();
    if (!leftside.get(idx)) {
      leftside.set(idx); // set the bit
      int type_idx = FormulaType.getFormulaType(wff).ordinal();
      if (leftFormulas[type_idx] == null)
        leftFormulas[type_idx] = new BitSetOfFormulas(formulaFactory);
      leftFormulas[type_idx].add(wff);
    }
  }

  @Override
  public void addLeft(Collection<Formula> formulas) {
    for (Formula formula : formulas)
      this.addLeft(formula);
  }

  public void addLeft(BitSetOfFormulas formulas) {
    for (Formula formula : formulas)
      this.addLeft(formula);
  }

  @Override
  public void addRight(Formula wff) {
    int idx = wff.getIndex();
    if (!rightside.get(idx)) {
      rightside.set(idx); // set the bit
      int type_idx = FormulaType.getFormulaType(wff).ordinal();
      if (rightFormulas[type_idx] == null)
        rightFormulas[type_idx] = new BitSetOfFormulas(formulaFactory);
      rightFormulas[type_idx].add(wff);
    }
  }

  @Override
  public void addRight(Collection<Formula> formulas) {
    for (Formula formula : formulas)
      this.addRight(formula);
  }

  public void addRight(BitSetOfFormulas formulas) {
    for (Formula formula : formulas)
      this.addRight(formula);
  }

  @Override
  public void clearLeft() {
    leftside.clear();
  }

  @Override
  public void clearRight() {
    rightside.clear();
    for (int i = 0; i < rightFormulas.length; i++)
      rightFormulas[i] = null;
  }

  @Override
  public SequentOnBSFWithFormulasByType clone() {
    try {
      SequentOnBSFWithFormulasByType result = (SequentOnBSFWithFormulasByType) super.clone();
      result.formulaFactory = this.formulaFactory;
      result.leftside = this.leftside.clone();
      result.rightside = this.rightside.clone();
      result.leftFormulas = new BitSetOfFormulas[FormulaType.values().length];
      for (int i = 0; i < leftFormulas.length; i++)
        if (this.leftFormulas[i] != null)
          result.leftFormulas[i] = this.leftFormulas[i].clone();
      result.rightFormulas = new BitSetOfFormulas[FormulaType.values().length];
      for (int i = 0; i < rightFormulas.length; i++)
        if (this.rightFormulas[i] != null)
          result.rightFormulas[i] = this.rightFormulas[i].clone();
      return result;
    } catch (CloneNotSupportedException e) {
      throw new ImplementationError(e.getMessage());
    }
  }

  @Override
  public boolean containsLeft(Formula wff) {
    return leftside.contains(wff);
  }

  public boolean containsLeft(FormulaType type) {
    return leftFormulas[type.ordinal()] != null;
  }

  @Override
  public boolean containsRight(Formula wff) {
    return rightside.contains(wff);
  }

  public boolean containsRight(FormulaType type) {
    return rightFormulas[type.ordinal()] != null;
  }

  @Override
  public String format() {
    return this.toString();
  }

  public FormulaFactory getFormulaFactory() {
    return formulaFactory;
  }

  @Override
  public Collection<Formula> getLeftFormulas() {
    return leftside.getAllFormulas();
  }

  @Override
  public Formula getLeft(FormulaType formulaType) {
    if (leftFormulas[formulaType.ordinal()] == null)
      return null;
    else
      return leftFormulas[formulaType.ordinal()].getFirst();
  }

  @Override
  public Collection<Formula> getLeftFormulas(FormulaType formulaType) {
    if (leftFormulas[formulaType.ordinal()] == null)
      return null;
    else
      return leftFormulas[formulaType.ordinal()].getAllFormulas(formulaType);
  }

  @Override
  public Formula getRight(FormulaType formulaType) {
    if (rightFormulas[formulaType.ordinal()] == null)
      return null;
    else
      return rightFormulas[formulaType.ordinal()].getFirst();
  }

  @Override
  public Collection<Formula> getRightFormulas() {
    return rightside.getAllFormulas();
  }

  @Override
  public Collection<Formula> getRightFormulas(FormulaType formulaType) {
    if (rightFormulas[formulaType.ordinal()] == null)
      return null;
    else
      return rightFormulas[formulaType.ordinal()].getAllFormulas(formulaType);
  }

  @Override
  public boolean isIdentityAxiom() {
    return leftside.intersects(rightside);
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

  public BitSetOfFormulas leftSide() {
    return leftside.clone();
  }

  public int leftSideCardinality() {
    return leftside.cardinality();
  }

  @Override
  public Iterator<Formula> leftSideIterator() {
    return leftside.iterator();
  }

  public BitSetOfFormulas rightSide() {
    return rightside.clone();
  }

  public int rigthSideCardinality() {
    return rightside.cardinality();
  }

  @Override
  public Iterator<Formula> rigtSideIterator() {
    return rightside.iterator();
  }

  @Override
  public boolean removeLeft(Formula wff) {
    int idx = wff.getIndex();
    if (leftside.get(idx)) {
      leftside.set(idx, false);
      int type_idx = wff.getFormulaType().ordinal();
      BitSetOfFormulas set = leftFormulas[type_idx];
      set.remove(wff);
      if (set.cardinality() == 0)
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
      BitSetOfFormulas set = rightFormulas[type_idx];
      set.remove(wff);
      if (set.cardinality() == 0)
        rightFormulas[type_idx] = null;
      return true;
    } else
      return false;
  }

  @Override
  public void stablePart() {
    this.clearRight();
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

}