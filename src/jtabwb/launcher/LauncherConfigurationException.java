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
 * Signals an error in the configuration of the launcher.
 * @author Mauro Ferrari
 *
 */
public class LauncherConfigurationException extends RuntimeException {

  public LauncherConfigurationException() {
    super();
  }

  public LauncherConfigurationException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public LauncherConfigurationException(String message, Throwable cause) {
    super(message, cause);
  }

  public LauncherConfigurationException(String message) {
    super(message);
  }

  public LauncherConfigurationException(Throwable cause) {
    super(cause);
  }

}
