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

import jtabwb.engine.IterationInfo.Move;

/*
 * This is the implementation of the engine, the other Engine classes are
 * subclasses of this one that provide some extra functionality by overriding
 * some method of this class.
 */
class EnginePlain {

  /* Rule used to force backtrack */
  private final _AbstractRule FORCE_BACKTRACK = new _AbstractRule() {

    @Override
    public String name() {
      return "FORCE_BACKTRACK";
    }
  };

  /* Rule used to force backtrack */
  private final _AbstractRule FORCE_BRANCH_POINT_SEARCH = new _AbstractRule() {

    @Override
    public String name() {
      return "FORCE_BRANCH_POINT_SEARCH";
    }
  };
  _Prover prover; // the prover
  _Strategy strategy; // the instance of the strategy used by this engine
  _AbstractGoal goal; // the goal of proof search
  DFStack stack; // the stack used to manage the depth first strategy

  ProofSearchResult result = null; // the result of the proof search
  GoalNode currentGoal; // the current node set

  final IterationInfo LAST_ITERATION_INFO;
  boolean verboseMode;

  /**
   * Constructs an instance of the engine that perform a proof-search of the
   * <code>goal</code> driven by the <code>prover</code> and using the
   * <stack>stack</code> as depth-first stack. This constructor is used by the
   * extension of this class providing engines which depth-first stack manages
   * some extra information.
   * 
   * @param prover the prover driving proof search.
   * @param goal the goal of the proof-search.
   * @param stack the stack managing the depth-first search.
   */
  EnginePlain(_Prover prover, _AbstractGoal goal, DFStack stack, boolean verboseMode) {
    this.prover = prover;
    this.goal = goal;
    this.stack = stack;
    this.strategy = prover.getStrategy();
    this.result = null;
    this.currentGoal = null;
    this.verboseMode = verboseMode;
    // reset the stack
    // reset data about last iteration
    LAST_ITERATION_INFO = new IterationInfo(this);
  }

  /**
   * Constructs an instance of the engine that perform a proof-search of the
   * <code>goal</code> driven by the <code>prover</code>.
   * 
   * @param prover the prover driving proof search.
   * @param goal the goal of the proof-search.
   */
  EnginePlain(_Prover prover, _AbstractGoal goal, boolean verboseMode) {
    this(prover, goal, null, verboseMode);
    this.stack = new DFStack(this, verboseMode, true);
  }

  /*
   * Resets the status of the engine.
   */
  private void reset() {
    this.result = null;
    this.currentGoal = null;
    // reset the stack
    this.stack = stack.newInstance();
    // reset data about last iteration
    LAST_ITERATION_INFO.reset();
  }

  /**
   * Applies the specified rule. The method returns the next rule to apply if
   * <code>ruleType</code> is {@link RuleType#META_BACKTRACK_RULE} and
   * <code>null</code> otherwise.
   * 
   * @param ruleToApply the step to apply.
   * @param ruleType the type of <code>ruleToApply</code>.
   */
  _AbstractRule applyRule(_AbstractRule ruleToApply, RuleType ruleType) {

    // update iteration info
    if (LAST_ITERATION_INFO.move != Move.BACKTRACK_POINT_SEARCH
        && LAST_ITERATION_INFO.move != Move.AND_BRANCH_POINT_SEARCH)
      stack.restored_DFStackNode = null;
    LAST_ITERATION_INFO.applied_rule = null;
    LAST_ITERATION_INFO.current_node_set_status = null;
    LAST_ITERATION_INFO.backtrack_point_added = false;
    LAST_ITERATION_INFO.branch_point_added = false;
    LAST_ITERATION_INFO.number_of_conclusions = -1;
    LAST_ITERATION_INFO.treated_conclusion = -1;
    LAST_ITERATION_INFO.number_of_iterations++;
    LAST_ITERATION_INFO.number_of_restored_backtrack_points =
        stack.number_of_restored_backtrack_points;
    LAST_ITERATION_INFO.number_of_restored_branch_points = stack.number_of_restored_branch_points;
    LAST_ITERATION_INFO.max_stack_size = stack.max_stack_size;

    // If ruleToApply requires status notification, we add ruleToApply to the notification 
    // list for node TOP at the top of the stack.  
    // The status of ruleToApply will be notified when TOP or a node before TOP will be  
    // restored. Important: if R generates a new node in the stack, it will be removed from
    // the stack before its status is determined (a node is removed from the stack when the last 
    // branch generated by the rule is selected for proof-search).
    if ((ruleToApply instanceof _OnRuleCompletedListener))
      stack.addOnCompletedRuleListener((_OnRuleCompletedListener) ruleToApply);

    _AbstractRule result;
    switch (ruleType) {
    case REGULAR: {
      // The first conclusion of the rule is selected as new goal. If the rule has 
      // more than one conclusion a new branch point referencing this rule instance
      // is added to the stack. 
      _RegularRule application = ((_RegularRule) ruleToApply);
      if (application.numberOfSubgoals() > 1) {
        // push a branch-node into the stack
        DFStackNode_Branch branchNode = stack.addBranchNode(application, currentGoal);
        // update iteration info
        LAST_ITERATION_INFO.branch_point_added = true;
        // select new goal
        currentGoal = branchNode.nextConclusionToTreat(this);
      } else
        currentGoal = new GoalNode(this, application.nextSubgoal());
      // update iteration info
      LAST_ITERATION_INFO.applied_rule = ruleToApply;
      LAST_ITERATION_INFO.number_of_conclusions = application.numberOfSubgoals();
      LAST_ITERATION_INFO.treated_conclusion = 0;
      LAST_ITERATION_INFO.move = IterationInfo.Move.REGULAR_RULE_APPLICATION;
      result = null;
    }
      break;
    case BRANCH_EXISTS: {
      // The first conclusion of the rule is selected as new goal. If the rule has 
      // more than one conclusion a new backtrack point (referencing this rule instance)
      // is added to the stack. 
      _BranchExistsRule application = (_BranchExistsRule) ruleToApply;
      // if it is a real branch exists rule  
      if (application.numberOfBranchExistsSubgoals() > 1) {
        // we push a branch node into the stack
        DFStackNode_BranchExists dfb = stack.addBranchExistsNode(application, currentGoal);
        // update iteration info
        LAST_ITERATION_INFO.backtrack_point_added = true;
        currentGoal = dfb.nextBranchToTreat(this);
        // select new goal
      } else
        currentGoal = new GoalNode(this, application.nextBranchExistsSubgoal());
      // update iteration info
      LAST_ITERATION_INFO.applied_rule = ruleToApply;
      LAST_ITERATION_INFO.number_of_conclusions = application.numberOfBranchExistsSubgoals();
      LAST_ITERATION_INFO.treated_conclusion = 0;
      LAST_ITERATION_INFO.move = IterationInfo.Move.BRANCH_EXISTS_RULE_APPLICATION;
      result = null;
    }
      break;
    case META_BACKTRACK_RULE: {
      // The first rule returned by this meta-rule is selected as next rule to apply. 
      // If the meta-rule contains more than one rule  a new backtrack point 
      // (referencing this meta-rule instance) is added to the stack. 
      _MetaBacktrackRule application = (_MetaBacktrackRule) ruleToApply;
      // it it is a real meta backtrack rule
      if (application.totalNumberOfRules() > 1) {
        // we push a backtrack node into the stack
        DFStackNode_MetaBacktrack backtrackNode =
            stack.addMetaBacktrackNode(application, currentGoal);
        // select the rule to apply
        result = backtrackNode.nextRuleToTry();
        // update iteration info
        LAST_ITERATION_INFO.backtrack_point_added = true;
      } else
        result = application.nextRule();
      // update iteration info
      LAST_ITERATION_INFO.applied_rule = ruleToApply;
      LAST_ITERATION_INFO.move = IterationInfo.Move.META_RULE_APPLICATION;
    }
      break;
    case CLASH_DETECTION_RULE: {
      // Apply the clash detection rule to determine the status of the goal. 
      _ClashDetectionRule application = (_ClashDetectionRule) ruleToApply;
      // update last iteration info
      LAST_ITERATION_INFO.current_node_set_status = application.status();
      LAST_ITERATION_INFO.applied_rule = ruleToApply;
      LAST_ITERATION_INFO.move = IterationInfo.Move.CLASH_DETECTION_RULE_APPLICATION;
      result = null;
    }
      break;
    case FORCE_BRANCH_FAILURE: {
      result = FORCE_BACKTRACK;
      LAST_ITERATION_INFO.current_node_set_status = ProofSearchResult.FAILURE;
      LAST_ITERATION_INFO.applied_rule = ruleToApply;
      LAST_ITERATION_INFO.move = IterationInfo.Move.FORCE_BRANCH_FAILURE_APPLICATION;
      break;
    }
    case FORCE_BRANCH_SUCCESS: {
      result = FORCE_BRANCH_POINT_SEARCH;
      LAST_ITERATION_INFO.current_node_set_status = ProofSearchResult.SUCCESS;
      LAST_ITERATION_INFO.applied_rule = ruleToApply;
      LAST_ITERATION_INFO.move = IterationInfo.Move.FORCE_BRANCH_SUCCESS_APPLICATION;
      break;
    }
    default:
      throw new ImplementationError(ImplementationError.CASE_NOT_IMPLEMENTED);
    }

    // VERBOSE
    if (verboseMode)
      VerboseModeSupport.printIterationInfo(LAST_ITERATION_INFO, stack, ruleType, result,
          currentGoal);
    return result;
  }

  /**
   * Returns the node-set generated in the last performed iteration or
   * <code>null</code> if no iteration has been executed.
   * 
   * @return the node-set generated by the last performed iteration or
   * <code>null</code>.
   */
  _AbstractGoal getLastIterationGeneratedNodeSet() {
    return currentGoal == null ? null : currentGoal.nodeSet;
  }

  /**
   * Returns the goal of the proof-search.
   * 
   * @return the goal.
   */
  _AbstractGoal getInitialGoal() {
    return goal;
  }

  /**
   * Returns the result of the last proof-search performed by this engine or
   * <code>null</code> if the proof-search has been performed or did not
   * terminated.
   * 
   * @return the result of the proof-search or <code>null</code>.
   */
  ProofSearchResult getResult() {
    return result;
  }

  /**
   * Searches a proof for the initial goal.
   * 
   * @return the result of the proof-search.
   */
  ProofSearchResult searchProof() {
    if (verboseMode)
      VerboseModeSupport.printInitialSetInfo(LAST_ITERATION_INFO, stack, goal);

    // set initial state
    this.reset();
    this.currentGoal = new GoalNode(this, goal.clone());
    this.result = null;

    // the main cycle
    boolean finished = false;
    // select the first rule to apply
    _AbstractRule nextRule = strategy.nextRule(currentGoal.nodeSet, LAST_ITERATION_INFO);

    while (!finished) {

      if (nextRule != null) {
        RuleType stepType = RuleType.getType(nextRule);
        // apply the selected rule
        _AbstractRule returnedRule = this.applyRule(nextRule, stepType);

        // if the applied rule is a META_BACKTRACK_RULE the next rule to apply is returned
        switch (stepType) {
        case META_BACKTRACK_RULE:
          nextRule = returnedRule;
          break;
        case FORCE_BRANCH_FAILURE:
          nextRule = null;
          break;
        case FORCE_BRANCH_SUCCESS: {
          // update iteration info
          LAST_ITERATION_INFO.move = IterationInfo.Move.AND_BRANCH_POINT_SEARCH;
          LAST_ITERATION_INFO.number_of_iterations++;
          // search a previous branch point and select the next rule to apply
          if (stack.noMoreBranchPoints()) { // if there is no branch-point in the stack then SUCCESS
            if (verboseMode)
              VerboseModeSupport.printNoMoreBranchPoints();
            result = ProofSearchResult.SUCCESS;
            finished = true;
          } else
            nextRule = stack.restorePreviousBranchPoint();
        }
          break;
        default: {
          if (LAST_ITERATION_INFO.current_node_set_status == ProofSearchResult.SUCCESS) { // if it is closed try non treated branches
            // update iteration info
            LAST_ITERATION_INFO.move = IterationInfo.Move.AND_BRANCH_POINT_SEARCH;
            LAST_ITERATION_INFO.number_of_iterations++;
            // search a previous branch point and select the next rule to apply
            if (stack.noMoreBranchPoints()) { // if there is no branch-point in the stack then SUCCESS
              if (verboseMode)
                VerboseModeSupport.printNoMoreBranchPoints();
              result = ProofSearchResult.SUCCESS;
              finished = true;
            } else
              nextRule = stack.restorePreviousBranchPoint();
          } else
            // select the next rule to apply
            nextRule = strategy.nextRule(currentGoal.nodeSet, LAST_ITERATION_INFO);
        }
        }
      } else { // ...no rule been selected, then search for a backtrack-point
        // update iteration info
        LAST_ITERATION_INFO.move = IterationInfo.Move.BACKTRACK_POINT_SEARCH;
        LAST_ITERATION_INFO.number_of_iterations++;

        if (stack.noMoreBacktrackPoints()) { // if there is no backtrack point in the stack FAIL
          if (verboseMode)
            VerboseModeSupport.printNoMoreBacktrackPoints();
          result = ProofSearchResult.FAILURE;
          finished = true;
        } else
          nextRule = stack.restorePreviousBacktrackPoint();
      }
    }
    return result;
  }

  String getStackTrace() {
    return stack.getStackTrace();
  }
}
