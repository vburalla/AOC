package day5;

import utils.ReadFiles;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day5 {

    private static List<LinkedList<String>> fillQueues(List<String> lines) {

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

    private static List<LinkedList<String>> executeInstructions(List<String> originalInstructions, List<LinkedList<String>> currentQueue, int model) {

        for(String instruction : originalInstructions) {
            String[] decodedInstruction = decodeInstruction(instruction);
            Integer quantity = Integer.valueOf(decodedInstruction[1]);
            Integer origin = Integer.valueOf(decodedInstruction[3]);
            Integer destination = Integer.valueOf(decodedInstruction[5]);
            LinkedList<String> originColumn = currentQueue.get(origin-1);
            LinkedList<String> destinationColumn = currentQueue.get(destination-1);
            LinkedList<String> tempLinkedList = new LinkedList<>();
            for(int i=0; i<quantity; i++) {
                if(model==9000){
                    destinationColumn.addLast(originColumn.removeLast());
                } else {
                    tempLinkedList.addFirst(originColumn.removeLast());
                }
            }
            destinationColumn.addAll(tempLinkedList);
            currentQueue.set(origin-1, originColumn);
            currentQueue.set(destination-1, destinationColumn);
        }
        return currentQueue;
    }

    private static String[] decodeInstruction(String inst) {

        return inst.split(" ");
    }

    private static void printLast(List<LinkedList<String>> columns) {

        for(LinkedList<String> column : columns) {
            if(column.getLast()!=null) {
                System.out.print("["+ column.getLast() + "]");
            }
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        List<String> lines = ReadFiles.getInputData("day5/input1.txt");
        List<LinkedList<String>> queues = fillQueues(lines);
        var result = executeInstructions(lines, queues,9000);
        System.out.print("Part1: ");
        printLast(result);
        lines = ReadFiles.getInputData("day5/input1.txt");
        queues = fillQueues(lines);
        result = executeInstructions(lines, queues,9001);
        System.out.print("Part2: ");
        printLast(result);
    }
}
