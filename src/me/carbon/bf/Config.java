package me.carbon.bf;

/**
 * Config determined by command line arguments
 */
public class Config {
    /**
     * Sends a message after every command with information about the process
     */
    public static boolean DEBUG = false;

    /**
     * Stops conversion of a byte to character when outputted
     */
    public static boolean RAW = false;

    public static void loadConfig(String[] args){
        DEBUG = hasArg(args, "-debug");
        RAW = hasArg(args, "-raw");
    }

    private static boolean hasArg(String[] args, String arg){
        for (String str : args){
            if(str.equalsIgnoreCase(arg)) return true;
        }
        return false;
    }
}
