/*******************************************************************************
 * Copyright (C) 2013, 2015 Mauro Ferrari
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
package jtabwb.engine;

import java.util.NoSuchElementException;

/**
 * Specialised version of {@link NoSuchElementException} thrown by the
 * {@link _RegularRule#nextSubgoal()} and
 * {@link _BranchExistsRule#nextBranchExistsSubgoal()} to to indicate that there
 * are no more subgoal returned by the rule.
 * 
 * @author Mauro Ferrari
 */
public class NoSuchSubgoalException extends NoSuchElementException {

  /**
   * Constructs a <code>NoSuchConclusionException</code> with <code>null</code>
   * as its error message string.
   */
  public NoSuchSubgoalException() {
    super();
  }

  /**
   * Constructs a <code>NoSuchConclusionException</code>, saving a reference to
   * the error message string <code>str</code> for later retrieval by the
   * {@link #getMessage}.
   * @param message the message.
   */
  public NoSuchSubgoalException(String message) {
    super(message);
  }

  /**
   * Constructs a <code>NoSuchConclusionException</code>, saving a reference to
   * the error message string <code>"Undefined branch: i"</code> where
   * <code>i</code> is the specified integer, for later retrieval by the
   * {@link #getMessage}.
   * @param undefinedBranch the index of the undefined subgoal
   */
  public NoSuchSubgoalException(int undefinedBranch) {
    super("Undefined branch: " + undefinedBranch);
  }

}
