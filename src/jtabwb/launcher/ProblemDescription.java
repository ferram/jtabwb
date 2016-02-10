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

import java.util.HashMap;
import java.util.LinkedList;

import jtabwb.engine.ProvabilityStatus;

/**
 * The description of a logical problem. It consists of some data identifying
 * the problem (problem name and source), a provability status (provable,
 * unprovable, unknown) and a logical specification. The logical specification
 * is given by a set of formulas (represented as strings) identified by a role.
 * As an example, a problem can be specified by a list of formulas having the
 * role "axioms" and a formula having the role "conjecture".
 * 
 * @author Mauro Ferrari
 */
public class ProblemDescription {

  private String problemName;
  private HashMap<String, LinkedList<String>> formulasByRole;
  private LinkedList<String> roles;
  private ProvabilityStatus status;
  private String source;

  public ProblemDescription(String name, String inputSource) {
    super();
    this.problemName = name;
    this.source = inputSource;
    this.formulasByRole = new HashMap<String, LinkedList<String>>();
    this.roles = new LinkedList<String>();
  }

  public ProblemDescription(String name) {
    this(name, null);
  }

  /**
   * Builds an empty problem description.
   */
  public ProblemDescription() {
    this(null, null);
  }

  /**
   * Returns the list of the formulas in this problem description with the
   * specified role or <code>null</code> if formula with the specified role is
   * defined for this problem.
   * 
   * @param role the role of the formulas to be returned.
   * @return the collection of formulas with the specified role or
   * <code>null</code>.
   */
  public LinkedList<String> getFormulasByRole(String role) {
    return formulasByRole.get(role);
  }

  /**
   * Returns the roles for this problem description.
   * 
   * @return the roles for this problem description.
   */
  public LinkedList<String> getRoles() {
    return roles;
  }

  /**
   * Add to the problem description a formula with the specified role.
   * 
   * @param role the role of the formula
   * @param formula the formula
   */
  public void add(String role, String formula) {
    LinkedList<String> formulas;
    if (roles.contains(role)) {
      formulas = this.formulasByRole.get(role);
      formulas.add(formula);
    } else {
      roles.add(role);
      formulas = new LinkedList<String>();
      this.formulasByRole.put(role, formulas);
      formulas.add(formula);
    }
  }

  /**
   * @return the problem name
   */
  public String getProblemName() {
    return this.problemName;
  }

  /**
   * @param name the problem name
   */
  public void setName(String name) {
    this.problemName = name;
  }

  /**
   * @return the provability status of the problem
   */
  public ProvabilityStatus getProblemStatus() {
    return this.status;
  }

  /**
   * @param status the provabilty status of the problem
   */
  public void setProblemStatus(ProvabilityStatus status) {
    this.status = status;
  }

  /**
   * @return the source
   */
  public String getSource() {
    return this.source;
  }

  /**
   * @param source the source to set
   */
  public void setSource(String source) {
    this.source = source;
  }

  @Override
  public String toString() {
    return "ProblemDescription [name=" + this.problemName + this.source + "]";
  }

}
