// Generated from ModalWff.g4 by ANTLR 4.5

  package jtabwbx.modal.parser; 

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ModalWffParser}.
 */
public interface ModalWffListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ModalWffParser#modalFormula}.
	 * @param ctx the parse tree
	 */
	void enterModalFormula(ModalWffParser.ModalFormulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModalWffParser#modalFormula}.
	 * @param ctx the parse tree
	 */
	void exitModalFormula(ModalWffParser.ModalFormulaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Par}
	 * labeled alternative in {@link ModalWffParser#wff}.
	 * @param ctx the parse tree
	 */
	void enterPar(ModalWffParser.ParContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Par}
	 * labeled alternative in {@link ModalWffParser#wff}.
	 * @param ctx the parse tree
	 */
	void exitPar(ModalWffParser.ParContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Neg}
	 * labeled alternative in {@link ModalWffParser#wff}.
	 * @param ctx the parse tree
	 */
	void enterNeg(ModalWffParser.NegContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Neg}
	 * labeled alternative in {@link ModalWffParser#wff}.
	 * @param ctx the parse tree
	 */
	void exitNeg(ModalWffParser.NegContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Or}
	 * labeled alternative in {@link ModalWffParser#wff}.
	 * @param ctx the parse tree
	 */
	void enterOr(ModalWffParser.OrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Or}
	 * labeled alternative in {@link ModalWffParser#wff}.
	 * @param ctx the parse tree
	 */
	void exitOr(ModalWffParser.OrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Prop}
	 * labeled alternative in {@link ModalWffParser#wff}.
	 * @param ctx the parse tree
	 */
	void enterProp(ModalWffParser.PropContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Prop}
	 * labeled alternative in {@link ModalWffParser#wff}.
	 * @param ctx the parse tree
	 */
	void exitProp(ModalWffParser.PropContext ctx);
	/**
	 * Enter a parse tree produced by the {@code And}
	 * labeled alternative in {@link ModalWffParser#wff}.
	 * @param ctx the parse tree
	 */
	void enterAnd(ModalWffParser.AndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code And}
	 * labeled alternative in {@link ModalWffParser#wff}.
	 * @param ctx the parse tree
	 */
	void exitAnd(ModalWffParser.AndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Box}
	 * labeled alternative in {@link ModalWffParser#wff}.
	 * @param ctx the parse tree
	 */
	void enterBox(ModalWffParser.BoxContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Box}
	 * labeled alternative in {@link ModalWffParser#wff}.
	 * @param ctx the parse tree
	 */
	void exitBox(ModalWffParser.BoxContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Eq}
	 * labeled alternative in {@link ModalWffParser#wff}.
	 * @param ctx the parse tree
	 */
	void enterEq(ModalWffParser.EqContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Eq}
	 * labeled alternative in {@link ModalWffParser#wff}.
	 * @param ctx the parse tree
	 */
	void exitEq(ModalWffParser.EqContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Dia}
	 * labeled alternative in {@link ModalWffParser#wff}.
	 * @param ctx the parse tree
	 */
	void enterDia(ModalWffParser.DiaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Dia}
	 * labeled alternative in {@link ModalWffParser#wff}.
	 * @param ctx the parse tree
	 */
	void exitDia(ModalWffParser.DiaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Imp}
	 * labeled alternative in {@link ModalWffParser#wff}.
	 * @param ctx the parse tree
	 */
	void enterImp(ModalWffParser.ImpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Imp}
	 * labeled alternative in {@link ModalWffParser#wff}.
	 * @param ctx the parse tree
	 */
	void exitImp(ModalWffParser.ImpContext ctx);
}