package me.carbon.bf.core;

import java.util.Arrays;

/**
 * All valid BF commands, along with their character counterpart
 */
public enum BFCommand {
    ADD('+'),
    SUBTRACT('-'),
    FORWARD('>'),
    BACKWARD('<'),
    START_LOOP('['),
    END_LOOP(']'),
    OUTPUT('.'),
    INPUT(',');

    private final char rawValue;

    BFCommand(char rawValue){
        this.rawValue = rawValue;
    }

    /**
     * Parses a character into a BFCommand
     * @param raw The raw character
     * @return    Parsed BFCommand value
     */
    public static BFCommand fromChar(char raw){
        return Arrays.stream(values()).filter(i -> i.rawValue == raw).findFirst().orElse(null);
    }
}
