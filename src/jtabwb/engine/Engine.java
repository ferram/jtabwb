/*******************************************************************************
 * Copyright (C) 2013, 2015 Mauro Ferrari This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this program; if not, see
 * <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package jtabwb.engine;

/**
 * The engine performs a depth-first search of a derivation tree.
 * 
 * @author Mauro Ferrari
 */
public class Engine {

  private EnginePlain realEngine;
  private ExecutionMode currentExecutionMode;

  /**
   * Engine execution mode flags.
   * 
   * @author Mauro Ferrari
   * 
   */
  public static enum ExecutionMode {
    /**
     * The prover is executed in plain mode, it is the fastest execution mode.
     */
    ENGINE_PLAIN,
    /**
     * The prover is executed in verbose mode, i.e., it prints on the standard
     * output a detailed description of the proof-search.
     */
    ENGINE_VERBOSE,
    /**
     * The prover is executed in trace mode, i.e., the proof-trace describing
     * the current proof search is built during proof-search.
     */
    ENGINE_TRACE;
  }

  /**
   * Builds an instance of the engine act that allows to search for a proof of
   * the goal specified as argument (<em>initial goal</em>) using the specified
   * prover. The specified mode modify the standard behaviour of the engine.
   * 
   * @param prover the prover to use.
   * @param goal the initial node set.
   * @param mode the execution mode.
   */
  public Engine(_Prover prover, _AbstractGoal goal, ExecutionMode mode) {
    this.currentExecutionMode = mode;
    switch (mode) {
    case ENGINE_PLAIN:
      realEngine = new EnginePlain(prover, goal, false);
      break;
    case ENGINE_TRACE:
      realEngine = new EngineTrace(prover, goal, false);
      break;
    case ENGINE_VERBOSE:
      realEngine = new EnginePlain(prover, goal, true);
      break;
    default:
      throw new ImplementationError(ImplementationError.CASE_NOT_IMPLEMENTED);
    }

  }

  /**
   * Builds an instance of the engine act that allows to search for a proof of
   * the goal specified as argument (<em>initial goal</em>) using the specified
   * prover.
   * 
   * @param prover the prover to use.
   * @param goal the initial node set.
   */
  public Engine(_Prover prover, _AbstractGoal goal) {
    this(prover, goal, ExecutionMode.ENGINE_PLAIN);
  }

  /**
   * Returns the initial goal.
   * 
   * @return the initial node set.
   */
  public _AbstractGoal getInitialGoal() {
    return realEngine.getInitialGoal();
  }

  /**
   * Returns the result of the last proof-search.
   * 
   * @return the result of the last proof-search.
   */
  public ProofSearchResult getResult() {
    return realEngine.getResult();
  }

  /**
   * Returns the trace of the last proof-search or null if the engine has not
   * been executed in trace mode (see {@link ExecutionMode}).
   * 
   * @return the trace of the last proof-search or null.
   */
  public Trace getTrace() {
    if (currentExecutionMode == ExecutionMode.ENGINE_TRACE)
      return ((EngineTrace) realEngine).getTrace();
    else
      return null;
  }

  /**
   * Searches a proof for the initial goal and, if it terminates, returns the
   * result of the proof-search.
   * 
   * @return the result of the search.
   */
  public ProofSearchResult searchProof() {
    return realEngine.searchProof();
  }

  /**
   * Returns a bunch of detailed information on the last iteration performed by
   * the prover.
   * 
   * @return information on the last iteration performed by the prover.
   */
  public IterationInfo getLastIterationInfo() {
    return realEngine.LAST_ITERATION_INFO;
  }

}
