/*******************************************************************************
 * Copyright (C) 2013, 2016 Mauro Ferrari This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this program; if not, see
 * <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package jtabwb.launcher;

import jtabwb.engine.ProofSearchResult;
import jtabwb.engine.Trace;
import jtabwb.engine._AbstractGoal;
import jtabwb.engine._Prover;
import jtabwb.launcher.Launcher.TestStatus;
import jtabwbx.problems.ProblemDescription;

/**
 * A bunch of data about a proof-search execution.
 * 
 * @author Mauro Ferrari
 */
public class ProofSearchData {

  /**
   * Describes how the proof-search terminated.
   * 
   * @author Mauro Ferrari
   * 
   */
  public static enum TerminationStatus {
    /**
     * The proof-search terminated correctly.
     */
    REGULAR, //
    /**
     * The proof-search has been terminated because of a timeout.
     */
    TIMEOUT;
  }

  TerminationStatus terminationStauts = null;
  ProofSearchResult proofSearchResult;
  ProblemDescription problemDescription;
  _AbstractGoal goal;
  _Prover selectedProver;
  TestStatus testStatus;
  long iterationCounter;
  int max_stack_size;
  long numberOfGeneratedNodes;
  long numberOfRestoredBacktrackPoints;
  long numberOfRestoredBranchPoints;
  long execution_start_time = 0;
  long execution_end_time = -1;
  long parsing_problem_start_time = 0;
  long parsing_problem_end_time = -1;
  long initial_node_set_construction_start_time = 0;
  long initial_node_set_construction_end_time = -1;
  Trace trace;

  ProofSearchData() {
  }

  
  /**
   * Returns the goal of the proof-search.
   * 
   * @return the goal of the proof-search.
   */
  public _AbstractGoal goal() {
    return goal;
  }
  
  /**
   * Returns the proved used to perform the proof-search.
   * 
   * @return the prover used to perform the proof-search.
   */
  public _Prover getProver() {
    return selectedProver;
  }

  public ProblemDescription getProblemDescription() {
    return problemDescription;
  }

  /**
   * Returns the result of the proof-search.
   * 
   * @return the result of the proof-search.
   */
  public ProofSearchResult getResult() {
    return this.proofSearchResult;
  }

  /**
   * @return the terminationStauts
   */
  public TerminationStatus getTerminationStauts() {
    return this.terminationStauts;
  }

  /**
   * Returns the number of iterations performed by the engine during the
   * proof-search.
   * 
   * @return the number of iterations performed by the engine.
   */
  public long getIterationCounter() {
    return this.iterationCounter;
  }

  /**
   * Returns the maximum depth of the stack during the proof-search.
   * 
   * @return the maximum depth of the stack.
   */
  public int getMaxStackSize() {
    return this.max_stack_size;
  }

  /**
   * Returns the number of node-sets generated during the proof-search.
   * 
   * @return the maximum of generated node-sets.
   */
  public long getNumberOfGeneratedNodes() {
    return this.numberOfGeneratedNodes;
  }

  /**
   * Returns the number of times a backtrack point has been restored from the
   * stack during the proof-search.
   * 
   * @return the number of restored backtrack points.
   */
  public long getNumberOfRestoredBacktrackPoints() {
    return this.numberOfRestoredBacktrackPoints;
  }

  /**
   * Returns the number of times a branch point has been restored from the stack
   * during the proof-search.
   * 
   * @return the number of restored branch points.
   */
  public long getNumberOfRestoredBranchPoints() {
    return this.numberOfRestoredBranchPoints;
  }

  /**
   * Returns the time required by the proof-search in milliseconds.
   * 
   * @return the time required by the proof-search.
   */
  public long getExecutionTime() {
    return this.execution_end_time - this.execution_start_time;
  }

  /**
   * Returns the time required to parse the problem description in milliseconds.
   * 
   * @return the time needed to parse the input problem.
   */
  public long getParsingProblemTime() {
    return this.parsing_problem_end_time - this.parsing_problem_start_time;
  }

  /**
   * Returns the time required to build the initial node set.
   * 
   * @return the time needed to build the initial node set.
   */
  public long getIntialNodeSetConstructionTime() {
    return this.initial_node_set_construction_end_time
        - this.initial_node_set_construction_start_time;
  }

  /**
   * Returns the start time of the proof-search.
   * 
   * @return the start time of the proof-search.
   */
  public long getStartTime() {
    return this.execution_start_time;
  }

  /**
   * Returns the end time of the proof-search or <code>-1</code> if the
   * proof-search did not terminate.
   * 
   * @return the end time of the proof-search or <code>-1</code> if the
   * proof-search did not terminate.
   */
  public long getEndTime() {
    return this.execution_end_time;
  }

  /**
   * Returns the trace of the proof-search if the trace option was set or
   * <code>null</code> otherwise.
   * 
   * @return the trace of the proof-search or <code>null</code>.
   */
  public Trace getTrace() {
    return this.trace;
  }

}
