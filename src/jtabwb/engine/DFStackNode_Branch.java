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
 * A node in the depth-first stack modelling a branch-point in proof-search.
 * 
 * @author Mauro Ferrari
 */
class DFStackNode_Branch extends DFStackNode {

  int conclusionsToTreat;
  int nextConclusionToTreat;

  /**
   * Builds an instance of the node for the specified rule application.
   * 
   * @param ruleApplication the rule application.
   */
  DFStackNode_Branch(_RegularRule ruleApplication, GoalNode premiseNode, boolean applyOnResume) {
    super(ruleApplication, premiseNode);
    this.nextConclusionToTreat = 0;
    this.conclusionsToTreat = ruleApplication.numberOfSubgoals();
    super.requireOnResumeInvocation =
        applyOnResume && (ruleApplication instanceof _OnRuleResumedListener);
  }

  /**
   * Returns the index of the last treated conclusion or -1 if no conclusion has
   * been treated.
   * 
   * @return the index of the last treated conclusion or -1.
   */
  int getIndexOfLastTreatedConclusion() {
    return nextConclusionToTreat - 1;
  }

  /**
   * Returns the number of conclusions to treat.
   * 
   * @return the number of conclusions to treat.
   */
  int conclusionsToTreat() {
    return conclusionsToTreat - nextConclusionToTreat;
  }

  /**
   * Returns <code>true</code> iff there is at least one more conclusion to
   * treat.
   * 
   * @return the number of conclusions to treat.
   */
  boolean hasMoreConclusionsToTreat() {
    return ((_RegularRule) appliedRule).hasNextSubgoal();
  }

  /**
   * Returns the engine node modelling the next conclusion to treat.
   * 
   * @return the engine node modelling the next conclusion to treat.
   */
  GoalNode nextConclusionToTreat(EnginePlain engine) {
    nextConclusionToTreat++;
    return new GoalNode(engine, ((_RegularRule) appliedRule).nextSubgoal());
  }

  void applyOnResume() {
    ((_OnRuleResumedListener) appliedRule).onResumed();
  }

  @Override
  boolean isCompleted() {
    return !((_RegularRule) appliedRule).hasNextSubgoal();
  }

  @Override
  public String toString() {
    return "AND branch point: regular rule " + appliedRule.name();
  }

}
