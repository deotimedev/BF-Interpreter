package me.carbon.bf.core;

import me.carbon.bf.Config;

import java.util.*;

/**
 * Used to process BFStatements, and to manage the memory involved in them
 */
public class BFProcessor {

    private final BFMemory memory;

    public BFProcessor(){
        this.memory = new BFMemory();
    }

    /**
     * Processes a BF statement
     * @param statement Statement to be processed
     */
    public void processStatement(BFStatement statement){
        long startTime = System.currentTimeMillis();
        int index = 0;
        LoopCache cache = LoopCache.cache(statement);

        BFCommand[] commands = statement.getCommands();
        while(index < commands.length){
            BFCommand command = commands[index];
            switch (command){

                /*
                * Add: Adds 1 to the current cell
                * Subtract: Subtracts 1 from the current cell
                * Forward: Moves the cell pointer 1 forward
                * Backward: Moves the cell pointer 1 backward
                */
                case ADD -> this.memory.add();
                case SUBTRACT -> this.memory.subtract();
                case FORWARD -> this.memory.forward();
                case BACKWARD -> this.memory.backward();

                /*
                * Start Loop: Goes to end of loop if current cell is 0
                * End Loop: Goes to beginning of loop if current cell is not 0
                */
                case START_LOOP -> {
                    int endLoopIndex = cache.getEndLoopIndex(index);
                    if(this.memory.getCurrentCell() == 0) index = endLoopIndex;
                }
                case END_LOOP -> {
                    int startLoopIndex = cache.getStartLoopIndex(index);
                    if(this.memory.getCurrentCell() != 0) index = startLoopIndex;
                }

                /*
                * Output: Outputs one byte at the selected cell
                * Input: Requests one byte of input from the user
                */
                case OUTPUT -> System.out.println(Config.RAW ? this.memory.getCurrentCell() : (char) this.memory.getCurrentCell());
                case INPUT -> this.memory.setCurrentCell(getInput());
            }
            index++;

            if(Config.DEBUG){
                System.out.println("-----------------------------------");
                System.out.printf("Current index: %s%n", index - 1);
                System.out.printf("BFCommand: %s%n", command.name());
                System.out.printf("First 25 cells of memory: %s%n", Arrays.toString(Arrays.copyOfRange(this.memory.getCells(), 0, 25)));
                System.out.printf("Pointer index: %s%n", this.memory.getPointer());
                System.out.println("-----------------------------------\n");
            }
        }
        System.out.printf("Completed in %s seconds.%n", (System.currentTimeMillis() - startTime) / 1000);
    }

    /**
     * Requests and validates a byte input from the user
     * @return Valid byte received by the user
     */
    private byte getInput(){
        Scanner scanner = new Scanner(System.in);
        byte value;
        while(true){
            System.out.print("Input: ");
            String input = scanner.nextLine();
            try{
                value = Byte.parseByte(input);
                break;
            } catch (NumberFormatException e){
                System.out.println("Invalid byte.");
            }
        }
        return value;
    }


    /**
     * A cache of where the indexes of all loops are, and
     * which index corresponds to their type
     */
    public static class LoopCache{
        private final Map<Integer, Integer> loopMappings;

        private LoopCache(Map<Integer, Integer> cache){
            this.loopMappings = cache;
        }

        /**
         * Caches all loops in a BFStatement
         * @param statement Cached statement
         */
        public static LoopCache cache(BFStatement statement){
            BFCommand[] commands = statement.getCommands();
            List<Integer> queuedStartIndexes = new ArrayList<>();
            Map<Integer, Integer> loopMappings = new HashMap<>();
            for(int i = 0; i < commands.length; i++){
                BFCommand command = commands[i];
                if(command == BFCommand.START_LOOP){
                    queuedStartIndexes.add(i);
                } else if(command == BFCommand.END_LOOP){
                    if(queuedStartIndexes.size() == 0) continue;
                    Integer startMatch = queuedStartIndexes.get(queuedStartIndexes.size() - 1);
                    loopMappings.put(startMatch, i);
                    queuedStartIndexes.remove(queuedStartIndexes.size() - 1);
                }
            }
            return new LoopCache(loopMappings);
        }

        /**
         * Finds the matching end loop index based on the start loop index
         * @param startLoopIndex Index of the start loop
         * @return Index of the end loop
         */
        public int getEndLoopIndex(int startLoopIndex){
            return loopMappings.getOrDefault(startLoopIndex, startLoopIndex + 1);
        }

        /**
         * Finds the matching start loop index based on the end loop index
         * @param endLoopIndex Index of the end loop
         * @return Index of the start loop
         */
        public int getStartLoopIndex(int endLoopIndex){
            return loopMappings
                    .entrySet().stream()
                    .filter(e -> e.getValue() == endLoopIndex)
                    .map(Map.Entry::getKey)
                    .findFirst().orElse(endLoopIndex + 1);
        }
    }
}
