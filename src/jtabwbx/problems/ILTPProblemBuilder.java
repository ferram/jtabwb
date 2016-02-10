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

import java.util.HashMap;

import jtabwb.engine.ProvabilityStatus;
import jtabwb.launcher.ProblemDescriptionException;
import jtabwb.util.CaseNotImplementedImplementationError;
import jtabwbx.problems.ILTProblemParser.Terminal;
import jtabwbx.prop.basic.PropositionalConnective;
import jtabwbx.prop.formula.Formula;
import jtabwbx.prop.formula.FormulaFactory;

class ILTPProblemBuilder {

  public ILTPProblemBuilder() {
    this.axioms = new HashMap<String, Formula>();
    this.conjectures = new HashMap<String, Formula>();
    this.formulaFactory = new FormulaFactory();
  }

  private FormulaFactory formulaFactory ;
  private String problemSource;
  private String problemName;
  private ProvabilityStatus problemStatus;
  private HashMap<String, Formula> axioms;
  private HashMap<String, Formula> conjectures; /*
                                                 * By now only one conjecture is
                                                 * admitted
                                                 */

  ILTPProblem build() throws ProblemDescriptionException {
    ILTPProblem pd = new ILTPProblem(problemName, problemSource);
    pd.setProblemStatus(problemStatus);
    if (!axioms.isEmpty())
      for (Formula formula : axioms.values())
        pd.addAxiom(formula.format());

    Formula conjecture = null;
    if (conjectures.isEmpty())
      throw new ProblemDescriptionException(ILTPProblemReader_MSG.PROBLEM_BUILDER.BUILD_ERROR_NO_CONJECTURE);

    if (conjectures.size() > 1)
      throw new ProblemDescriptionException(ILTPProblemReader_MSG.PROBLEM_BUILDER.BUILD_ERROR_MORE_CONJECTURES);

    conjecture = conjectures.values().toArray(new Formula[1])[0];
    pd.addConjecture(conjecture.format());
    return pd;
  }

  public void addFormula(Terminal formulaRole, String name, Formula formula) {
    switch (formulaRole) {
    case KEY_AXIOM:
      this.axioms.put(name, formula);
      break;
    case KEY_CONJECTURE:
      this.conjectures.put(name, formula);
      break;
    default:
      throw new CaseNotImplementedImplementationError(formulaRole.name());
    }
  }

  public Formula buildAtomic(String name) {
    return formulaFactory.buildAtomic(name);
  }

  public Formula buildUnary(Terminal operator, Formula subf) {
    PropositionalConnective mainConnective = null;
    switch (operator) {
    case OP_NOT:
      mainConnective = PropositionalConnective.NOT;
      break;
    default:
      throw new CaseNotImplementedImplementationError(operator.name());
    }
    Formula formula = formulaFactory.buildCompound(mainConnective, subf);
    return formula;
  }

  public Formula buildBinary(Terminal operator, Formula subf1, Formula subf2) {
    PropositionalConnective mainConnective = null;
    switch (operator) {
    case OP_AND:
      mainConnective = PropositionalConnective.AND;
      break;
    case OP_OR:
      mainConnective = PropositionalConnective.OR;
      break;
    case OP_IMPLIES:
      mainConnective = PropositionalConnective.IMPLIES;
      break;
    case OP_EQ:
      mainConnective = PropositionalConnective.EQ;
      break;
    default:
      throw new CaseNotImplementedImplementationError(operator.name());
    }
    Formula formula = formulaFactory.buildCompound(mainConnective, new Formula[] { subf1, subf2 });
    return formula;
  }

  /**
   * @param formulaFactory the formulaFactory to set
   */
  public void setFormulaFactory(FormulaFactory formulaFactory) {
    this.formulaFactory = formulaFactory;
  }

  /**
   * @param problemSource the problemSource to set
   */
  public void setProblemSource(String problemSource) {
    this.problemSource = problemSource;
  }

  /**
   * @param problemName the problemName to set
   */
  public void setProblemName(String problemName) {
    this.problemName = problemName;
  }

  /**
   * @param problemStatus the problemStatus to set
   */
  public void setProblemStatus(ProvabilityStatus problemStatus) {
    this.problemStatus = problemStatus;
  }

  /**
   * @param problemStatus the problemStatus to set
   */
  public void setProblemStatus(String problemStatus) throws ProblemDescriptionException {
    if (problemStatus.equals("Theorem"))
      this.problemStatus = ProvabilityStatus.PROVABLE;
    else if (problemStatus.equals("Non-Theorem"))
      this.problemStatus = ProvabilityStatus.UNPROVABLE;
    else if (problemStatus.equals("Unsolved"))
      this.problemStatus = ProvabilityStatus.UNKNOWN;
    else
      throw new ProblemDescriptionException(String.format(ILTPProblemReader_MSG.PROBLEM_BUILDER.UNKONW_STATUS_STRING,
          problemStatus));
  }

}
