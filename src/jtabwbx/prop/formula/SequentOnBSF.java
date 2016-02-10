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
public class SequentOnBSF implements _Sequent, Cloneable {

  private FormulaFactory formulaFactory;

  private BitSetOfFormulas leftside;
  private BitSetOfFormulas rightside;

  public SequentOnBSF(FormulaFactory formulaFactory) {
    this.formulaFactory = formulaFactory;
    this.leftside = new BitSetOfFormulas(formulaFactory);
    this.rightside = new BitSetOfFormulas(formulaFactory);
  }

  @Override
  public void addLeft(Formula wff) {
    leftside.add(wff);
  }

  /**
   * Add all the formulas in the specified formula bitset to the left-hand side
   * of this sequent.
   * 
   * @param formulas he set of formulas to add to the lef-hand side of this
   * sequent.
   */
  public void addLeft(BitSetOfFormulas formulas) {
    leftside.or(formulas);
  }

  @Override
  public void addRight(Formula wff) {
    rightside.add(wff);
  }

  /**
   * Add all the formulas in the specified formula bitset to the right-hand side
   * of this sequent.
   * 
   * @param formulas the set of formulas to add to the right-hand side of this
   * sequent.
   */
  public void addRight(BitSetOfFormulas formulas) {
    rightside.or(formulas);
  }

  @Override
  public boolean containsLeft(Formula wff) {
    return leftside.contains(wff);
  }

  public boolean containsLeft(FormulaType type) {
    return leftside.containsFormulaOfType(type);
  }

  @Override
  public boolean containsRight(Formula wff) {
    return rightside.contains(wff);
  }

  public boolean containsRight(FormulaType type) {
    return rightside.containsFormulaOfType(type);
  }

  @Override
  public Formula getLeft(FormulaType formulaType) {
    return leftside.getFirst(formulaType);
  }

  @Override
  public Collection<Formula> getLeftFormulas() {
    return leftside.getAllFormulas();
  }

  @Override
  public Collection<Formula> getLeftFormulas(FormulaType formulaType) {
    return leftside.getAllFormulas(formulaType);
  }

  @Override
  public Formula getRight(FormulaType formulaType) {
    return rightside.getFirst(formulaType);
  }

  @Override
  public Collection<Formula> getRightFormulas() {
    return rightside.getAllFormulas();
  }

  @Override
  public Collection<Formula> getRightFormulas(FormulaType formulaType) {
    return rightside.getAllFormulas(formulaType);
  }

  @Override
  public boolean isEmpty() {
    return leftside.isEmpty() && rightside.isEmpty();
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

  public BitSetOfFormulas leftSide() {
    return leftside.clone();
  }

  public int leftSideCardinality() {
    return leftside.cardinality();
  }

  public BitSetOfFormulas rightSide() {
    return rightside.clone();
  }

  public int rigthSideCardinality() {
    return rightside.cardinality();
  }

  @Override
  public boolean removeLeft(Formula wff) {
    return leftside.remove(wff);
  }

  @Override
  public boolean removeRight(Formula wff) {
    return rightside.remove(wff);
  }

  @Override
  public SequentOnBSF clone() {
    try {
      SequentOnBSF result = (SequentOnBSF) super.clone();
      result.formulaFactory = this.formulaFactory;
      result.leftside = this.leftside.clone();
      result.rightside = this.rightside.clone();
      return result;
    } catch (CloneNotSupportedException e) {
      throw new ImplementationError(e.getMessage());
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

  public FormulaFactory getFormulaFactory() {
    return formulaFactory;
  }

  @Override
  public void stablePart() {
    this.clearRight();
  }

  @Override
  public void clearLeft() {
    leftside.clear();
  }

  @Override
  public void clearRight() {
    rightside.clear();
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

  @Override
  public Iterator<Formula> leftSideIterator() {
    return leftside.iterator();
  }

  @Override
  public Iterator<Formula> rigtSideIterator() {
    return rightside.iterator();
  }

}
