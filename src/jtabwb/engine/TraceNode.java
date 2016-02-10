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

import jtabwb.engine.TraceStack.GeneratingPremise;

/**
 * A node in the proof-trace, it describes a rule applied in the proof-search.
 * 
 * @author Mauro Ferrari
 */
public class TraceNode {

  private int nodeIndex; // the index of the node  

  private _AbstractGoal premise; // the premise of the rule applied in this step
  _AbstractRule appliedRule; // the rule applied in this step
  private RuleType ruleType; // the type of the rule applied in this step
  ProofSearchResult status; // the status of the c-tree starting with this rule application
  LinkedList<TraceNode> children; // the trace-node describing the children of this rule application
  TraceNode parent; // the parent of this trace-node or null if this is the root of trace-nodes
  private TraceNode nodeDeterminingPremise; /*
                                             * the node that generated the
                                             * premise of the rule applied in
                                             * this step
                                             */
  private int nodeDeterminingPremiseTreatedConclusion; /*
                                                        * the conclusion of the
                                                        * parent trace-node
                                                        * treated by this rule
                                                        * application, i.e., the
                                                        * trace node generating
                                                        * the premise of the
                                                        * rule application
                                                        * described by this
                                                        * trace-node
                                                        */
  private int maxNumberOfSuccessors; /*
                                      * the number of possible successors of
                                      * this trace node. In details: if the
                                      * ruleType is: CLASH_DETECTION_RULE: the
                                      * number of possible successors is 0.
                                      * REGULAR, this is the number of branches
                                      * of the rule application, but if the
                                      * proof-search for one of the subgoals
                                      * fails, the other conclusion are not
                                      * treated. Hence . numberOfSuccessors can
                                      * be greater than the number of children
                                      * of this node. If the type of the applied
                                      * rule is BRANCH_EXISTS or
                                      * META_BACKTRACK_RULE and the proof-search
                                      * for one of the subgoals succeeds, the
                                      * other cases are not treated, hence
                                      * numberOfSuccessors can be greater then
                                      * the number of children.
                                      */

  /**
   * Constructs an instance of a trace node.
   * 
   * @param premise the premise of the rule application described by this
   * trace-node.
   * @param nodeDeterminingPremiseTreatedConclusion the premise is the TODO
   * @param appliedRule the rule applied in the rule application described by
   * this trace-node.
   * @param parent the parent of this trace-node.
   */
  TraceNode(_AbstractGoal premise, GeneratingPremise generatingPremise,
      _AbstractRule appliedRule, TraceNode parent, int nodeNumber) {
    super();
    this.premise = premise;
    this.nodeDeterminingPremise = generatingPremise == null ? null : generatingPremise.node;
    this.nodeDeterminingPremiseTreatedConclusion =
        generatingPremise == null ? -1 : generatingPremise.idxConclusion;
    this.appliedRule = appliedRule;
    this.ruleType = RuleType.getType(appliedRule);
    this.status = null;
    this.parent = parent;
    this.nodeIndex = nodeNumber;

    switch (this.ruleType) {
    case CLASH_DETECTION_RULE:
    case FORCE_BRANCH_FAILURE:
    case FORCE_BRANCH_SUCCESS:
      maxNumberOfSuccessors = 0;
      break;
    case REGULAR:
      maxNumberOfSuccessors = ((_RegularRule) appliedRule).numberOfSubgoals();
      break;
    case BRANCH_EXISTS:
      maxNumberOfSuccessors = ((_BranchExistsRule) appliedRule).numberOfBranchExistsSubgoals();
      break;
    case META_BACKTRACK_RULE:
      maxNumberOfSuccessors = ((_MetaBacktrackRule) appliedRule).totalNumberOfRules();
      break;
    default:
      throw new ImplementationError();
    }
  }

  public _AbstractGoal getPremise() {
    return premise;
  }

  public int getNodeDeterminingPremiseTreatedConclusion() {
    return this.nodeDeterminingPremiseTreatedConclusion;
  }

  public TraceNode getNodeDeterminingPremise() {
    return this.nodeDeterminingPremise;
  }

  public _AbstractRule getAppliedRule() {
    return this.appliedRule;
  }

  public int getMaxNumberOfSuccessors() {
    return this.maxNumberOfSuccessors;
  }

  public TraceNode getParent() {
    return this.parent;
  }

  void addChild(TraceNode node) {
    if (children == null)
      children = new LinkedList<TraceNode>();
    children.add(node);
  }

  void replaceChild(TraceNode oldNode, TraceNode newNode) {
    for (int i = 0; i < children.size(); i++) {
      if (children.get(i).equals(oldNode)) {
        children.remove(i);
        children.add(i, newNode);
      }
    }
  }

  public RuleType getRuleType() {
    return this.ruleType;
  }

  public void setRuleType(RuleType ruleType) {
    this.ruleType = ruleType;
  }

  public ProofSearchResult getStatus() {
    return this.status;
  }

  void setStatus(ProofSearchResult status) {
    if (this.status != null)
      return;
    else {
      this.status = status;
      if (children != null)
        for (TraceNode tn : children)
          tn.setStatus(status);
    }
  }

  public LinkedList<TraceNode> getChildren() {
    return this.children;
  }

  void print() {
    System.out.println(this.toString());
    if (children != null)
      for (TraceNode n : children)
        n.print();
  }

  @Override
  public String toString() {
    String successors = "";
    if (children == null)
      successors = "";
    else
      for (TraceNode tn : children)
        successors += " #" + tn.nodeIndex;

    _AbstractFormula wff;
    switch (ruleType) {
    case BRANCH_EXISTS:
      wff = ((_BranchExistsRule) appliedRule).mainFormula();
      break;
    case REGULAR:
      wff = ((_RegularRule) appliedRule).mainFormula();
      break;
    default:
      wff = null;
    }

    String strNumber = "#" + nodeIndex;
    String strWff = "";
    if (wff != null) {
      String space = String.format("%-" + (strNumber.length() + 3) + "s", " ");
      strWff = String.format("\n%sMAIN FORMULA: %s", space, wff.format());
    }
    String treatedConclusion = toStringTreatedConclusion(this);
    if (treatedConclusion != null) {
      String space = String.format("%-" + (strNumber.length() + 3) + "s", " ");
      treatedConclusion = String.format("\n%s%s", space, treatedConclusion);
    } else
      treatedConclusion = "";
    return strNumber + " - STATUS: " + (status == null ? "NULL" : status.name()) + ", "
        + ruleType.name() + "[" + appliedRule.name() + "], SUCCESSORS[" + maxNumberOfSuccessors
        + "]:" + successors + strWff + treatedConclusion;
  }

  private String toStringTreatedConclusion(TraceNode tn) {
    if (tn.nodeDeterminingPremise == null)
      return null;
    //		if (tn.parent.ruleType == RuleType.CLASH_DETECTION_RULE)
    //			return toStringTreatedConclusion(tn.parent);

    String counter =
        "[" + (nodeDeterminingPremiseTreatedConclusion + 1) + "/"
            + tn.nodeDeterminingPremise.maxNumberOfSuccessors + "] of " + "#"
            + nodeDeterminingPremise.nodeIndex;
    switch (tn.nodeDeterminingPremise.ruleType) {
    case CLASH_DETECTION_RULE:
      return toStringTreatedConclusion(tn.parent);
    case REGULAR:
      return "Treats conclusion " + counter;
    case BRANCH_EXISTS:
      return "Treats branch-exists conclusion " + counter;
    case META_BACKTRACK_RULE:
      return "Treats backtrack rule " + counter;
    default:
      throw new ImplementationError();
    }

  }

  @Override
  public int hashCode() {
    return nodeIndex;
  }
}
