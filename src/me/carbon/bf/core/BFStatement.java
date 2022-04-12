package me.carbon.bf.core;

import me.carbon.bf.util.BFUtils;

/**
 * Class for containing an array of BFCommands, which can
 * then be processed by the BFProcessor
 */
public class BFStatement {

    private final BFCommand[] commands;

    public BFStatement(BFCommand[] commands){
        this.commands = commands;
    }

    public BFCommand[] getCommands(){
        return this.commands;
    }

    /**
     * Parses a string into a BF statement
     * @param rawData Raw string data
     * @return        Parsed BF statement
     */
    public static BFStatement parseString(String rawData){
        if(!BFUtils.isValidBF(rawData)) throw new IllegalArgumentException("Invalid BF");
        char[] chars = rawData.toCharArray();
        BFCommand[] commands = new BFCommand[chars.length];
        for(int i = 0; i < chars.length; i++){
            commands[i] = BFCommand.fromChar(chars[i]);
        }
        return new BFStatement(commands);
    }
}
