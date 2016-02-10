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
package jtabwbx.modal.formula;

import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import jtabwbx.modal.basic.ModalFormulaType;
import jtabwbx.modal.basic._ModalFormulaSet;

/**
 * Sets of modal formulas implemented over bitsets.
 * 
 * @author Mauro Ferrari
 */
public class BitSetOfModalFormulas extends BitSet implements _ModalFormulaSet,
    Iterable<ModalFormula> {

  ModalFormulaFactory formulaFactory;

  public BitSetOfModalFormulas(ModalFormulaFactory factory) {
    super(factory.numberOfGeneratedFormulas());
    this.formulaFactory = factory;
  }

  @Override
  public boolean add(ModalFormula wff) {
    int idx = wff.getIndex();
    if (this.get(idx))
      return true;
    this.set(idx);
    return false;
  }

  @Override
  public void addAll(_ModalFormulaSet set) {
    for (ModalFormula wff : set)
      this.add(wff);
  }

  public void addAll(BitSetOfModalFormulas set) {
    this.or(set);
  }

  public void and(BitSetOfModalFormulas set) {
    super.and(set);
  }

  @Override
  public BitSetOfModalFormulas clone() {
    BitSetOfModalFormulas newset = (BitSetOfModalFormulas) super.clone();
    newset.formulaFactory = formulaFactory;
    return newset;
  }

  @Override
  public boolean contains(ModalFormula wff) {
    return this.get(wff.getIndex());
  }

  public boolean containsFormulaOfType(ModalFormulaType type) {
    return getFirst(type) != null;
  }

  /**
   * Returns the collection containing all the formulas in this set or null if
   * this set is empty.
   * 
   * @return the collection storing all the formulas in this set.
   */
  public Collection<ModalFormula> getAllFormulas() {
    LinkedList<ModalFormula> list = new LinkedList<ModalFormula>();
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
   * @return the collection storing all the formulas in this set having the
   * specified type.
   */
  public Collection<ModalFormula> getAllFormulas(ModalFormulaType formulaType) {
    LinkedList<ModalFormula> list = new LinkedList<ModalFormula>();
    for (int i = this.nextSetBit(0); i >= 0; i = this.nextSetBit(i + 1)) {
      ModalFormula wff = formulaFactory.getByIndex(i);
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
   * @return the bitset storing all the formulas in this set having the
   * specified type.
   */
  public BitSetOfModalFormulas getBitsetOfAllFormulas(ModalFormulaType formulaType) {
    BitSetOfModalFormulas bset = new BitSetOfModalFormulas(this.formulaFactory);
    for (int i = this.nextSetBit(0); i >= 0; i = this.nextSetBit(i + 1)) {
      ModalFormula wff = formulaFactory.getByIndex(i);
      if (wff.getFormulaType() == formulaType)
        bset.add(wff);
    }
    return bset.isEmpty() ? null : bset;
  }

  public ModalFormula getFirst() {
    int firstIdx = this.nextSetBit(0);
    return firstIdx == -1 ? null : formulaFactory.getByIndex(firstIdx);
  }

  /**
   * Returns the first formula in this bitset and removes it from the bitset.
   * The method returns <code>null</code> if this bitset is empty.
   * 
   * @return the first formula in this bitset or <code>null</code>.
   */
  public ModalFormula getFirstAndRemove() {
    int firstIdx = this.nextSetBit(0);
    if (firstIdx == -1)
      return null;
    else {
      ModalFormula wff = formulaFactory.getByIndex(firstIdx);
      this.clear(wff.getIndex());
      return wff;
    }
  }

  /**
   * Returns the first formula with the specified type in this bitset and it
   * removes such a formula from the bitset. The method returns
   * <code>null</code> if this bitset does not contain any formula of the
   * specified type.
   * 
   * @param type the type of rhe formula to return.
   * @return the first formula in this bitset with the specified type or
   * <code>null</code>.
   */
  public ModalFormula getFirstAndRemove(ModalFormulaType type) {
    for (int i = this.nextSetBit(0); i >= 0; i = this.nextSetBit(i + 1)) {
      ModalFormula wff = formulaFactory.getByIndex(i);
      if (wff.getFormulaType() == type) {
        ModalFormula found = formulaFactory.getByIndex(i);
        this.clear(found.getIndex());
        return found;
      }
    }
    return null;
  }

  public ModalFormula getFirst(ModalFormulaType type) {
    for (int i = this.nextSetBit(0); i >= 0; i = this.nextSetBit(i + 1)) {
      ModalFormula wff = formulaFactory.getByIndex(i);
      if (wff.getFormulaType() == type)
        return formulaFactory.getByIndex(i);
    }
    return null;
  }

  public ModalFormulaFactory getFactory() {
    return formulaFactory;
  }

  @Override
  public Iterator<ModalFormula> iterator() {
    return new Iterator<ModalFormula>() {

      int nextElement = BitSetOfModalFormulas.this.nextSetBit(0);

      @Override
      public boolean hasNext() {
        return nextElement >= 0;
      }

      @Override
      public ModalFormula next() {
        ModalFormula wff = formulaFactory.getByIndex(nextElement);
        nextElement = BitSetOfModalFormulas.this.nextSetBit(nextElement + 1);
        return wff;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException("remove not supported.");
      }

    };
  }

  public boolean remove(ModalFormula wff) {
    boolean result = this.get(wff.getIndex());
    if (result)
      this.flip(wff.getIndex());
    return result;
  }

  public ModalFormula[] toArray() {
    ModalFormula[] result = new ModalFormula[this.cardinality()];
    for (int i = this.nextSetBit(0), j = 0; i >= 0; i = this.nextSetBit(i + 1), j++)
      result[j] = formulaFactory.getByIndex(i);

    return result;
  }

  public boolean subseteq(BitSetOfModalFormulas other) {
    BitSetOfModalFormulas cloned = this.clone();
    cloned.and(other);
    return cloned.equals(this);
  }

  public boolean superseteq(BitSetOfModalFormulas other) {
    return other.subseteq(this);
  }

  @Override
  public String toString() {
    Collection<ModalFormula> all = this.getAllFormulas();
    if (all != null) {
      ModalFormula[] array = all.toArray(new ModalFormula[all.size()]);
      String str = "";
      for (int i = 0; i < array.length; i++)
        str += array[i].toString() + (i < array.length - 1 ? ", " : "");
      return str;
    }
    return "";
  }

}
