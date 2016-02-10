/*******************************************************************************
 * Copyright (C) 2013, 2014 Mauro Ferrari
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


/**
 * Set of signed formulas implemented over LinkedList.
 * 
 * @author Mauro Ferrari
 */
public class FormulaSetOnList extends LinkedList<Formula> implements _FormulaSet {

  /**
   * Builds an empty set of signed formulas.
   */
  public FormulaSetOnList() {
    super();
  }

  @Override
  public Collection<Formula> getAllFormulas() {
    return this;
  }

  @Override
  public void addAll(_FormulaSet other) {
    for (Formula s : other)
      this.add(s);
  }

  @Override
  public boolean remove(Formula wff) {
    return super.remove(wff);
  }

  @Override
  public Formula getFirst() {
    return super.getFirst();
  }

  @Override
  public int cardinality() {
    return this.size();
  }

  @Override
  public boolean contains(Formula swff) {
    return super.contains(swff);
  }

  public FormulaSetOnList clone() {
    return (FormulaSetOnList) super.clone();
  }

  @Override
  public Formula[] toArray() {
    return (Formula[]) this.toArray(new Formula[this.cardinality()]);
  }

  @Override
  public String toString() {
    Formula[] swffs = this.toArray();
    String str = "";
    for (int i = 0; i < swffs.length; i++)
      str += swffs[i].toString() + (i < swffs.length - 1 ? ", " : "");
    return str + "\n";
  }

}
