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
package jtabwbx.prop.basic;

import java.io.Serializable;

/**
 * The basic propositional connectives.
 * 
 * @author Mauro Ferrari
 */
public enum PropositionalConnective implements Serializable {
  //TRUE("true", 0, true), FALSE("false", 0, true),
  /**
   * Negation.
   */
  NOT("~", 1, true),
  /**
   * Conjunction.
   */
  AND("&", 2, true),
  /**
   * Disjunction.
   */
  OR("|", 2, true),
  /**
   * Implication.
   */
  IMPLIES("->", 2, false),
  /**
   * IFF (Biconditional).
   */
  EQ("<=>", 2, false), ;

  private String name;
  private int arity;
  private boolean isCommutative;

  private PropositionalConnective(String name, int arity, boolean isCommutative) {
    this.name = name;
    this.arity = arity;
    this.isCommutative = isCommutative;
  }

  public String getName() {
    return name;
  }

  public int arity() {
    return arity;
  }

  public boolean isCommutative() {
    return isCommutative;
  }

  @Override
  public String toString() {
    return name;
  }

}
