package me.carbon.bf.util;

public class BFUtils {
    /**
     * Checks if a specific string can be used as valid BF code
     * @param rawData Raw string data
     * @return If the data is valid BF code
     */
    public static boolean isValidBF(String rawData){
        return rawData.matches("[+\\-><\\[\\].,]+");
    }
}
