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
package jtabwb.launcher;

/**
 * Class collecting the strings used by this package.
 * 
 * @author Mauro Ferrari
 * 
 */
class MSG {

  final static String INDENT = "   ";

  static class LAUNCHER {

    static class CONFIGURATION_EXCEPTIONS {

      static final String INCONSISTENT_CONFIGURATION = "Incompatible flags [%s] and [%s].";
      static final String PROVER_INSTANTIATION_ERROR =
          "Prover [%s] has not an accessible nullary constructor.";
      static final String PROVER_NAME_ALREADY_DEFINED =
          "A prover with name [%s] is already defined.";
      static final String PROVER_WRONG_NAME = "The prover name cannot be null or the empty string.";
      static final String READER_INSTANTIATION_ERROR =
          "Reader [%s] has not an accessible nullary constructor.";
      static final String READER_NAME_ALREADY_DEFINED =
          "A reader with name [%s] is already defined.";
      static final String READER_WRONG_NAME = "The reader name cannot be null or the empty string.";
    }

    static class ERROR_MSG {

      static final String LAUNCH_EXECUTION_EXCEPTION =
          "The following exception occured durign launch execution:\n%s";

      static final String IO_EXCEPTION = "IO EXCEPTION... %s";
      static final String INITIAL_NODE_BUILDER_ERROR = "PROBLEM BUILDING NODE SET - %s";
      static final String INPUT_AND_FILENAMES =
          "Some filename is specified but the [-i] option is set.";

      static final String LATEX_CTREES_NO_CTREE_DEFINED =
          "CTREES (LaTeX) GENERATION: no c-tree defined by the proof-search!";
      static final String LATEX_GENERATION_EXCEPTION = "LATEX GENERATION: %s.";
      static final String LATEX_PROOF_NOT_A_PROOF_TRACE =
          "PROOF (LaTeX) GENERATION: translation can be performed only for a successful proof-search.";
      static final String LATEX_SUPPORT_REQUIRED =
          "LaTeX generator: [%s] does not provide the LaTeX support.";

      static final String LOG_DIR_CANNOT_BE_CREATED = "Log directory [%s] cannot be created: %s";
      static final String LOG_DIR_IS_NOT_A_DIR = "Log selected directory [%s] is not a directory.";
      static final String LOG_FILE_CANNOT_BE_GENERATED = "Log file cannot be generated.";

      static final String NO_INPUT_IS_SPECIFIED = "No input specified!!";
      static final String NO_SUCH_FILE = "No such file: %s";

      static final String PARSER_EXCEPTION = "PARSER ERROR... %s";
      static final String PROBLEM_WRONG_FORMAT = "PROBLEM DESCRIPTION - wrong format: %s";

      static final String TESTSET_FILE_CANNOT_BE_CREATED =
          "Testset log file [%s] cannot be created: %s";
      static final String TESTSET_TMP_FILE_CANNOT_BE_CREATEd =
          "Testset temporary file cannot be created: %s";

    }

    static class PROOF_SEARCH_INFO {

      static final String CHECK_PASSED = "PASSED";
      static final String CHECK_FAILED = "FAILED";
      static final String UNCHECKED = "UNCHECKED";
      static final String INFO_SEPARATOR =
          "****************************************************************";
      static final String STDIN_READER_DETAILS = "** Std-input reader [%s]";
      static final String PROVER_DETAILS = "** Prover [%s]";
      static final String TEST_DETAILS =
          "** Problem [%s], status [%s], proof-search [%s], test [%s]"; //
      static final String PROBLEM_DETAILS = "** Problem [%s], status [%s]";
      static final String READER_DETAILS = "** Reader [%s]";
      static final String STAT_STRING =
          "** Generated nodes [%d], restored backtrack-points [%d], restored branch-points [%d]";
      static final String TESTSET_EXEC_CONFIG_INFO = "--Reader [%s], Prover [%s]\n";
      static final String TESTSET_PROBLEM_INFO = "** [%s], status [%s],";
      static final String TOTAL_TIME_1 = "** Proof time (PS + NSC + PP): (ms) [%d]";
      static final String TOTAL_TIME_2 = "** Proof time (PS + NSC + PP): (ms) [%d] (hh:mm:ss + ms) [%s]";
      static final String TIMINGS_DETAILS = "** Timings (ms): PS (proof-search) [%d]," //
          + " NSC (initial node set) [%d], PP (problem parsing) [%d]";
    }

    static class INVOCATION_EXCEPTIONS {

      static final String AVAILABLE_READERS_EMPTY =
          "No reader has been defined. Readers must be added invoking the configProblemDescriptionReader() method.";
      static final String AVAILABLE_PROVERS_EMPTY =
          "No prover has been defined. Provers must be added invoking the configTheoremProver() method.";
      static final String INITIAL_NODE_SET_BUILDER_UNDEFINED =
          "No initial node set builder has been defined, set it with configureInitialNodeSetBuilder().";
      static final String PROCESS_OPTIONS_NOT_EXECUTED =
          "processCmdLineArguments() must be invoked before launch().";
      static final String STDIN_PARSER_UNDEFINED =
          "The problem reader for standard input is undefined, define it using the configStandardInputReader() method.";

    }

    static class INFO {

      static final String INITIAL_NODE_SET_BUILDING_BEGIN = "> Building initial node set...";
      static final String INITIAL_NODE_SET_BUILDING_END = " time (ms) [%d]";

      static final String LATEX_GENERATION_BEGIN = "> Generating latex... ";
      static final String LATEX_GENEATION_DONE = "saved in [%s]";
      static final String LOG_FILE_GENERATED = "> Log saved in [%s]";
      static final String LOG_TIME_FILE_GENERATED = "> Time log saved in [%s]";

      static final String PROBLEM_DESCRIPTION_PARSING_BEGIN = "> Parsing problem...";
      static final String PROBLEM_DESCRIPTION_PARSING_END = " time (ms) [%d]";
      static final String PROVING_BEGIN = "> Proving...";

      static final String TRACE_GENERATION_BEGIN = "> Generating trace...";
      static final String TRACE_GENERATION_END = "saved in [%s]";

      static final String STDIN_INPUT_PARSING_PROBLEM_BEGIN =
          "> Reading input problem with [%s]... ";
      static final String STDIN_INPUT_PARSING_PROBLEM_END = " time (ms) [%d]";
      static final String STDIN_INPUT_TYPE_A_FORMULA = "> Type a formula:";

    }

    static class TESTSET_INFO {

      static final String SINGLE_OUTPUT_RESULT =
          " proof-search [%s], test [%s] %s\n" //
              + INDENT //
              + "Iterations [%d], max_stack [%d], nodes [%d], branches [%d], backtracks [%d]\n"
              + INDENT //
              + "Times (sec.): total [%s], proof-search [%s], initial node set construction [%s], problem parsing [%s]"; //

      static final String PROBLEMS_PREAMBLE =
          "** Single problems report, columns description:\n" // 
              + "**   test: problem, status, proof-search result, test result;\n"
              + "**   times (ms): proof-time (PS + NSC), PS (proof-search), NSC (initial node set construction), PP (problem parsing);\n" //
              + "**   details: iterations, generated nodes, max stack size, visited branches, failed branches;\n" //
              + "**   problem source file";
      static final String TESTSET_LOGFILE_RESULT =
          "test: %s, %s, %s, %s; times (ms): %d, %d, %d, %d; details: %d; %d; %d; %d; %d; %s";
      static final String TESTSET_LOGFILE_PROVER_NAME = "** Prover: %s; version=%s; variant=(%s)";
      static final String TESTSET_LOGFILE_TESTSETNAME = "** Testset: %s";
      static final String TESTSET_PROBLEMS =
          "** Problems: total [%d], provable [%d], unprovable [%d], unknown status [%d]";
      static final String TESTSET_TEST =
          "** Test report: failed [%d], successful [%d], unchecked [%d]";
      static final String TESTSET_PROOF_TIME_1 = "** Total proof time (PS + NSC) (sec): [%s]";
      static final String TESTSET_PROOF_TIME_2 =
          "** Total proof time (PS + NSC) (sec): [%s] (hh:mm:ss + ms) [%s]";
      static final String TESTSET_TOTAL_TIME_1 = "** Total proof time (PS + NSC + PP) (sec): [%s]";
      static final String TESTSET_TOTAL_TIME_2 =
          "** Total proof time (PS + NSC + PP) (sec): [%s] (hh:mm:ss + ms) [%s]";
      static final String TIMINGS_DETAILS = "** Total timings (sec): PS (proof-search) [%s]," //
          + " NSC (initial node set) [%s], PP (problem parsing) [%s]";
      static final String TESTSET_LOGFILE_SEPARATOR =
          "****************************************************************";
      static final String TESTSET_LOGFILE_ROW_SEPARATOR =
          "** --------------------------------------------------------------";
      static final String TESTSET_LOGFILE_CANNOT_BE_OPENED =
          "Testset temporary file cannot be opened: %s";
      static final String TESTSET_PROVER = "** Prover [%s]";
    }

    static class TIME_STR {

      //static final String JTABWB_TEST_STRING = "%s: %s, %s, %s; times (seconds): %s, %s, %s, %s";
      static final String JTABWB_DETAILS_STRING_LEGENDA =
          "problem name; problem status; proof search result; test result; total proof-time; proof-search time; initial node set construction; problem parsing time; generated nodes; resumed backtrack points; resumed branch points";
      static final String JTABWB_DETAILS_STRING = "%s; %s; %s; %s; %s; %s; %s; %s; %d; %d; %d";
      static final String JTABWB_TIME_STRING = "%d.%03d";
      static final String F3_TIME_STRING = "%d.%03d seconds";
    }

  }

  static class IMPLEMENTATION_ERROR {

    static final String CASE_NOT_IMPLEMENTED = "Case not implemented!";
    static final String ARGUMENT_CANNOT_BE_NULL = "Argument [%s] cannot be null!";
  }

  static class CMD_LINE_OPTIONS {

    static class INFO {

      static final String AVAILABLE_PROVERS = "Available provers (* is the default value):\n%s";
      static final String AVAILABLE_READERS = "Available readers (* is the default value):\n%s";
      static final String HELP_HEADER = "Laucher for the prover.";
      static final String HELP_FOOTER = "";
    }

    static class EXCEPTIONS {

      static final String LAUNCHER_OPTION_NAME_REDEFINITION_ERROR =
          "ERROR - setCmdLineOptions - option [%s] is one of reserved launcher options.";

    }

    static class ERRORS {

      static final String INVOCATION_ERROR = "Command line parsing failed: %s";
      static final String NO_READER_WITH_NAME =
          "There is not a problem description reader with name [%s].";
      static final String NO_PROVER_WITH_NAME = "There is not a prover with name [%s].";
    }

    static class OPTIONS_DESCRIPTIONS {

      static final String HELP = "Print usage message and exit.";
      static final String HELP_DETAILED = "Print this help message and exit.";
      static final String F3_TIME_STR = "Prints on the standard output timing info in f3 format.";
      static final String JTABWB_TIME_STR =
          "Prints on the standard output timing info in JTabWb format.";
      static final String INPUT_FORMULA = "Read the formula from standard input.";
      static final String LATEX_CTREE =
          "Save the LaTeX of the c-trees generated by the proof-search in [%s].";
      static final String LATEX_CTREE_FILE =
          "Save the LaTeX of the c-trees generated by the proof-search in the specified file.";
      static final String LATEX_PROOF =
          "Save the LaTeX of the proof generated by a successful proof-search in [%s].";
      static final String LATEX_PROOF_FILE =
          "Save the LaTeX of the proof generated by a successful proof-search in the specified file.";
      static final String LIST_READER = "Detailed description of available readers.";
      static final String LIST_PROVERS = "Detailed description of available provers.";
      static final String LOG = "Save execution details in [%s].";
      static final String LOG_FILE = "Save execution details in the the specified file.";
      static final String LOG_TIME = "Save time execution details in the specified file.";
      static final String READER =
          "Set the formula reader, if this option is not set the default is used. Available readers (* is default): %s";
      static final String PROVER =
          "Set the theorem prover, if this option is not set the default is used. Available provers (* is default): %s";
      static final String TRACE = "Generate the trace of the proof-search.";
      static final String PTRACE = "Save the trace of the proof-search in [%s].";
      static final String PTRACE_FILE = "Save the trace of the proof-search in the specified file.";
      static final String VERBOSE = "Print detailed informations on the proof process.";
      static final String TESTSET =
          "Execute a test on the problems specified on the command line. test-name is used to name the log files.";
      static final String LOGDIR = "Defines the directory used to save log files.";
    }

  }

}
