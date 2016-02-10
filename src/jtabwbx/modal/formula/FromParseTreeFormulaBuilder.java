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
package jtabwbx.modal.formula;

import java.util.Stack;

import jtabwb.util.ImplementationError;
import jtabwbx.modal.basic.ModalConnective;
import jtabwbx.modal.parser.ModalWffBaseListener;
import jtabwbx.modal.parser.ModalWffParser.AndContext;
import jtabwbx.modal.parser.ModalWffParser.BoxContext;
import jtabwbx.modal.parser.ModalWffParser.DiaContext;
import jtabwbx.modal.parser.ModalWffParser.EqContext;
import jtabwbx.modal.parser.ModalWffParser.ImpContext;
import jtabwbx.modal.parser.ModalWffParser.ModalFormulaContext;
import jtabwbx.modal.parser.ModalWffParser.NegContext;
import jtabwbx.modal.parser.ModalWffParser.OrContext;
import jtabwbx.modal.parser.ModalWffParser.ParContext;
import jtabwbx.modal.parser.ModalWffParser.PropContext;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

class FromParseTreeFormulaBuilder extends ModalWffBaseListener {

  public FromParseTreeFormulaBuilder(ModalFormulaFactory formulaFactory) {
    super();
    this.formulaFactory = formulaFactory;
  }

  private Stack<ModalFormula> stack;
  private ModalFormulaFactory formulaFactory;

  ModalFormula buildFrom(ParseTree tree) {
    ParseTreeWalker walker = new ParseTreeWalker();

    // create listener then feed to walker
    walker.walk(this, tree); // walk parse tree
    if (stack.size() == 1) {
      ModalFormula wff = stack.pop();
      stack = null;
      return wff;
    } else
      throw new ImplementationError("Something wrong in parse tree walking.");
  }

  @Override
  public void enterModalFormula(ModalFormulaContext ctx) {
    stack = new Stack<ModalFormula>();
  }

  @Override
  public void exitProp(PropContext ctx) {
    stack.push(formulaFactory.buildAtomic(ctx.getText()));
  }

  @Override
  public void exitAnd(AndContext ctx) {
    ModalFormula right = stack.pop();
    ModalFormula left = stack.pop();
    stack.push(formulaFactory.buildCompound(ModalConnective.AND, left, right));
  }

  @Override
  public void exitOr(OrContext ctx) {
    ModalFormula right = stack.pop();
    ModalFormula left = stack.pop();
    stack.push(formulaFactory.buildCompound(ModalConnective.OR, left, right));
  }

  @Override
  public void exitImp(ImpContext ctx) {
    ModalFormula right = stack.pop();
    ModalFormula left = stack.pop();
    stack.push(formulaFactory.buildCompound(ModalConnective.IMPLIES, left, right));
  }

  @Override
  public void exitEq(EqContext ctx) {
    ModalFormula right = stack.pop();
    ModalFormula left = stack.pop();
    stack.push(formulaFactory.buildCompound(ModalConnective.EQ, left, right));
  }

  @Override
  public void exitNeg(NegContext ctx) {
    ModalFormula subf = stack.pop();
    stack.push(formulaFactory.buildCompound(ModalConnective.NOT, subf));
  }

  
  @Override
  public void exitBox(BoxContext ctx) {
    ModalFormula subf = stack.pop();
    stack.push(formulaFactory.buildCompound(ModalConnective.BOX, subf));
  }
  
  
  @Override
  public void exitDia(DiaContext ctx) {
    ModalFormula subf = stack.pop();
    stack.push(formulaFactory.buildCompound(ModalConnective.DIA, subf));
  }
  
  @Override
  public void exitPar(ParContext ctx) {
  }

}
