package lucenenewsgroups;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class that provides static methods to interact with files
 * @author Luciano Mammino <lmammino[at]oryzone[dot]com>
 * @version 1.0
 */
public class FileUtils {

    /**
     * Reads the content of a file
     * @param file the file to read
     * @return a string with the content of the given file
     */
    public static String readFileContent(File file) {
        String content = "";
        Scanner scanner;

        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                content += "\n" + scanner.nextLine();
            }
            scanner.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return content;
    }

    /**
     * Reads the content of a file
     * @param filename the file name of the file to read
     * @return the content of the file
     */
    public static String readFileContent(String filename) {
        return FileUtils.readFileContent(new File(filename));
    }

    /**
     * Removes a directory recursively
     * @param path the directory to remove
     * @return true if the deletion suceeded, false otherwise
     */
    public static boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }
}
