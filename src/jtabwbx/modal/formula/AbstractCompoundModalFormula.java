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
package jtabwbx.modal.formula;

import java.util.HashSet;

import jtabwbx.modal.basic.ModalConnective;
import jtabwbx.modal.basic.ModalFormulaType;

/**
 * Abstract supertype for formula implementation.
 * 
 * @author Mauro Ferrari
 */
abstract class AbstractCompoundModalFormula extends ModalFormula {

  /* Null formula, used to unify unary and binary formulae methods */
  static final ModalFormula NULL_FORMULA = new ModalFormula() {

    @Override
    public int getIndex() {
      return -1;
    }

    @Override
    public boolean containsTrue() {
      return false;
    }

    @Override
    public boolean containsFalse() {
      return false;
    }

    @Override
    public boolean containsProposition(ModalFormulaProposition name) {
      return false;
    }

    @Override
    public boolean isAtomic() {
      return false;
    }

    @Override
    public boolean isCompound() {
      return false;
    }

    @Override
    public ModalFormulaType getFormulaType() {
      return null;
    }

    @Override
    public ModalConnective mainConnective() {
      return null;
    }

    @Override
    public ModalFormula[] immediateSubformulas() {
      return null;
    }

    @Override
    public String shortName() {
      return "NullFormula";
    }

    @Override
    public String format() {
      return this.toString();
    }

    @Override
    public String toString() {
      return "NullFormula, used to unify unary and binary formulae methods";
    }

    @Override
    public boolean isFalse() {
      return false;
    }

    @Override
    public boolean isTrue() {
      return false;
    }

    @Override
    public ModalFormulaFactory getFactory() {
      return null;
    }

  };

  private HashSet<ModalFormulaProposition> propositions;
  private HashSet<ModalFormula> formulae;

  private final int hash;
  private ModalConnective mainConnective;

  protected final ModalFormulaFactory formulaFactory;
  protected final ModalFormula[] subFormulas;
  protected final ModalFormula left;
  protected final ModalFormula right;

  protected AbstractCompoundModalFormula(ModalFormulaFactory formulaFactory,
      ModalConnective mainConnective, ModalFormula[] subFormulas, ModalFormula left,
      ModalFormula right) {
    this.formulaFactory = formulaFactory;
    this.mainConnective = mainConnective;
    this.subFormulas = subFormulas;
    this.left = left;
    this.right = right;
    this.propositions = null;
    this.hash = computeHashCode(subFormulas);

  }

  @Override
  public final ModalFormulaFactory getFactory() {
    return formulaFactory;
  }

  public ModalConnective mainConnective() {
    return mainConnective;
  }

  public String shortName() {
    return mainConnective.getName() + "-formula";
  }

  private void updateHashSetsIfNeeded() {
    if (propositions == null) {
      propositions = new HashSet<ModalFormulaProposition>();
      formulae = new HashSet<ModalFormula>();
      for (ModalFormula sub : subFormulas) {
        if (sub instanceof ModalFormulaProposition) {
          ModalFormulaProposition f = (ModalFormulaProposition) sub;
          propositions.add(f);
        } else {
          AbstractCompoundModalFormula subCompound = (AbstractCompoundModalFormula) sub;
          subCompound.updateHashSetsIfNeeded();
          propositions.addAll(subCompound.propositions);
          formulae.add(subCompound);
          formulae.addAll(subCompound.formulae);
        }
      }
    }
  }

  @Override
  public ModalFormula[] immediateSubformulas() {
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
  public final int hashCode() {
    return hash;
  }

  @Override
  public final boolean containsProposition(ModalFormulaProposition proposition) {
    updateHashSetsIfNeeded();
    return propositions.contains(proposition);
  }

  @Override
  public final boolean containsTrue() {
    return containsProposition((ModalFormulaProposition) formulaFactory.getTrue());
  }

  @Override
  public final boolean containsFalse() {
    return containsProposition((ModalFormulaProposition) formulaFactory.getFalse());
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

    AbstractCompoundModalFormula that = (AbstractCompoundModalFormula) o;

    boolean pair = left == that.left && right == that.right;
    return pair || left == that.right && right == that.left;
  }

  @Override
  public String format() {
    return this.toString();
  }

  @Override
  public String toString() {
    String formula = left.toString() + " " + mainConnective().toString() +" " + right.toString();
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
  protected int computeHashCode(ModalFormula[] subFormulas) {
    int result = mainConnective().hashCode();
    int leftHash = this.subFormulas[0].hashCode();
    int rightHash = this.subFormulas[1].hashCode();
    result = 31 * result + Math.min(leftHash, rightHash);
    result = 31 * result + Math.max(leftHash, rightHash);
    return result;
  }

}
