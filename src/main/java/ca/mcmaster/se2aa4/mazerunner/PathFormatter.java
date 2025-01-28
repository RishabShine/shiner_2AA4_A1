package ca.mcmaster.se2aa4.mazerunner;

public class PathFormatter {

    /*
     * 
     * will return the factorized version of a canonical path, if an invalid path is given, will return null
     * 
     * @param path: canonical path
     * 
     */
    public String getFactorizedPath (String path) {

        // ensure path only contains valid characters
        for (char c : path.toCharArray()) {
            if (c != 'R' && c != 'L' && c != 'F' && c != ' ') {
                return null;
            }
        }

        // initial direction is forward 'F'
        char currDir = 'F';
        int count = 0;
        StringBuilder factorizedPath = new StringBuilder(); 

        // iterating over given path
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == currDir) {
                count++;
            } else if (path.charAt(i) != ' ') {
                factorizedPath.append(count).append(currDir).append(" ");
                currDir = path.charAt(i);
                count = 1;
            }
        }
        factorizedPath.append(count).append(currDir);
        return factorizedPath.toString();
    }
}