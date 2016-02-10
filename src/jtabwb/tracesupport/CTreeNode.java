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

import java.util.LinkedList;

import jtabwb.engine.ProofSearchResult;
import jtabwb.engine.TraceNode;
import jtabwb.engine._AbstractGoal;
import jtabwb.engine._AbstractRule;

/**
 * An object of this class represents a node of a C-tree.
 * @author Mauro Ferrari
 */
public class CTreeNode {

	private String INDENTATION = "  ";

	private int treatedConclusionOfParent;
	private LinkedList<CTreeNode> successors;
	private _AbstractRule appliedRule;
	private _AbstractGoal premise;
	private ProofSearchResult status;

	CTreeNode(TraceNode traceNode, CTreeNode parent, int treatedConclusionOfParent,
	    ProofSearchResult status) {
		this.successors = null;
		this.treatedConclusionOfParent = treatedConclusionOfParent;
		this.appliedRule = traceNode.getAppliedRule();
		this.premise = traceNode.getPremise();
		this.status = status;
	}

	public int getTreatedConclusion() {
		return treatedConclusionOfParent;
	}

	public void addSuccessor(CTreeNode successor) {
		if (successors == null)
			successors = new LinkedList<CTreeNode>();
		successors.addLast(successor);
	}

	public LinkedList<CTreeNode> getSuccessors() {
		return this.successors;
	}

	public _AbstractRule getAppliedRule() {
		return this.appliedRule;
	}

	public _AbstractGoal getNodeSet() {
		return this.premise;
	}

	public ProofSearchResult getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return toStringIndent("");
	}

	private String toStringIndent(String indent) {
		String str = indent + this.premise + "---" + this.appliedRule.name() + "\n";
		if (this.successors == null)
			return str;
		else {
			indent += INDENTATION;
			for (CTreeNode succ : successors)
				str += succ.toStringIndent(indent);
			return str;
		}

	}

}
