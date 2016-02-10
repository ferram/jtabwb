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

import jtabwbx.modal.basic.ModalConnective;
import jtabwbx.modal.basic.ModalFormulaType;

/**
 * @author Mauro Ferrari
 */
final class ModalFormulaDIA extends AbstractCompoundModalFormula {

  protected ModalFormulaDIA(ModalFormulaFactory formulaFactory, ModalFormula subFormula) {
    super(formulaFactory, ModalConnective.DIA, new ModalFormula[] { subFormula }, subFormula,
        AbstractCompoundModalFormula.NULL_FORMULA);
  }

  @Override
  protected int computeHashCode(ModalFormula[] subFormulas) {
    int result = mainConnective().hashCode();
    result = 31 * result + left.hashCode();
    result = 31 * result + 1;
    return result;
  }

  @Override
  public String toString() {
    String subf = "";
    if (left.isAtomic())
      subf = " " + left.toString();
    else {
      switch (left.mainConnective()) {
      case DIA:
      case BOX:
      case NOT:
        subf= "(" + left.toString() + ")";
        break;
      default:
        subf = left.toString();
      }
    }
    return mainConnective().toString() + subf;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    return left == ((ModalFormulaDIA) o).left;
  }

  @Override
  public ModalFormulaType getFormulaType() {
    return ModalFormulaType.DIA_WFF;
  }

}
