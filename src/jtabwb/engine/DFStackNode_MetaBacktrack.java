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
 * A node in the depth-first stack modelling a backtrack-point corresponding to
 * a {@link RuleType#META_BACKTRACK_RULE} rule application.
 * 
 * @author Mauro Ferrari
 */
class DFStackNode_MetaBacktrack extends DFStackNode {

  int totalNumberOfRulesToTry; // total number of rules treated by the rule application
  int indexOfNextRule; // 

  DFStackNode_MetaBacktrack(_MetaBacktrackRule appliedRule, GoalNode premiseNode,
      boolean applyOnResume) {
    super(appliedRule, premiseNode);
    this.totalNumberOfRulesToTry = appliedRule.totalNumberOfRules();
    this.indexOfNextRule = 0;
    this.requireOnResumeInvocation =
        applyOnResume && (appliedRule instanceof _OnRuleResumedListener);
  }

  /**
   * Returns the index of the last treated rule or -1 if no rule has been
   * treated.
   * 
   * @return the index of the last treated rule or -1.
   */
  int getIndexOfLastTreatedRule() {
    return indexOfNextRule - 1;
  }

  /**
   * The index of the last treated rule. The index is zero-based, hence the
   * index if the first rule to try is zero while the index of the last rule is
   * totalNumberOfRulesToTry - 1.
   * 
   * @return the index of the next rule to try.
   */
  int indexOfLastTreatedRule() {
    return indexOfNextRule - 1;
  }

  /**
   * The index of the next rule to try. The index is zero-based, hence the index
   * if the first rule to try is zero while the index of the last rule is
   * totalNumberOfRulesToTry - 1.
   * 
   * @return the index of the next rule to try.
   */
  int indexOfNextRule() {
    return indexOfNextRule;
  }

  /**
   * The number of remaining rules to try.
   */
  int numberOfRulesToTry() {
    return totalNumberOfRulesToTry - indexOfNextRule;
  }

  /**
   * The next rule to try.
   * 
   * @return the next rule to try.
   */
  public _AbstractRule nextRuleToTry() {
    indexOfNextRule++;
    return ((_MetaBacktrackRule) appliedRule).nextRule();
  }

  public _AbstractGoal getGoal() {
    return ((_MetaBacktrackRule) appliedRule).goal();
  }

  @Override
  void applyOnResume() {
    ((_OnRuleResumedListener) appliedRule).onResumed();
  }

  @Override
  boolean isCompleted() {
    return !((_MetaBacktrackRule) appliedRule).hasNextRule();
  }
  
  
  @Override
  public String toString() {
    return "OR branch point: meta-backtrack rule " + appliedRule.name();
  }

}
