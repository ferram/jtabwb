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
 * Implementation of the {@link _SingleSuccedentSequent} interface using an
 * array of integers to represents the sequent. 
 * 
 * @author Mauro Ferrari
 */
public class SingleSuccedentSequentOnArray implements _SingleSuccedentSequent, Cloneable {

  private static final int ABSENT = 0;
  private static final int LEFT = 1;
  private static final int RIGHT = 2;
  private static final int BOTH = 3;

  private BitSetOfFormulas clashes;
  private int[] sequent;
  private LinkedList<Formula>[] leftFormulas; // formulas in the left-hand side
                                              // collected by FormulaType
  private Formula rightFormula;

  @SuppressWarnings("unchecked")
  public SingleSuccedentSequentOnArray(FormulaFactory factory) {
    sequent = new int[factory.numberOfGeneratedFormulas()];
    leftFormulas = new LinkedList[FormulaType.values().length];
    rightFormula = null;
    clashes = new BitSetOfFormulas(factory);
  }

  int getArrayLength() {
    return sequent.length;
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
  public Formula getRight() {
    return rightFormula;
  }

  private void addClashOn(Formula formula) {
    clashes.add(formula);
  }

  private void removeClashOn(Formula formula) {
    clashes.remove(formula);
  }

  @Override
  public void addLeft(Formula wff) {
    int idx = wff.getIndex();

    // if wff is already in the left hand-side do nothing
    if (sequent[idx] == LEFT || sequent[idx] == BOTH)
      return;

    if (sequent[idx] == RIGHT) {
      sequent[idx] = BOTH;
      addClashOn(wff);
    } else
      sequent[idx] = LEFT;
    addToLeftFormulas(wff);
  }

  private void addToLeftFormulas(Formula wff) {
    int idx = FormulaType.getFormulaType(wff).ordinal();
    if (leftFormulas[idx] == null)
      leftFormulas[idx] = new LinkedList<Formula>();
    leftFormulas[idx].add(wff);
  }

  private boolean removeFromLeftFormulas(Formula wff) {
    int idx = FormulaType.getFormulaType(wff).ordinal();
    LinkedList<Formula> list = leftFormulas[idx];
    boolean result = false;
    if (list != null) {
      result = list.remove(wff);
      if (list.size() == 0)
        leftFormulas[idx] = null;
    }
    return result;
  }

  @Override
  public void addRight(Formula wff) {
    removeRight();
    int idx = wff.getIndex();
    if (sequent[idx] == LEFT) {
      sequent[idx] = BOTH;
      addClashOn(wff);
    } else
      sequent[idx] = RIGHT;
    rightFormula = wff;
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
  public Formula getLeft() {
    for (LinkedList<Formula> list : leftFormulas)
      if (list != null)
        return list.getFirst();
    return null;
  }

  @Override
  public Formula getRightFormulaOfType(FormulaType formulaType) {
    if (rightFormula != null && FormulaType.getFormulaType(rightFormula) == formulaType)
      return rightFormula;
    else
      return null;
  }

  @Override
  public boolean removeLeft(Formula wff) {
    int idx = wff.getIndex();
    if (sequent[idx] == BOTH) {
      sequent[idx] = RIGHT;
      removeClashOn(wff);
    } else
      sequent[idx] = ABSENT;
    return removeFromLeftFormulas(wff);
  }

  public boolean removeRight() {
    if (rightFormula != null) {
      int idx = rightFormula.getIndex();
      if (sequent[idx] == BOTH) {
        sequent[idx] = LEFT;
        removeClashOn(rightFormula);
      } else
        sequent[idx] = ABSENT;
      return true;
    } else
      return false;
  }

  @Override
  public boolean isIdentityAxiom() {
    return !clashes.isEmpty();
  }

  @Override
  public boolean containsLeft(Formula wff) {
    int idx = wff.getIndex();
    return sequent[idx] == LEFT || sequent[idx] == BOTH;
  }

  @SuppressWarnings("unchecked")
  @Override
  public SingleSuccedentSequentOnArray clone() {
    try {
      SingleSuccedentSequentOnArray cloned = (SingleSuccedentSequentOnArray) super.clone();
      cloned.clashes = this.clashes.clone();
      cloned.sequent = this.sequent.clone();
      cloned.leftFormulas = new LinkedList[FormulaType.values().length];
      for (int i = 0; i < cloned.leftFormulas.length; i++)
        if (this.leftFormulas[i] != null)
          cloned.leftFormulas[i] = (LinkedList<Formula>) this.leftFormulas[i].clone();
      cloned.rightFormula = this.rightFormula;
      return cloned;
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
    String str = "";
    for (int i = 0; i < FormulaType.values().length; i++)
      if (leftFormulas[i] != null)
        str += leftFormulas[i].toString();

    return str + "==>\n" + (rightFormula == null ? "" : rightFormula.toString());
  }

  public static SingleSuccedentSequentOnArray buildArraySequent(FormulaFactory factory,
      Collection<Formula> leftFormulas, Formula rightFormula) {
    SingleSuccedentSequentOnArray seq = new SingleSuccedentSequentOnArray(factory);

    if (leftFormulas != null)
      for (Formula wff : leftFormulas)
        seq.addLeft(wff);
    seq.addRight(rightFormula);
    return seq;
  }

}
