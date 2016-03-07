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
 *******************************************************************************/
package jtabwb.launcher;

import jtabwb.engine._AbstractGoal;
import jtabwb.engine._Prover;
import jtabwb.launcher.Launcher.LaunchConfiguration;
import jtabwbx.problems.ProblemDescription;

// TODO: doc
public interface _SingleExecutionConfigurator {

  /**
   * This method is executed by the launcher immediately before invoking
   * {@link _ProblemReader#read(java.io.Reader)}.
   * 
   * @param reader the reader that will be invoked
   * @param currentLauncherConfiguration the current launcher configuration
   */
  public void configProblemReader(_ProblemReader reader,
      LaunchConfiguration currentLauncherConfiguration);

  /**
   * This method is executed by the launcher immediately before invoking
   * {@link _InitialGoalBuilder#buildInitialNodeSet(ProblemDescription)}.
   * 
   * @param problemescrption the current problem description
   * @param launcherConfiguration the current launcher configuration
   */
  public void configInitialNodeSetBuilder(ProblemDescription problemescrption,
      LaunchConfiguration launcherConfiguration);

  /**
   * This method is executed by the launcher immediately before invoking
   * {@link _InitialGoalBuilder#buildInitialNodeSet(ProblemDescription)}.
   * 
   * @param prover the prover that will be invoked
   * @param initialGoal the goal
   * @param currentLauncherConfiguration the current launcher configuration
   */
  public void configProver(_Prover prover, _AbstractGoal initialGoal,
      LaunchConfiguration currentLauncherConfiguration);
}
