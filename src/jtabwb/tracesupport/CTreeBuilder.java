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
package jtabwb.tracesupport;

import java.util.Collection;
import java.util.LinkedList;

import jtabwb.engine.RuleType;
import jtabwb.engine.Trace;
import jtabwb.engine.TraceNode;
import jtabwb.engine._AbstractRule;

/**
 * Build a C-tree starting from a proof-trace.
 * 
 * @author Mauro Ferrari
 */
class CTreeBuilder {

  private Trace trace;
  private int counter = 1;

  public CTreeBuilder(Trace trace) {
    super();
    this.trace = trace;
  }

  /**
   * Build the collection of C-trees described by the trace provided as argument
   * to the constructor.
   * 
   * @return the collection of C-trees described by the trace provided by
   * argument to the constructor.
   */
  Collection<CTree> build() {
    TraceNode traceHead = trace.getProofSearchTree();

    // build the trees representing c-trees
    LinkedList<CTreeNode> ctreeRoots = _build(traceHead, null);

    if (ctreeRoots.size() == 0)
      return null;
    else { // build a c-tree object for every tree 
      LinkedList<CTree> ctrees = new LinkedList<CTree>();
      for (CTreeNode root : ctreeRoots)
        ctrees.add(new CTree(trace.getProver(), root, counter++));

      return ctrees;
    }
  }

  SequenceOfCtreeNodes _build(TraceNode currentTraceNode, CTreeNode parent) {
    _AbstractRule rule = currentTraceNode.getAppliedRule();
    RuleType ruleType = RuleType.getType(rule);
    switch (ruleType) {
    case FORCE_BRANCH_SUCCESS:
    case FORCE_BRANCH_FAILURE: {
      SequenceOfCtreeNodes list = new SequenceOfCtreeNodes();
      list.add(new CTreeNode(currentTraceNode, parent, currentTraceNode
          .getNodeDeterminingPremiseTreatedConclusion(), currentTraceNode.getStatus()));
      return list;
    }
    case CLASH_DETECTION_RULE: /*
                                * remove failed _ClashDetectionRule applications
                                */
      if (currentTraceNode.getChildren() != null) // current is a failed clash-detection rule application
        return _build(currentTraceNode.getChildren().getFirst(), parent);
      else { // the returned list only contains the C-tree corresponding to clash-detection-rule
        SequenceOfCtreeNodes list = new SequenceOfCtreeNodes();
        list.add(new CTreeNode(currentTraceNode, parent, currentTraceNode
            .getNodeDeterminingPremiseTreatedConclusion(), currentTraceNode.getStatus()));
        return list;
      }
    case META_BACKTRACK_RULE: {
      TraceNode[] children =
          currentTraceNode.getChildren().toArray(
              new TraceNode[currentTraceNode.getChildren().size()]);

      SequenceOfCtreeNodes result = new SequenceOfCtreeNodes();
      // every C-tree coming from one of the rules in this META_BACKTRACK_RULE
      // is a C-for the backtrack rule application
      for (int i = 0; i < children.length; i++)
        result.addAll(_build(children[i], parent));
      return result;
    }
    case BRANCH_EXISTS: {
      TraceNode[] children =
          currentTraceNode.getChildren().toArray(
              new TraceNode[currentTraceNode.getChildren().size()]);

      SequenceOfCtreeNodes result = new SequenceOfCtreeNodes();
      // for every C-tree of one of the conclusions of the rule, the c-tree
      // obtained by extending such a c-tree with this rule application is
      // a c-tree for this rule application
      for (int i = 0; i < children.length; i++) {
        SequenceOfCtreeNodes subtrees = _build(children[i], null);
        for (CTreeNode subtree : subtrees) {
          CTreeNode ct =
              new CTreeNode(currentTraceNode, parent,
                  currentTraceNode.getNodeDeterminingPremiseTreatedConclusion(),
                  subtree.getStatus());
          ct.addSuccessor(subtree);
          result.add(ct);
        }
      }
      return result;
    }
    case REGULAR:
      // build the trees corresponding to the children of the current node
      ChildrenWithCtreeNodes ctreesForChildren = new ChildrenWithCtreeNodes(currentTraceNode.getChildren());
      for (TraceNode child : currentTraceNode.getChildren())
        ctreesForChildren.put(child, _build(child, null));

      CombinatorialCTreeGenerator cg =
          new CombinatorialCTreeGenerator(currentTraceNode, parent, ctreesForChildren);
      return cg.generates();

    default:
      throw new ImplementationError();
    }
  }
}
