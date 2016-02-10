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
package jtabwbx.problems;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import jtabwb.launcher.ProblemDescriptionException;
import jtabwb.util.CaseNotImplementedImplementationError;
import jtabwbx.problems.ILTPProblemLexer.Token;
import jtabwbx.problems.ILTPProblemLexer.TokenType;
import jtabwbx.problems.ILTProblemParser.InfoLineExtractor.InfoLineResult;
import jtabwbx.prop.formula.Formula;

/**
 * A parser for ILTP-problems.
 * 
 * @author Mauro Ferrari
 *
 */
class ILTProblemParser {

  static enum Terminal {
    KEY_FOF("fof", TokenType.LOWER_NAME), //
    KEY_AXIOM("axiom", TokenType.LOWER_NAME), //
    KEY_CONJECTURE("conjecture", TokenType.LOWER_NAME), //
    OP_AND("&", TokenType.OPERATOR), //
    OP_EQ("<=>", TokenType.OPERATOR), //
    OP_OR("|", TokenType.OPERATOR), //
    OP_NOT("~", TokenType.OPERATOR), //
    OP_IMPLIES("=>", TokenType.OPERATOR), //
    SEP_DOT(".", TokenType.SEPARATOR), //
    SEP_COMMA(",", TokenType.SEPARATOR), //
    SEP_LEFTPAR("(", TokenType.SEPARATOR), //
    SEP_RIGHTPAR(")", TokenType.SEPARATOR), //
    INFO_COMMENT(null, TokenType.INFO_COMMENT), //
    ID(null, TokenType.LOWER_NAME), //
    EOF(null, TokenType.EOF);

    private final String lexeme;
    private final TokenType requiredTokenType; /*
                                                * the token corresponding to
                                                * this terminal. The terminal is
                                                * recognised if the current
                                                * token has this token type and
                                                * the semantic condition is
                                                * satisfied.
                                                */

    private Terminal(String lexeme, TokenType requiredTokenType) {
      this.lexeme = lexeme;
      this.requiredTokenType = requiredTokenType;
    }

    /**
     * @return the lexeme
     */
    public String getLexeme() {
      return this.lexeme;
    }

    /**
     * Checks if the specified token match the terminal invoking this method.
     * The token match this terminal if it has the required token type and the
     * required text value (in the cases where the text value mus be checked).
     * 
     * @param token the token to match with this terminal
     * @return true if the specified token match this terminal
     */
    boolean checkToken(Token token) {
      if (this.requiredTokenType != token.getType())
        return false;
      switch (this) {
      case ID:
      case EOF:
        return true;
      default: // for other tokens check value
        return this.lexeme.equals(token.getText());
      }

    }

  }

  public ILTProblemParser(String filename) throws FileNotFoundException {
    this(new FileReader(filename));
  }

  public ILTProblemParser(Reader reader) {
    super();
    this.lexer = new ILTPProblemLexer(reader);
    this.currentToken = lexer.nextToken();
    this.problemBuilder = new ILTPProblemBuilder();
    this.infoLineExtractor = new InfoLineExtractor();
  }

  private final ILTPProblemLexer lexer;
  private Token currentToken;
  private ILTPProblemBuilder problemBuilder;
  private final InfoLineExtractor infoLineExtractor;

  public ILTPProblem parse() throws ILTPProblemParserError, ProblemDescriptionException {
    this.start();
    return problemBuilder.build();

  }

  /* GRAMMAR RULES: a method for every grammar rule */

  private void start() throws ILTPProblemParserError, ProblemDescriptionException {
    while (currentToken.getType() != TokenType.EOF)
      infoCommentOrProblemDescription();
  }

  private void infoCommentOrProblemDescription() throws ILTPProblemParserError,
      ProblemDescriptionException {
    if (currentToken.getType() == TokenType.INFO_COMMENT)
      infoComment();
    else
      fof();
  }

  private void infoComment() throws ProblemDescriptionException {
    InfoLineResult info = infoLineExtractor.extract(currentToken.getText());
    switch (info.type) {
    case FILE:
      problemBuilder.setProblemName(info.text);
      break;
    case STATUS: {
      problemBuilder.setProblemStatus(info.text);
    }
      break;
    case IGNORE:
      break;
    default:
      throw new CaseNotImplementedImplementationError(info.type.name());
    }
    consumeToken();
  }

  private void fof() throws ILTPProblemParserError {
    match(Terminal.KEY_FOF);
    match(Terminal.SEP_LEFTPAR);
    String fof_name = IDENTIFIER();
    match(Terminal.SEP_COMMA);
    Terminal formula_role = match(Terminal.KEY_AXIOM, Terminal.KEY_CONJECTURE).terminal;
    match(Terminal.SEP_COMMA);
    Formula formula = formula();
    match(Terminal.SEP_RIGHTPAR);
    match(Terminal.SEP_DOT);
    // add element
    problemBuilder.addFormula(formula_role, fof_name, formula);
  }

  private Formula formula() throws ILTPProblemParserError {
    Terminal lookahead = lookhead(Terminal.SEP_LEFTPAR, Terminal.ID, Terminal.OP_NOT);
    switch (lookahead) {
    case ID:
      return atomicFormula();
    case OP_NOT:
      match(Terminal.OP_NOT);
      Formula subf = formula();
      // build element
      return problemBuilder.buildUnary(lookahead, subf);
    case SEP_LEFTPAR:
      return compoundFormula();
    default:
      throw new CaseNotImplementedImplementationError(lookahead.name());
    }
  }

  private Formula atomicFormula() throws ILTPProblemParserError {
    String name = match(Terminal.ID).text;
    // build element
    return problemBuilder.buildAtomic(name);
  }

  private Formula compoundFormula() throws ILTPProblemParserError {
    match(Terminal.SEP_LEFTPAR);
    Formula subf1 = formula();
    Terminal lookahead =
        lookhead(Terminal.SEP_RIGHTPAR, Terminal.OP_AND, Terminal.OP_OR, Terminal.OP_IMPLIES,
            Terminal.OP_EQ);
    switch (lookahead) {
    case SEP_RIGHTPAR:
      match(Terminal.SEP_RIGHTPAR);
      return subf1;
    default:
      match(lookahead);
      break;
    }
    Formula subf2 = formula();
    match(Terminal.SEP_RIGHTPAR);
    // build element
    return problemBuilder.buildBinary(lookahead, subf1, subf2);
  }

  private String IDENTIFIER() throws ILTPProblemParserError {
    String result = currentToken.getText();
    match(Terminal.ID);
    return result;
  }

  /* PARSING METHODS */

  private static class MatchResult {

    public MatchResult(Terminal terminal, String text) {
      super();
      this.text = text;
      this.terminal = terminal;
    }

    final Terminal terminal;
    final String text;
  }

  private MatchResult match(Terminal... terminals) throws ILTPProblemParserError {
    for (Terminal terminal : terminals)
      if (terminal.checkToken(currentToken)) {
        MatchResult result = new MatchResult(terminal, currentToken.getText());
        currentToken = lexer.nextToken();
        return result;
      }

    throw new ILTPProblemParserError(matchErrorString(terminals));
  }

  private Terminal lookhead(Terminal... terminals) throws ILTPProblemParserError {
    for (Terminal terminal : terminals)
      if (terminal.checkToken(currentToken)) {
        return terminal;
      }
    throw new ILTPProblemParserError(matchErrorString(terminals));
  }

  private String matchErrorString(Terminal... terminals) {
    if (terminals.length == 1)
      return String.format(ILTPProblemReader_MSG.PARSER.WRONG_MATCH, currentToken.getText(),
          terminals[0].getLexeme());

    else {
      String expecting = "";
      for (int i = 0; i < terminals.length; i++)
        expecting +=
            String.format(ILTPProblemReader_MSG.PARSER.LEXEME_FOR_OUTPUT, terminals[i].getLexeme())
                + (i < terminals.length - 1 ? " " : "");
      return expecting;
    }
  }

  private void consumeToken() {
    currentToken = lexer.nextToken();
  }

  public static void main(String[] args) {
    Log log = new Log();
    for (String filename : args) {
      log.infoNoLn(String.format(ILTPProblemReader_MSG.PARSER.PARSING_BEGIN, filename));
      try {
        ILTProblemParser parser = new ILTProblemParser(filename);
        long start = System.currentTimeMillis();
        ILTPProblem pd = parser.parse();
        long end = System.currentTimeMillis();
        // Create tokens and print them
        log.info(String.format(ILTPProblemReader_MSG.PARSER.PARSING_END, buildTimeString(end
            - start)));
      } catch (FileNotFoundException e) {
        log.error(String.format(ILTPProblemReader_MSG.PARSER.FILE_NOT_FOUND, args[0]));
      } catch (ILTPProblemParserError e) {
        log.error(String.format(ILTPProblemReader_MSG.PARSER.PARSER_ERROR, e.getMessage()));
      } catch (ProblemDescriptionException e) {
        log.error(String.format(ILTPProblemReader_MSG.PARSER.PARSER_ERROR, e.getMessage()));
      }
    }

  }

  private static String buildTimeString(long miliSeconds) {
    int sec = (int) TimeUnit.MILLISECONDS.toSeconds(miliSeconds);
    int mil = (int) miliSeconds % 1000;

    return String.format(ILTPProblemReader_MSG.PARSER.TIME_STRING, sec, mil);
  }

  static class InfoLineExtractor {

    public InfoLineExtractor() {
      super();
    }

    static enum InfoLineType {
      FILE, STATUS, IGNORE;
    }

    static class InfoLineResult {

      public InfoLineResult(InfoLineType type, String text) {
        super();
        this.text = text;
        this.type = type;
      }

      final String text;
      final InfoLineType type;

    }

    private final String FILE = "File";
    private final String STATUS = "Status";
    private final String INTUITIONISTIC = "(intuit.)";
    private final String SEPARATOR = "%-";

    InfoLineResult extract(final String infoLine) {
      if (infoLine.startsWith(SEPARATOR))
        return new InfoLineResult(InfoLineType.IGNORE, infoLine);
      String toAnalyze = infoLine.substring(1).trim();
      if (toAnalyze.length() == 0)
        return new InfoLineResult(InfoLineType.IGNORE, infoLine);
      StringTokenizer stk = new StringTokenizer(toAnalyze, " ");
      String token = stk.nextToken();
      if (token.equals(FILE)) {
        stk.nextToken(); // ignore :
        String name = stk.nextToken();
        return new InfoLineResult(InfoLineType.FILE, name);
      }
      if (token.equals(STATUS)) {
        String s = stk.nextToken(); // ignore (intuit.)
        if (s.equals(INTUITIONISTIC))
          stk.nextToken(); // ignore :
        else
          return new InfoLineResult(InfoLineType.IGNORE, infoLine);
        String status = stk.nextToken();
        return new InfoLineResult(InfoLineType.STATUS, status);
      }
      return new InfoLineResult(InfoLineType.IGNORE, infoLine);
    }

  }

}
