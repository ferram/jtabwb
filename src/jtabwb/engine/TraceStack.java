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
 ******************************************************************************/
package jtabwb.engine;

/**
 * The stack storing trace nodes during proof-search. Every time the engine
 * applies a rule, a trace-node is generated and added to the trace-stack. We
 * notice that, while the depth-first stack ({@link DFStack}) only contains
 * nodes corresponding to branch and backtrack rule applications, the
 * trace-stack contains nodes for every rule application. Every element contains
 * a reference to the related depth-first stack element or null if the
 * corresponding rule application did not generated a depth-first stack element.
 * When the engine restore a branch-point,
 * {@link TraceStack#restoreBranchPoint(DFStackNode)} is invoked so to remove
 * from the trace-stack all the elements until the one corresponding to the
 * specified <code>DFStackNode</code> element; for every element removed from
 * the trace-stack the status of the corresponding <code>traceNode</code> is
 * updated. When the engine restore a backtrack-point,
 * {@link TraceStack#restoreBacktrackPoint(DFStackNode)} is invoked so to remove
 * from the trace-stack all the elements until the one corresponding to the
 * specified <code>DFStackNode</code> element; for every element removed from
 * the trace-stack the status of the corresponding <code>traceNode</code> is
 * updated.
 * @author Mauro Ferrari
 */
class TraceStack {

	private TNode head;
	private TNode top;
	GeneratingPremise nodeGeneratingPremise;

	static class GeneratingPremise {

		TraceNode node;
		int idxConclusion;

		public GeneratingPremise(TraceNode node, int idxConclusion) {
			super();
			this.node = node;
			this.idxConclusion = idxConclusion;
		}

	}

	/**
	 * An element of trace-stack.
	 * @author Mauro Ferrari
	 */
	private static class TNode {

		TraceNode traceNode; // the trace node
		DFStackNode dfNode; // the related depth-first stack node
		TNode previous; // the previous node in the stack
		GeneratingPremise generatingPremise;

		/**
		 * Constructs an element of the trace-stack.
		 * @param traceNode the trace node to store in the element.
		 * @param dfNode the related node of the depth-first stack.
		 * @param previous the previous element in the trace-stack.
		 */
		public TNode(TraceNode traceNode, DFStackNode dfNode, TNode previous, GeneratingPremise gp) {
			super();
			this.traceNode = traceNode;
			this.dfNode = dfNode;
			this.previous = previous;
			this.generatingPremise = gp;
		}
	}

	/**
	 * Constructs an empty stack.
	 */
	public TraceStack() {
		top = null;
		nodeGeneratingPremise = null;
		head = null;
	}

	/*
	 * Push in the the stack the specified trace-node with its related depth-first
	 * stack element.
	 */
	void pushWithDFStackModified(TraceNode traceNode, DFStackNode dfNode) {
		TNode tmp = new TNode(traceNode, dfNode, top, nodeGeneratingPremise);
		top = tmp;
		if (head == null)
			head = top;
		/*
		 * the node generating premise is modified only if the point added to the
		 * depth-first stack does not corresponds to a META_BACKTRACK_RULE
		 * application (such a rule does not modify the the goal).
		 */
		if (dfNode instanceof DFStackNode_Branch)
			nodeGeneratingPremise = new GeneratingPremise(traceNode,
			    ((DFStackNode_Branch) dfNode).getIndexOfLastTreatedConclusion());
		if (dfNode instanceof DFStackNode_BranchExists)
			nodeGeneratingPremise = new GeneratingPremise(traceNode,
			    ((DFStackNode_BranchExists) dfNode).getIndexOfLastTreatedConclusion());
	}

	/*
	 * Push in the the stack the specified trace-node with its related depth-first
	 * stack element.
	 */
	void pushWithDFStackUnchanged(TraceNode traceNode) {
		TNode tmp = new TNode(traceNode, null, top, nodeGeneratingPremise);
		top = tmp;
		if (head == null)
			head = top;
		switch (RuleType.getType(traceNode.appliedRule)) {
		case REGULAR:
		case BRANCH_EXISTS:
			nodeGeneratingPremise = new GeneratingPremise(traceNode, 0);
			break;
		case CLASH_DETECTION_RULE:
		case FORCE_BRANCH_FAILURE:
		case FORCE_BRANCH_SUCCESS:
		case META_BACKTRACK_RULE:
			break;
		default:
			throw new ImplementationError();
		}
	}

	/**
	 * Remove from the trace-stack all the elements until the one corresponding to
	 * the specified <code>wheretoStop</code> element; for every element removed
	 * from the trace-stack the status of the corresponding <code>traceNode</code>
	 * is updated.
	 * @param branchPoint the branch-point to restore.
	 * @return the trace-node at the top of the stack.
	 */
	private TraceNode restoreUntilWithProofStatus(DFStackNode wheretoStop, ProofSearchResult result) {
		TNode tmp = top;
		do {
			if (!(tmp.traceNode.appliedRule instanceof _ClashDetectionRule))
				tmp.traceNode.status = result;
			tmp = tmp.previous;
		} while (tmp.dfNode != wheretoStop);
		top = tmp;
		if (wheretoStop != null) {
			if (top.dfNode instanceof DFStackNode_Branch)
				nodeGeneratingPremise = new GeneratingPremise(top.traceNode,
				    ((DFStackNode_Branch) top.dfNode).nextConclusionToTreat);
			else if (top.dfNode instanceof DFStackNode_BranchExists)
				nodeGeneratingPremise = new GeneratingPremise(top.traceNode,
				    ((DFStackNode_BranchExists) top.dfNode).nextToTreat);
			else
				nodeGeneratingPremise = top.generatingPremise;
		}
		return tmp.traceNode;
	}

	/**
	 * Remove from the trace-stack all the elements until the one corresponding to
	 * the specified <code>branchPoint</code> element; for every element removed
	 * from the trace-stack the status of the corresponding <code>traceNode</code>
	 * is updated.
	 * @param branchPoint the branch-point to restore.
	 * @return the trace-node at the top of the stack.
	 */
	public TraceNode restoreBranchPoint(DFStackNode branchPoint) {
		return restoreUntilWithProofStatus(branchPoint, ProofSearchResult.SUCCESS);
	}

	/**
	 * Remove from the trace-stack all the elements until the one corresponding to
	 * the specified <code>backtrackPoint</code> element; for every element
	 * removed from the trace-stack the status of the corresponding
	 * <code>traceNode</code> is updated.
	 * @param branchPoint the branch-point to restore.
	 * @return the trace-node at the top of the stack.
	 */
	public TraceNode restoreBacktrackPoint(DFStackNode backtrackPoint) {
		return restoreUntilWithProofStatus(backtrackPoint, ProofSearchResult.FAILURE);
	}

	/**
	 * When proof-search terminates the trace-stack can contains some elements
	 * (corresponding to regular non branching or clash-detection rule
	 * applications), this methods removes these elements from the trace-stack
	 * updating the status of the corresponding trace-nodes with the result of
	 * proof-search provided as argument.
	 * @param status the result of the proof-search.
	 * @return the head of the trace-nodes.
	 */
	public TraceNode closeTrace(ProofSearchResult status) {
		if (top == null)
			return head.traceNode;
		TNode tmp = top;
		do {
			if (!(tmp.traceNode.appliedRule instanceof _ClashDetectionRule))
				tmp.traceNode.status = status;
			top = tmp;
			tmp = tmp.previous;
		} while (tmp != null);
		// ripulire lo stack ??
		return top.traceNode;
	}

}
