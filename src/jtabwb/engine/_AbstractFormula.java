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
 * 
 * <p>
 * Represents a formula during proof-search. The engine needs very little
 * information about the formulas on which the proof-search works. Indeed,
 * formulas are explicitly used only by the implementation of the rules of the
 * calculus (i.e., implementations of {@link _RegularRule} and
 * {@link _BranchExistsRule}) and by the implementation of goals (
 * {@link _AbstractGoal}). The engine only uses the methods {@link #shortName()}
 * and {@link #format()} when it works in verbose mode to print information on
 * the proof-search. The short name should specify some essential information
 * about the formula; e.g., in the case of a sequent calculus implementation, it
 * should be something like <code>left-AND</code>, to mean that this is a
 * formula with AND as main connective belonging to the left hand-side of the
 * sequent.
 * </p>
 * 
 * @author Mauro Ferrari
 */
public interface _AbstractFormula {

  /**
   * Returns the string representation of the formula
   * 
   * @return the string describing the formula
   */
  public String format();

  /**
   * Returns the short name of the formula. It is only used to provide
   * information on the proof-search. Typically the short is the main connective
   * of the formula
   * 
   * @return returns the short name of the formula
   */
  public String shortName();

}
