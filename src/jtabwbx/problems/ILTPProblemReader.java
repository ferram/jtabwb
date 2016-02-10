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
 ******************************************************************************/
package jtabwbx.problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import jtabwb.launcher.ProblemDescription;
import jtabwb.launcher.ProblemDescriptionException;
import jtabwb.launcher._ProblemReader;

/**
 * Problem reader for ILTP-library problems.
 * 
 * @author Mauro Ferrari
 */
public class ILTPProblemReader implements _ProblemReader {

  public static final String NAME = "iltp";
  static final String DESCRIPTION = "Reader for problems of ILTP-library.";

  

  /**
   * Builds an instance of this problem description reader.
   */
  public ILTPProblemReader() {
    super();
  }

  /**
   * Returns a problem-description of the problem read from the specified input
   * stream.
   * 
   * @param input the input stream where the problem description is read.
   * @return the object describing the ILTP-library problem description.
   * @throws IOException if something goes wrong reading the file.
   * @throws ProblemDescriptionException if an error occurs while parsing one of
   * the problem formulas.
   */
  public ProblemDescription read(Reader input) throws ProblemDescriptionException, IOException {
    BufferedReader reader = new BufferedReader(input);
    ILTProblemParser parser = new ILTProblemParser(reader);
    try {
      return parser.parse();
    } catch (ILTPProblemParserError e) {
      throw new ProblemDescriptionException(e.getMessage());
    }
  }

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }

  @Override
  public String getName() {
    return NAME;
  }

}
