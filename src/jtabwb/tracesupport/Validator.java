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

import jtabwb.engine.ProofSearchResult;
import jtabwb.engine.ProvabilityStatus;
import jtabwb.engine.ProverName;
import jtabwb.engine.Trace;
import jtabwb.engine._Prover;
import jtabwb.engine._Strategy;

/**
 * @author Mauro Ferrari
 */
public class Validator implements _Prover {

  private static String DESCRIPTION = "Trace validator";
  private static String NAME = "trace_validator";

  private _Prover prover;
  private _Strategy strategy;
  private ProverName proverName;

  public Validator(Trace trace, _Prover prover, _TraceManager traceManager) {
    super();

    if (trace.getStatus() != ProofSearchResult.SUCCESS)
      throw new TraceSupportException(TraceSupportMessageManager.getMsg(
          "validate.trace.is.not.a.proof", trace.getStatus().name()));
    if (!trace.isPruned())
      throw new TraceSupportException(
          TraceSupportMessageManager.getMsg("validate.trace.is.not.pruned"));
    this.strategy = new ValidatorStrategy(trace, traceManager);
    this.prover = prover;
    this.proverName = new ProverName(NAME + "[" + trace.getProver().getProverName().getDetailedName()
        + "]");
    this.proverName.setDescription(DESCRIPTION + "for ["
        + trace.getProver().getProverName().getDetailedName() + "]");
  }

  @Override
  public ProverName getProverName() {
    return proverName;
  }

  @Override
  public _Strategy getStrategy() {
    return strategy;
  }

  @Override
  public ProvabilityStatus statusFor(ProofSearchResult result) {
    return prover.statusFor(result);
  }

}
