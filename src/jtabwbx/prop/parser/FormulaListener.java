// Generated from Formula.g4 by ANTLR 4.5

  package jtabwbx.prop.parser; 

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FormulaParser}.
 */
public interface FormulaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FormulaParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterFormula(FormulaParser.FormulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormulaParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitFormula(FormulaParser.FormulaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Par}
	 * labeled alternative in {@link FormulaParser#wff}.
	 * @param ctx the parse tree
	 */
	void enterPar(FormulaParser.ParContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Par}
	 * labeled alternative in {@link FormulaParser#wff}.
	 * @param ctx the parse tree
	 */
	void exitPar(FormulaParser.ParContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Neg}
	 * labeled alternative in {@link FormulaParser#wff}.
	 * @param ctx the parse tree
	 */
	void enterNeg(FormulaParser.NegContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Neg}
	 * labeled alternative in {@link FormulaParser#wff}.
	 * @param ctx the parse tree
	 */
	void exitNeg(FormulaParser.NegContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Or}
	 * labeled alternative in {@link FormulaParser#wff}.
	 * @param ctx the parse tree
	 */
	void enterOr(FormulaParser.OrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Or}
	 * labeled alternative in {@link FormulaParser#wff}.
	 * @param ctx the parse tree
	 */
	void exitOr(FormulaParser.OrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Prop}
	 * labeled alternative in {@link FormulaParser#wff}.
	 * @param ctx the parse tree
	 */
	void enterProp(FormulaParser.PropContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Prop}
	 * labeled alternative in {@link FormulaParser#wff}.
	 * @param ctx the parse tree
	 */
	void exitProp(FormulaParser.PropContext ctx);
	/**
	 * Enter a parse tree produced by the {@code And}
	 * labeled alternative in {@link FormulaParser#wff}.
	 * @param ctx the parse tree
	 */
	void enterAnd(FormulaParser.AndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code And}
	 * labeled alternative in {@link FormulaParser#wff}.
	 * @param ctx the parse tree
	 */
	void exitAnd(FormulaParser.AndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Eq}
	 * labeled alternative in {@link FormulaParser#wff}.
	 * @param ctx the parse tree
	 */
	void enterEq(FormulaParser.EqContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Eq}
	 * labeled alternative in {@link FormulaParser#wff}.
	 * @param ctx the parse tree
	 */
	void exitEq(FormulaParser.EqContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Imp}
	 * labeled alternative in {@link FormulaParser#wff}.
	 * @param ctx the parse tree
	 */
	void enterImp(FormulaParser.ImpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Imp}
	 * labeled alternative in {@link FormulaParser#wff}.
	 * @param ctx the parse tree
	 */
	void exitImp(FormulaParser.ImpContext ctx);
}