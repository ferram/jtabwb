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
package jtabwbx.prop.btformula;

import jtabwbx.prop.basic.PropositionalConnective;
import jtabwbx.prop.basic._PropositionalFormula;

/**
 * A simple implementation of a formula as a binary tree. This is an abstract
 * class providing the basic methods for a formula, the real implementation is
 * given by one of the concrete subclasses of this class.
 * 
 * @author Mauro Ferrari
 */
public abstract class BTFormula implements _PropositionalFormula {

  @Override
  abstract public PropositionalConnective mainConnective();

  @Override
  abstract public BTFormula[] immediateSubformulas();
}
