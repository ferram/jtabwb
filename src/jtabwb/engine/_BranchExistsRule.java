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
 * 
 * This interface describes a branch-exists rule, namely a backtrack rule that
 * succeeds if at least one of its subgoals succeeds. The application of this
 * rule generates a backtrack point in the proof-search. An instance of a
 * branch-exists rule is <em>successful</em> if the proof-search for <b>at least
 * one of its subgoals</b> is successful. The sequence of sub-goals is provided
 * as an enumeration implemented by the methods
 * {@link #nextBranchExistsSubgoal()} and {@link #hasNextBranchExistsSubgoal()}.
 * 
 * <h3>Engine assumptions</h3>
 * 
 * The engine assumes that an instance of the rule
 * <em>always contains at least a subgoal</em>. Namely, the following conditions
 * must be satisfied when the strategy returns an instance of this rule to the
 * engine:
 * <ul>
 * <li>{@link #numberOfBranchExistsSubgoals()} returns a value grater than zero;
 * </li>
 * <li>{@link #nextBranchExistsSubgoal()} can be safely invoked at least once
 * returning a non null value.</li>
 * </ul>
 * 
 * 
 * <h3>Rule application details</h3>
 * 
 * <p>
 * When the engine applies an instance of a branch-exists rule, it invokes
 * {@link #nextBranchExistsSubgoal()} to get the new goal, then it invokes
 * {@link #hasNextBranchExistsSubgoal()} to check if the rule has more subgoals.
 * If this is the case the engine stores the rule application in its internal
 * stack as an OR-branch point.
 * </p>
 * 
 * <p>
 * When a branch in the proof-search fails, the engine searches the stack for an
 * OR-branch-point. If the stack does not contain any backtrack point, the
 * engine stops and the proof-search is unsuccessful. Otherwise, the rule
 * application stored in the OR-branch point is restored. If the rule
 * application is an instance of {@link _BranchExistsRule}, then the engine
 * invokes {@link #nextBranchExistsSubgoal()} to determine the new goal. Then
 * the engine invokes {@link #hasNextBranchExistsSubgoal()} to check if the rule
 * application has more subgoals. If this is the case the engine keeps the
 * OR-branch point in the stack otherwise it pop the OR-branch point from the
 * stack.
 * </p>
 * 
 * 
 * @author Mauro Ferrari
 */
public interface _BranchExistsRule extends _AbstractRule {

  /**
   * Returns the number of subgoals of this rule application.
   * 
   * @return the number of subgoals.
   */
  public int numberOfBranchExistsSubgoals();

  /**
   * The main formula of this rule or <code>null</code> if this rule does not
   * have a main formula.
   * 
   * @return the main formula of the rule or <code>null</code>.
   */
  public _AbstractFormula mainFormula();

  /**
   * 
   * Returns the next subgoal of this rule.
   * 
   * @return the next subgoal of the rule.
   */
  public _AbstractGoal nextBranchExistsSubgoal();

  /**
   * Returns true if this rule has a next subgoal.
   * 
   * @return true if this rule has a next subgoal.
   */
  public boolean hasNextBranchExistsSubgoal();

}
