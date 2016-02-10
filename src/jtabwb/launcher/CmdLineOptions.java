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
package jtabwb.launcher;

import java.util.LinkedList;

import jtabwb.engine.Engine.ExecutionMode;
import jtabwb.launcher.Launcher.LaunchConfiguration;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

import ferram.CLIOptionsSupport.CLIOptionsSupport;

/**
 * The class managing the launcher command line options.
 * 
 * @author Mauro Ferrari
 * 
 */
class CmdLineOptions {

  private final Log LOG;

  static class OptNames {

    static String HELP = "h"; //
    static String INPUT = "i"; //
    static String READER = "r"; //
    static String PROVER = "p"; //
    static String VERBOSE = "v"; //

    static String F3_TIME_STR = "f3time"; //
    static String JTABWB_TIME_STR = "jtabwbtime"; //
    static String LATEX_CTREE = "latex-ctrees"; //
    static String LATEX_PROOF = "latex-proof"; //
    static String LIST_READERS = "readers"; //
    static String LIST_PROVERS = "provers"; //
    static String SAVE_TRACE = "save-trace"; //
    static String LOG = "log"; //
    static String LOG_TIME = "time";
    static String TESTSET = "testset";
    static String LOG_DIR = "logdir";

    static void defineIncompatibility(CLIOptionsSupport incomp) {
      incomp.addIncompatibility(VERBOSE, TESTSET);
      incomp.addIncompatibility(VERBOSE, LATEX_CTREE);
      incomp.addIncompatibility(VERBOSE, LATEX_PROOF);
      incomp.addIncompatibility(VERBOSE, SAVE_TRACE);
      incomp.addIncompatibility(TESTSET, VERBOSE);
      incomp.addIncompatibility(TESTSET, LATEX_CTREE);
      incomp.addIncompatibility(TESTSET, LATEX_PROOF);
      incomp.addIncompatibility(TESTSET, SAVE_TRACE);
      incomp.addIncompatibility(TESTSET, F3_TIME_STR);
      incomp.addIncompatibility(READER, INPUT);
    }
  }

  private CLIOptionsSupport incomp = new CLIOptionsSupport();
  LaunchConfiguration configuration;

  public CmdLineOptions(LaunchConfiguration configuration) {
    super();
    this.LOG = new Log();
    this.incomp = new CLIOptionsSupport();
    this.configuration = configuration;
    OptNames.defineIncompatibility(incomp);
  }

  void defineCmdLineOptions() {
    LinkedList<Option> lo = new LinkedList<Option>();

    // HELP
    lo.add(Option.builder(OptNames.HELP).desc(MSG.CMD_LINE_OPTIONS.OPTIONS_DESCRIPTIONS.HELP)
        .build());
    // INPUT
    lo.add(Option.builder(OptNames.INPUT)
        .desc(MSG.CMD_LINE_OPTIONS.OPTIONS_DESCRIPTIONS.INPUT_FORMULA).build());
    // LATEX_CTREE
    lo.add(Option.builder().longOpt(OptNames.LATEX_CTREE)
        .desc(MSG.CMD_LINE_OPTIONS.OPTIONS_DESCRIPTIONS.LATEX_CTREE_FILE).build());
    // LATEX_PROOF
    lo.add(Option.builder().longOpt(OptNames.LATEX_PROOF)
        .desc(MSG.CMD_LINE_OPTIONS.OPTIONS_DESCRIPTIONS.LATEX_PROOF_FILE).build());
    if (configuration.availableProvers.size() > 1) {
      // LIST_PROVERS
      lo.add(Option.builder().longOpt(OptNames.LIST_PROVERS)
          .desc(MSG.CMD_LINE_OPTIONS.OPTIONS_DESCRIPTIONS.LIST_PROVERS).build());
      // PROVER
      if (configuration.availableProvers.size() > 1)
        lo.add(Option
            .builder(OptNames.PROVER)
            .hasArg()
            .argName("name")
            .desc(
                String.format(MSG.CMD_LINE_OPTIONS.OPTIONS_DESCRIPTIONS.PROVER,
                    configuration.availableProvers.getNames())).build());
    }
    if (configuration.availableReaders.size() > 1) {
      // READER
      lo.add(Option
          .builder(OptNames.READER)
          .hasArg()
          .argName("name")
          .desc(
              String.format(MSG.CMD_LINE_OPTIONS.OPTIONS_DESCRIPTIONS.READER,
                  configuration.availableReaders.getNames())).build());
      // LIST_READERS
      lo.add(Option.builder().longOpt(OptNames.LIST_READERS)
          .desc(MSG.CMD_LINE_OPTIONS.OPTIONS_DESCRIPTIONS.LIST_READER).build());
    }
    // LOG
    lo.add(Option.builder().longOpt(OptNames.LOG)
        .desc(MSG.CMD_LINE_OPTIONS.OPTIONS_DESCRIPTIONS.LOG_FILE).build());
    // LOG_TIME
    lo.add(Option.builder().longOpt(OptNames.LOG_TIME)
        .desc(MSG.CMD_LINE_OPTIONS.OPTIONS_DESCRIPTIONS.LOG_TIME).build());
    // SAVE_TRACE
    lo.add(Option.builder().longOpt(OptNames.SAVE_TRACE)
        .desc(MSG.CMD_LINE_OPTIONS.OPTIONS_DESCRIPTIONS.PTRACE_FILE).build());
    // VERBOSE
    lo.add(Option.builder(OptNames.VERBOSE).desc(MSG.CMD_LINE_OPTIONS.OPTIONS_DESCRIPTIONS.VERBOSE)
        .build());
    // TESTSET
    lo.add(Option.builder().longOpt(OptNames.TESTSET).hasArg(true).argName("test-name")
        .desc(MSG.CMD_LINE_OPTIONS.OPTIONS_DESCRIPTIONS.TESTSET).build());
    // LOGDIR
    lo.add(Option.builder().longOpt(OptNames.LOG_DIR).hasArg(true).argName("filename")
        .desc(MSG.CMD_LINE_OPTIONS.OPTIONS_DESCRIPTIONS.LOGDIR).build());
    // F3_TIME_STR
    lo.add(Option.builder().longOpt(OptNames.F3_TIME_STR).hasArg(false)
        .desc(MSG.CMD_LINE_OPTIONS.OPTIONS_DESCRIPTIONS.F3_TIME_STR).build());
    // JTABWB_TIME_STR 
    lo.add(Option.builder().longOpt(OptNames.JTABWB_TIME_STR).hasArg(false)
        .desc(MSG.CMD_LINE_OPTIONS.OPTIONS_DESCRIPTIONS.JTABWB_TIME_STR).build());

    for (Option opt : lo)
      if (configuration.cmdLineOptions.getOption(opt.getOpt()) != null
          || configuration.cmdLineOptions.getOption(opt.getLongOpt()) != null)
        throw new LauncherOptionDefinitionException(String.format(
            MSG.CMD_LINE_OPTIONS.EXCEPTIONS.LAUNCHER_OPTION_NAME_REDEFINITION_ERROR, opt.getOpt()));
      else
        configuration.cmdLineOptions.addOption(opt);
  }

  Launcher.LaunchConfiguration processComdLineOptions(String[] args) {
    defineCmdLineOptions();

    // create the parser
    CommandLineParser parser = new DefaultParser();
    try {
      // parse the command line arguments
      configuration.commandLine = parser.parse(configuration.cmdLineOptions, args, false);
    } catch (ParseException exp) {
      // oops, something went wrong
      LOG.error(MSG.CMD_LINE_OPTIONS.ERRORS.INVOCATION_ERROR, exp.getMessage());
      System.exit(1);
    }

    // Check options compatibility
    if (incomp.hasIncompatibleOptions(configuration.commandLine)) {
      LOG.error(incomp.firstIncompatibilityDescription(configuration.commandLine));
      System.exit(1);
    }

    // NOW WE PROCESS THE COMMAND LINE OPTIONS 

    // ...FIRST PROCESS LAUNCHER INFO OPTIONS
    // if one of these options is set print info and then exit
    if (configuration.commandLine.hasOption(OptNames.HELP)) {
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp(configuration.launcherClassName, MSG.CMD_LINE_OPTIONS.INFO.HELP_HEADER,
          configuration.cmdLineOptions, MSG.CMD_LINE_OPTIONS.INFO.HELP_FOOTER, true);
      System.exit(0);
    }

    if (configuration.commandLine.hasOption(OptNames.LIST_READERS)) {
      LOG.info(MSG.CMD_LINE_OPTIONS.INFO.AVAILABLE_READERS,
          configuration.availableReaders.getNamedArgumentsDescription());
      System.exit(0);
    }

    if (configuration.commandLine.hasOption(OptNames.LIST_PROVERS)) {
      LOG.info(MSG.CMD_LINE_OPTIONS.INFO.AVAILABLE_PROVERS,
          configuration.availableProvers.getNamedArgumentsDescription());
      System.exit(0);
    }

    // OTHER OPTIONS: we define the corresponding runtime options
    if (configuration.commandLine.hasOption(OptNames.INPUT)) {
      if (configuration.stdinReader == null)
        throw new LauncherExecutionException(
            String.format(MSG.LAUNCHER.INVOCATION_EXCEPTIONS.STDIN_PARSER_UNDEFINED));
      else
        configuration.readFromStandardInput = true;
    }

    if (configuration.commandLine.hasOption(OptNames.LATEX_PROOF)) {
      configuration.engineExecutionMode = ExecutionMode.ENGINE_TRACE;
      configuration.generateLatexOfProof = true;
    }

    if (configuration.commandLine.hasOption(OptNames.LATEX_CTREE)) {
      configuration.engineExecutionMode = ExecutionMode.ENGINE_TRACE;
      configuration.generateLatexOfCtrees = true;
    }

    if (configuration.commandLine.hasOption(OptNames.LOG))
      configuration.generateLogFile = true;

    if (configuration.commandLine.hasOption(OptNames.LOG_TIME))
      configuration.generateLogTimeFile = true;

    if (configuration.commandLine.hasOption(OptNames.SAVE_TRACE)) {
      configuration.engineExecutionMode = ExecutionMode.ENGINE_TRACE;
      configuration.saveTrace = true;
    }

    if (configuration.commandLine.hasOption(OptNames.LOG_DIR))
      configuration.logDirAbsolutePath = configuration.commandLine.getOptionValue(OptNames.LOG_DIR);

    if (configuration.commandLine.hasOption(OptNames.TESTSET)) {
      configuration.testsetmode = true;
      configuration.testsetName = configuration.commandLine.getOptionValue(OptNames.TESTSET);
    }

    if (configuration.commandLine.hasOption(OptNames.F3_TIME_STR)) {
      configuration.generatef3TimeStr = true;
    }

    if (configuration.commandLine.hasOption(OptNames.JTABWB_TIME_STR)) {
      configuration.generateJTabWbTimeStr = true;
    }

    if (configuration.commandLine.hasOption(OptNames.READER)) {
      String readerName = configuration.commandLine.getOptionValue(OptNames.READER);
      ConfiguredProblemDescriptioReader reader =
          configuration.availableReaders.searchReaderByName(readerName);
      if (reader != null)
        configuration.fileReader = reader;
      else {
        LOG.error(MSG.CMD_LINE_OPTIONS.ERRORS.NO_READER_WITH_NAME, readerName);
        System.exit(1);
      }
    } else
      // otherwise set the default or the last added reader 
      configuration.fileReader = configuration.availableReaders.getDefaultReader();

    if (configuration.commandLine.hasOption(OptNames.PROVER)) {
      String proverName = configuration.commandLine.getOptionValue(OptNames.PROVER);
      ConfiguredTheoremProver prover =
          configuration.availableProvers.searchProverByName(proverName);
      if (prover != null)
        configuration.selectedProver = prover;
      else {
        LOG.error(MSG.CMD_LINE_OPTIONS.ERRORS.NO_PROVER_WITH_NAME, proverName);
        System.exit(1);
      }
    } else
      // otherwise set the default or the last added prover
      configuration.selectedProver = configuration.availableProvers.getDefaultProver();

    if (configuration.commandLine.hasOption(OptNames.VERBOSE)) {
      configuration.verboseExecutionMode = true;
      configuration.engineExecutionMode = ExecutionMode.ENGINE_VERBOSE;
    }

    return configuration;
  }
}
