/*******************************************************************************
 * Copyright (C) 2013, 2016 Mauro Ferrari
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
 * The type of a rule classify a rule applied during proof search. The type is
 * determined by the interface implemented by a rule. We remark that this type
 * is essentially redundant since the used value is completely determined by the
 * interface implemented by a rule, it is useful to simplify the source code of
 * the engine.
 * 
 * @author Mauro Ferrari
 */
public enum RuleType {
  /**
   * Is the type of a rule implementing {@link _BranchExistsRule}.
   */
  BRANCH_EXISTS,
  /**
   * Is the type of a rule implementing {@link _ClashDetectionRule}.
   */
  CLASH_DETECTION_RULE,
  /**
   * Is the type of a rule implementing {@link _MetaBacktrackRule}.
   */
  META_BACKTRACK_RULE,
  /**
   * Is the type of a rule implementing {@link _RegularRule}.
   */
  REGULAR,
  /**
   * Is the type of {@link ForceBranchFailure} rule which forces branch failure
   * and backtrack-point search.
   */
  FORCE_BRANCH_FAILURE,
  /**
   * Is the type of the {@link ForceBranchSuccess} rule which forces branch
   * success and branch-point search.
   */
  FORCE_BRANCH_SUCCESS;

  /**
   * Returns the type of the specified rule.
   * 
   * @param ruleToApply the rule.
   * @return the type of the rule.
   */
  public static RuleType getType(_AbstractRule ruleToApply) {
    if (ruleToApply instanceof ForceBranchFailure)
      return RuleType.FORCE_BRANCH_FAILURE;

    if (ruleToApply instanceof ForceBranchSuccess)
      return RuleType.FORCE_BRANCH_SUCCESS;

    if (ruleToApply instanceof _ClashDetectionRule)
      return RuleType.CLASH_DETECTION_RULE;

    if (ruleToApply instanceof _BranchExistsRule)
      return RuleType.BRANCH_EXISTS;

    if (ruleToApply instanceof _MetaBacktrackRule)
      return RuleType.META_BACKTRACK_RULE;

    if (ruleToApply instanceof _RegularRule)
      return RuleType.REGULAR;

    throw new ImplementationError(
        "IMPLEMENTATION ERROR -- the rule should be an instance of a proper subtype of _AbstractRule but it is: "
            + ruleToApply.getClass().getCanonicalName());
  }
}
