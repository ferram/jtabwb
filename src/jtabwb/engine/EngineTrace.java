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
 * An extension of the engine generating traces.
 * 
 * @author Mauro Ferrari
 */
class EngineTrace extends EnginePlain {

  Trace trace;
  _AbstractGoal premiseOfRuleApplication;

  /**
   * Constructs an instance of this engine that perform a proof-search of the
   * <code>initialNodeSet</code> driven by the <code>prover</code>.
   * 
   * @param prover the prover driving proof search.
   * @param initialNodeSet the initial node-set of the proof-search.
   */
  EngineTrace(_Prover prover, _AbstractGoal initialNodeSet, boolean verboseMode) {
    super(prover, initialNodeSet, null, verboseMode);
    this.stack = new DFStackWithTrace(this,verboseMode);
  }

  /**
   * Returns the trace of the proof search.
   * 
   * @return the trace.
   */
  Trace getTrace() {
    return trace;
  }

  @Override
  public _AbstractRule applyRule(_AbstractRule ruleToApply, RuleType ruleType) {
    DFStackWithTrace dfManagerTrace = ((DFStackWithTrace) stack);
    premiseOfRuleApplication = (currentGoal.nodeSet).clone();
    dfManagerTrace.isStackModified = false;
    _AbstractRule result = super.applyRule(ruleToApply, ruleType);
    dfManagerTrace.traceRule(this, premiseOfRuleApplication, ruleToApply);
    return result;
  }

  /**
   * Searches a proof for the initial node-set.
   * 
   * @return the result of the search.
   */
  public ProofSearchResult searchProof() {
    ProofSearchResult result = super.searchProof();
    this.trace = ((DFStackWithTrace) stack).getTrace(goal, prover, result);
    return result;
  }
}
