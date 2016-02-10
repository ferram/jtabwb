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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;


/**
 * Set of signed formulas implemented over HashSet.
 * @author Mauro Ferrari
 */
public class FormulaSetOnHashSet implements Iterable<Formula>, _FormulaSet {

	private HashSet<Formula> set;

	/**
	 * Builds an empty set of signed formulas.
	 */
	public FormulaSetOnHashSet() {
		super();
		set = new HashSet<Formula>();
	}

	protected FormulaSetOnHashSet(FormulaSetOnHashSet hset) {
		super();
		this.set = hset.set;
	}

	@Override
	public boolean add(Formula swff) {
		if (set.contains(swff))
			return false;
		set.add(swff);
		return true;
	}

	@Override
	public boolean remove(Formula wff) {
		return set.remove(wff);
	}

	@Override
	public Formula getFirst() {
		Iterator<Formula> it = set.iterator();
		return it.next();
	}

	@Override
	public Collection<Formula> getAllFormulas() {
		return new LinkedList<Formula>(set);
	}

	@Override
	public int cardinality() {
		return set.size();
	}
	
	
	@Override
  public boolean isEmpty() {
    return set.isEmpty();
  }

  @Override
	public boolean contains(Formula swff) {
		return set.contains(swff);
	}

	@Override
	public void addAll(_FormulaSet other) {
		for (Formula s : other)
			this.add(s);
	}

	@Override
	public Iterator<Formula> iterator() {
		return set.iterator();
	}

	@SuppressWarnings("unchecked")
	@Override
	public FormulaSetOnHashSet clone() {
		FormulaSetOnHashSet c = new FormulaSetOnHashSet();
		c.set = (HashSet<Formula>) this.set.clone();
		return c;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return this.set.equals(((FormulaSetOnHashSet) obj).set);
	}

	@Override
	public Formula[] toArray() {
		return (Formula[]) set.toArray(new Formula[set.size()]);
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
