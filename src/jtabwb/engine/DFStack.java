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
 *******************************************************************************/
package jtabwb.engine;

/**
 * It is a stack storing branch and backtrack points during the depth-first
 * proof-search.
 * 
 * @author Mauro Ferrari
 */
class DFStack {

  /* INFO ON THE STACK */

  boolean verboseMode = true; // if true the verbose mode is active
  boolean applyOnResume = true; // if true onRuleResumed is applied

  //STACK SIZE
  int current_stack_size = 0; // the size of the stack at the current iteration
  int max_stack_size = 0; // the maximum size of the stack up to this iteration

  // RESTORED POINTS INFO
  long number_of_restored_backtrack_points = 0; /*
                                                 * the number of times a
                                                 * backtrack-point has been
                                                 * restored up to this
                                                 * iteration.
                                                 */
  long number_of_restored_branch_points = 0; /*
                                              * the number of times a
                                              * branch-point has been restored
                                              * up to this iteration.
                                              */

  DFStackNode restored_DFStackNode; /*
                                     * the node of the depth-first stack
                                     * restored in the last iteration or null if
                                     * the last iteration did not restored a
                                     * depth-first stack node
                                     */

  /*
   * The stack is implemented as a linked list of nodes. A backtrack entry is an
   * entry storing a backtrack node, while a branch entry is an entry storing a
   * branch node. Every entry store the previous backtrack entry and the
   * previous branch entry to make more efficient to restore branch and
   * backtrack points.
   */
  // >>>>>> THESE FIELDS ARE DIRECTLY USED BY THE EXTENSIONS OF THIS CLASS
  DFStackNode df_head; // the head of the list
  DFStackNode df_lastBacktrack; // the last added branch entry 
  DFStackNode df_lastBranch; // the last added backtrack entry

  // <<<<<<<<<
  EnginePlain engine; // The engine using this stack

  /**
   * Constructs an emtpy stack.
   */
  DFStack(EnginePlain engine, boolean verboseMode, boolean applyOnResume) {
    super();
    this.engine = engine;
    this.verboseMode = verboseMode;
    this.applyOnResume = applyOnResume;
    this.df_head = null;
    this.df_lastBacktrack = null;
    this.df_lastBranch = null;
    //
    this.current_stack_size = 0;
    this.max_stack_size = 0;
    this.number_of_restored_backtrack_points = 0;
    this.number_of_restored_branch_points = 0;
    this.restored_DFStackNode = null;
  }

  /**
   * Clear the stack.
   */
  void clearStack() {
    df_head = null;
    df_lastBacktrack = null;
    df_lastBranch = null;
  }

  boolean noMoreBacktrackPoints() {
    return df_lastBacktrack == null;
  }

  boolean noMoreBranchPoints() {
    return df_lastBranch == null;
  }

  DFStack newInstance() {
    return new DFStack(this.engine, this.verboseMode, true);
  }

  /**
   * Removes the top node of the stack.
   */
  private void pop(DFStackNode node) {
    df_head = node.df_previousNode;
    current_stack_size = node.node_height - 1;
    df_lastBranch = node.df_previousBranch;
    df_lastBacktrack = node.df_previousBacktrack;
  }

  /**
   * Push the specified node in the stack.
   * 
   * @param node the node.
   */
  private void push(DFStackNode node) {
    // set node fields
    node.node_height = ++current_stack_size;
    node.generated_at_iteration = engine.LAST_ITERATION_INFO.number_of_iterations;
    node.df_previousNode = df_head;
    node.df_previousBranch = df_lastBranch;
    node.df_previousBacktrack = df_lastBacktrack;
    // update stack reference
    df_head = node;
    // update stack info
    max_stack_size += max_stack_size < current_stack_size ? 1 : 0;
  }

  /**
   * Push the specified branch node in the stack.
   * 
   * @param node the branch node.
   */
  DFStackNode_Branch addBranchNode(_RegularRule regularRuleApplication, GoalNode currentGoal) {
    DFStackNode_Branch node =
        new DFStackNode_Branch(regularRuleApplication, currentGoal, applyOnResume);
    push(node);
    // update stack references
    df_lastBranch = df_head;
    return node;
  }

  /**
   * Push the specified meta backtrack node in the stack.
   * 
   * @param node the backtrack node.
   */
  DFStackNode_MetaBacktrack addMetaBacktrackNode(_MetaBacktrackRule metaBacktrackRuleApplication,
      GoalNode currentGoal) {
    DFStackNode_MetaBacktrack node =
        new DFStackNode_MetaBacktrack(metaBacktrackRuleApplication, currentGoal, applyOnResume);
    push(node);
    // update stack references
    df_lastBacktrack = df_head;
    return node;
  }

  /**
   * Push the specified branch-exists node in the stack.
   * 
   * @param node the branch-exists node.
   */
  DFStackNode_BranchExists addBranchExistsNode(_BranchExistsRule branchExistsRuleApplication,
      GoalNode currentGoal) {
    DFStackNode_BranchExists node =
        new DFStackNode_BranchExists(branchExistsRuleApplication, currentGoal, applyOnResume);
    push(node);
    // update stack references
    df_lastBacktrack = df_head;
    return node;
  }

  /**
   * Returns <code>true</code> iff this stack is empty.
   * 
   * @return <code>true</code> iff this stack is empty.
   */
  boolean isEmpty() {
    return df_head == null;
  }

  /**
   * Restores the last added backtrack-point of proof-search. Doing that the
   * method removes all nodes above the last added backtrack-point. The method
   * returns the next rule to apply stored in the last added backtrack point or
   * <code>null</code> if no backtrack point is stored into the stack. If the
   * last added backtrack point contains only one more rule to try, the
   * backtrack point node is removed from the stack. After the execution of the
   * method {@link #restoredStepPremise} and {@link #currentGoal} both contains
   * the node of the proof search representing the premise of rule generating
   * the restored backtrack point.
   * 
   * @param engine the engine invoking this method.
   * @return the next rule to apply in the proof-search.
   */
  //This method is overridden in DFStackVerbose and in DFStackWithTrace
  _AbstractRule restorePreviousBacktrackPoint() {

    //search for an active backtrack point, that is a backtrack point
    //that, after on resume execution (if required) is not completed    
    DFStackNode activeBacktrackPoint;
    if (df_lastBacktrack != null) {
      activeBacktrackPoint = df_lastBacktrack;
      boolean continueSearch = true;
      do {
        number_of_restored_backtrack_points++;
        // if the restored is an OnResumeListener apply onResume()
        if (activeBacktrackPoint.requireOnResumeInvocation) {
          activeBacktrackPoint.applyOnResume();
          // if now this node is completed go on searching a new active backtrack node
          if (activeBacktrackPoint.isCompleted()) {
            activeBacktrackPoint = activeBacktrackPoint.df_previousBacktrack;
            continueSearch = activeBacktrackPoint != null;
          } else
            continueSearch = false;
        } else
          continueSearch = false;
      } while (continueSearch);
    } else
      activeBacktrackPoint = null;

    _AbstractRule result;
    if (activeBacktrackPoint == null) { // there is no active backtrack node to resume
      // notify failure to all nodes stored listeners
      onCompletedRuleHandler.notify(ProofSearchResult.FAILURE, 0);
      // update stack info
      restored_DFStackNode = null;
      clearStack();
      result = null;
    } else {
      // notify failure to all listeners following the activeBacktrackPoint
      onCompletedRuleHandler.notify(ProofSearchResult.FAILURE, activeBacktrackPoint.node_height);
      // update the stack and stack info
      df_head = activeBacktrackPoint;
      df_lastBacktrack = activeBacktrackPoint;
      df_lastBranch = activeBacktrackPoint.df_previousBranch;
      current_stack_size = activeBacktrackPoint.node_height;
      restored_DFStackNode = activeBacktrackPoint;
      if (df_head instanceof DFStackNode_MetaBacktrack) {
        // update stack info about restored node
        DFStackNode_MetaBacktrack restoredNode = (DFStackNode_MetaBacktrack) df_head;
        restored_DFStackNode = restoredNode;
        // update the goal
        engine.currentGoal = new GoalNode(engine, restoredNode.getGoal());
        // select the next step
        _AbstractRule nextStep = restoredNode.nextRuleToTry();
        if (restoredNode.numberOfRulesToTry() == 0) // remove the backtrack-point if needed
          pop(restoredNode);
        result = nextStep;
      } else {
        // update stack info about restored node
        DFStackNode_BranchExists restoredNode = (DFStackNode_BranchExists) df_head;
        restored_DFStackNode = restoredNode; //
        // update the goal
        engine.currentGoal = restoredNode.nextBranchToTreat(engine);
        if (!restoredNode.hasMoreBranchesTOTreat()) // remove the backtrack-point if needed
          pop(restoredNode);
        // select and return next step
        result = engine.strategy.nextRule(engine.currentGoal.nodeSet, engine.LAST_ITERATION_INFO);
      }
    }

    if (verboseMode) { // if verbose, print informations
      if (result != null)
        VerboseModeSupport.printResumedBacktrackPointInfo(engine, result);
      else {
        VerboseModeSupport.printIterationDetails(engine.LAST_ITERATION_INFO, this);
        VerboseModeSupport.printNoMoreBacktrackPoints();
      }
    }

    return result;
  }

  /**
   * Restores the last branch-point added during proof-search. Doing that the
   * method removes all nodes above the last added branch-point. The method
   * returns the next rule to apply, which is selected invoking the
   * {@link _Strategy#nextRule(_AbstractNodeSet, EngineMove)} method on the next
   * conclusion stored in last added branch point; the method returns
   * <code>null</code> if no branch point is stored into the stack. If the last
   * added branch-point contains only one more conclusion, the backtrack point
   * node is removed from the stack. After the execution of the method
   * {@link #restoredStepPremise} contains the premise of the rule which
   * generated the branch point, while {@link #currentGoal} contains the new
   * conclusion restored from the branch point.
   * 
   * @param engine the engine invoking this method.
   * @return the next rule to apply in the proof-search.
   */
  //  This method is overridden in DFManagerVerbose 
  _AbstractRule restorePreviousBranchPoint() {
    //search for an active branch point, that is a branch point
    //that, after on resume execution (if required) is not completed    
    DFStackNode activeBranchPoint;
    if (df_lastBranch != null) {
      activeBranchPoint = df_lastBranch;
      boolean continueSearch = true;
      do {
        number_of_restored_branch_points++;
        // if the restored is an OnResumeListener apply onResume()
        if (activeBranchPoint.requireOnResumeInvocation) {
          activeBranchPoint.applyOnResume();
          // if now this node is completed go on searching a new active branch node
          if (activeBranchPoint.isCompleted()) {
            activeBranchPoint = activeBranchPoint.df_previousBranch;
            continueSearch = activeBranchPoint != null;
          } else
            continueSearch = false;
        } else
          continueSearch = false;
      } while (continueSearch);
    } else
      activeBranchPoint = null;

    _AbstractRule result = null;
    boolean restoredBranchPoint = false;
    if (activeBranchPoint == null) {
      // notify failure to all nodes stored listeners
      onCompletedRuleHandler.notify(ProofSearchResult.SUCCESS, 0);
      // update stack info
      restored_DFStackNode = null;
      clearStack();
    } else {
      restoredBranchPoint = true;
      // notify success to all listeners following the activeBacktrackPoint
      onCompletedRuleHandler.notify(ProofSearchResult.SUCCESS, activeBranchPoint.node_height);
      // update the stack and stack info
      df_head = activeBranchPoint;
      df_lastBacktrack = activeBranchPoint.df_previousBacktrack;
      df_lastBranch = activeBranchPoint;
      current_stack_size = activeBranchPoint.node_height;
      restored_DFStackNode = activeBranchPoint;
      // restore the branch-point
      DFStackNode_Branch branchNode = (DFStackNode_Branch) df_head;
      // update the goal
      engine.currentGoal = branchNode.nextConclusionToTreat(engine);
      if (!branchNode.hasMoreConclusionsToTreat()) // remove the branch point if needed 
        pop(branchNode);
      // select and return the next rule to apply

      result = engine.strategy.nextRule(engine.currentGoal.nodeSet, engine.LAST_ITERATION_INFO);
    }

    // VERBOSE 
    if (verboseMode) {
      if (restoredBranchPoint)
        VerboseModeSupport.printResumedBranchPointInfo(engine);
      else
        VerboseModeSupport.printNoMoreBranchPoints();
    }

    return result;
  }

  OnCompletedRuleHandler onCompletedRuleHandler = new OnCompletedRuleHandler(this);

  void addOnCompletedRuleListener(_OnRuleCompletedListener listener) {
    onCompletedRuleHandler.add(listener);
  }

  public String getStackTrace() {
    if (df_head == null)
      return "  <EMPTY>";
    else {
      String str = null;
      DFStackNode current = df_head;
      do {
        str = (str == null) ? "" : str + "\n";
        str += "  <" + current.node_height + "> " + current.toString();
        current = current.df_previousNode;
      } while (current != null);
      return str;
    }
  }
}
