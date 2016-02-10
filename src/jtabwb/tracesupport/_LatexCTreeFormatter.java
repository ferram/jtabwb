/*******************************************************************************
 * Copyright (C) 2013, 2015 Mauro Ferrari
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
package jtabwb.tracesupport;

import jtabwb.engine._AbstractRule;
import jtabwb.engine._AbstractGoal;

/**
 * An object implementing this interface provides to a LaTeX generator the
 * methods needed to construct a LaTeX representation of a C-Tree.
 * 
 * @author Mauro Ferrari
 */
public interface _LatexCTreeFormatter {

  /**
   * Returns the proof style to use.
   * 
   * @return the proof style to use.
   */
  public LatexTranslator.ProofStyle proofStyle();

  /**
   * Returns the text to add to the preamble of the LaTeX file; in general it
   * contains local macro definitions.
   * 
   * @return the text to add to the preamle.
   */
  public String getPreamble();

  /**
   * Returns the text to add at the beginning of the LaTeX document; this text
   * is added immediately after "<code>\begin{document}</code>".
   * 
   * @return the intro.
   */
  public String getIntro();

  /**
   * Returns the LaTeX translation of the spcified node set.
   * 
   * @param node the node set to translate.
   * @return the LaTeX representation of the node set.
   */
  public String format(_AbstractGoal node);

  /**
   * Returns the LaTeX translation of the name of the rule.
   * 
   * @param rule the rule.
   * @return the LaTeX representation of name of the rule.
   */
  public String formatRuleName(_AbstractRule rule);

  /**
   * If it returns <code>true</code> the generator will index the node-sets
   * occurring in the C-Tree
   * 
   * @return <code>true</code> if the node-sets must be numbered.
   */
  public boolean generateNodeSetIndex();

  /**
   * If it returns <code>true</code> the generator will index the rule
   * applications occurring in the C-Tree
   * 
   * @return <code>true</code> if the node-sets must be numbered.
   */
  public boolean generateRuleIndex();

  /**
   * If it returns <code>true</code> the generator will print details about
   * failed goals of C-tree.
   * 
   * @return <code>true</code> details about failed goals will be printed.
   */
  public boolean generateFailureGoalAnnotations();

  /**
   * The LaTeX to insert before c-tree source or <code>null</code> if no text is
   * to be inserted.
   * 
   * @param ctree the c-tree.
   * @return the text to insert before C-tree source or <code>null</code> if no
   * text is to be inserted.
   */
  public String pre(CTree ctree);

  /**
   * The LaTeX to insert after c-tree source.
   * 
   * @param ctree the c-tree
   * @return the text to insert after C-tree source or <code>null</code> if no
   * text is to be inserted..
   */
  public String post(CTree ctree);

}
