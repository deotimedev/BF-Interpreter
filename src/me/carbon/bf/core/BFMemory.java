package me.carbon.bf.core;

/**
 * Stores an array of bytes used as memory by the BFProcessor
 */
public class BFMemory {

    /**
     * Maximum amount of cells that can exist
     */
    private static final int MAX_SIZE = 30000;

    private final byte[] memory = new byte[MAX_SIZE];
    private int pointer = 0;

    public void add(){
        memory[pointer]++;
    }

    public void subtract(){
        memory[pointer]--;
    }

    public void forward(){
        if(pointer < MAX_SIZE) pointer++;
    }

    public void backward(){
        if(pointer > 0) pointer--;
    }

    public byte getCurrentCell(){
        return memory[pointer];
    }

    public void setCurrentCell(byte value){
        memory[pointer] = value;
    }

    public byte[] getCells(){
        return this.memory;
    }

    public int getPointer(){
        return this.pointer;
    }
}
