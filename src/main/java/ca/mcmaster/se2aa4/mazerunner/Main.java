package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;

public class Main {

    private static final Logger logger = LogManager.getLogger();
    private static final MazeService mazeService = new MazeService(new ShortestPathSolver(), '#', ' ', 'p');
    private static final ArgumentProcessor argumentProcessor = new ArgumentProcessor();
    private static final PathFormatter pathFormatter = new PathFormatter();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");
        System.out.println("** Starting Maze Runner");

        try {
            CommandLine cmd = argumentProcessor.parseArguments(args);

            // retrieve -i and -p options
            String filePath = cmd.getOptionValue("i");
            String userPath = cmd.getOptionValue("p");

            if (filePath != null) {
                mazeService.loadMaze(filePath);
            } else {
                System.out.println("** No maze file spcified");
                return;
            }

            // if user provided a path for validation
            if (userPath != null) {
                String isValidPath = mazeService.isValidPath(userPath);
                logger.info(isValidPath);
                System.out.println(isValidPath);
                return;
            } else { // otherwise will find path for the provided maze
                
                // getting path
                logger.info("**** Computing path");
                System.out.println("**** Computing path");
                String path = mazeService.getPath();

                if (path != null) {
                    logger.info("** Canonical Path: " + path);
                    System.out.println("** Canonical Path: " + path);
                    String factorizedPath = pathFormatter.getFactorizedPath(path);
                    if (factorizedPath == null) {
                        logger.error("INVALID PATH");
                        return;
                    }
                    logger.info("** Factorized Path: " + factorizedPath);
                    System.out.println("** Factorized Path: " + factorizedPath);
                    logger.info("** End of MazeRunner");
                    System.out.println("** End of MazeRunner");
                } else {
                    logger.info("PATH NOT COMPUTED");
                    System.out.println("PATH NOT COMPUTED");
                }
            }
        } catch (ParseException e) {
            logger.error("Error parsing command-line arguments: " + e.getMessage());
            System.out.println("Error parsing command-line arguments: " + e.getMessage());
            argumentProcessor.printHelp();
            return;
        } catch (RuntimeException e) {
            logger.error("/!\\ An error has occurred /!\\");
            System.out.println("/!\\ An error has occurred /!\\");
            return;
            // e.printStackTrace();
            // return;
        }
    }
}
