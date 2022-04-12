package me.carbon.bf;

import me.carbon.bf.core.BFProcessor;
import me.carbon.bf.core.BFStatement;
import me.carbon.bf.util.BFUtils;
import me.carbon.bf.util.FileUtils;

import java.io.File;

public class Main {

    /**
     * Starts the program, activated through the BFI batch command
     * @param args 0: Name of file 1: Directory where command was executed
     */
    public static void main(String[] args) {
	    String fileName = args[0];
        String sourcePath = args[1];

        File targetFile = new File(String.format("%s/%s", sourcePath, fileName));
        if(!targetFile.exists()){
            System.out.println("File does not exist.");
            return;
        }
        if(!FileUtils.isBFFile(targetFile)){
            System.out.println("File extension type must be \".bf\".");
            return;
        }
        Config.loadConfig(args);
        init(targetFile);
    }

    /**
     * Initiates the BF processor, and gives it a statement from the file
     * @param file BF file to be parsed
     */
    private static void init(File file){
        String rawData = FileUtils.readFile(file);
        assert rawData != null;
        if(!BFUtils.isValidBF(rawData)){
            System.out.println("Syntax error.");
            return;
        }
        BFProcessor processor = new BFProcessor();
        BFStatement statement = BFStatement.parseString(rawData);
        processor.processStatement(statement);
    }

}
