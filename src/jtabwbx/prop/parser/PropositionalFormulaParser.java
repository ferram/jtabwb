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
package jtabwbx.prop.parser;

import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * A parser for propositional formulas. Formulas must be defined as follows:
 * <ul>
 * <li><b>Propositional variables</b>.
 * <code>["_","a"-"z","A"-"Z"] | (["_","a"-"z","A"-"Z"] | ["0"-"9"])* </code></li>
 * <li><b>Logical constants</b>. Listed in order of precedence (from highest to
 * lowest):
 * <ul>
 * <li><code>true</code>, <code>false</code></li>
 * <li><code>~</code> (not): prefix, unary</li>
 * <li><code>&amp;</code> (and): infix, binary</li>
 * <li><code>|</code> (or): infix, binary</li>
 * <li><code>&lt;=&gt;</code> (iff): infix, binary</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * @author Mauro Ferrari
 */
public class PropositionalFormulaParser {

  private final String name = "Propositional_formula_parser";

  /**
   * Constructs a parser.
   */
  public PropositionalFormulaParser() {
  }

  /**
   * Parses the string given as argument and returns the corresponding
   * {@link ParseTree}.
   * 
   * @param str the string describing the formula.
   * @return the parse tree.
   * @throws FormulaParseException if an error occurs during parsing.
   */
  public ParseTree parse(String str) throws FormulaParseException {
    try {
      FormulaLexerSignalingErrors lexer =
          new FormulaLexerSignalingErrors(new ANTLRInputStream(str));
      lexer.removeErrorListeners();
      lexer.addErrorListener(new ErrorListener());
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      FormulaParser parser = new FormulaParser(tokens);
      parser.removeErrorListeners();
      parser.addErrorListener(new ErrorListener());
      parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
      ParseTree tree = parser.formula();
      return tree;
    } catch (ParserError e) {
      throw new FormulaParseException(e.getMessage());
    }
  }

  /**
   * Returns the parser name.
   * 
   * @return the parser name.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns a description of the parser.
   * 
   * @return the string describing the parser.
   */
  public String getDescription() {
    return "Formula formula parser\n"
        + "Propositional variables: ['_','a'-'z','A'-'Z'] | (['_','a'-'z','A'-'Z'] | ['0'-'9'])*\n"
        + "Logical constants: true, false, & (and), | (or), ~ (not), -> (implies), <=> (equivalence)";
  }

  static void main(String[] args) {
    PropositionalFormulaParser parser = new PropositionalFormulaParser();
    try {
      ParseTree pt = parser.parse(args[0]);
      System.out.println(pt.toString());
    } catch (FormulaParseException e) {
      System.out.println(e.toString());
    }
  }

  static class ErrorListener extends BaseErrorListener {

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
        int charPositionInLine, String msg, RecognitionException e) {
      throw new ParserError("line " + line + ":" + charPositionInLine + " - " + msg);
    }

  }

}
