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
 * <p>
 * A meta-backtrack rule specifies an enumeration of <em>backtrack
 * rules</em> to try in the proof-search. An instance of a meta backtrack-rule
 * is <em>successful</em> if at <b>least one of the backtrack rules is
 * successful</b>. The sequence of backtrack rules is provided as an enumeration
 * implemented by the methods {@link #nextRule()} and {@link #hasNextRule()}.
 * </p>
 * 
 * 
 * <h3>Engine assumptions</h3>

 * The engine assumes that an instance of the rule
 * <em>always contains at least a rule</em>. Namely, the following conditions
 * must be satisfied when the strategy returns an instance of this rule to the
 * engine:
 * <ul>
 * <li>{@link #totalNumberOfRules()} returns a value grater than zero;</li>
 * <li>{@link #nextRule()} can be safely invoked at least once returning a
 * non null value.</li>
 * </ul>
 * 
 * 
 * <h3>Rule application details</h3>
 * 
 * When the engine applies an instance of a meta-backtrack rule, it invokes
 * {@link #nextRule()} to get the new rule to apply, then it invokes
 * {@link #hasNextRule()} to check if the enumeration of the rules contains more
 * elements. If this is the case the engine stores the rule application in its
 * internal stack as an OR-branch point.
 * 
 * When a branch in the proof-search fails, the engine searches the stack for an
 * OR-branch-point. If the stack does not contain any backtrack point, the
 * engine stops and the proof-search is unsuccessful. Otherwise, the rule
 * application stored in the OR-branch point is restored. If the rule
 * application is an instance of {@link _MetaBacktrackRule}, then the engine
 * invokes {@link #nextRule()} to determine the next rule to apply. Then the
 * engine invokes {@link #hasNextRule()} to check if the enumeration of rules
 * has more elements. If this is the case the engine keeps the OR-branch point
 * in the stack otherwise it pop the OR-branch point from the stack.
 * 
 * 
 * @author Mauro Ferrari
 */
public interface _MetaBacktrackRule extends _AbstractRule {

  /**
   * Returns the goal of this meta-backtrack rule application.
   * 
   * @return the goal.
   */
  public _AbstractGoal goal();

  /**
   * Returns the total number of backtrack-rules provided by this rule
   * application.
   * 
   * @return the number of backtrack-rules.
   */
  public int totalNumberOfRules();

  /**
   * 
   * Returns the next rule.
   * 
   * @return the next rule.
   * @throws NoSuchBacktrackRuleException if no rule exists
   */
  public _AbstractRule nextRule() throws NoSuchBacktrackRuleException;

  /**
   * Returns true if this metabacktrack rule has a next rule.
   * 
   * @return true if there io a next rule.
   */
  public boolean hasNextRule();
}
