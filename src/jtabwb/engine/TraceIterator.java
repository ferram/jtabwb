/*******************************************************************************
 * Copyright (C) 2013, 2014 Mauro Ferrari
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
package jtabwb.engine;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * An instance of this class provides an iterator over traces. The iterator
 * returns the node of the trace with an inorder visit of the trace.
 * @author Mauro Ferrari
 */
class TraceIterator implements Iterator<TraceNode> {

	private Stack<TraceNode> stack;
	private TraceNode currentNode;

	public TraceIterator(Trace trace) {
		super();
		this.stack = new Stack<TraceNode>();
		this.currentNode = trace.getProofSearchTree();
	}

	/*
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return currentNode != null;
	}

	private TraceNode inOrder() {
		if (currentNode == null)
			throw new NoSuchElementException();

		TraceNode result = currentNode;
		if (currentNode.children != null) {
			LinkedList<TraceNode> children = currentNode.children;
			currentNode = children.get(0);
			for (int i = children.size() - 1; i >= 1; i--)
				stack.push(children.get(i));
		} else {
			if (!stack.isEmpty())
				currentNode = stack.pop();
			else
				currentNode = null;
		}
		return result;
	}

	/*
	 * @see java.util.Iterator#next()
	 */
	@Override
	public TraceNode next() {
		return inOrder();
	}

	/**
	 * This method is not implemented, its application has no effect on this
	 * iterator.
	 */
	@Override
	public void remove() {
	}

}
