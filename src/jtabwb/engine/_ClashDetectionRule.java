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
 ******************************************************************************/
package jtabwb.engine;

/**
 * An object realizing a clash-detection rule.
 * 
 * @author Mauro Ferrari
 */
public interface _ClashDetectionRule extends _AbstractRule {

  /**
   * Returns the goal on which this this clash-detection rule act.
   * 
   * @return the goal of this rule application.
   */
  public _AbstractGoal goal();

  /**
   * Returns {@link ProofSearchResult#SUCCESS} if the goal of this rule
   * contains a clash and {@link ProofSearchResult#FAILURE} otherwise.
   * 
   * @return the status of this clash-detection rule instance.
   */
  public ProofSearchResult status();
}
