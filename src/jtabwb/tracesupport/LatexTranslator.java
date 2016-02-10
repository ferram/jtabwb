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

import jtabwb.engine.ProofSearchResult;
import jtabwb.engine.Trace;
import jtabwb.engine.TraceNode;
import jtabwb.engine._AbstractGoal;
import jtabwb.engine._AbstractRule;

import java.io.PrintStream;
import java.util.LinkedList;

/**
 * This class generates a LaTeX description of a proof starting from a pruned
 * trace describing a successful proof search (a proof).
 * @author Mauro Ferrari
 */
public class LatexTranslator {

	/**
	 * The style of the generated proof.
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

	private Trace trace;
	private _LatexCTreeFormatter formatter;
	private ProofStyle style;

	/**
	 * Builds an instance of the translator which uses the specified latex
	 * formatter to generate a proof for the specified trace.
	 * @param trace the trace describing the proof.
	 * @param formatter the LaTeX formatter.
	 */
	public LatexTranslator(Trace trace, _LatexCTreeFormatter formatter) throws TraceSupportException {
		super();
		if (trace.getStatus() != ProofSearchResult.SUCCESS)
			throw new TraceSupportException(TraceSupportMessageManager.getMsg(
			    "latex.trace.is.not.a.proof", trace.getStatus().name()));
		if (!trace.isPruned())
			throw new TraceSupportException(
			    TraceSupportMessageManager.getMsg("latex.trace.is.not.pruned"));
		this.trace = trace;
		this.formatter = formatter;
	}

	private final String LATEX_PREAMBLE = //
	"\\documentclass[10pt]{article}\n" + //
	    "\\usepackage{color}\n" + //
	    "\\usepackage{xifthen}\n" + //
	    "\\pdfpagewidth 200in\n" + //
	    "\\pdfpageheight 100in\n" + //
	    "\\DeclareMathSizes{10}{12}{12}{8}\n" //
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
	private final String LATEX_END_PROOF = "\\]\n";
	private final String LATEX_END_COCUMENT = "\\end{document}";

	/**
	 * Generates the LaTeX proof and write it on the specified stream.
	 * @param out the stream where where the proof is written.
	 */
	public void generateLatex(PrintStream out) {
		switch (formatter.proofStyle()) {
		case SEQUENT:
			generateSequentStyle(out);
			break;
		case TABLEAUX:
			generateTableauxStyle(out);
			break;
		default:
			throw new ImplementationError("Case not treated [" + style.name() + "].");
		}
	}

	private void generateSequentStyle(PrintStream out) {
		out.print(LATEX_PREAMBLE);
		out.print(SEQ_MACRO);
		String str = formatter.getPreamble();
		if (str != null)
			out.print(str);
		out.print(LATEX_BEGIN_DOCUMENT);
		str = formatter.getIntro();
		if (str != null)
			out.print(str);
		out.print(LATEX_BEGIN_PROOF);
		_AbstractGoal initialNodeSet = trace.getInitialNodeSet();
		_proofSequent(out, trace.getProofSearchTree(), initialNodeSet);
		out.print(LATEX_END_PROOF);
		out.print(LATEX_END_COCUMENT);
	}

	// format for inference rule \infer[rule_name]{prem}{cons1 & ... & consn}
	private final String INFER_FORMAT_1 = "\\infer[";
	private final String INFER_FORMAT_2 = "]{\n";
	private final String INFER_FORMAT_3 = "}{\n";
	private final String INFER_FORMAT_4 = "}\n";
	private final String INFER_FORMAT_AND = "&\n";

	private void _proofSequent(PrintStream out, TraceNode node, _AbstractGoal premise) {
		_AbstractRule rule = node.getAppliedRule();
		out.print(INFER_FORMAT_1);
		out.print(formatter.formatRuleName(rule));
		out.print(INFER_FORMAT_2);
		out.print(formatter.format(premise));
		out.print(INFER_FORMAT_3);
		LinkedList<TraceNode> children = node.getChildren();
		if (children != null) {
			TraceNode[] premises = children.toArray(new TraceNode[children.size()]);
			for (int i = 0; i < premises.length; i++) {
				_proofSequent(out, premises[i], premises[i].getPremise());
				if (i < premises.length - 1)
					out.print(INFER_FORMAT_AND);
			}
		}
		out.print(INFER_FORMAT_4);
		return;
	}

	private void generateTableauxStyle(PrintStream out) {
		out.print(LATEX_PREAMBLE);
		out.print(TAB_MACRO);
		String str = formatter.getPreamble();
		if (str != null)
			out.print(str);
		out.print(LATEX_BEGIN_DOCUMENT);
		str = formatter.getIntro();
		if (str != null)
			out.print(str);
		out.print(LATEX_BEGIN_PROOF);
		_AbstractGoal initialNodeSet = trace.getInitialNodeSet();
		_proofTableaux(out, trace.getProofSearchTree(), initialNodeSet);
		out.print(LATEX_END_PROOF);
		out.print(LATEX_END_COCUMENT);
	}

	private final String TAB_FORMAT_1 = "\\Tab{";
	private final String TAB_FORMAT_2 = "\n}{";
	private final String TAB_FORMAT_3 = "\n}{";
	private final String TAB_FORMAT_4 = "\n}";
	private final String TAB_FORMAT_OR = "~|~";

	private void _proofTableaux(PrintStream out, TraceNode node, _AbstractGoal premise) {
		_AbstractRule rule = node.getAppliedRule();
		out.print(TAB_FORMAT_1);
		out.print(formatter.format(premise));
		out.print(TAB_FORMAT_2);
		LinkedList<TraceNode> children = node.getChildren();
		if (children != null) {
			TraceNode[] premises = children.toArray(new TraceNode[children.size()]);
			for (int i = 0; i < premises.length; i++) {
				_proofTableaux(out, premises[i], premises[i].getPremise());
				if (i < premises.length - 1)
					out.print(TAB_FORMAT_OR);
			}
		}
		out.print(TAB_FORMAT_3);
		out.print(formatter.formatRuleName(rule));
		out.print(TAB_FORMAT_4);
		return;
	}

}
