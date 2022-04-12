package me.carbon.bf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils {
    /**
     * Checks if a file has the .bf extension type
     * @param file Received file
     * @return If the file extension type is .bf
     */
    public static boolean isBFFile(File file){
        return file.getName().endsWith(".bf");
    }

    /**
     * Parses a file into a String
     * @param file The received file
     * @return Parsed output
     */
    public static String readFile(File file){
        try {
            FileInputStream stream = new FileInputStream(file);
            return new String(stream.readAllBytes());
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
