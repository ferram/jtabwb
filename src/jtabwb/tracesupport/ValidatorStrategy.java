/*******************************************************************************
 * Copyright (C) 2013, 2015 Mauro Ferrari
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option)
 * any later version.
 *  
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package jtabwb.tracesupport;

import java.util.Iterator;

import jtabwb.engine.IterationInfo;
import jtabwb.engine.RuleType;
import jtabwb.engine.Trace;
import jtabwb.engine.TraceNode;
import jtabwb.engine._AbstractGoal;
import jtabwb.engine._AbstractRule;
import jtabwb.engine._RegularRule;
import jtabwb.engine._Strategy;

class ValidatorStrategy implements _Strategy {

	private Iterator<TraceNode> iterator;
	private _TraceManager traceManager;

	public ValidatorStrategy(Trace trace, _TraceManager tracemanager) {
		this.traceManager = tracemanager;
		this.iterator = trace.iterator();
	}

	/*
	 * A purged successful proof trace only contains applications of _RegularRule
	 * and _ClashDetectionRule
	 */
	@Override
	public _AbstractRule nextRule(_AbstractGoal node, IterationInfo lastIteration) {
		if (iterator.hasNext()) {
			_AbstractRule rule = iterator.next().getAppliedRule();
			switch (RuleType.getType(rule)) {
			case CLASH_DETECTION_RULE:
				return traceManager.getRuleByName(rule.name(), node, null);
			case REGULAR:
				return traceManager.getRuleByName(rule.name(), node, ((_RegularRule) rule).mainFormula());
			case BRANCH_EXISTS:
				return traceManager.getRuleByName(rule.name(), node, ((_RegularRule) rule).mainFormula());
			case META_BACKTRACK_RULE:
				return traceManager.getRuleByName(rule.name(), node, null);
			default:
				throw new ImplementationError();
			}
		}
		return null;
	}
}
