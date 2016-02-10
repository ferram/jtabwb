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

import jtabwbx.prop.basic.FormulaType;

/**
 * Sets of formulas implemented over bitsets.
 * 
 * @author Mauro Ferrari
 */
public class BitSetOfFormulas extends BitSet implements _FormulaSet, Iterable<Formula> {

  FormulaFactory formulaFactory;

  public BitSetOfFormulas(FormulaFactory factory) {
    super(factory.numberOfGeneratedFormulas());
    this.formulaFactory = factory;
  }

  @Override
  public boolean add(Formula wff) {
    int idx = wff.getIndex();
    if (this.get(idx))
      return true;
    this.set(idx);
    return false;
  }

  @Override
  public void addAll(_FormulaSet set) {
    for (Formula wff : set)
      this.add(wff);
  }

  public void addAll(BitSetOfFormulas set) {
    this.or(set);
  }

  public void and(BitSetOfFormulas set) {
    super.and(set);
  }

  @Override
  public BitSetOfFormulas clone() {
    BitSetOfFormulas newset = (BitSetOfFormulas) super.clone();
    newset.formulaFactory = formulaFactory;
    return newset;
  }

  @Override
  public boolean contains(Formula wff) {
    return this.get(wff.getIndex());
  }

  public boolean containsFormulaOfType(FormulaType type) {
    return getFirst(type) != null;
  }

  /**
   * Returns the collection containing all the formulas in this set or null if
   * this set is empty.
   * 
   * @return the collection storing all the formulas in this set.
   */
  public Collection<Formula> getAllFormulas() {
    LinkedList<Formula> list = new LinkedList<Formula>();
    for (int i = this.nextSetBit(0); i >= 0; i = this.nextSetBit(i + 1)) {
      list.add(formulaFactory.getByIndex(i));
    }
    return list.size() == 0 ? null : list;
  }

  /**
   * Returns a collection containing all formulas with the specified formula
   * type in this set or null if this set does not contain any formulas with the
   * specified formula type.
   * 
   * @param formulaType the type of the formulas to return.
   * @return the collection of all the formulas in this set with the specfied
   * type or <code>null</code> if no formula with the specified type belong to
   * this set.
   */
  public Collection<Formula> getAllFormulas(FormulaType formulaType) {
    LinkedList<Formula> list = new LinkedList<Formula>();
    for (int i = this.nextSetBit(0); i >= 0; i = this.nextSetBit(i + 1)) {
      Formula wff = formulaFactory.getByIndex(i);
      if (wff.getFormulaType() == formulaType)
        list.add(wff);
    }
    return list.size() == 0 ? null : list;
  }

  /**
   * Returns bitset of all the formulas with the specified formula type in this
   * set or null if this set does not contain any formulas with the specified
   * formula type.
   * 
   * @param formulaType the type of the formulas to return.
   * @return the bitset of all the formulas in this set with the specified type
   * or <code>null</code> if this set does not contain formulas with the
   * specified type.
   */
  public BitSetOfFormulas getBitsetOfAllFormulas(FormulaType formulaType) {
    BitSetOfFormulas bset = new BitSetOfFormulas(this.formulaFactory);
    for (int i = this.nextSetBit(0); i >= 0; i = this.nextSetBit(i + 1)) {
      Formula wff = formulaFactory.getByIndex(i);
      if (wff.getFormulaType() == formulaType)
        bset.add(wff);
    }
    return bset.isEmpty() ? null : bset;
  }

  @Override
  public Formula getFirst() {
    int firstIdx = this.nextSetBit(0);
    return firstIdx == -1 ? null : formulaFactory.getByIndex(firstIdx);
  }

  /**
   * Returns the first formula of this bitset and remove it from the bitset. The
   * method returns <code>null</code> if this bitset is empty.
   * 
   * @return the first formula in this bitset or <code>null</code> if this
   * bitset is empty.
   */
  public Formula getFirstAndRemove() {
    int firstIdx = this.nextSetBit(0);
    if (firstIdx == -1)
      return null;
    else {
      Formula wff = formulaFactory.getByIndex(firstIdx);
      this.clear(wff.getIndex());
      return wff;
    }
  }

  /**
   * Returns the first formula with the specified type of this bitset and remove
   * it from the bitset. The method returns <code>null</code> if this bitset
   * does not contain any formula of the specified type.
   * 
   * @param type the type of the formula to return.
   * @return the first formula with the specified type in this bitset or
   * <code>null</code>.
   */
  public Formula getFirstAndRemove(FormulaType type) {
    for (int i = this.nextSetBit(0); i >= 0; i = this.nextSetBit(i + 1)) {
      Formula wff = formulaFactory.getByIndex(i);
      if (wff.getFormulaType() == type) {
        Formula found = formulaFactory.getByIndex(i);
        this.clear(found.getIndex());
        return found;
      }
    }
    return null;
  }

  public Formula getFirst(FormulaType type) {
    for (int i = this.nextSetBit(0); i >= 0; i = this.nextSetBit(i + 1)) {
      Formula wff = formulaFactory.getByIndex(i);
      if (wff.getFormulaType() == type)
        return formulaFactory.getByIndex(i);
    }
    return null;
  }

  public FormulaFactory getFactory() {
    return formulaFactory;
  }

  @Override
  public Iterator<Formula> iterator() {
    return new Iterator<Formula>() {

      int nextElement = BitSetOfFormulas.this.nextSetBit(0);

      @Override
      public boolean hasNext() {
        return nextElement >= 0;
      }

      @Override
      public Formula next() {
        Formula wff = formulaFactory.getByIndex(nextElement);
        nextElement = BitSetOfFormulas.this.nextSetBit(nextElement + 1);
        return wff;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException("remove not supported.");
      }

    };
  }

  public boolean remove(Formula wff) {
    boolean result = this.get(wff.getIndex());
    this.flip(wff.getIndex());
    return result;
  }

  public Formula[] toArray() {
    Formula[] result = new Formula[this.cardinality()];
    for (int i = this.nextSetBit(0), j = 0; i >= 0; i = this.nextSetBit(i + 1), j++)
      result[j] = formulaFactory.getByIndex(i);

    return result;
  }

  public boolean subseteq(BitSetOfFormulas other) {
    BitSetOfFormulas cloned = this.clone();
    cloned.and(other);
    return cloned.equals(this);
  }

  public boolean superseteq(BitSetOfFormulas other) {
    return other.subseteq(this);
  }

  @Override
  public String toString() {
    Collection<Formula> all = this.getAllFormulas();
    if (all != null) {
      Formula[] array = all.toArray(new Formula[all.size()]);
      String str = "";
      for (int i = 0; i < array.length; i++)
        str += array[i].toString() + (i < array.length - 1 ? ", " : "");
      return str;
    }
    return "";
  }

}
