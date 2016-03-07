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

import jtabwb.engine.ProvabilityStatus;
import jtabwb.launcher._ProblemReader;

/**
 * Instances of this class allow one to read a problem only consisting of the
 * problem formula.
 * 
 * @author Mauro Ferrari
 */
public class PlainProblemReader implements _ProblemReader {

  public static final String NAME = "plain";
  private static final String DESCRIPTION =
      "The file consists of exactly one line containing the conjecture";

  /**
   * Build a plain reader.
   * 
   */
  public PlainProblemReader() {
  }

  /**
   * Returns the problem description build from the specified input stream; the
   * specified input stream is supposed to contain only one line describing the
   * formula.
   * 
   * @param input the input stream.
   * @return the object describing the problem. * @throws IOException if
   * something goes wrong reading the file.
   * @throws ProblemDescriptionException if the input does not respect the
   * problem format.
   */
  public JTabWbSimpleProblem read(Reader input) throws ProblemDescriptionException, IOException {

    BufferedReader reader = new BufferedReader(input);
    String wff = reader.readLine();
    JTabWbSimpleProblem pd = new JTabWbSimpleProblem();
    pd.addConjecture(wff);
    pd.setProblemStatus(ProvabilityStatus.UNKNOWN);
    return pd;
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }

}
