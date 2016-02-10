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
 *******************************************************************************/
package jtabwb.engine;

import java.util.LinkedList;

class OnCompletedRuleHandler {

  public OnCompletedRuleHandler(DFStack dfStack) {
    super();
    this.dfStack = dfStack;
    this.head = new Node();
    this.head.nodeIndex = 0;
    this.top = this.head;
  }

  private final DFStack dfStack;
  private final Node head;
  private Node top;

  private static class Node {

    Node previousNode;
    int nodeIndex;
    LinkedList<_OnRuleCompletedListener> listeners = new LinkedList<_OnRuleCompletedListener>();
  }

  void add(_OnRuleCompletedListener listener) {
    if (dfStack.current_stack_size == top.nodeIndex)
      top.listeners.addFirst(listener);
    else {
      Node newNode = new Node();
      newNode.nodeIndex = dfStack.current_stack_size;
      newNode.listeners.addFirst(listener);
      newNode.previousNode = top;
      top = newNode;
    }
  }

  void notify(ProofSearchResult result, int endIndex) {
    boolean goon = true;
    while (goon && top.nodeIndex >= endIndex) {
      _OnRuleCompletedListener listener;
      while ((listener = top.listeners.pollFirst()) != null) 
        listener.onCompleted(result);
      
      if (top != head)
        top = top.previousNode;
      else
        goon = false;
    }

  }
}
