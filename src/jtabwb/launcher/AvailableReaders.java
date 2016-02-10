/*******************************************************************************
 * Copyright (C) 2013, 2016 Mauro Ferrari This program is free software; you can
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

/**
 * The available readers.
 * 
 * @author Mauro Ferrari
 */
class AvailableReaders extends NamedArgumentsSet<Class<_ProblemReader>> {

  public AvailableReaders() {
    super();
  }

  void addReader(String name, String description, Class<_ProblemReader> obj) {
    this.add(new ConfiguredProblemDescriptioReader(name, description, obj));
  }

  void addReader(String name, String description, Class<_ProblemReader> obj,
      boolean isDefault) {
    this.add(new ConfiguredProblemDescriptioReader(name, description, obj), isDefault);
  }

  ConfiguredProblemDescriptioReader getDefaultReader() {
    ConfiguredProblemDescriptioReader def = (ConfiguredProblemDescriptioReader) this.getDefault();
    if (def == null)
      def = (ConfiguredProblemDescriptioReader)this.getLastAdded();
    return def;
  }
  
  public ConfiguredProblemDescriptioReader searchReaderByName(String name){
    return (ConfiguredProblemDescriptioReader) this.searchByName(name);
  }

  static boolean isAReader(Class<?> classObj) {
    return _ProblemReader.class.isAssignableFrom(classObj);
  }

  /**
   * Returns true iff the specified name is the name of an available reader and
   * false otherwise.
   * 
   * @param name the name of the prover.
   * @return
   */
  boolean isAvailableReader(String name) {
    Object result = super.searchByName(name);
    return result != null;
  }

}
