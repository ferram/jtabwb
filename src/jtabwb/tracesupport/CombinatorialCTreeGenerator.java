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
package jtabwb.tracesupport;

import jtabwb.engine.ProofSearchResult;
import jtabwb.engine.TraceNode;
import jtabwb.engine._RegularRule;

/**
 * @author Mauro Ferrari
 */
class CombinatorialCTreeGenerator {

  TraceNode currentTrace;
  int appliedRuleNumberOfConclusions;
  CTreeNode parent;
  _RegularRule appliedRule;
  ChildrenWithCtreeNodes childrenWithNodes;

  //  the set of successful c-trees for conclusions up tp n
  CollectionOfArrayOfNCTrees[] succesfulUpTo;

  public CombinatorialCTreeGenerator(TraceNode currentTraceNode, CTreeNode parent,
      ChildrenWithCtreeNodes successorTrees) {
    this.childrenWithNodes = successorTrees;
    this.currentTrace = currentTraceNode;
    this.parent = parent;
    this.appliedRule = (_RegularRule) currentTraceNode.getAppliedRule();
    this.appliedRuleNumberOfConclusions = appliedRule.numberOfSubgoals();
    this.succesfulUpTo = new CollectionOfArrayOfNCTrees[currentTraceNode.getChildren().size()];
  }

  SequenceOfCtreeNodes generates() {
    SequenceOfCtreeNodes successfull = generateSuccesfull();
    SequenceOfCtreeNodes failed = generateFailed();
    if (successfull != null) {
      if (failed != null)
        successfull.addAll(failed);
      return successfull;
    }
    return failed;
  }

  SequenceOfCtreeNodes generateSuccesfull() {
    CollectionOfArrayOfNCTrees subderivations =
        succesfulUpToIndex(appliedRuleNumberOfConclusions - 1);
    if (subderivations.isEmpty())
      return null;
    SequenceOfCtreeNodes succesful = new SequenceOfCtreeNodes();
    for (CTreeNode[] sub : subderivations) {
      CTreeNode ctree =
          new CTreeNode(currentTrace, parent,
              currentTrace.getNodeDeterminingPremiseTreatedConclusion(), ProofSearchResult.SUCCESS);
      for (CTreeNode c : sub)
        ctree.addSuccessor(c);
      succesful.add(ctree);
    }
    return succesful;
  }

  SequenceOfCtreeNodes generateFailed() {
    CollectionOfArrayOfNCTrees subderivations = failedUpToIndex(childrenWithNodes.numberOfChildren() - 1);
    if (subderivations.isEmpty())
      return null;
    SequenceOfCtreeNodes failed = new SequenceOfCtreeNodes();
    for (CTreeNode[] sub : subderivations) {
      CTreeNode ctree =
          new CTreeNode(currentTrace, parent,
              currentTrace.getNodeDeterminingPremiseTreatedConclusion(), ProofSearchResult.FAILURE);
      for (CTreeNode c : sub)
        ctree.addSuccessor(c);
      failed.add(ctree);
    }
    return failed;
  }

  private CollectionOfArrayOfNCTrees failedUpToIndex(int n) {
    CollectionOfArrayOfNCTrees arrays = new CollectionOfArrayOfNCTrees(n + 1);
    if (n == 0) {
      if (childrenWithNodes.failedCTreesOfChild(0) != null) /*
                                                             * all the failed
                                                             * C-trees of
                                                             * conclusion 0,
                                                             * generates a
                                                             * failed C-tree for
                                                             * currentTrace.
                                                             */
        for (CTreeNode ct : childrenWithNodes.failedCTreesOfChild(0))
          arrays.generateCombinations(ct, null);
      return arrays;
    }

    // there is no failed c-tree for n-th child we have nothing to do
    if (childrenWithNodes.failedCTreesOfChild(n) == null)
      return arrays;

    // otherwise must exists at least a successful c-tree for child 0...(n-1)

    CollectionOfArrayOfNCTrees succesfull = succesfulUpToIndex(n - 1);
    if (succesfull.isEmpty())
      throw new ImplementationError();

    for (CTreeNode ct : childrenWithNodes.failedCTreesOfChild(n))
      arrays.generateCombinations(ct, succesfull);

    return arrays;
  }

  /*
   * Here n is index of a conclusion, the method returns a list containing all
   * the possible sequences of of successful C-trees for conclusions 0,...n.
   */
  private CollectionOfArrayOfNCTrees succesfulUpToIndex(int n) {
    CollectionOfArrayOfNCTrees result = new CollectionOfArrayOfNCTrees(n + 1);
    // if a child in 0...n does not have successful c-trees return null
    for (int i = 0; i <= n; i++)
      if (childrenWithNodes.succesfulCTreesOfChild(i) == null)
        return result;

    // if the set has already been constructed return it
    if (succesfulUpTo[n] != null)
      return succesfulUpTo[n];

    for (CTreeNode ct : childrenWithNodes.succesfulCTreesOfChild(n)) {
      /*
       * Builds all the sequences of length n extending all the possible
       * sequences of length (n - 1) with an element a successful C-tree for
       * conclusion n
       */
      if (n == 0)
        result.generateCombinations(ct, null);
      else
        result.generateCombinations(ct, succesfulUpToIndex(n - 1));
    }
    succesfulUpTo[n] = result;
    return result;
  }

}