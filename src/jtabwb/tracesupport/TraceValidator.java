/*******************************************************************************
 * Copyright (C) 2013, 2014 Mauro Ferrari
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
package jtabwb.tracesupport;

import jtabwb.engine.Engine;
import jtabwb.engine.Engine.ExecutionMode;
import jtabwb.engine.ProofSearchResult;
import jtabwb.engine.Trace;
import jtabwb.engine._Prover;

public class TraceValidator {

  private _Prover prover;
  private Trace trace;
  private _TraceSupport traceSupport;

  public TraceValidator(Trace trace, _Prover prover, _TraceSupport traceSupport) {
    super();
    this.prover = prover;
    this.trace = trace;
    this.traceSupport = traceSupport;
  }

  public Trace getTrace() {
    return this.trace;
  }

  public _Prover getProver() {
    return this.prover;
  }

  public boolean validateProofTrace() throws TraceSupportException {
    _Prover validatorProver = new Validator(trace, prover, traceSupport.getTraceManager());
    Engine engine = new Engine(validatorProver, trace.getInitialNodeSet(),
        ExecutionMode.ENGINE_VERBOSE);
    ProofSearchResult result = engine.searchProof();
    return result == trace.getStatus();
  }

}
