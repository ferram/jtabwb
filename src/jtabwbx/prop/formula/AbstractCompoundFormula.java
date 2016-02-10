/*******************************************************************************
 * Copyright (C) 2013, 2016 Mauro Ferrari This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this program; if not, see
 * <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package jtabwbx.prop.formula;

import java.util.HashSet;

import jtabwb.util.CaseNotImplementedImplementationError;
import jtabwbx.prop.basic.PropositionalConnective;

/**
 * Abstract supertype for formula implementation.
 * 
 * @author Lorenzo Bossi
 */
abstract class AbstractCompoundFormula extends Formula {

  private HashSet<FormulaProposition> propositions;
  private HashSet<Formula> formulae;
  private Formula booleanSimplifiedVersion;

  private final int hash;
  private boolean isLocal;
  private PropositionalConnective mainConnective;

  protected final FormulaFactory formulaFactory;
  protected final Formula[] subFormulas;
  protected final Formula left;
  protected final Formula right;

  protected AbstractCompoundFormula(FormulaFactory formulaFactory,
      PropositionalConnective mainConnective, Formula[] subFormulas, Formula left, Formula right) {
    this.formulaFactory = formulaFactory;
    this.mainConnective = mainConnective;
    this.subFormulas = subFormulas;
    this.left = left;
    this.right = right;
    this.propositions = null;
    this.booleanSimplifiedVersion = null;
    this.hash = computeHashCode(subFormulas);

    switch (mainConnective) {
    case AND:
      this.isLocal = left.isIntuitionisticLocalFormula() || right.isIntuitionisticLocalFormula();
      this.size = left.size + right.size + 1;
      break;
    case OR:
      isLocal = left.isIntuitionisticLocalFormula() && right.isIntuitionisticLocalFormula();
      this.size = left.size + right.size + 1;
      break;
    case IMPLIES:
    case EQ:
      isLocal = false;
      this.size = left.size + right.size + 1;
      break;
    case NOT:
      isLocal = false;
      this.size = left.size + 1;
      break;
    default:
      throw new CaseNotImplementedImplementationError(mainConnective.toString());
    }

  }

  @Override
  public final FormulaFactory getFactory() {
    return formulaFactory;
  }

  @Override
  public PropositionalConnective mainConnective() {
    return mainConnective;
  }

  @Override
  public String shortName() {
    return mainConnective.getName() + "-formula";
  }

  private void updateHashSetsIfNeeded() {
    if (propositions == null) {
      propositions = new HashSet<FormulaProposition>();
      formulae = new HashSet<Formula>();
      for (Formula sub : subFormulas) {
        if (sub instanceof FormulaProposition) {
          FormulaProposition f = (FormulaProposition) sub;
          propositions.add(f);
        } else {
          AbstractCompoundFormula subCompound = (AbstractCompoundFormula) sub;
          subCompound.updateHashSetsIfNeeded();
          propositions.addAll(subCompound.propositions);
          formulae.add(subCompound);
          formulae.addAll(subCompound.formulae);
        }
      }
    }
  }

  @Override
  public Formula[] immediateSubformulas() {
    return subFormulas;
  }

  @Override
  public final boolean isAtomic() {
    return false;
  }

  @Override
  public final boolean isCompound() {
    return true;
  }

  @Override
  public final boolean isFalse() {
    return false;
  }

  @Override
  public final boolean isTrue() {
    return false;
  }

  @Override
  public final boolean isIntuitionisticLocalFormula() {
    return this.isLocal;
  }

  @Override
  public final int hashCode() {
    return hash;
  }

  private boolean containSubFormula(Substitution subst) {
    updateHashSetsIfNeeded();
    boolean contain = false;
    for (Formula key : subst.keySet()) {
      contain = contain || formulae.contains(key) || propositions.contains(key);
    }
    return contain;
  }

  private boolean containProposition(PropositionalSubstitution subst) {
    updateHashSetsIfNeeded();
    boolean contain = false;
    for (FormulaProposition key : subst.keySet()) {
      contain = contain || propositions.contains(key);
    }
    return contain;
  }

  @Override
  public final boolean containsProposition(FormulaProposition proposition) {
    updateHashSetsIfNeeded();
    return propositions.contains(proposition);
  }

  @Override
  public final boolean containsTrue() {
    return containsProposition((FormulaProposition) formulaFactory.getTrue());
  }

  @Override
  public final boolean containsFalse() {
    return containsProposition((FormulaProposition) formulaFactory.getFalse());
  }

  /**
   * Computes a boolean simplification on this formula. This method performs
   * caching and optimisation, overrides @see(computeBooleanSimplification).
   * 
   * @return the simplified formula.
   */
  @Override
  public final Formula calculateBooleanSimplification() {
    if (booleanSimplifiedVersion != null)
      return booleanSimplifiedVersion;

    updateHashSetsIfNeeded();
    if (!(propositions.contains(formulaFactory.getTrue()) || propositions.contains(formulaFactory
        .getFalse())))
      booleanSimplifiedVersion = this;
    else
      booleanSimplifiedVersion = computeBooleanSimplification();
    return booleanSimplifiedVersion;
  }

  /**
   * Tests class equality and commutative equality on two subformulas. Redefines
   * if subclass uses unary or not commutative operator.
   * 
   * @param o
   * @return true iff <code>o</code> is a formula and is equals to
   * <code>this</code>.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    AbstractCompoundFormula that = (AbstractCompoundFormula) o;

    boolean pair = left == that.left && right == that.right;
    return pair || left == that.right && right == that.left;
  }

  @Override
  public final Formula applySubstitution(Substitution subst) {
    Formula newF = subst.get(this);

    if (newF != null)
      return newF;

    if (!containSubFormula(subst))
      return this;

    newF =
        formulaFactory.buildCompound(mainConnective(), left.applySubstitution(subst),
            right.applySubstitution(subst));

    return newF;
  }

  @Override
  public final Formula applySubstitution(PropositionalSubstitution subst) {
    if (!containProposition(subst))
      return this;

    Formula newF =
        formulaFactory.buildCompound(mainConnective(), left.applySubstitution(subst),
            right.applySubstitution(subst));

    return newF;
  }

  @Override
  public final Formula applyIntuitionisticPartialSubstitution(Substitution subst) {
    Formula newF = subst.get(this);

    if (newF != null)
      return newF;

    newF = computePartialSubstitution(subst);
    return newF;
  }

  @Override
  public String format() {
    return this.toString();
  }

  @Override
  public String toString() {
    String formula = left.toString() + " " + mainConnective().toString() + " " + right.toString();
    return "(" + formula + ")";
  }

  /**
   * Computes hash code of commutative binary operator, redefine in subclass if
   * use other operator type. NB: since this method is called only in the
   * constructor and return value is cached, this method MUST NOT use any field
   * because they aren't initialized yet.
   * 
   * @param subFormulas subformualas
   * @return the hash code of this object
   */
  protected int computeHashCode(Formula[] subFormulas) {
    int result = mainConnective().hashCode();
    int leftHash = this.subFormulas[0].hashCode();
    int rightHash = this.subFormulas[1].hashCode();
    result = 31 * result + Math.min(leftHash, rightHash);
    result = 31 * result + Math.max(leftHash, rightHash);
    return result;
  }

  abstract Formula computeBooleanSimplification();

  protected abstract Formula computePartialSubstitution(Substitution subst);
}
