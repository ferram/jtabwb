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

/**
 * The strategy defines the way the proof-search space is visited; at every
 * iteration the engine invokes
 * {@link #nextRule(_AbstractGoal, IterationInfo)}) to determine the rule to
 * apply to the current-goal of the proof-search.
 * @author Mauro Ferrari
 */
public interface _Strategy {

	/**
	 * This is a call-back method invoked by the engine when it needs to determine
	 * the rule to to apply to <code>currentGoal</code>; doing this the engine
	 * provides to the method as second argument a bunch of data describing the
	 * last performed iteration. The method returns the rule instance to apply to
	 * the current goal or <code>null</code> if no rule can be applied the current
	 * goal.
	 * @param currentGoal the current goal of the proof-search.
	 * @param lastIteration a bunch of data describing the last iteration
	 *          performed by the engine.
	 * @return the next rule to apply or <code>null</code> if no rule can be
	 *         applied to <code>currentGoal</code>.
	 */
	public abstract _AbstractRule nextRule(_AbstractGoal currentGoal, IterationInfo lastIteration);

}
