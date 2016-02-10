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
import java.util.LinkedList;

import jtabwb.util.ImplementationError;
import jtabwbx.prop.basic.FormulaType;

/**
 * Implementation of the {@link _Sequent} interface using an array of integers
 * to represents the sequent. TODO: doc
 * 
 * @author Mauro Ferrari
 */
public class SequentOnArray implements _Sequent, Cloneable {

  private static final int ABSENT = 0;
  private static final int LEFT = 1;
  private static final int RIGHT = 2;
  private static final int BOTH = 3;
  private static int NUMBER_OF_FORMULA_TYPE = FormulaType.values().length;

  private int[] sequent;
  private FormulaFactory factory;
  private BitSetOfFormulas[] leftFormulas;
  private BitSetOfFormulas[] rightFormulas;
  private BitSetOfFormulas clashes;

  public SequentOnArray(FormulaFactory factory) {
    this.factory = factory;
    this.sequent = new int[factory.numberOfGeneratedFormulas()];
    this.leftFormulas = new BitSetOfFormulas[NUMBER_OF_FORMULA_TYPE];
    this.rightFormulas = new BitSetOfFormulas[NUMBER_OF_FORMULA_TYPE];
    this.clashes = new BitSetOfFormulas(factory);
  }

  @Override
  public Collection<Formula> getLeftFormulas() {
    Collection<Formula> coll = new LinkedList<Formula>();
    for (BitSetOfFormulas set : leftFormulas)
      if (set != null)
        coll.addAll(set.getAllFormulas());
    return coll.size() == 0 ? null : coll;
  }

  @Override
  public Collection<Formula> getLeftFormulas(FormulaType formulaType) {
    return leftFormulas[formulaType.ordinal()] == null ? null : leftFormulas[formulaType.ordinal()]
        .getAllFormulas();
  }

  @Override
  public Collection<Formula> getRightFormulas() {
    Collection<Formula> coll = new LinkedList<Formula>();
    for (FormulaType ft : FormulaType.values()) {
      if (rightFormulas[ft.ordinal()] != null)
        coll.addAll(rightFormulas[ft.ordinal()].getAllFormulas());

    }
    return coll.size() == 0 ? null : coll;
  }

  @Override
  public Collection<Formula> getRightFormulas(FormulaType formulaType) {
    return rightFormulas[formulaType.ordinal()] == null ? null : rightFormulas[formulaType
        .ordinal()].getAllFormulas();
  }

  @Override
  public void addLeft(Formula wff) {
    // update array sequent
    int idx = wff.getIndex();

    // if wff is already in the left hand-side do nothing
    if (sequent[idx] == LEFT || sequent[idx] == BOTH)
      return;

    if (sequent[idx] == RIGHT) {
      sequent[idx] = BOTH;
      clashes.add(wff);
    } else
      sequent[idx] = LEFT;

    // update leftFormulas
    int type = wff.getFormulaType().ordinal();
    if (leftFormulas[type] == null)
      leftFormulas[type] = new BitSetOfFormulas(factory);

    leftFormulas[type].add(wff);
  }

  @Override
  public void addRight(Formula wff) {
    // update array sequent
    int idx = wff.getIndex();

    // if wff is already in the right hand-side do nothing
    if (sequent[idx] == RIGHT || sequent[idx] == BOTH)
      return;

    if (sequent[idx] == LEFT) {
      sequent[idx] = BOTH;
      clashes.add(wff);
    } else
      sequent[idx] = RIGHT;

    // update rightFormulas
    int type = wff.getFormulaType().ordinal();
    if (rightFormulas[type] == null)
      rightFormulas[type] = new BitSetOfFormulas(factory);

    rightFormulas[type].add(wff);
  }

  @Override
  public Formula getLeft(FormulaType formulaType) {
    BitSetOfFormulas set = leftFormulas[formulaType.ordinal()];
    return set == null ? null : set.getFirst();
  }

  @Override
  public Formula getRight(FormulaType formulaType) {
    BitSetOfFormulas set = rightFormulas[formulaType.ordinal()];
    return set == null ? null : set.getFirst();
  }

  @Override
  public boolean removeLeft(Formula wff) {
    int idx = wff.getIndex();

    // if wff is not in the left hand-side do nothing
    if (sequent[idx] != LEFT && sequent[idx] != BOTH)
      return false;

    if (sequent[idx] == BOTH) {
      sequent[idx] = RIGHT;
      clashes.remove(wff);
    } else
      sequent[idx] = ABSENT;

    // update leftFormulas
    int type = wff.getFormulaType().ordinal();
    if (leftFormulas[type].cardinality() == 1)
      leftFormulas[type] = null;
    else
      leftFormulas[type].remove(wff);

    return true;
  }

  @Override
  public boolean removeRight(Formula wff) {
    int idx = wff.getIndex();

    // if wff is not in the right hand-side do nothing
    if (sequent[idx] != RIGHT && sequent[idx] != BOTH)
      return false;

    // update array sequent
    if (sequent[idx] == BOTH) {
      sequent[idx] = LEFT;
      clashes.remove(wff);
    } else
      sequent[idx] = ABSENT;

    // update rightFormulas
    int type = wff.getFormulaType().ordinal();
    if (rightFormulas[type].cardinality() == 1)
      rightFormulas[type] = null;
    else
      rightFormulas[type].remove(wff);
    return true;
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

  public boolean containsLeft(FormulaType type) {
    return leftFormulas[type.ordinal()] != null;
  }

  @Override
  public boolean containsRight(Formula wff) {
    int idx = wff.getIndex();
    return sequent[idx] == RIGHT || sequent[idx] == BOTH;
  }

  public boolean containsRight(FormulaType type) {
    return rightFormulas[type.ordinal()] != null;
  }

  @Override
  public boolean isLeftSideEmpty() {
    for (BitSetOfFormulas set : leftFormulas)
      if (set != null)
        return false;
    return true;
  }

  @Override
  public boolean isRightSideEmpty() {
    for (BitSetOfFormulas set : rightFormulas)
      if (set != null)
        return false;
    return true;
  }

  @Override
  public boolean isEmpty() {
    return isLeftSideEmpty() && isRightSideEmpty();
  }

  @Override
  public SequentOnArray clone() {
    try {
      SequentOnArray cloned = (SequentOnArray) super.clone();
      cloned.sequent = this.sequent.clone();
      cloned.leftFormulas = new BitSetOfFormulas[NUMBER_OF_FORMULA_TYPE];
      cloned.rightFormulas = new BitSetOfFormulas[NUMBER_OF_FORMULA_TYPE];
      for (int i = 0; i < NUMBER_OF_FORMULA_TYPE; i++) {
        if (this.leftFormulas[i] != null)
          cloned.leftFormulas[i] = this.leftFormulas[i].clone();
        if (this.rightFormulas[i] != null)
          cloned.rightFormulas[i] = this.rightFormulas[i].clone();
      }
      cloned.clashes = this.clashes.clone();
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
    for (int i = 0; i < sequent.length; i++)
      if (sequent[i] == LEFT || sequent[i] == BOTH)
        this.removeLeft(factory.getByIndex(i));
  }

  @Override
  public void clearRight() {
    for (int i = 0; i < sequent.length; i++)
      if (sequent[i] == RIGHT || sequent[i] == BOTH)
        this.removeRight(factory.getByIndex(i));
  }

  @Override
  public void addLeft(Collection<Formula> formulas) {
    for (Formula wff : formulas)
      this.addLeft(wff);
  }

  @Override
  public void addRight(Collection<Formula> formulas) {
    for (Formula wff : formulas)
      this.addRight(wff);
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
