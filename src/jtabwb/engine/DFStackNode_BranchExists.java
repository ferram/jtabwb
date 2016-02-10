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
 * A node in the depth-first stack modelling a backtrack point corresponding to
 * a {@link RuleType#BRANCH_EXISTS} rule application.
 * 
 * @author Mauro Ferrari
 */
class DFStackNode_BranchExists extends DFStackNode {

  int branchesToTreat;
  int nextToTreat;

  /**
   * Builds an instance of the node for the specified rule application.
   * 
   * @param ruleApplication the rule application.
   */
  DFStackNode_BranchExists(_BranchExistsRule ruleApplication, GoalNode premiseNode,
      boolean applyOnResume) {
    super(ruleApplication, premiseNode);
    this.nextToTreat = 0;
    this.branchesToTreat = ruleApplication.numberOfBranchExistsSubgoals();
    this.requireOnResumeInvocation = applyOnResume && (ruleApplication instanceof _OnRuleResumedListener);
  }

  /**
   * Returns the index of the last treated branch or -1 if no branch has been
   * treated.
   * 
   * @return the index of the last treated branch or -1.
   */
  int getIndexOfLastTreatedConclusion() {
    return nextToTreat - 1;
  }

  /**
   * Returns the number of the branches to treat.
   * 
   * @return the number of branches to treat.
   */
  int branchesToTreat() {
    return branchesToTreat - nextToTreat;
  }

  /**
   * Returns <code>true</code> iff there exists at least a branch that has not
   * been already treated.
   * 
   * @return <code>true</code> iff there exists at least a branch to treat.
   */
  boolean hasMoreBranchesTOTreat() {
    return ((_BranchExistsRule) appliedRule).hasNextBranchExistsSubgoal();
  }

  /**
   * Returns the next branch to treat.
   * 
   * @return the next branch to treat.
   * @throws NoSuchSubgoalException if no more branches exist.
   */
  GoalNode nextBranchToTreat(EnginePlain engine) {
    nextToTreat++;
    return new GoalNode(engine, ((_BranchExistsRule) appliedRule).nextBranchExistsSubgoal());
  }

  @Override
  void applyOnResume() {
    ((_OnRuleResumedListener) appliedRule).onResumed();

  }

  @Override
  boolean isCompleted() {
    return !((_BranchExistsRule) appliedRule).hasNextBranchExistsSubgoal();
  }

  @Override
  public String toString() {
    return "OR branch point: branch exists rule " + appliedRule.name();
  }
}
