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
        int step = 1;
        String[] instructionParts;
        System.out.println("X begins with value: " + value);
        for(String instruction : instructions) {
           
            instructionParts = instruction.split(" ");
            if(!instructionParts[0].equals("noop")) {
                for(int i=0;i<2;i++){
                    values.add(value);
                }
                value+= Integer.valueOf(instructionParts[1]);
            }
            System.out.println("At end of step " + step + ", X value is: " + value);
             values.add(value);
             step++;

        }
    }
    
}
