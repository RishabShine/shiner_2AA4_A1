package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;

public class Main {

    private static final Logger logger = LogManager.getLogger();
    private static final MazeService mazeService = new MazeService(new MazeSolver());
    private static final ArgumentProcessor argumentProcessor = new ArgumentProcessor();
    private static final PathFormatter pathFormatter = new PathFormatter();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");

        try {
            CommandLine cmd = argumentProcessor.parseArguments(args);

            // retrieve -i and -p options
            String filePath = cmd.getOptionValue("i");
            String userPath = cmd.getOptionValue("p");

            // if user provided a path for validation
            if (userPath != null) {
                //! return, will add logic to valdiate path
                logger.info(mazeService.isValidPath(filePath, userPath));
                return;
            } else { // otherwise will find path for the provided maze
                
                // getting path
                logger.info("**** Computing path");
                String path = mazeService.getPath(filePath);

                if (path != null) {
                    logger.info("** Canonical Path: " + path);
                    String factorizedPath = pathFormatter.getFactorizedPath(path);
                    if (factorizedPath == null) {
                        logger.error("INVALID PATH");
                    }
                    logger.info("** Factorized Path: " + factorizedPath);
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
}
