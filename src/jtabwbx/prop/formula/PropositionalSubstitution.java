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
 ******************************************************************************/
package jtabwbx.prop.formula;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A propositional substitution is a mapping between propositional variables and
 * formulas. 
 * 
 * @author Mauro Ferrari
 *
 */
public class PropositionalSubstitution extends HashMap<FormulaProposition, Formula> {

  private static final long serialVersionUID = 1L;

  @Override
  public String toString() {
    String str = "";
    Set<Map.Entry<FormulaProposition, Formula>> entries = this.entrySet();
    for (Map.Entry<FormulaProposition, Formula> e : entries) {
      str += "(" + e.getKey().toString() + "," + e.getValue().toString() + ") ";
    }
    return str;
  }

}
