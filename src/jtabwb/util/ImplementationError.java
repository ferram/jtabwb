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
package jtabwb.util;

public class ImplementationError extends RuntimeException {

  public static String CASE_NOT_IMPLEMENTED = "Case not implemented!";
  public static String CONTRACT_VIOLATION= "Contract violation!";
  public static String ARGUMENT_CANNOT_BE_NULL = "Argument cannot be null!"; 
  public static String NO_ARGUMENT_CAN_BE_NULL = "Arguments cannot be null! ";
  public static String SOMETHING_WENT_WRONG = "Oops! Something went wrong.";
  
  public ImplementationError() {
    super();
  }

  public ImplementationError(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }

  public ImplementationError(String arg0) {
    super(arg0);
  }

  public ImplementationError(Throwable arg0) {
    super(arg0);
  }

}
