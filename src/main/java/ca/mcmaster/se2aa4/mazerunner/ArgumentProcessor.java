package ca.mcmaster.se2aa4.mazerunner;

import org.apache.commons.cli.*;

public class ArgumentProcessor {

    private final Options options;

    public ArgumentProcessor() {
        this.options = defineOptions();
    }

    // define all CLI options
    private Options defineOptions() {
        Options options = new Options();

        // -i flag for input file
        Option inputFileOption = Option.builder("i")
                .longOpt("input")
                .desc("Path to the maze input file")
                .hasArg()
                .argName("FILE")
                .required(true)
                .build();
        options.addOption(inputFileOption);

        // -p flag for user provided path
        Option pathOption = Option.builder("p")
                .longOpt("path")
                .desc("User-provided path to validate (e.g., FFFF)")
                .hasArg()
                .argName("PATH")
                .required(false)
                .build();
        options.addOption(pathOption);

        return options;
    }

    public CommandLine parseArguments(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        return parser.parse(options, args);
    }

    public void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("MazeRunner", options);
    }
    
}
