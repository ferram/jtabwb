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
 * A regular rule is a rule generating one or more sub-goals to solve. It can
 * generate a branch point in the proof-search. An instance of the regular rule
 * is <em>successful</em> if the proof-search for <b>all</b> its sub-goals is
 * successful. The sequence of sub-goals is provided as an enumeration
 * implemented by the methods {@link #nextSubgoal()} and
 * {@link #hasNextSubgoal()}.
 * 
 * 
 * <h3>Engine assumptions</h3>

 * The engine assumes that an instance of the rule
 * <em>always contains at least a subgoal</em>. Namely, the following conditions
 * must be satisfied when the strategy returns an instance of this rule to the
 * engine:
 * <ul>
 * <li>{@link #numberOfSubgoals()} returns a value grater than zero;</li>
 * <li>{@link #nextSubgoal()} can be safely invoked at least once returning a
 * non null value.</li>
 * </ul>
 * 
 * <h3>Rule application details</h3>
 * 
 * When the engine applies an instance of a regular rule, it invokes the
 * {@link #nextSubgoal()} method to get the new goal, then it invokes then it
 * invokes {@link #hasNextSubgoal()} to check if the rule has more subgoals. If
 * this is the case the engine stores the rule application in its internal stack
 * as an AND-branch point.
 * 
 * When a branch in the proof-search succeeds, the engine searches for a
 * AND-branch point into the stack. If the stack does not contain any branch
 * point, the engine stops and the proof-search is successful. Otherwise, the
 * rule application stored in the AND-branch point is restored. If the rule
 * application is an instance of {@link _RegularRule}, then the engine invokes
 * {@link #nextSubgoal()} to determine the new goal. Then the engine invokes
 * {@link #hasNextSubgoal()} to check if the rule application has more subgoals.
 * If this is the case the engine keeps the AND-branch point in the stack
 * otherwise it pop the AND-branch point from the stack.
 * 
 * 
 * @author Mauro Ferrari
 */
public interface _RegularRule extends _AbstractRule {

  /**
   * Returns the number of subgoals of this rule.
   * 
   * @return the number of subgoals.
   */
  public int numberOfSubgoals();

  /**
   * Returns the main formula of this rule or <code>null</code> if this rule
   * does not have a main formula.
   * 
   * @return the main formula of the rule.
   */
  public _AbstractFormula mainFormula();

  /**
   * 
   * Returns the next subgoal of this rule.
   * 
   * @return the next subgoal of the rule.
   */
  public _AbstractGoal nextSubgoal();

  /**
   * Returns true if this rule has a next subgoal.
   * 
   * @return true if this rule has a next subgoal.
   */
  public boolean hasNextSubgoal();

}
