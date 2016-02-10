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
 * Models a node of the proof search. It consists of the node for which the
 * engine is searching a proof and a counter numbering the node of the proof
 * search.
 * 
 * @author Mauro Ferrari
 */
class GoalNode {

  _AbstractGoal nodeSet;
  long counter;
  long generatedAtStep;

  public GoalNode(EnginePlain engine, _AbstractGoal node) {
    super();
    this.nodeSet = node;
    this.counter = engine.LAST_ITERATION_INFO.number_of_generated_nodes++;
    this.generatedAtStep = engine.LAST_ITERATION_INFO.number_of_iterations;
  }

  @Override
  public String toString() {
    return formatAsEngineNode();
  }

  String formatAsRestoredEngineNode() {
    String strNodeSet = nodeSet.format();
    strNodeSet = "    | " + strNodeSet.replaceAll("\n", "\n    | ");
    return "(" + this.counter + ") generated at step [" + generatedAtStep + "]\n" + strNodeSet;
  }

  // TODO generalizzare in modo che la formattazione sia modificabile.
  String formatAsEngineNode() {
    return "(" + this.counter + ")\n" + VerboseModeSupport.indentWithPrefix("| ", nodeSet.format());
  }

}
