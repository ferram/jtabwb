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
 * An object of this class describes a problem of the ILTP-library. A problem
 * consists of a list of axioms (role {@link #ROLE_AXIOM}) and a conjecture
 * (role {@link #ROLE_CONJECTURE}).
 * 
 * @author Mauro Ferrari
 *
 */
public class ILTPProblem extends ProblemDescription {

  public final String ROLE_AXIOM = "axiom";
  public final String ROLE_CONJECTURE = "conjecture";

  ILTPProblem(String name, String inputSource) {
    super(name, inputSource);
  }

  ILTPProblem(String name) {
    this(name, null);
  }

  public void addConjecture(String wff) {
    super.add(ROLE_CONJECTURE, wff);
  }

  public void addAxiom(String wff) {
    super.add(ROLE_AXIOM, wff);
  }

  public String getConjecture() {
    return super.getFormulasByRole(ROLE_CONJECTURE).getFirst();
  };

  public LinkedList<String> getAxioms() {
    return super.getFormulasByRole(ROLE_AXIOM);
  }

}
