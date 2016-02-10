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
import jtabwb.launcher.ProblemDescriptionException;
import jtabwb.launcher._ProblemReader;

/**
 * Instances of this class allow one to read a problem description in the JTabWb
 * format where a problem is specified in a file in with the following
 * structure:
 * 
 * <pre>
 * %------------------------
 * % File     : formula_name
 * % Status   : status
 * %------------------------
 * &lt;FORMULA&gt;
 * %------------------------
 * </pre>
 * 
 * where <code>formula_name</code> is the name of the problem,
 * <code>status</code> is either <code>provable</code> or
 * <code>unprovable</code> and <code>&lt;FORMULA&gt;</code> is the specification
 * of the formula. *
 * 
 * @author Mauro Ferrari
 */
// TODO: rename JTabWbSimpleFormatProblemReader
public class JTabWbSimpleProblemReader implements _ProblemReader {

  public static final String NAME = "jtawb_fromat";
  private static final String DESCRIPTION =
      "The file describes a problem according with simple JTabWb format";

  private final String PRE_NAME = "% File     :";
  private final String PRE_STATUS = "% Status   :";

  /**
   * Builds an instance of this problem description reader.
   */
  public JTabWbSimpleProblemReader() {
  }

  /**
   * Returns the problem description corresponding to the PITP problem
   * description read from the specified stream.
   * 
   * @param input the stream with the PITP description of a problem.
   * @return the formula description.
   * @throws IOException if something goes wrong reading the file.
   * @throws ProblemDescriptionException if the input does not respect the problem format.
   */
  public JTabWbSimpleProblem read(Reader input) throws ProblemDescriptionException, IOException {

    BufferedReader reader = new BufferedReader(input);

    String name = null;
    String status = null;
    String formula = null;

    reader.readLine(); // irrelevant
    name = reader.readLine().substring(PRE_NAME.length()).trim();
    status = reader.readLine().substring(PRE_STATUS.length()).trim();
    reader.readLine(); // irrelevant
    formula = reader.readLine();

    ProvabilityStatus stat = null;

    if (status.equals("provable"))
      stat = ProvabilityStatus.PROVABLE;
    else if (status.equals("unprovable"))
      stat = ProvabilityStatus.UNPROVABLE;
    else
      stat = ProvabilityStatus.UNKNOWN;

    JTabWbSimpleProblem pd = new JTabWbSimpleProblem(name);
    pd.addConjecture(formula);
    pd.setProblemStatus(stat);
    return pd;
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
