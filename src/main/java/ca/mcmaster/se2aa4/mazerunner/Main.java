package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;

public class Main {

    private static final Logger logger = LogManager.getLogger();
    private static final MazeReader mazeReader = new MazeReader();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");

        Options options = new Options();

        // define the -i option for input file
        Option inputFileOption = Option.builder("i")
                .longOpt("input")
                .desc("Path to the maze input file")
                .hasArg()
                .argName("FILE")
                .required(true)
                .build();
        options.addOption(inputFileOption);

        CommandLineParser parser = new DefaultParser();

        try {
            // parse the command-line arguments
            CommandLine cmd = parser.parse(options, args);

            // get the value of the -i flag
            String filePath = cmd.getOptionValue("i");

            char[][] maze = mazeReader.readMaze(filePath);

            //? testing state of maze read in
            System.out.println(maze);
        } catch (ParseException e) {
            logger.error("Error parsing command-line arguments: " + e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("MazeRunner", options);
        } catch (RuntimeException e) {
            logger.error("/!\\ An error has occurred /!\\");
            e.printStackTrace();
        }

        // try {
        //     System.out.println("**** Reading the maze from file " + args[0]);
        //     BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        //     String line;
        //     while ((line = reader.readLine()) != null) {
        //         for (int idx = 0; idx < line.length(); idx++) {
        //             if (line.charAt(idx) == '#') {
        //                 System.out.print("WALL ");
        //             } else if (line.charAt(idx) == ' ') {
        //                 System.out.print("PASS ");
        //             }
        //         }
        //         System.out.print(System.lineSeparator());
        //     }
        // } catch(Exception e) {
        //     System.err.println("/!\\ An error has occured /!\\");
        // }
        logger.info("**** Computing path");
        logger.info("PATH NOT COMPUTED");
        logger.info("** End of MazeRunner");
    }
}
