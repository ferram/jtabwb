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
package jtabwb.launcher;

import jtabwb.engine._Prover;
import ferram.rtoptions._NamedArgument;

/**
 * An object of this class describes a theorem prover added to the configuration
 * of the current launcher.
 * 
 * @author Mauro Ferrari
 * 
 */
public class ConfiguredTheoremProver implements _NamedArgument<Class<_Prover>> {

  public ConfiguredTheoremProver(String name, String description, Class<_Prover> value) {
    super();
    this.name = name;
    this.description = description;
    this.value = value;
  }

  private String name;
  private String description;
  private Class<_Prover> value;

  /**
   * Returns the name given to the prover in the launcher configuration.
   * @return  the name of the prover
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Returns the description given to the prover in the launcher configuration.
   * @return  the description of the prover
   */ 
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * Returns the class of the prover.
   * @return  the class of the prover. 
   */
  @Override
  public Class<_Prover> getValue() {
    return value;
  }

}
