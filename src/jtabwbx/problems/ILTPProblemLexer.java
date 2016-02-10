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
import java.io.StringReader;
import java.util.Scanner;
import java.util.regex.Pattern;

class ILTPProblemLexer {

  /*
   * The pattern string used to build the regex Pattern. NOTE: in the parsed
   * language there are no reserved words, hence we have a // group 1:
   * white-spaces; group 2: identifier; group 3: info-comment
   */
  private final static String PATTERN = "" // 
      + "(\\s+)" //white-spaces - group 1 
      + "|(&|\\||~|<=>|=>)" //   operators - group 2 
      + "|(\\.|,|\\(|\\))" //  separators - group 3
      + "|([a-z][_a-zA-Z0-9]*)"//  lower_name - group 4
      + "|(%.*)" //info-comment - group 5
      + "|."; //

  private static int GROUP_WHITESPACES = 1;
  private static int GROUP_OPERATORS = 2;
  private static int GROUP_SEPARATORS = 3;
  private static int GROUP_LOWER_NAME = 4;
  private static int GROUP_INFO_COMMENT = 5;

  static enum TokenType {
    OPERATOR, //
    SEPARATOR, //
    LOWER_NAME, //
    INFO_COMMENT, //
    ERROR, //
    EOF;
  }

  static class Token {
    
    class Location {

      public final int line;
      public final int column;

      public Location(int line, int column) {
        this.line = line;
        this.column = column;
      }

      public String toString() {
        return line + ":" + column;
      }

    }


    private TokenType type;
    private String text;
    Location begin;
    Location end;

    public Token(TokenType type, String data) {
      this.type = type;
      this.text = data;
    }

    @Override
    public String toString() {
      return String.format("(%s, %s)", type.name(), text);
    }

    /**
     * @return the type
     */
    public TokenType getType() {
      return this.type;
    }

    /**
     * @return the text
     */
    public String getText() {
      return this.text;
    }

  }

  private Pattern pattern;
  private final Log LOG;
  private int currentLine = 0;
  private boolean verbose;

  private Scanner scanner;

  public ILTPProblemLexer(String s) {
    this(new StringReader(s));
  }

  /** A new Lexer taking input from READER. */
  public ILTPProblemLexer(Reader reader) {
    this.scanner = new Scanner(reader);
    this.LOG = new Log();
    this.pattern = Pattern.compile(PATTERN);
    this.verbose = false;
  }

  private void updateLineNumber(String white) {
    for (int i = white.indexOf('\n', 0); i >= 0; i = white.indexOf('\n', i + 1))
      currentLine++;
  }

  /**
   * Read the next token, storing it in lastLexeme, and returning its Category.
   * Returns EOF at end of file, and ERROR for erroneous input (one character).
   */
  public Token nextToken() {
    Token token = _nextToken();
    if (verbose)
      LOG.info(String.format(ILTPProblemReader_MSG.LEXER.LEXER_VERBOSE, token.toString()));
    return token;
  }

  private Token _nextToken() {
    if (scanner.findWithinHorizon(pattern, 0) == null)
      return new Token(TokenType.EOF, "<EOF>");
    else {
      String lastLexeme = scanner.match().group(0);
      // if whitespace -> skip
      if (scanner.match().start(GROUP_WHITESPACES) != -1) {
        updateLineNumber(lastLexeme);
        return _nextToken();
      }
      if (scanner.match().start(GROUP_OPERATORS) != -1)
        return new Token(TokenType.OPERATOR, lastLexeme);

      if (scanner.match().start(GROUP_SEPARATORS) != -1)
        return new Token(TokenType.SEPARATOR, lastLexeme);

      if (scanner.match().start(GROUP_LOWER_NAME) != -1)
        return new Token(TokenType.LOWER_NAME, lastLexeme);

      if (scanner.match().start(GROUP_INFO_COMMENT) != -1)
        return new Token(TokenType.INFO_COMMENT, lastLexeme);

      return new Token(TokenType.ERROR, lastLexeme);
    }
  }
  
  public void setVerbose(boolean verbose){
    this.verbose =verbose; 
  }

  public static void main(String[] args) {

    Reader reader;
    try {
      reader = new FileReader(args[0]);
      ILTPProblemLexer lexer = new ILTPProblemLexer(reader);
      // Create tokens and print them
      Token t;
      do {
        t = lexer.nextToken();
        System.out.println(t);
      } while (t.type != TokenType.EOF);
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}