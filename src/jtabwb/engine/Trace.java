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

import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A trace describes the steps performed during a proof search; it provides data
 * about:
 * <ul>
 * <li>the prover used to generate the proof-search;</li>
 * <li>the initial node-set of the proof-search;</li>
 * <li>the status of the proof-search;</li>
 * <li>the root of the trace-tree describing the performed proof search.</li>
 * </ul>
 * We call <em>trace tree</em> the tree describing the proof-search. The nodes
 * of the tree are instances of {@link TraceNode}; the head of the tree is
 * returned by the {@link Trace#getProofSearchTree()} method. Performing a
 * depth-first order traversal of the proof-search tree one gets the sequence of
 * rules applications performed by the prover during the proof-search. <h3>
 * Pruning a trace</h3> In general the proof-search tree contains both failed
 * branches and successful branches. A trace describing a <b>successful</b>
 * proof-search can be pruned, so to remove from the tree describing the
 * proof-search all the failed branches. A pruned proof search describes a
 * derivation in the calculus implemented by the prover.
 * 
 * @author Mauro Ferrari
 */
public class Trace implements Iterable<TraceNode> {

  private _AbstractGoal initialNodeSet; // the initial node-set of proof-search
  private TraceNode head;
  private ProofSearchResult status; // the status of the proof-search
  private boolean isPruned;
  private _Prover prover; // the prover performing the proof-search

  /**
   * Build a new trace.
   * 
   * @param initialNodeSet the initial node set.
   * @param prover the theorem prover used to generate the proof-trace.
   * @param head the head trace node.
   * @param the status for this trace.
   */
  Trace(_AbstractGoal initialNodeSet, _Prover prover, TraceNode head, ProofSearchResult status) {
    this.prover = prover;
    this.initialNodeSet = initialNodeSet;
    this.head = head;
    this.status = status;
    this.isPruned = false;
  }

  /**
   * Returns the root of the proof-search tree.
   * 
   * @return the proof search tree.
   */
  public TraceNode getProofSearchTree() {
    return head;
  }

  /**
   * Returns the initial node set (the node set at the root of the
   * proof-search).
   * 
   * @return the initial node set.
   */
  public _AbstractGoal getInitialNodeSet() {
    return initialNodeSet;
  }

  /**
   * Returns the prover used to generate this trace.
   * 
   * @return the prover used to generate this trace.
   */
  public _Prover getProver() {
    return prover;
  }

  /**
   * Returns the status of the proof-search described by this trace.
   * 
   * @return the status of the proof-search for this trace.
   */
  public ProofSearchResult getStatus() {
    return status;
  }

  /**
   * Returns <code>true</code> iff this trace has already been pruned.
   * 
   * @return <code>true</code> iff this trace is pruned.
   */
  public boolean isPruned() {
    return isPruned;
  }

  /**
   * Returns the iterator containing the nodes of the proof-search tree as
   * visited by a depth-first order traversal.
   * 
   * @return an iterator over the proof-search tree nodes.
   */
  @Override
  public Iterator<TraceNode> iterator() {
    return new TraceIterator(this);
  }

  /**
   * Prunes the trace-tree removing the inessential branches from the successful
   * trace. If this method has already been applied it terminates without
   * affecting this trace. If the method correctly ends, successive calls to
   * {@link #isPruned()} will return <code>true</code>. For a trace describing a
   * successful proof-search this methods removes all paths corresponding to
   * failed branches in the proof search.
   * 
   * @throws TraceException if this trace is not a successful trace and hence
   * pruning cannot be performed.
   */
  public void pruneSuccessful() throws TraceException {
    if (this.status != ProofSearchResult.SUCCESS)
      throw new TraceException(MSG.getMsg(MSG.TRACE.CANNOT_PRUNE, this.status.toString()));
    pruneSuccessfulTrace(head);
    isPruned = true;
  }

  /*
   * The recursive method performing pruning.
   */
  private void pruneSuccessfulTrace(TraceNode current) {
    _AbstractRule rule = current.appliedRule;
    RuleType ruleType = RuleType.getType(rule);
    switch (ruleType) {
    case CLASH_DETECTION_RULE: /*
                                * remove failed _ClashDetectionRule applications
                                */
      if (current.getChildren() != null) { // current is a failed clash detection
        if (current != head) {
          TraceNode newNode = current.children.get(0);
          current.parent.replaceChild(current, newNode);
          newNode.parent = current.parent;
          pruneSuccessfulTrace(newNode);
        } else {
          head = current.children.get(0);
          pruneSuccessfulTrace(head);
        }
      }
      break;
    case META_BACKTRACK_RULE: { /*
                                 * remove any _MetaBacktrackRule applications
                                 * and its failed branches
                                 */
      if (current == head) {
        head = current.children.getLast(); /*
                                            * the successful node is the last
                                            * added to the trace
                                            */
        pruneSuccessfulTrace(head);
      } else {
        TraceNode newNode = current.children.getLast(); /*
                                                         * the successful node
                                                         * is the last added to
                                                         * the trace
                                                         */
        current.parent.replaceChild(current, newNode);
        newNode.parent = current.parent;
        pruneSuccessfulTrace(newNode);
      }
    }
      break;
    case BRANCH_EXISTS: { // the successful branch is the last added to the trace
      TraceNode succesFullChild = current.children.getLast();
      current.children = new LinkedList<TraceNode>();
      current.children.add(succesFullChild);
      pruneSuccessfulTrace(succesFullChild);
    }
      break;
    case REGULAR: { // all children are successful nodes.
      for (int i = 0; i < current.children.size(); i++)
        pruneSuccessfulTrace(current.children.get(i));
    }
      break;
    default:
      throw new ImplementationError();
    }
  }

  @Override
  public String toString() {
    return "Trace generated by [" + prover.getProverName().getDetailedName()
        + "] prover.\nTrace status: " + status.name() + "\nInitial node set:\n"
        + initialNodeSet.format();
  }

  /**
   * Print a description of the trace on the specified stream.
   * 
   * @param out the stream where to write the trace.
   */
  public void print(PrintStream out) {
    out.println(toString());
    out.println("A trace node consists of: status, applied rule, successors, main formula.");
    for (TraceNode tn : this)
      out.println(tn.toString());
  }

}
