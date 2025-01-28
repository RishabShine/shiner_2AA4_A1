package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;

public class Main {

    private static final Logger logger = LogManager.getLogger();
    private static final MazeService mazeService = new MazeService(new MazeSolver());
    private static final ArgumentProcessor argumentProcessor = new ArgumentProcessor();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");

        // Options options = new Options();

        // // define the -i option for input file
        // Option inputFileOption = Option.builder("i")
        //         .longOpt("input")
        //         .desc("Path to the maze input file")
        //         .hasArg()
        //         .argName("FILE")
        //         .required(true)
        //         .build();
        // options.addOption(inputFileOption);

        // CommandLine cmd = argumentProcessor.parseArguments(args);

        // // retrieve -i and -p options
        // String filePath = cmd.getOptionValue("i");
        // String userPath = cmd.getOptionValue("p");

        try {
            CommandLine cmd = argumentProcessor.parseArguments(args);

            // retrieve -i and -p options
            String filePath = cmd.getOptionValue("i");
            String userPath = cmd.getOptionValue("p");

            // parse the command-line arguments
            //CommandLine cmd = parser.parse(options, args);

            // get the value of the -i flag
            //String filePath = cmd.getOptionValue("i");

            // if user provided a path for validation
            if (userPath != null) {
                //! return, will add logic to valdiate path
                return;
            } else { // otherwise will find path for the provided maze
                
                // getting path
                logger.info("**** Computing path");
                String path = mazeService.getPath(filePath);

                if (path != null) {
                    logger.info("** Path: " + path);
                    logger.info("** End of MazeRunner");
                } else {
                    logger.info("PATH NOT COMPUTED");
            }
        }

        } catch (ParseException e) {
            logger.error("Error parsing command-line arguments: " + e.getMessage());
            argumentProcessor.printHelp();
        } catch (RuntimeException e) {
            logger.error("/!\\ An error has occurred /!\\");
            e.printStackTrace();
        }
    }

    private static Options defineOptions() {
        Options options = new Options();

        // Define the -i option for input file
        Option inputFileOption = Option.builder("i")
                .longOpt("input")
                .desc("Path to the maze input file")
                .hasArg()
                .argName("FILE")
                .required(true)
                .build();
        options.addOption(inputFileOption);

        // Define the -p option for user-provided path
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
}
