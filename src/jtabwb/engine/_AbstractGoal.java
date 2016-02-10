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
 * <p>
 * An abstract goal represent a goal during proof-search. An implementation of
 * this interface is a data structure representing the set of formulas (or
 * signed formulas) on which the rules of the calculus work. E.g., implementing
 * a proof-search over a sequent calculus, the implementation of this interface
 * represents a sequent; implementing a proof-search over a tableau calculus,
 * its implementation represents a set of signed formulas. The engine needs very
 * little information about node-sets; indeed, it only uses the method
 * {@link #format()} when it works in verbose mode to provide information on
 * proof-search and the method {@link #clone()} to store some information needed
 * to proof-search and trace generation.
 * </p>
 * 
 * @author Mauro Ferrari
 */
public interface _AbstractGoal {

  /**
   * Returns a string describing this goal
   * 
   * @return a string describing this goal
   */
  public String format();

  /**
   * Returns a fresh copy of this goal
   * 
   * @return a clone of this goal
   */
  public _AbstractGoal clone();
}
