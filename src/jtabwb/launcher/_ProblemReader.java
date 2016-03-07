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
package jtabwb.launcher;

import java.io.IOException;
import java.io.Reader;

import jtabwbx.problems.ProblemDescription;
import jtabwbx.problems.ProblemDescriptionException;

/**
 * An object providing a parser for a problem description. A problem description
 * generally contains information as the problem name, the problem assumptions,
 * the problem formula (the formula to prove), the problem status (provable,
 * unprovable or unknown).
 * 
 * @author Mauro Ferrari
 */
public interface _ProblemReader {

  /**
   * Reads the problem description from the specified {@link Reader} and returns
   * the corresponding {@link ProblemDescription}.
   * 
   * @param input the input reader.
   * @return the problem description.
   * @throws ProblemDescriptionException if the file does not have the required
   * format.
   * @throws IOException if an input/output error occurs.
   */
  public ProblemDescription read(Reader input) throws ProblemDescriptionException, IOException;

  /**
   * Returns a string describing this problem reader.
   * 
   * @return the description of this reader.
   */
  public String getDescription();

  /**
   * Returns the name of this problem reader.
   * 
   * @return the description of this reader.
   */
  public String getName();

}
