package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author tiera
 */
public class FileFetcher {

    public FileFetcher() {
    }
    
    /**
     * Gets a file in string form
     * @param path absolute path to the file desired
     * @return requested file as a string
     */
    public String getFileAsString(String path) {
        
        String result = "";
        try {
            Path fileName = Paths.get(path);
            result = Files.readString(fileName);
        } catch (IOException e) {
            System.out.println("An error occurred reading the file:\n" + e);
        }
        return result;
    }
    
    /**
     * Gets a file in a byte array form
     * @param path absolute path to the file desired
     * @return requested file as a byte array
     */
    public byte[] getFileAsByteArray(String path) {
        byte[] result = new byte[16];
        try {
            Path fileName = Paths.get(path);
            result = Files.readAllBytes(fileName);
        } catch (IOException e) {
            System.out.println("An error occurred reading the file:\n" + e);
        }
        return result;
    }
    
}
