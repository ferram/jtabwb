/*******************************************************************************
 * Copyright (C) 2013, 2015 Mauro Ferrari This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this program; if not, see
 * <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package jtabwb.launcher;

import ferram.rtoptions.NamedArgumentsSet;
import jtabwb.engine._Prover;

/**
 * The available provers.
 * 
 * @author Mauro Ferrari
 */
class AvailableProvers extends NamedArgumentsSet<Class<_Prover>> {

  public AvailableProvers() {
    super();
  }

  void addProver(String name, String description, Class<_Prover> proverClass) {
    this.add(new ConfiguredTheoremProver(name, description, proverClass));
  }

  void addProver(String name, String description, Class<_Prover> proverClass, boolean isDefault) {
    this.add(new ConfiguredTheoremProver(name, description, proverClass), isDefault);
  }

  /**
   * Returns the default prover or the last added prover if no default prover
   * has been defined.
   * 
   * @return the default prover or the last added prover if no default prover
   * has been defined
   */
  ConfiguredTheoremProver getDefaultProver() {
    ConfiguredTheoremProver def = (ConfiguredTheoremProver) this.getDefault();
    if (def == null)
      def = (ConfiguredTheoremProver) this.getLastAdded();
    return def;
  }

  public ConfiguredTheoremProver searchProverByName(String name) {
    return (ConfiguredTheoremProver) this.searchByName(name);
  }

  /**
   * Check if the specified class object implements the {@link _Prover}
   * interface.
   * 
   * @param classObj the class to check.
   * @return true if <code>classObj</code> implements the required interface.
   */
  static boolean isAProver(Class<?> classObj) {
    return _Prover.class.isAssignableFrom(classObj);
  }

  /**
   * Check if the specified class object implements the
   * {@link _InitialGoalBuilder} interface.
   * 
   * @param classObj the class to check.
   * @return true if <code>classObj</code> implements the required interface.
   */
  static boolean isAnInitialNodeSetBuilder(Class<?> classObj) {
    return _InitialGoalBuilder.class.isAssignableFrom(classObj);
  }

  /**
   * Returns true iff the specified name is the name of an available prover and
   * false otherwise.
   * 
   * @param name the name of the prover.
   * @return
   */
  boolean isAvailableProver(String name) {
    Object result = this.searchByName(name);
    return result != null;
  }
}
