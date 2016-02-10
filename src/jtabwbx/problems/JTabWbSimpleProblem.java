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
package jtabwbx.problems;

import java.util.LinkedList;

import jtabwb.launcher.ProblemDescription;

/**
 * Pitp problem description only contains one conjecture, the propositional
 * formula to prove.
 * 
 * @author Mauro Ferrari
 * 
 */
public class JTabWbSimpleProblem extends ProblemDescription {

  public JTabWbSimpleProblem() {
    super();
  }

  public JTabWbSimpleProblem(String name, String inputSource) {
    super(name, inputSource);
  }

  public JTabWbSimpleProblem(String name) {
    super(name);
  }

  public static String ROLE_CONJECTURE = "conjecture";

  public void addConjecture(String wff) {
    super.add(ROLE_CONJECTURE, wff);
  }

  public String getConjecture() {
    return super.getFormulasByRole(ROLE_CONJECTURE).getFirst();
  }

}
