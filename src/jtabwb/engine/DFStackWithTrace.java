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
 * An extension of the depth-first stack generating a trace of the proof-search.
 * 
 * @author Mauro Ferrari
 */
class DFStackWithTrace extends DFStack {

  private int nodeCounter = 0;

  boolean isStackModified;
  TraceNode currentTraceNode;
  TraceNode headOfTrace;
  TraceStack traceStack;

  public DFStackWithTrace(EngineTrace engine, boolean verboseMode) {
    super(engine, verboseMode, false);
    traceStack = new TraceStack();
    isStackModified = false;
    currentTraceNode = null;
    headOfTrace = null;
  }

  @Override
  DFStack newInstance() {
    return new DFStackWithTrace((EngineTrace) engine, verboseMode);
  }

  @Override
  DFStackNode_Branch addBranchNode(_RegularRule rule, GoalNode currentGoal) {
    isStackModified = true;
    return super.addBranchNode(rule, currentGoal);
  }

  @Override
  DFStackNode_MetaBacktrack addMetaBacktrackNode(_MetaBacktrackRule rule, GoalNode currentGoal) {
    isStackModified = true;
    return super.addMetaBacktrackNode(rule, currentGoal);
  }

  @Override
  DFStackNode_BranchExists addBranchExistsNode(_BranchExistsRule rule, GoalNode currentGoal) {
    isStackModified = true;
    return super.addBranchExistsNode(rule, currentGoal);
  }

  void traceRule(EnginePlain engine, _AbstractGoal premiseOfRuleApplication, _AbstractRule rule) {
    TraceNode newTraceNode =
        new TraceNode(premiseOfRuleApplication, traceStack.nodeGeneratingPremise, rule,
            currentTraceNode, nodeCounter++);

    // update curent trace node children
    if (currentTraceNode != null)
      currentTraceNode.addChild(newTraceNode);
    else
      headOfTrace = newTraceNode;
    currentTraceNode = newTraceNode;

    // add to the trace-stack
    if (isStackModified)
      traceStack.pushWithDFStackModified(newTraceNode, df_head);
    else
      traceStack.pushWithDFStackUnchanged(newTraceNode);

    switch (RuleType.getType(rule)) {
    case CLASH_DETECTION_RULE:
    case FORCE_BRANCH_FAILURE:
    case FORCE_BRANCH_SUCCESS:
      newTraceNode.status = engine.LAST_ITERATION_INFO.current_node_set_status;
      break;
    case BRANCH_EXISTS:
    case REGULAR:
    case META_BACKTRACK_RULE:
      break;
    default:
      throw new ImplementationError(ImplementationError.CASE_NOT_IMPLEMENTED);
    }
    isStackModified = false;
  }

  @Override
  _AbstractRule restorePreviousBranchPoint() {
    currentTraceNode = traceStack.restoreBranchPoint(df_lastBranch);
    _AbstractRule result = super.restorePreviousBranchPoint();
    return result;
  }

  @Override
  _AbstractRule restorePreviousBacktrackPoint() {
    currentTraceNode = traceStack.restoreBacktrackPoint(df_lastBacktrack);
    _AbstractRule result = super.restorePreviousBacktrackPoint();
    return result;
  }

  public Trace getTrace(_AbstractGoal initialNodeSet, _Prover prover, ProofSearchResult result) {
    TraceNode headOfTrace = traceStack.closeTrace(result);
    return new Trace(initialNodeSet, prover, headOfTrace, result);
  }

}
