/*******************************************************************************
 * Copyright (C) 2013, 2016 Mauro Ferrari
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
 * A bunch of data describing the last iteration performed by the engine.
 * 
 * @author Mauro Ferrari
 */
public class IterationInfo {

  /**
   * The possible moves performed by the engine.
   * 
   * @author Mauro Ferrari
   */
  public enum Move {
    /**
     * The engine is in the initial state (no iteration has been executed).
     */
    INITIAL_STATE,
    /**
     * The move executed in the last iteration was a regular rule application.
     */
    REGULAR_RULE_APPLICATION,
    /**
     * The move executed in the last completed iteration was a branch-exists
     * rule application.
     */
    BRANCH_EXISTS_RULE_APPLICATION,
    /**
     * The move executed in the last completed iteration was an attempt to
     * restore a branch point.
     */
    BRANCH_POINT_SEARCH,
    /**
     * The move executed in the last completed iteration was an attempt to
     * restore a backtrack point.
     */
    BACKTRACK_POINT_SEARCH,
    /**
     * The move executed in the last completed iteration was a clash-detection
     * rule application.
     */
    CLASH_DETECTION_RULE_APPLICATION,
    /**
     * 
     */
    FORCE_BRANCH_FAILURE_APPLICATION,
    /**
     * 
     */
    FORCE_BRANCH_SUCCESS_APPLICATION,
    /**
     * The move executed in the last completed iteration was a meta-backtrack
     * rule application.
     */
    META_RULE_APPLICATION;
  }

  // GENERAL ITERATION INFO
  long number_of_iterations; // the number of iterations performed by the prover up to this one
  long number_of_generated_nodes; // the number of nodes generated up to this iteration

  // LAST APPLIED RULE INFO
  Move move = null; // the last move of the engine
  _AbstractRule applied_rule; // the rule applied in the last iteration
  ProofSearchResult current_node_set_status; /*
                                              * if the last move was a
                                              * CLASH_DETECTION_RULE_APPLICATION
                                              * , it contains the result of such
                                              * an application otherwise it
                                              * contains null
                                              */
  int number_of_conclusions; // number of conclusion of last rule application
  int treated_conclusion; // the index of the conclusion treated in the last iteration
  boolean backtrack_point_added; // true iff the last move added a backtrack node to the stack
  boolean branch_point_added; // true iff the last move added a branch node to the stack
  long number_of_restored_backtrack_points;
  long number_of_restored_branch_points;
  int max_stack_size;
  EnginePlain engine;

  IterationInfo(EnginePlain engine) {
    reset();
    this.engine = engine;
  }

  void reset() {
    // PROOF SEARCH INFO
    number_of_iterations = 0;
    number_of_generated_nodes = 0;
    number_of_restored_backtrack_points = 0;
    number_of_restored_branch_points = 0;
    max_stack_size = 0;
    // LAST APPLIED RULE INFO
    move = Move.INITIAL_STATE;
    current_node_set_status = null;
    applied_rule = null;
    number_of_conclusions = 0;
    treated_conclusion = -1;
    backtrack_point_added = false;
    branch_point_added = false;
  }

  /**
   * Returns the number of iterations performed by the engine.
   * 
   * @return the number of the iteration.
   */
  public long getNumberOfIterations() {
    return this.number_of_iterations;
  }

  /**
   * Returns the rule applied in the last iteration or <code>null</code> if no
   * rule has been applied in the last iteration of the engine. In details,
   * according with the value returned by {@link IterationInfo#getMove()}:
   * <ul>
   * <li>{@link Move#INITIAL_STATE}: the method returns <code>null</code>.</li>
   * <li>{@link Move#REGULAR_RULE_APPLICATION},
   * {@link Move#BRANCH_EXISTS_RULE_APPLICATION},
   * {@link Move#META_RULE_APPLICATION},
   * {@link Move#CLASH_DETECTION_RULE_APPLICATION}: the method returns the
   * instance of the applied rule.</li>
   * <li>{@link Move#BACKTRACK_POINT_SEARCH}: if the stack contained a
   * backtrack-point the engine restored such a point, in this case the method
   * returns the instance of the rule restored from the stack. If the stack did
   * not contained a backtrack point, the method returns <code>null</code>.</li>
   * <li>{@link Move#BRANCH_POINT_SEARCH}: if the stack contained a branch-point
   * the engine restored such a point, in this case the method returns the
   * instance of the rule restored from the stack. If the stack did not
   * contained a branch-point, the method returns <code>null</code>.</li>
   * </ul>
   * 
   * @return the rule applied in the last iteration of the engine or
   * <code>null</code> if no rule has been applied in such an iteration.
   */
  public _AbstractRule getAppliedRule() {
    return this.applied_rule;
  }

  /**
   * Returns the move the engine performed in the last iteration. In details:
   * <ul>
   * <li>The method returns {@link Move#INITIAL_STATE} if no iteration has been
   * performed by the engine.</li>
   * <li>The method returns one of the values
   * {@link Move#REGULAR_RULE_APPLICATION},
   * {@link Move#BRANCH_EXISTS_RULE_APPLICATION},
   * {@link Move#META_RULE_APPLICATION}, if this is the kind of the rule applied
   * in the last iteration.</li>
   * <li>The method returns {@link Move#CLASH_DETECTION_RULE_APPLICATION} if the
   * rule applied in the last iteration was an instance of
   * {@link _ClashDetectionRule} and the method
   * {@link _ClashDetectionRule#status()} returned
   * {@link ProofSearchResult#FAILURE}.</li>
   * <li>The method returns {@link Move#BACKTRACK_POINT_SEARCH} if in the last
   * iteration the engine invoked
   * {@link _Strategy#nextRule(_AbstractGoal, IterationInfo)} and such a
   * method returned <code>null</code>. This means that the search for a proof
   * of the current goal failed and the engine searched the stack for a
   * backtrack-point.</li>
   * <li>The method returns {@link Move#BRANCH_POINT_SEARCH} if the rule applied
   * in the last iteration was an instance of {@link _ClashDetectionRule} and
   * the method {@link _ClashDetectionRule#status()} returned
   * {@link ProofSearchResult#SUCCESS}. In this case the engine query the stack
   * for a branch-point.</li>
   * </ul>
   * 
   * @return the move performed by the engine in the last iteration.
   */
  public Move getMove() {
    return this.move;
  }

  /**
   * Returns <code>true</code> if the last rule application added a
   * backtrack-point to the stack. This happens if:
   * <ul>
   * <li>in the last iteration the engine applied an instance of
   * {@link _BranchExistsRule} (hence {@link #getMove()} returns
   * {@link Move#BRANCH_EXISTS_RULE_APPLICATION}) and the invocation of
   * {@link _BranchExistsRule#numberOfBranchExistsSubgoals()} on such an
   * instance returned a value greater than 1.</li>
   * <li>in the last iteration the engine applied an instance of
   * {@link _MetaBacktrackRule} (hence {@link #getMove()} returns
   * {@link Move#META_RULE_APPLICATION}) and the invocation of
   * {@link _MetaBacktrackRule#totalNumberOfRules()} on such an instance
   * returned a value greater than 1.</li>
   * </ul>
   * 
   * @return <code>true</code> if and only if, executing the last iteration, the
   * engine added a backtrack-point to the stack.
   */
  public boolean backtrackPointAdded() {
    return this.backtrack_point_added;
  }

  /**
   * Returns <code>true</code> if the last rule application added a branch-point
   * to the stack. This happens if in the last iteration the engine applied an
   * instance of {@link _RegularRule} (hence {@link #getMove()} returns
   * {@link Move#REGULAR_RULE_APPLICATION}) and the invocation of
   * {@link _RegularRule#numberOfSubgoals()} on such an instance returned a
   * value greater than 1.
   * 
   * @return <code>true</code> if and only if, executing the last iteration, the
   * engine added a branch-point to the stack.
   */
  public boolean branchPointAdded() {
    return this.branch_point_added;
  }

  /**
   * Returns the number of conclusions in the last rule application or -1 if
   * this information has no meaning for the move performed by the engine in the
   * last iteration. Namely:
   * <ul>
   * <li>If in the last iteration the engine applied an instance of
   * {@link _RegularRule} the method returns the result of the invocation of
   * {@link _RegularRule#numberOfSubgoals()} on such an instance.</li>
   * <li>If in the last iteration the engine applied an instance of
   * {@link _BranchExistsRule} the method returns the values of the invocation
   * of {@link _BranchExistsRule#numberOfBranchExistsSubgoals()} on such an
   * instance.</li>
   * <li>If in the last iteration the engine restored a branch-point, the method
   * returns the number of conclusion of the restored rule instance.</li>
   * <li>In all other cases the methods returns -1.</li>
   * </ul>
   * 
   * @return the number of conclusions of the rule applied in the last iteration
   * of the engine or -1 if in the last iteration the engine did not applied a
   * rule with conclusions.
   */
  public int getNumberOfConclusions() {
    return this.number_of_conclusions;
  }

  /**
   * TODO: doc caso di meta-backtrack If in the last iteration the engine
   * applied a rule with conclusions, this method returns the index of the
   * conclusion that became the new goal of the engine, otherwise the method
   * returns -1. Namely:
   * <ul>
   * <li>If in the last iteration the engine applied an instance of
   * {@link _RegularRule}, the method returns 0 (the index of the first
   * conclusion).</li>
   * <li>If in the last iteration the engine applied an instance of
   * {@link _BranchExistsRule}, the method returns 0 (the index of the first
   * conclusion).</li>
   * <li>If in the last iteration the engine restored a branch-point, then the
   * restored rule is an instance of a {@link _RegularRule}; the engine invoke
   * the method <code>conclusion(i)</code> to get the new subgoal where i is the
   * index of the first non treated conclusion of the rule; in this case the
   * method returns i.</li>
   * <li>In all other cases the method returns -1.</li>
   * </ul>
   * 
   * @return the index of the conclusion of the rule applied in the last
   * iteration of the engine or -1 if in the last iteration the engine did not
   * applied a rule with conclusions.
   */
  public int getTreatedConclusion() {
    return this.treated_conclusion;
  }

  /**
   * Returns the number of node-sets generated up to this iteration.
   * 
   * @return the number of generated node-sets.
   */
  public long getNumberOfGeneratedNodes() {
    return this.number_of_generated_nodes;
  }

  /**
   * Returns the number of times a backtrack-point has been restored up to this
   * iteration.
   * 
   * @return the number of restored backtrack-points.
   */
  public long getNumberOfRestoredBacktrackPoints() {
    return number_of_restored_backtrack_points;
  }

  /**
   * Returns the number of times a branch-point has been restored up to this
   * iteration.
   * 
   * @return the number of restored branch-points.
   */
  public long getNumberOfRestoredBranchPoints() {
    return number_of_restored_branch_points;
  }

  /**
   * Returns the maximum size of the stack up to this iteration.
   * 
   * @return the maximum size o the stack.
   */
  public int getMaxStackSize() {
    return max_stack_size;
  }

  /**
   * Return the stack trace.
   * 
   * @return the stack trace.
   */
  public String getStackTrace() {
    return engine.getStackTrace();
  }

}
