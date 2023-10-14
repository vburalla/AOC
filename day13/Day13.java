package day13;

import utils.ReadFiles;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 {

    public static void main(String[] args) {

        var signalLines = ReadFiles.getInputData("day13/test.txt");
        //part1(signalLines);
        part2(signalLines);
    }

    private static void part1(List<String> lines) {
        int i=0;
        int pairs = 0;
        int rightOrder = 0;
        int rightPairsSum = 0;
        String text = "";
        while(i <lines.size()){
            Signal signal1 = new Signal(lines.get(i++));
            Signal signal2 = new Signal(lines.get(i++));
            i++;
            pairs++;
            if(signal1.compareTo(signal2) < 0) {
                rightOrder++;
                rightPairsSum+=pairs;
                text = "is in RIGHT order";
            } else {
                text = "is NOT in the RIGHT order";
            }
            System.out.println(String.format("#%s Comparing %s and %s : result %s", pairs,signal1.packet, signal2.packet, text));
        }
        System.out.println(String.format("Part 1: There are %s pairs in the right order, and SUM = %s", rightOrder,rightPairsSum));
    }

    private static void part2(List<String> lines) {

        lines = lines.stream().filter(line -> !line.equals("")).collect(Collectors.toList());
        lines.add("[[2]]");
        lines.add("[[6]]");
        List<Signal> signals = lines.stream().map(Signal::new).collect(Collectors.toList());
        Collections.sort(signals);
        for(Signal signal : signals) {
            System.out.println(signal.packet);
        }
    }

}