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
 * An final instance of {@link _ClashDetectionRule} whose
 * {@link _ClashDetectionRule#status()} always returns
 * {@link ProofSearchResult#SUCCESS}. This rule force branch success. Hence,
 * after the application of this rule, the engine search for a branch point to
 * restore and proof-search succeed if no branch-point exists.
 * 
 * @author Mauro Ferrari
 * 
 */
public class ForceBranchSuccess implements _AbstractRule {

  public ForceBranchSuccess(String name, _AbstractGoal goal) {
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
   * 
   * @return the abstract node set on which the the proof-search failed.
   */
  final public _AbstractGoal premise() {
    return premise;
  }


}
