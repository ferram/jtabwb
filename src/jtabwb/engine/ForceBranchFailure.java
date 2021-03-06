/*******************************************************************************
 * Copyright (C) 2013, 2015 Mauro Ferrari
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
package jtabwb.engine;

/**
 * This rule force branch failure. Hence, after the application of this rule,
 * the engine search for a backtrack point to restore and proof-search fail if
 * no backtrack point exists.
 * 
 * @author Mauro Ferrari
 * 
 */
public class ForceBranchFailure implements _AbstractRule {

  public ForceBranchFailure(String name, _AbstractGoal goal) {
    super();
    this.name = name;
    this.premise = goal;
  }

  private String name;
  private _AbstractGoal premise;

  @Override
  final public String name() {
    return name;
  }

  /**
   * Returns the abstract node set on which the the proof-search failed.
   * @return  the abstract node set on which the the proof-search failed.
   */
  final public _AbstractGoal goal() {
    return premise;
  }

}
