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
 * Specifies that the rule implementing this interface listens for the
 * on-rule-completed event. A rule is completed when the proof-search procedure
 * has determined the proof-search status of its goal. When this happens the
 * {@link #onCompleted(ProofSearchResult)} method is invoked with such a
 * proof-search status as argument.
 * 
 * @author Mauro Ferrari
 * 
 */
public interface _OnRuleCompletedListener extends _AbstractRule {

  /**
   * Invoked by the engine when the proof-search starting with this rule
   * application terminates.
   * 
   * @param status the status of the proof search.
   */
  public void onCompleted(ProofSearchResult status);
}
