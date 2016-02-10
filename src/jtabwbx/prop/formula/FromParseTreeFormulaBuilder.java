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
package jtabwbx.prop.formula;

import java.util.Stack;

import jtabwb.util.ImplementationError;
import jtabwbx.prop.basic.PropositionalConnective;
import jtabwbx.prop.parser.FormulaBaseListener;
import jtabwbx.prop.parser.FormulaParser.AndContext;
import jtabwbx.prop.parser.FormulaParser.EqContext;
import jtabwbx.prop.parser.FormulaParser.FormulaContext;
import jtabwbx.prop.parser.FormulaParser.ImpContext;
import jtabwbx.prop.parser.FormulaParser.NegContext;
import jtabwbx.prop.parser.FormulaParser.OrContext;
import jtabwbx.prop.parser.FormulaParser.ParContext;
import jtabwbx.prop.parser.FormulaParser.PropContext;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

class FromParseTreeFormulaBuilder extends FormulaBaseListener {

  public FromParseTreeFormulaBuilder(FormulaFactory formulaFactory) {
    super();
    this.formulaFactory = formulaFactory;
  }

  private Stack<Formula> stack;
  private FormulaFactory formulaFactory;

  Formula buildFrom(ParseTree tree) {
    ParseTreeWalker walker = new ParseTreeWalker();

    // create listener then feed to walker
    walker.walk(this, tree); // walk parse tree
    if (stack.size() == 1) {
      Formula wff = stack.pop();
      stack = null;
      return wff;
    } else
      throw new ImplementationError("Something wrong in parse tree walking.");
  }

  @Override
  public void enterFormula(FormulaContext ctx) {
    stack = new Stack<Formula>();
  }

  @Override
  public void exitProp(PropContext ctx) {
    stack.push(formulaFactory.buildAtomic(ctx.getText()));
  }

  @Override
  public void exitAnd(AndContext ctx) {
    Formula right = stack.pop();
    Formula left = stack.pop();
    stack.push(formulaFactory.buildCompound(PropositionalConnective.AND, left, right));
  }

  @Override
  public void exitOr(OrContext ctx) {
    Formula right = stack.pop();
    Formula left = stack.pop();
    stack.push(formulaFactory.buildCompound(PropositionalConnective.OR, left, right));
  }

  @Override
  public void exitImp(ImpContext ctx) {
    Formula right = stack.pop();
    Formula left = stack.pop();
    stack.push(formulaFactory.buildCompound(PropositionalConnective.IMPLIES, left, right));
  }

  @Override
  public void exitEq(EqContext ctx) {
    Formula right = stack.pop();
    Formula left = stack.pop();
    stack.push(formulaFactory.buildCompound(PropositionalConnective.EQ, left, right));
  }

  @Override
  public void exitNeg(NegContext ctx) {
    Formula subf = stack.pop();
    stack.push(formulaFactory.buildCompound(PropositionalConnective.NOT, subf));
  }

  @Override
  public void exitPar(ParContext ctx) {
  }

}
