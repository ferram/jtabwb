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
package jtabwb.launcher;

import jtabwb.engine._AbstractGoal;

/**
 * An object building the initial goal starting from a problem description.
 * 
 * @author Mauro Ferrari
 */
public interface _InitialGoalBuilder {

  /**
   * Build a goal from the specified problem description.
   * 
   * @param inputProblem the input problem
   * @return the goal
   * @throws InitialGoalBuilderException if a goal cannot be built
   */
  public _AbstractGoal buildInitialNodeSet(ProblemDescription inputProblem)
      throws InitialGoalBuilderException;

}
