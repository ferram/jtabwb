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
package jtabwb.engine;

/**
 * An object realizing an instance of a prover.
 * 
 * @author Mauro Ferrari
 */
public interface _Prover {

  /**
   * Returns an object describing the detailed name of the prover.
   * 
   * @return the detailed name of the prover.
   */
  public ProverName getProverName();

  /**
   * Returns the strategy used by this prover.
   * 
   * @return the strategy.
   */
  public _Strategy getStrategy();

  /**
   * Returns the provability status corresponding to the specified proof-search
   * result. In a usual calculus a successful proof-search (result
   * {@link ProofSearchResult#SUCCESS}) corresponds to a provable problem
   * (status {@link ProvabilityStatus#PROVABLE}), while a failed proof-search
   * (result {@link ProofSearchResult#FAILURE}) corresponds to an unprovable
   * problem (status {@link ProvabilityStatus#UNPROVABLE}). On the other hand,
   * in a calculus for unprovability a successful proof-search corresponds to an
   * unprovable formula, while a failed proof-search corresponds to a provable
   * formula (status {@link ProvabilityStatus#PROVABLE}).
   * 
   * @param result the result of the proof-search.
   * @return the corresponding provability status.
   */
  public ProvabilityStatus statusFor(ProofSearchResult result);

}
