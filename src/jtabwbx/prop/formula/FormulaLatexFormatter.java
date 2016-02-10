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
package jtabwbx.prop.formula;

import java.util.Collection;

import jtabwb.util.CaseNotImplementedImplementationError;


/**
 * Provides a static method to generate the LaTeX of a formula.
 * 
 * @author Mauro Ferrari
 */
public class FormulaLatexFormatter {

  //TODO: definire opzioni per gestione abreviazioni
  private boolean abbr_negation = true;

  /**
   * Returns the LaTex source of the formulas in the given array; formulas are
   * separated by the specified separator. The result of this method must placed
   * in math mode. The method returns the empty string if <code>formulas</code>
   * is <code>null</code> or empty.
   * 
   * @param formulas the formulas to translate.
   * @param separator the string to use as separator.
   * @return the LaTeX source of the formulas.
   */
  public String toLatex(Formula[] formulas, String separator) {
    String str = "";
    if (formulas == null)
      return "";

    for (int i = 0; i < formulas.length; i++)
      str += toLatex(formulas[i]) + (i < formulas.length - 1 ? separator : "");
    return str;
  }

  /**
   * Returns the LaTex source of the formulas in the given collection; formulas
   * are separated by the specified separator. The result of this method must
   * placed in math mode. The method returns the empty string if
   * <code>formulas</code> is <code>null</code> or empty.
   * 
   * @param formulas the formulas to translate.
   * @param separator the string to use as separator.
   * @return the LaTeX source of the formulas.
   */
  public String toLatex(Collection<Formula> formulas, String separator) {
    if (formulas == null || formulas.size() == 0)
      return "";
    else
      return toLatex(formulas.toArray(new Formula[formulas.size()]), separator);
  }

  /**
   * Returns the LaTex source of the specified formula, the result of this
   * method must placed in math mode.
   * 
   * @param wff the formula to translate.
   * @return the LaTeX source of the formula.
   */
  public String toLatex(Formula wff) {
    return _toLatex(wff, false);
  }

  public String _toLatex(Formula wff, boolean parenthesize) {
    if (wff.isAtomic()) {
      parenthesize = false;
      FormulaProposition prop = (FormulaProposition) wff;
      if (prop.isFalse())
        return "\\bot";
      if (prop.isTrue())
        return "\\top";
      return correctName(wff.format());
    } else {
      String wffStr;
      Formula[] subwffs = wff.immediateSubformulas();
      switch (wff.mainConnective()) {
      case AND:
        wffStr = buildBinary(subwffs, " \\land ");
        break;
      case OR:
        wffStr = buildBinary(subwffs, " \\lor ");
        break;
      case NOT:
        wffStr = " \\neg " + _toLatex(subwffs[0], true);
        parenthesize = false;
        break;
      case IMPLIES:
        if (abbr_negation && subwffs[1].isFalse()) {
          wffStr = " \\neg " + _toLatex(subwffs[0], true);
          parenthesize = false;
        } else
          wffStr = buildBinary(subwffs, " \\to ");
        break;
      default:
        throw new CaseNotImplementedImplementationError(wff.mainConnective().getName());
      }
      if (parenthesize)
        return "(" + wffStr + ")";
      else
        return wffStr;
    }
  }

  private String buildBinary(Formula[] subwffs, String connective) {
    String str = "";
    for (int i = 0; i < subwffs.length; i++)
      str += _toLatex(subwffs[i], true) + (i < subwffs.length - 1 ? connective : " ");
    return str;
  }

  private static String[][] replacement = new String[][] { { "_", "\\\\_" }, //
      { "\\$", "\\$" } };

  private static String correctName(String name) {
    String result = name;
    for (int i = 0; i < replacement.length; i++)
      result = result.replaceAll(replacement[i][0], replacement[i][1]);
    return result;
  }

}
