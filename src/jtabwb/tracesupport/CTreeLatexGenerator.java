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

import java.io.PrintStream;
import java.util.Collection;
import java.util.LinkedList;

import jtabwb.engine.ForceBranchFailure;
import jtabwb.engine.ProofSearchResult;
import jtabwb.engine._AbstractGoal;
import jtabwb.engine._AbstractRule;
import jtabwb.engine._ClashDetectionRule;

/**
 * Latex generator for C-trees.
 * 
 * @author Mauro Ferrari
 */
class CTreeLatexGenerator {

  boolean index_node_set = false;
  boolean index_rule = false;

  /**
   * The style of the generated proof.
   * 
   * @author Mauro Ferrari
   */
  public enum ProofStyle {
    /**
     * Sequent style proof.
     */
    SEQUENT,
    /**
     * Tableaux style proof.
     */
    TABLEAUX;
  }

  private Collection<CTree> ctrees;
  private _LatexCTreeFormatter formatter;
  private ProofStyle style;

  /**
   * Builds an instance of the translator which uses the specified latex
   * formatter to generate a proof for the specified trace.
   * 
   * @param trace the trace describing the proof.
   * @param formatter the LaTeX formatter.
   */
  public CTreeLatexGenerator(CTree ctree, _LatexCTreeFormatter formatter)
      throws TraceSupportException {
    super();
    this.formatter = formatter;
    ctrees = new LinkedList<CTree>();
    ctrees.add(ctree);
    this.index_node_set = formatter.generateNodeSetIndex();
    this.index_rule = formatter.generateRuleIndex();
  }

  /**
   * Builds an instance of the translator which uses the specified latex
   * formatter to generate a proof for the specified trace.
   * 
   * @param trace the trace describing the proof.
   * @param formatter the LaTeX formatter.
   */
  public CTreeLatexGenerator(Collection<CTree> ctrees, _LatexCTreeFormatter formatter)
      throws TraceSupportException {
    super();
    this.formatter = formatter;
    this.ctrees = ctrees;
    this.index_node_set = formatter.generateNodeSetIndex();
    this.index_rule = formatter.generateRuleIndex();
  }

  private final String LATEX_PREAMBLE = //
      "\\documentclass[10pt]{article}\n" + //
          "\\usepackage{color}\n" + //
          "\\usepackage{xifthen}\n" + //
          "\\pdfpagewidth 200in\n" + //
          "\\pdfpageheight 100in\n" + //
          "\\DeclareMathSizes{10}{12}{12}{8}\n" + //
          "\\pagestyle{empty}" //
  ;

  private final String SEQ_MACRO = "\\usepackage{proof}\n";

  private final String TAB_MACRO = //
      "\\newcommand{\\Tab}[3]{\\frac{\\phantom{a}\\stackrel{\\textstyle #1}"
          + "{\\phantom{\\scriptscriptstyle .}}\\phantom{a}}" + //
          "{\\stackrel{\\phantom{\\scriptscriptstyle .}}{\\textstyle #2}}{\\scriptstyle #3} }";

  private final String LATEX_BEGIN_DOCUMENT = //
      "\\begin{document}\n" + //
          "\\thispagestyle{empty}\n"; //

  private final String LATEX_BEGIN_PROOF = "\\[\n";
  private final String LATEX_END_PROOF = "\\]\n\\vspace{4ex}";
  private final String LATEX_END_COCUMENT = "\\end{document}";

  /**
   * Generates the LaTeX proof and write it on the specified stream.
   * 
   * @param out the stream where where the proof is written.
   */
  public void generateLatex(PrintStream out) {
    switch (formatter.proofStyle()) {
    case SEQUENT:
    case TABLEAUX:
      break;
    default:
      throw new ImplementationError("Case not treated [" + style.name() + "].");
    }

    out.print(LATEX_PREAMBLE);
    switch (formatter.proofStyle()) {
    case SEQUENT:
      out.print(SEQ_MACRO);
      break;
    case TABLEAUX:
      out.print(TAB_MACRO);
      break;
    }
    String str = formatter.getPreamble();
    if (str != null)
      out.println(str + "\\par");
    out.print(LATEX_BEGIN_DOCUMENT);
    str = formatter.getIntro();
    if (str != null)
      out.println(str + "\\par");
    _generateLatex(out);
    out.print(LATEX_END_COCUMENT);
  }

  private final String INTEST =
      "\\noindent{\\Huge Number of generated C-trees: %d}\\vspace{4ex}\n\n";
  private final String CTREE_TITLE = "\\section*{C-tree %d, prover %s, status %s}";

  private void _generateLatex(PrintStream out) {
    if (ctrees.size() == 1) {
      CTree ctree = ctrees.iterator().next();
      String pre = formatter.pre(ctree);
      out.print(pre == null ? "" : pre);
      out.print(LATEX_BEGIN_PROOF);
      LinkedList<String> annotations = new LinkedList<String>();
      switch (formatter.proofStyle()) {
      case SEQUENT:
        _generateCTreeSequentSyle(out, ctrees.iterator().next().getRoot(), annotations);
        break;
      case TABLEAUX:
        _generateCTreeTableauSyle(out, ctrees.iterator().next().getRoot(), annotations);
        break;
      }
      out.print(LATEX_END_PROOF);
      if (formatter.generateFailureGoalAnnotations())
        printAnnotations(out, annotations);
      String post = formatter.post(ctree);
      out.print(post == null ? "" : post);
    } else {
      out.println(String.format(INTEST, ctrees.size()));
      int counter = 1;
      for (CTree ctree : ctrees) {
        out.println(String.format(CTREE_TITLE, counter++, correctName(ctree.getProver()
            .getProverName().getProperNoun()), ctree.getStatus().toString()));
        String pre = formatter.pre(ctree);
        out.print(pre == null ? "" : pre);
        out.print(LATEX_BEGIN_PROOF);
        node_set_counter = 1;
        rule_counter = 1;
        LinkedList<String> annotations = new LinkedList<String>();
        switch (formatter.proofStyle()) {
        case SEQUENT:
          _generateCTreeSequentSyle(out, ctree.getRoot(), annotations);
          break;
        case TABLEAUX:
          _generateCTreeTableauSyle(out, ctree.getRoot(), annotations);
          break;
        default:
          throw new ImplementationError();
        }
        out.print(LATEX_END_PROOF);
        if (formatter.generateFailureGoalAnnotations())
          printAnnotations(out, annotations);
        String post = formatter.post(ctree);
        out.print(post == null ? "" : post);
        out.print("\n\n");
        out.print("\\newpage");
      }
    }
  }

  private void printAnnotations(PrintStream out, LinkedList<String> annotations) {
    if (annotations == null || annotations.size() == 0)
      return;
    out.print("\\begin{itemize}\n");
    for (String s : annotations)
      out.print(s);
    out.print("\\end{itemize}\n\n");
  }

  // format for inference rule \infer[rule_name]{prem}{cons1 & ... & consn}
  private final String INFER_FORMAT_1 = "\\infer[";
  private final String INFER_FORMAT_2 = "]{\n";
  private final String INFER_FORMAT_3 = "}{\n";
  private final String INFER_FORMAT_4 = "}\n";
  private final String INFER_FORMAT_AND = "&\n";
  private final String NODE_SET_INDEX = "\\mbox{$_{%d}$}";
  private final String RULE_INDEX = "\\mbox{\\small~$(%d)$}";
  private final String BRANCH_FAILURE_ANNOTATION_FMT = "\\item $\\sigma_{%d}$ brach FAILURE: $%s$";
  private final String NO_MORE_RULE_FAILURE_ANNOTATION_FMT =
      "\\item $\\sigma_{%d}$ no rule can be applied";

  int node_set_counter = 1;
  int rule_counter = 1;

  /* Generates a list of annotations to be printed after the c-tree */
  private void _generateCTreeSequentSyle(PrintStream out, CTreeNode ctree,
      LinkedList<String> annotations) {
    _AbstractRule rule = ctree.getAppliedRule();
    _AbstractGoal premise = ctree.getNodeSet();
    if ((rule instanceof _ClashDetectionRule) && (ctree.getStatus() == ProofSearchResult.FAILURE)) {
      out.print(formatter.format(premise)
          + (index_node_set ? String.format(NODE_SET_INDEX, node_set_counter) : ""));
      annotations.add(String.format(NO_MORE_RULE_FAILURE_ANNOTATION_FMT, node_set_counter));
      node_set_counter++;
    } else if (rule instanceof ForceBranchFailure) {
      out.print(formatter.format(premise)
          + (index_node_set ? String.format(NODE_SET_INDEX, node_set_counter) : ""));
      annotations.add(String.format(BRANCH_FAILURE_ANNOTATION_FMT, node_set_counter,
          formatter.formatRuleName(rule)));
      node_set_counter++;
    } else {
      out.print(INFER_FORMAT_1);
      out.print(formatter.formatRuleName(rule)
          + (index_rule ? String.format(RULE_INDEX, rule_counter++) : ""));
      out.print(INFER_FORMAT_2);
      out.print(formatter.format(premise)
          + (index_node_set ? String.format(NODE_SET_INDEX, node_set_counter++) : ""));
      out.print(INFER_FORMAT_3);
      LinkedList<CTreeNode> children = ctree.getSuccessors();
      if (children != null) {
        CTreeNode[] premises = children.toArray(new CTreeNode[children.size()]);
        for (int i = 0; i < premises.length; i++) {
          _generateCTreeSequentSyle(out, premises[i], annotations);
          if (i < premises.length - 1)
            out.print(INFER_FORMAT_AND);
        }
      }
      out.print(INFER_FORMAT_4);
    }
  }

  private final String TAB_FORMAT_1 = "\\Tab{";
  private final String TAB_FORMAT_2 = "\n}{";
  private final String TAB_FORMAT_3 = "\n}{";
  private final String TAB_FORMAT_4 = "\n}";
  private final String TAB_FORMAT_OR = "~|~";

  // TODO: annotations must be implemented
  private void _generateCTreeTableauSyle(PrintStream out, CTreeNode ctree,
      LinkedList<String> annotations) {
    _AbstractRule rule = ctree.getAppliedRule();
    _AbstractGoal premise = ctree.getNodeSet();
    if ((rule instanceof _ClashDetectionRule) && (ctree.getStatus() == ProofSearchResult.FAILURE))
      out.print(formatter.format(premise));
    else {
      out.print(TAB_FORMAT_1);
      out.print(formatter.format(premise));
      out.print(TAB_FORMAT_2);
      LinkedList<CTreeNode> children = ctree.getSuccessors();
      if (children != null) {
        CTreeNode[] premises = children.toArray(new CTreeNode[children.size()]);
        for (int i = 0; i < premises.length; i++) {
          _generateCTreeTableauSyle(out, premises[i], annotations);
          if (i < premises.length - 1)
            out.print(TAB_FORMAT_OR);
        }
      }
      out.print(TAB_FORMAT_3);
      out.print(formatter.formatRuleName(rule));
      out.print(TAB_FORMAT_4);
    }
  }

  // private void _generatesDerivationsTableauxStyle(PrintStream out) {
  // // if (ctrees.size() == 1) {
  // // out.print(LATEX_BEGIN_PROOF);
  // // //_generateCTreeSequentSyle(out, ctrees.iterator().next());
  // // out.print(LATEX_END_PROOF);
  // // } else {
  // // out.println(String.format(INTEST, ctrees.size()));
  // // int counter = 1;
  // // for (CTreeNode ct : ctrees) {
  // // out.println(String.format(BEGIN_TABLE, counter++));
  // // out.print(LATEX_BEGIN_PROOF);
  // // _generateADerivationTableauxStyle(out, ct);
  // // out.print(LATEX_END_PROOF);
  // // out.println(String.format(END_TABLE));
  // // }
  // // }
  // }
  //
  // private void _generateADerivationTableauxStyle(PrintStream out, CTreeNode
  // ctree) {
  // _AbstractRule rule = ctree.getAppliedRule();
  // _AbstractNodeSet premise = ctree.getNodeSet();
  // out.print(TAB_FORMAT_1);
  // out.print(formatter.format(premise));
  // out.print(TAB_FORMAT_2);
  // LinkedList<CTreeNode> children = ctree.getSuccessors();
  // if (children != null) {
  // CTreeNode[] premises = children.toArray(new CTreeNode[children.size()]);
  // for (int i = 0; i < premises.length; i++) {
  // _generateADerivationTableauxStyle(out, premises[i]);
  // if (i < premises.length - 1)
  // out.print(TAB_FORMAT_OR);
  // }
  // }
  // out.print(TAB_FORMAT_3);
  // out.print(formatter.formatRuleName(rule));
  // out.print(TAB_FORMAT_4);
  // return;
  // }

  private static String[][] replacement = new String[][] { { "_", "\\\\_" }, //
      { "\\$", "\\$" } };

  private static String correctName(String name) {
    String result = name;
    for (int i = 0; i < replacement.length; i++)
      result = result.replaceAll(replacement[i][0], replacement[i][1]);
    return result;
  }

}
