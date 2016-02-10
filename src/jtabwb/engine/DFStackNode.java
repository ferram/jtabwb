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

import java.util.LinkedList;

/**
 * The supertype for depth-first stack elements.
 * 
 * @author Mauro Ferrari
 */
abstract class DFStackNode {

  int node_height = 0; // the node height in the stack
  long generated_at_iteration = 0;

  DFStackNode df_previousNode; // the previous node in the stack
  DFStackNode df_previousBacktrack; // the previous backtrack node in the stack
  DFStackNode df_previousBranch; // the previous branch node in the stack
  DFStackNode df_nodeGeneratingPremise; /*
                                         * the node in the stack that generated
                                         * the premise to the rule application
                                         * of this node.
                                         */

  final _AbstractRule appliedRule; // the rule application generating this node

  GoalNode premiseEngineNode; /*
                               * needed by DFStackVerbose to provide information
                               * on restored branch and backtrack points.
                               */

  LinkedList<_OnRuleCompletedListener> ruleStatusListeners = null;

  boolean requireOnResumeInvocation; /*
                                      * Set true in subclasses if the applied
                                      * rule is a dynamic rule requiring
                                      * invocation of onResume when the
                                      * corresponding rule is restored from the
                                      * stack.
                                      */

  abstract void applyOnResume();

  abstract boolean isCompleted();

  public DFStackNode(_AbstractRule appliedRule, GoalNode premiseNode) {
    this.appliedRule = appliedRule;
    this.premiseEngineNode = premiseNode;
  }

  void addListener(_OnRuleCompletedListener listener) {
    if (ruleStatusListeners == null)
      ruleStatusListeners = new LinkedList<_OnRuleCompletedListener>();
    ruleStatusListeners.addFirst(listener); // LIFO queue
  }

  void onCompleted(ProofSearchResult status) {
    if (ruleStatusListeners != null) {
      for (_OnRuleCompletedListener toNotify : ruleStatusListeners)
        ((_OnRuleCompletedListener) toNotify).onCompleted(status);
      ruleStatusListeners = null;
    }
  }

  /**
   * The rule application stored in this node.
   * 
   * @return the rule application for this node.
   */
  public _AbstractRule getRuleApplication() {
    return appliedRule;
  }

}
