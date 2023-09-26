package day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day5 {
    
    private static List<String> getInputData(String inputFile) {

        try{
            return Files.readAllLines(Paths.get(inputFile));
        } catch (IOException ex) {
            System.out.println("Exception" + ex.getLocalizedMessage());
        }
        return null;
    }

    private static List<LinkedList<String>> fillQueues(List<String> lines) {

        int columns = (lines.get(0).length()+1)/4;
        List<LinkedList<String>> queues = new ArrayList<>();
        List<String> formattedRows = new ArrayList<>();
        while(!lines.get(1).equals("")) {
            formattedRows.add(formatRow(lines.remove(0)));
        }
        lines.remove(0);
        lines.remove(0);
        for(int i=0; i<formattedRows.get(0).length(); i+=3) {
            LinkedList<String> newList = new LinkedList<>();
            for(String row : formattedRows) {
                String value = row.substring(i,i+3).trim();
                if(!value.equals("")) newList.addFirst(value);
            }
            queues.add(newList);
            i++;
        }
        
        return queues;
    }

    private static String formatRow(String row) {

        return row.replaceAll("[\\[\\]]"," ");
    }

    private static List<String> executeInstructions(List<String> originalInstructions, List<LinkedList<String>> currentQueue) {

        for(String instruction : originalInstructions) {
            var decodedInstruction = decodeInstruction(instruction);
        }
        return null;
    }

    private static String[] decodeInstruction(String inst) {

        return inst.split(" ");
    }
    
    public static void main(String[] args) {
        var lines = getInputData("day5/input1.txt");
        List<LinkedList<String>> queues = fillQueues(lines);
        var instructions = executeInstructions(lines);
    }
}
