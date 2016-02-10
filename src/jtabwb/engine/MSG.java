/*******************************************************************************
 * Copyright (C) 2013, 2014 Mauro Ferrari
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
package jtabwb.engine;

class MSG {

  static class WARNING {

    static String NO_STATUS_LISTENER = "Status notification required for [%s] but the strategy does not define the status listener!";
  }

  static class TRACE {

    static String CANNOT_PRUNE = "A trace with [%s] status cannot be pruned.";
  }
  
  static class IMPLEMENTATION_ERROR {
    static final String CASE_NOT_IMPLEMENTED = "Case not implemented!";
    static final String ARGUMENT_CANNOT_BE_NULL_$1 = "Argument [%s] cannot be null!"; 
    static final String NO_ARGUMENT_CAN_BE_NULL = "Arguments cannot be null!";
    static final String PROPER_NOUN_CANNOT_BE_EMPTY = "properNoun cannot be empty.";
  }

  public static String getMsg(String key, Object... args) {
    return String.format(key, args);
  }

}
