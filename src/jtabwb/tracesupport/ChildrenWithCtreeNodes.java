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
 *******************************************************************************/
package jtabwb.tracesupport;

import java.util.HashMap;
import java.util.LinkedList;

import jtabwb.engine.TraceNode;

public class ChildrenWithCtreeNodes {

  public ChildrenWithCtreeNodes(LinkedList<TraceNode> children) {
    super();
    this.children = children.toArray(new TraceNode[children.size()]);
    this.ctreesOfChild = new HashMap<TraceNode, SequenceOfCtreeNodes>();
    this.failedCtreesOfChild = new HashMap<TraceNode, SequenceOfCtreeNodes>();
    this.succesfulCtreesOfChild = new HashMap<TraceNode, SequenceOfCtreeNodes>();
  }

  private TraceNode[] children;
  private HashMap<TraceNode, SequenceOfCtreeNodes> ctreesOfChild;
  private HashMap<TraceNode, SequenceOfCtreeNodes> succesfulCtreesOfChild;
  private HashMap<TraceNode, SequenceOfCtreeNodes> failedCtreesOfChild;

  public void put(TraceNode tnode, SequenceOfCtreeNodes ctrees) {
    this.ctreesOfChild.put(tnode, ctrees);
    for (CTreeNode node : ctrees)
      switch (node.getStatus()) {
      case FAILURE:
        putFailed(tnode, node);
        break;
      case SUCCESS:
        putSuccessful(tnode, node);
        break;
      default:
        throw new ImplementationError(ImplementationError.CASE_NOT_IMPLEMENTED);
      }
  }

  private void putSuccessful(TraceNode tnode, CTreeNode node) {
    SequenceOfCtreeNodes seq = succesfulCtreesOfChild.get(tnode);
    if (seq == null) {
      seq = new SequenceOfCtreeNodes();
      succesfulCtreesOfChild.put(tnode, seq);
    }
    seq.add(node);
  }

  private void putFailed(TraceNode tnode, CTreeNode node) {
    SequenceOfCtreeNodes seq = failedCtreesOfChild.get(tnode);
    if (seq == null) {
      seq = new SequenceOfCtreeNodes();
      failedCtreesOfChild.put(tnode, seq);
    }
    seq.add(node);
  }

  public SequenceOfCtreeNodes succesfulCTreesOfChild(int i) {
    return succesfulCtreesOfChild.get(children[i]);
  }

  public SequenceOfCtreeNodes failedCTreesOfChild(int i) {
    return failedCtreesOfChild.get(children[i]);
  }
  
  public int numberOfChildren(){
    return children.length;
  }

}
