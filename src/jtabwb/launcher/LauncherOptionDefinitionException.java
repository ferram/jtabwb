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
package jtabwb.launcher;

/**
 * Signals an errore in the definition of an option.
 * @author Mauro Ferrari
 *
 */
public class LauncherOptionDefinitionException extends RuntimeException {

  public LauncherOptionDefinitionException() {
    super();
  }

  public LauncherOptionDefinitionException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public LauncherOptionDefinitionException(String message, Throwable cause) {
    super(message, cause);
  }

  public LauncherOptionDefinitionException(String message) {
    super(message);
  }

  public LauncherOptionDefinitionException(Throwable cause) {
    super(cause);
  }

}
