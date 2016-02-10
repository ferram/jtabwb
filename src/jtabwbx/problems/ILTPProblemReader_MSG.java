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

class ILTPProblemReader_MSG {

  static class LEXER {

    static final String WRONG_MATCH = "expecting %c found %c";
    static final String INVALID_CHAR = "invalid character: %c";
    static final String LEXER_VERBOSE = "--LEXER: Token = %s";
  }

  static class PARSER {

    static final String WRONG_MATCH = "Found \"%s\" expecting %s";
    static final String WRONG_MATCH_ONE_OF = "Found <%s> expecting one of: %s";
    static final String PARSER_ERROR = "PARSER ERROR: %s";
    static final String FILE_NOT_FOUND = "File not found: %s";
    static final String FOUND = "Found: %s";
    static final String LEXEME_FOR_OUTPUT = "%s";
    static final String TIME_STRING = "%d.%03d seconds";
    static final String PARSING_BEGIN = "File [%s]... ";
    static final String PARSING_END = "parsing time %s";
  }

  static class PROBLEM_BUILDER {

    static final String BUILD_ERROR_NO_CONJECTURE = "No conjecture is specified";
    static final String BUILD_ERROR_MORE_CONJECTURES = "More than one conjecture is specified";
    static final String UNKONW_STATUS_STRING = "Unknown status string [%s]";
  }

  static class ILTP_LIBRARY_PROBLEM_DESCRIPTION_READER {


    static final String MSG_ERROR_FACTORY =
        "ILTPLibraryProblemDescriptionReader: no factory has been defined; invoke setFactory() before reading problem.";

  }
}
