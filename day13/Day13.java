package day13;

import utils.ReadFiles;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 {

    public static void main(String[] args) {

        var signalLines = ReadFiles.getInputData("day13/input1.txt");
        part1(signalLines);
        part2(signalLines);
    }

    private static void part1(List<String> lines) {
        int i=0;
        int pairs = 0;
        int rightOrder = 0;
        int rightPairsSum = 0;

        while(i <lines.size()){
            Signal signal1 = new Signal(lines.get(i++));
            Signal signal2 = new Signal(lines.get(i++));
            i++;
            pairs++;
            if(signal1.compareTo(signal2) < 0) {
                rightOrder++;
                rightPairsSum+=pairs;
            }
        }
        System.out.println(String.format("Part 1: There are %s pairs in the right order, and SUM = %s", rightOrder,rightPairsSum));
    }

    private static void part2(List<String> lines) {
        String divider1 = "[[2]]";
        String divider2 = "[[6]]";
        lines = lines.stream().filter(line -> !line.equals("")).collect(Collectors.toList());
        lines.add(divider1);
        lines.add(divider2);
        List<Signal> signals = lines.stream().map(Signal::new).collect(Collectors.toList());
        Collections.sort(signals);
        int result = 1;
        int index = 1;
        for(Signal signal : signals) {
            if(signal.packet.equals(divider1) || signal.packet.equals(divider2)) {
                result*=index;
            }
            index++;
        }
        System.out.println(String.format("Part 2: decoder key = %s",result));
    }

}