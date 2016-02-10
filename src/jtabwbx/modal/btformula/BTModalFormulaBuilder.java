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
package jtabwbx.modal.btformula;

import java.util.Stack;

import jtabwb.util.ImplementationError;
import jtabwbx.modal.basic.ModalConnective;
import jtabwbx.modal.parser.ModalWffBaseListener;
import jtabwbx.modal.parser.ModalWffParser;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

class BTModalFormulaBuilder extends ModalWffBaseListener {

  private Stack<BTModalFormula> stack;

  BTModalFormula buildFrom(ParseTree tree) {
    ParseTreeWalker walker = new ParseTreeWalker();

    // create listener then feed to walker
    walker.walk(this, tree); // walk parse tree
    if (stack.size() == 1) {
      BTModalFormula wff = stack.pop();
      stack = null;
      return wff;
    } else
      throw new ImplementationError();
  }

  @Override
  public void enterModalFormula(ModalWffParser.ModalFormulaContext ctx) {
    stack = new Stack<BTModalFormula>();
  }

  @Override
  public void exitProp(ModalWffParser.PropContext ctx) {
    stack.push(new BTModalFormulaProposition(ctx.getText()));
  }

  @Override
  public void exitAnd(ModalWffParser.AndContext ctx) {
    BTModalFormula right = stack.pop();
    BTModalFormula left = stack.pop();
    BTModalFormula and = new BTModalFormulaCompound(ModalConnective.AND, left, right);
    stack.push(and);
  }

  @Override
  public void exitOr(ModalWffParser.OrContext ctx) {
    BTModalFormula right = stack.pop();
    BTModalFormula left = stack.pop();
    BTModalFormula or = new BTModalFormulaCompound(ModalConnective.OR, left, right);
    stack.push(or);
  }

  @Override
  public void exitImp(ModalWffParser.ImpContext ctx) {
    BTModalFormula right = stack.pop();
    BTModalFormula left = stack.pop();
    BTModalFormula imp = new BTModalFormulaCompound(ModalConnective.IMPLIES, left, right);
    stack.push(imp);
  }

  @Override
  public void exitEq(ModalWffParser.EqContext ctx) {
    BTModalFormula right = stack.pop();
    BTModalFormula left = stack.pop();
    BTModalFormula eq = new BTModalFormulaCompound(ModalConnective.EQ, left, right);
    stack.push(eq);
  }

  @Override
  public void exitNeg(ModalWffParser.NegContext ctx) {
    BTModalFormula subwff = stack.pop();
    BTModalFormula not = new BTModalFormulaCompound(ModalConnective.NOT, subwff);
    stack.push(not);
  }

  @Override
  public void exitDia(ModalWffParser.DiaContext ctx) {
    BTModalFormula subwff = stack.pop();
    BTModalFormula dia = new BTModalFormulaCompound(ModalConnective.DIA, subwff);
    stack.push(dia);
  }

  @Override
  public void exitBox(ModalWffParser.BoxContext ctx) {
    BTModalFormula subwff = stack.pop();
    BTModalFormula box = new BTModalFormulaCompound(ModalConnective.BOX, subwff);
    stack.push(box);
  }

  @Override
  public void exitPar(ModalWffParser.ParContext ctx) {
  }

}
