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

class VerboseModeSupport {

  static class MSG {

    static class ITERATION_INFO {

      static final String BACKTRACK_POINT_ADDED = "Stack size: [%s], BACKTRACK POINT added";
      static final String AND_BRANCH_POINT_ADDED = "Stack size: [%s], AND-BRANCH POINT added";
      static final String CLASH_RULE_NAME = "Rule name: %s";
      static final String FORCE_BRANCH_FAILURE_RULE_NAME = "Rule name: %s";
      static final String FORCE_BRANCH_SUCCESS_RULE_NAME = "Rule name: %s";
      static final String CLOSURE_CHECK = "Clash status: %s";
      static final String INITIAL_NODESET = "Initial goal: (0)\n%s";
      static final String MAIN_CYCLE_ITERATION = "[%s] ------------------------------------ [%s]";
      static final String STACK_SIZE = "Stack size [%s]";
      static final String STACK_TRACE_BEGIN = "Stack trace -->";
      static final String STACK_TRACE_END = "<--";
    }

    static class RULE_APPLICATION_DETAILS {

      static final String GENERATED_NODESET = "Generated node: %s";
      static final String BRANCH_EXISTS_NEXT_STEP =
          "Rule name [%s], conclusions [%d], treating conclusion [%d of %2$d]";
      static final String META_BACKTRACK_RULE_APPLICATION = "Rule name [%s], rules to try [%d]";
      static final String ON_RULE_COMPLETED_LISTENER =
          "Added [on rule completed] listener for [%s]";
      static final String ON_RULE_RESUMED_LISTENER = "Added [on rule resumed] listener for [%s]";
      static final String REGULAR_RULE_APPLICATION =
          "Rule name [%s], conclusions [%d], treating branch [%d of %2$d]";
      static final String RULE_DETAILS = "Details: %s";
      static final String SELECTED_BACKTRACK_RULE = "Rule [%d of %d] selected, [%s]";
      static final String SELECTED_BACKTRACK_RULE_FOR =
          "Rule [%d of %d] selected, [%s] on [%s]: %s";
      static final String SELECTED_MAIN_FORMULA = "Selected formula [%s]: %s";
    }

    static class RESUMED_NODE_INFO {

      static final String BACKTRACK_POINT_RESUMED = "Resumed BACKTRACK point. Node set %s";
      static final String BACKTRACK_TYPE = "Backtrack rule type [%s]";
      static final String BRANCH_POINT_RESUMED =
          "Resumed AND-BRANCH point generated at iteration [%d]";
      static final String BRANCH_POINT_RESUMED_NODE_SET = "Node set %s";
      static final String NO_MORE_BACKTRACK_POINTS =
          "No more backtrack points to resume; end of search.";
      static final String NO_MORE_BRANCH_POINTS =
          "No more AND-branch points to resume; end of search.";

    }

  }

  /**
   * Prints details on the initial node-set.
   * 
   * @param engine the engine performing the proof-search.
   * @param initialNodeSet the initial node-set.
   */
  static void printInitialSetInfo(final IterationInfo LAST_ITERATION_INFO, DFStack stack,
      _AbstractGoal initialNodeSet) {
    printIterationDetails(LAST_ITERATION_INFO, stack);
    iterationStepInfo(MSG.ITERATION_INFO.INITIAL_NODESET,
        indentWithPrefix("| ", initialNodeSet.format()));
  }

  /**
   * Prints details about the last iteration performed by the engine.
   */
  static void printIterationDetails(final IterationInfo LAST_ITERATION_INFO, DFStack stack) {
    println(MSG.ITERATION_INFO.MAIN_CYCLE_ITERATION,
        String.valueOf(LAST_ITERATION_INFO.number_of_iterations), LAST_ITERATION_INFO.move.name());

    if (LAST_ITERATION_INFO.branch_point_added)
      iterationStepInfo(MSG.ITERATION_INFO.AND_BRANCH_POINT_ADDED,
          String.valueOf(stack.current_stack_size));
    else if (LAST_ITERATION_INFO.backtrack_point_added)
      iterationStepInfo(MSG.ITERATION_INFO.BACKTRACK_POINT_ADDED,
          String.valueOf(stack.current_stack_size));
    else
      iterationStepInfo(MSG.ITERATION_INFO.STACK_SIZE, String.valueOf(stack.current_stack_size));

    //if (printStackTrace) {
    // TODO: add option to control stack trace
    //      iterationStepInfo(MSG.ITERATION_INFO.STACK_TRACE_BEGIN);
    //      iterationStepInfo(indent(LAST_ITERATION_INFO.getStackTrace()));
    //      iterationStepInfo(MSG.ITERATION_INFO.STACK_TRACE_END);
    //}
  }

  /**
   * Prints details about the last applied rule.
   * 
   * @param ruleType the type of the last applied rule.
   * @param nextStepSelectedRule the rule selected for the next step, it must be
   * non-<code>null</code> only if the last applied rule was a META_BACKTRACK
   * rule.
   */
  static void printIterationInfo(final IterationInfo LAST_ITERATION_INFO, DFStack stack,
      RuleType ruleType, _AbstractRule nextStepSelectedRule, GoalNode currentGoal) {
    switch (ruleType) {
    case CLASH_DETECTION_RULE: {
      printIterationDetails(LAST_ITERATION_INFO, stack);
      iterationStepInfo(MSG.ITERATION_INFO.CLASH_RULE_NAME, LAST_ITERATION_INFO.applied_rule.name());
      iterationStepInfo(MSG.ITERATION_INFO.CLOSURE_CHECK,
          LAST_ITERATION_INFO.current_node_set_status.name());
    }
      break;

    case META_BACKTRACK_RULE: {
      printIterationDetails(LAST_ITERATION_INFO, stack);
      printMetaBacktrackDetails((_MetaBacktrackRule) LAST_ITERATION_INFO.applied_rule, 0, // the index of treated backtrack is 0 
          nextStepSelectedRule,false);

    }
      break;

    case REGULAR: {
      _RegularRule rrule = (_RegularRule) LAST_ITERATION_INFO.applied_rule;
      printIterationDetails(LAST_ITERATION_INFO, stack);
      printRegularRuleApplicationDetails(rrule, 1, currentGoal, false);
    }
      break;

    case BRANCH_EXISTS: {
      printIterationDetails(LAST_ITERATION_INFO, stack);
      printBranchExistsDetails((_BranchExistsRule) LAST_ITERATION_INFO.applied_rule, 1,
          currentGoal, false);
    }
      break;

    case FORCE_BRANCH_FAILURE: {
      ForceBranchFailure rule = (ForceBranchFailure) LAST_ITERATION_INFO.applied_rule;
      printIterationDetails(LAST_ITERATION_INFO, stack);
      printForceBranchFailureDetails(rule, currentGoal);
    }
      break;
    case FORCE_BRANCH_SUCCESS: {
      ForceBranchSuccess rule = (ForceBranchSuccess) LAST_ITERATION_INFO.applied_rule;
      printIterationDetails(LAST_ITERATION_INFO, stack);
      printForceBranchSuccessDetails(rule, currentGoal);
    }
      break;
    default:
      throw new ImplementationError();
    }
  }

  /*
   * Prints details of a force branch success rule application.
   */
  static void printForceBranchSuccessDetails(ForceBranchSuccess appliedRule, GoalNode currentGoal) {
    iterationStepInfo(MSG.ITERATION_INFO.FORCE_BRANCH_SUCCESS_RULE_NAME, appliedRule.name());

    // listeners
    if (appliedRule instanceof _OnRuleCompletedListener)
      iterationStepInfo(MSG.RULE_APPLICATION_DETAILS.ON_RULE_COMPLETED_LISTENER, appliedRule.name());

    // print rule specific details if defined 
    if (appliedRule instanceof _RuleWithDetails)
      iterationStepInfo(MSG.RULE_APPLICATION_DETAILS.RULE_DETAILS,
          indent(((_RuleWithDetails) appliedRule).getDetails()));

  }

  /*
   * Prints details of a force branch failure rule application.
   */
  static void printForceBranchFailureDetails(ForceBranchFailure appliedRule, GoalNode currentGoal) {
    iterationStepInfo(MSG.ITERATION_INFO.FORCE_BRANCH_FAILURE_RULE_NAME, appliedRule.name());

    // listeners
    if (appliedRule instanceof _OnRuleCompletedListener)
      iterationStepInfo(MSG.RULE_APPLICATION_DETAILS.ON_RULE_COMPLETED_LISTENER, appliedRule.name());

    // print rule specific details if defined 
    if (appliedRule instanceof _RuleWithDetails)
      iterationStepInfo(MSG.RULE_APPLICATION_DETAILS.RULE_DETAILS,
          indent(((_RuleWithDetails) appliedRule).getDetails()));

  }

  /**
   * Prints details of a regular rule rule application.
   *
   * @param appliedRule the applied rule
   * @param treatedConclusion the index of the conclusion treated by the rule
   * application
   * @param currentGoal the current goal
   * @param isARestoredRuleApplication if true the engine is applying a rule
   * resumed from the engine internal stack; in this case the lister are not
   * added (they were added when the rule was applied for the first time).
   */
  static void printRegularRuleApplicationDetails(_RegularRule appliedRule, int treatedConclusion,
      GoalNode currentGoal, boolean isARestoredRuleApplication) {
    _AbstractFormula treatedFormula = appliedRule.mainFormula();
    iterationStepInfo(MSG.RULE_APPLICATION_DETAILS.SELECTED_MAIN_FORMULA,
        treatedFormula == null ? "[NONE]" : treatedFormula.shortName(),
        treatedFormula == null ? "[NONE]" : treatedFormula.format());
    int totalNumberoOfConclusions = appliedRule.numberOfSubgoals();
    iterationStepInfo(MSG.RULE_APPLICATION_DETAILS.REGULAR_RULE_APPLICATION, appliedRule.name(),
        totalNumberoOfConclusions, treatedConclusion);

    // listeners
    if (!isARestoredRuleApplication) {
      if (appliedRule instanceof _OnRuleCompletedListener)
        iterationStepInfo(MSG.RULE_APPLICATION_DETAILS.ON_RULE_COMPLETED_LISTENER,
            appliedRule.name());
      if (appliedRule instanceof _OnRuleResumedListener)
        iterationStepInfo(MSG.RULE_APPLICATION_DETAILS.ON_RULE_RESUMED_LISTENER, appliedRule.name());
    }

    // print rule specific details if defined 
    if (appliedRule instanceof _RuleWithDetails)
      iterationStepInfo(MSG.RULE_APPLICATION_DETAILS.RULE_DETAILS,
          indent(((_RuleWithDetails) appliedRule).getDetails()));

    iterationStepInfo(MSG.RULE_APPLICATION_DETAILS.GENERATED_NODESET,
        currentGoal.formatAsEngineNode());
  }

  /**
   * Prints details on a {@link RuleType#META_BACKTRACK_RULE} rule application.
   * 
   * @param appliedRule the applied {@link _MetaBacktrackRule}.
   * @param nextStepSelectedRule the rule selected for application.
   * @param isARestoredRuleApplication if true the engine is applying a rule
   * resumed from the engine internal stack; in this case the lister are not
   * added (they were added when the rule was applied for the first time).
   */
  static void printMetaBacktrackDetails(_MetaBacktrackRule appliedRule,
      int indexOfTreatedBacktrackRule, _AbstractRule nextStepSelectedRule,
      boolean isARestoredRuleApplication) {
    iterationStepInfo(MSG.RULE_APPLICATION_DETAILS.META_BACKTRACK_RULE_APPLICATION,
        appliedRule.name(), appliedRule.totalNumberOfRules());

    RuleType nextStepType = RuleType.getType(nextStepSelectedRule);
    int totalNumberOfRules = appliedRule.totalNumberOfRules();
    switch (nextStepType) {
    case REGULAR:
      iterationStepInfo(MSG.RULE_APPLICATION_DETAILS.SELECTED_BACKTRACK_RULE_FOR,
          indexOfTreatedBacktrackRule + 1, totalNumberOfRules, nextStepSelectedRule.name(),
          ((_RegularRule) nextStepSelectedRule).mainFormula().shortName(),
          ((_RegularRule) nextStepSelectedRule).mainFormula().format());
      break;
    case BRANCH_EXISTS:
      iterationStepInfo(MSG.RULE_APPLICATION_DETAILS.SELECTED_BACKTRACK_RULE_FOR,
          indexOfTreatedBacktrackRule + 1, totalNumberOfRules, nextStepSelectedRule.name(),
          ((_BranchExistsRule) nextStepSelectedRule).mainFormula().shortName(),
          ((_BranchExistsRule) nextStepSelectedRule).mainFormula().format());
      break;
    default:
      iterationStepInfo(MSG.RULE_APPLICATION_DETAILS.SELECTED_BACKTRACK_RULE,
          nextStepSelectedRule.name());
    }

    if (!isARestoredRuleApplication) {
      // listener added
      if (appliedRule instanceof _OnRuleCompletedListener)
        iterationStepInfo(MSG.RULE_APPLICATION_DETAILS.ON_RULE_COMPLETED_LISTENER,
            appliedRule.name());
      if (appliedRule instanceof _OnRuleResumedListener)
        iterationStepInfo(MSG.RULE_APPLICATION_DETAILS.ON_RULE_RESUMED_LISTENER, appliedRule.name());
    }

    // print rule specific details if defined 
    if (appliedRule instanceof _RuleWithDetails)
      iterationStepInfo(MSG.RULE_APPLICATION_DETAILS.RULE_DETAILS,
          indent(((_RuleWithDetails) appliedRule).getDetails()));

  }

  /**
   * Prints details on a {@link RuleType#BRANCH_EXISTS} rule application.
   * 
   * @param engine the engine performing the proof-search.
   * @param appliedRule the applied {@link _BranchExistsRule}.
   * @param treatedConclusion the treated conclusion of the
   * {@link _BranchExistsRule} application.
   * @param isARestoredRuleApplication if true the engine is applying a rule
   * resumed from the engine internal stack; in this case the lister are not
   * added (they were added when the rule was applied for the first time).
   */
  static void printBranchExistsDetails(_BranchExistsRule appliedRule, int treatedConclusion,
      GoalNode currentGoal, boolean isARestoredRuleApplication) {
    _AbstractFormula treatedFormula = appliedRule.mainFormula();
    iterationStepInfo(MSG.RULE_APPLICATION_DETAILS.SELECTED_MAIN_FORMULA,
        treatedFormula == null ? "[NONE]" : treatedFormula.shortName(),
        treatedFormula == null ? "[NONE]" : treatedFormula.format());
    iterationStepInfo(MSG.RULE_APPLICATION_DETAILS.BRANCH_EXISTS_NEXT_STEP, appliedRule.name(),
        appliedRule.numberOfBranchExistsSubgoals(), treatedConclusion);

    if (!isARestoredRuleApplication) {
      // listener added
      if (appliedRule instanceof _OnRuleCompletedListener)
        iterationStepInfo(MSG.RULE_APPLICATION_DETAILS.ON_RULE_COMPLETED_LISTENER,
            appliedRule.name());
      if (appliedRule instanceof _OnRuleResumedListener)
        iterationStepInfo(MSG.RULE_APPLICATION_DETAILS.ON_RULE_RESUMED_LISTENER, appliedRule.name());
    }

    // print rule specific details if defined 
    if (appliedRule instanceof _RuleWithDetails)
      iterationStepInfo(MSG.RULE_APPLICATION_DETAILS.GENERATED_NODESET,
          indent(((_RuleWithDetails) appliedRule).getDetails()));

    iterationStepInfo(MSG.RULE_APPLICATION_DETAILS.GENERATED_NODESET, currentGoal.toString());
  }

  // FROM DFSTACK VERBOSE

  static void printNoMoreBacktrackPoints() {
    iterationStepInfo(MSG.RESUMED_NODE_INFO.NO_MORE_BACKTRACK_POINTS);
  }

  static void printNoMoreBranchPoints() {
    iterationStepInfo(MSG.RESUMED_NODE_INFO.NO_MORE_BRANCH_POINTS);
  }

  /*
   * Prints details about the resumed node
   */
  static void printResumedBacktrackPointInfo(EnginePlain engine, _AbstractRule rule) {
    printIterationDetails(engine.LAST_ITERATION_INFO, engine.stack);
    iterationStepInfo(MSG.RESUMED_NODE_INFO.BACKTRACK_POINT_RESUMED,
        engine.stack.restored_DFStackNode.premiseEngineNode.formatAsRestoredEngineNode());
    if (engine.stack.restored_DFStackNode.appliedRule instanceof _MetaBacktrackRule) {
      iterationStepInfo(MSG.RESUMED_NODE_INFO.BACKTRACK_TYPE, RuleType.META_BACKTRACK_RULE.name());
      printMetaBacktrackDetails((_MetaBacktrackRule) engine.stack.restored_DFStackNode.appliedRule,
          ((DFStackNode_MetaBacktrack) engine.stack.restored_DFStackNode)
              .getIndexOfLastTreatedRule(), rule, true);
    } else {
      iterationStepInfo(MSG.RESUMED_NODE_INFO.BACKTRACK_TYPE, RuleType.BRANCH_EXISTS.name());
      printBranchExistsDetails((_BranchExistsRule) engine.stack.restored_DFStackNode.appliedRule,
          ((DFStackNode_BranchExists) engine.stack.restored_DFStackNode).nextToTreat,
          engine.currentGoal, true);
    }

  }

  /*
   * Prints details about the resumed branch node
   */
  static void printResumedBranchPointInfo(EnginePlain engine) {
    printIterationDetails(engine.LAST_ITERATION_INFO, engine.stack);
    iterationStepInfo(MSG.RESUMED_NODE_INFO.BRANCH_POINT_RESUMED,
        engine.stack.restored_DFStackNode.generated_at_iteration);
    iterationStepInfo(MSG.RESUMED_NODE_INFO.BRANCH_POINT_RESUMED_NODE_SET,
        engine.stack.restored_DFStackNode.premiseEngineNode.formatAsRestoredEngineNode());

    _RegularRule appliedRule = (_RegularRule) engine.stack.restored_DFStackNode.appliedRule;
    printRegularRuleApplicationDetails(appliedRule,
        ((DFStackNode_Branch) engine.stack.restored_DFStackNode).nextConclusionToTreat,
        engine.currentGoal, true);
  }

  // UTILITY METHODS

  private static String INDENT_STRING = "    ";

  /*
   * Returns the string where every occurrence <code>\n</code> is replaced with
   * an <code>indentation<code> string.
   */
  public static String indent(String str) {
    return str.replaceAll("\n", "\n" + INDENT_STRING);
  }

  static String indentWithPrefix(String prefix, String str) {
    String indent = INDENT_STRING + prefix;
    return indent + str.replaceAll("\n", "\n" + indent);
  }

  // PRINTING METHODS

  private static final String INFO_TAB = "    ";

  static void iterationStepInfo(String key, Object... args) {
    System.out.println(INFO_TAB + String.format(key, args));
  }

  static void println(String key, Object... args) {
    System.out.println(String.format(key, args));
  }

}
