package day10;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.List;

public class Day10 {

    private static List<Integer> values = new ArrayList<>();

    public static void main(String[] args) {

        executeInstructions(ReadFiles.getInputData("day10/test.txt"));
    }

    private static void executeInstructions(List<String> instructions) {

        Integer value = 1;
        String[] instructionParts;
        for(String instruction : instructions) {
            instructionParts = instruction.split(" ");
            if(!instructionParts[0].equals("noop")){

            }
            values.add(value);
        }

    }
    
}
