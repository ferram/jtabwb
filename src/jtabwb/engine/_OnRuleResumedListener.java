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
 * Specifies that the rule implementing this interface listens for the
 * on-resume-event. In detail:
 * <ul>
 * <li>An instance of {@link _RegularRule} is resumed if the proof-search for
 * its last selected subgola succeeded.</li>
 * <li>An instance of a {@link _BranchExistsRule} is resumed if the proof-search
 * for its last selected subgoal failed.</li>
 * <li>An instance of a {@link _MetaBacktrackRule} is resumed if the
 * proof-search starting with tje lasr selected rule application failed.</li>
 * </ul>
 * When the rule is resumed the engine invokes the {@link #onResumed()} method
 * before performing any other action.
 * 
 * @author Mauro Ferrari
 * 
 */
public interface _OnRuleResumedListener extends _AbstractRule {

  public void onResumed();

}
