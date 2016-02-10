/*******************************************************************************
 * Copyright (C) 2013, 2014 Mauro Ferrari This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this program; if not, see
 * <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package jtabwb.util;

import ferram.rtoptions.RTOptions;
import ferram.rtoptions._RTOption;

/**
 * An exception to signal that incompatible options has been set in the option
 * manager. This exception is provided as a facility to signal options
 * incompatibility, but it is never thrown by the classes managing options.
 * @author Mauro Ferrari
 */
public class IncompatibleOptionsException extends RuntimeException {

  private _RTOption[] incompatible;

  public IncompatibleOptionsException(_RTOption[] incompatible) {
    super(RTOptions.getIncompatibityDescription(incompatible));
    this.incompatible = incompatible;
  }

  private static String format(_RTOption[] opts) {
    String str = "";
    for (int i = 0; i < opts.length; i++)
      str += opts[i].getName() + (i < opts.length - 1 ? "," : "");

    return str;
  }

  /**
   * Returns the array containing the incompatible options which caused this
   * exception.
   * @return the array containing the incompatible options.
   */
  public _RTOption[] getIncompatibleOptions() {
    return incompatible;
  }

  /**
   * Returns the comma separated list of incompatible options.
   * @return a string description of incompatible options.
   */
  public String incompatibleOptions() {
    return format(incompatible);
  }

}
