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
package jtabwb.tracesupport;

import java.util.Iterator;
import java.util.LinkedList;

public class CollectionOfArrayOfNCTrees implements Iterable<CTreeNode[]> {

  LinkedList<CTreeNode[]> arrays;
  private int arrayDimension;

  public CollectionOfArrayOfNCTrees(int arrayDimension) {
    this.arrayDimension = arrayDimension;
    arrays = new LinkedList<CTreeNode[]>();
  }

  //  private void add(CTreeNode[] array) {
  //    if (array.length != arrayDimension)
  //      throw new ImplementationError();
  //    arrays.add(array);
  //  }

  public void generateCombinations(CTreeNode ct, CollectionOfArrayOfNCTrees prefixes) {
    if (prefixes == null)
      if (arrayDimension != 1)
        throw new ImplementationError();
      else
        arrays.add(new CTreeNode[] { ct });
    else if (prefixes.arrayDimension != arrayDimension - 1)
      throw new ImplementationError();
    else
      for (CTreeNode[] prefix : prefixes.arrays)
        arrays.add(extendWith(prefix, ct));
  }

  /*
   * Returns the array obtained by extending a with the element ct.
   */
  private CTreeNode[] extendWith(CTreeNode[] a, CTreeNode ct) {
    CTreeNode[] newa = new CTreeNode[a.length + 1];
    for (int i = 0; i < a.length; i++)
      newa[i] = a[i];
    newa[newa.length - 1] = ct;
    return newa;
  }

  public boolean isEmpty() {
    return arrays.size() == 0;
  }

  @Override
  public Iterator<CTreeNode[]> iterator() {
    return arrays.iterator();
  }

}
