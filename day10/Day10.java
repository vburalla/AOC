package day10;

import utils.ReadFiles;
import utils.Console;

import java.util.ArrayList;
import java.util.List;

public class Day10 {

    private static List<Integer> values;
    private static List<Integer> positions = List.of(20, 60, 100, 140, 180, 220);
    private static char[] screen = "·".repeat(240).toCharArray();
    private static final char PRINTED_PIXEL = '#';

    public static void main(String[] args) throws InterruptedException {

        executeInstructions(ReadFiles.getInputData("day10/input1.txt"));
        Console.clearScreen();
        System.out.println("Part 1: " + getSignalStrengthSumForPositions(positions));
        System.out.println("Part 2: ");
        printCRTScreen(6, 40, false);
    }

    private static void executeInstructions(List<String> instructions) throws InterruptedException {

        Integer value = 1;
        int step = 1;
        String[] instructionParts;
        values = new ArrayList<>();

        values.add(value);
        for(String instruction : instructions) {
            instructionParts = instruction.split(" ");
            if(!instructionParts[0].equals("noop")) {
                for(int i=0;i<2;i++){
                    values.add(value);
                    if(isPrintable(value, step)) screen[step-1] = PRINTED_PIXEL;
                    printCRTScreen(6, 40,true);
                    step++;
                }
                value+= Integer.valueOf(instructionParts[1]);
            } else {
                values.add(value);
                if(isPrintable(value, step)) screen[step-1] = PRINTED_PIXEL;
                printCRTScreen(6, 40,true);
                step++;
            }  
        }
    }

    private static boolean isPrintable(int xPosition, int cycle) {

        int column = cycle % 40;
        return (column >= xPosition & column <= xPosition + 2);
    }

    private static Integer getSignalStrengthSumForPositions(List<Integer> positionsList) {

        Integer sum = 0;

        for(Integer pos : positionsList) {
            sum+= pos * values.get(pos);
        }

        return sum;
    }

    private static void printCRTScreen(int lines, int rows, boolean clearScreen) throws InterruptedException {

        if(clearScreen) Console.clearScreen();

        for(int j=0; j<lines; j++) {
            System.out.println(new String(screen).substring(j*rows, (j+1)*rows));
        }
        Thread.sleep(30);
    }
    
}
