package AOC2024.day5;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {

    static List<List<Integer>> rules = new ArrayList<>();

    static List<List<Integer>> numbers = new ArrayList<>();

    static Integer total = 0;

    static Integer totalUnsorted = 0;

    public static void main(String[] args) {

        List<String> lines = ReadFiles.getInputData("AOC2024/day5/input1.txt");
        readRulesAndInputs(lines);
        sortLists();
        System.out.println("Total sorted = " + total);
        System.out.println("Total unsorted = " + totalUnsorted);
    }

    private static void readRulesAndInputs(List<String> lines) {

        int i = 0;
        do {
            String[] rule = lines.get(i).split("\\|");
            Day5.rules.add(List.of(Integer.parseInt(rule[0]), Integer.parseInt(rule[1])));
            i++;
        } while(!lines.get(i).equals(""));
        i++;
        while(i < lines.size()) {
            List<Integer> numbersGroup = Arrays.stream(lines.get(i).split(",")).map(Integer::parseInt).collect(Collectors.toList());
            Day5.numbers.add(numbersGroup);
            i++;
        }
    }

    private static void sortLists() {

        for(List<Integer> numberList : Day5.numbers) {
            printIfSorted(numberList);
            //sortNumbers(numberList);
        }
    }

    private static void printIfSorted(List<Integer> numberList) {

        int i = 0;
        int j= 1;
        boolean rightOrder = true;

        while(j < numberList.size()) {
            if(isLessThan(numberList.get(i), numberList.get(j))) {
                i++;
                j++;
            } else {
                rightOrder = false;
                sortNumbers(numberList);
                totalUnsorted+= numberList.get(numberList.size() / 2);
                break;
            }
        }
        if(j == numberList.size() && rightOrder) {
            System.out.println(numberList);
            total+= numberList.get(numberList.size() / 2);
        }
    }

    private static void sortNumbers(List<Integer> numberList) {

        int i = 0;
        int j= 1;

        while(j < numberList.size()) {
            if(isLessThan(numberList.get(i) ,numberList.get(j))) {
                i++;
                j++;
            } else {
                int aux = numberList.get(i);
                numberList.set(i, numberList.get(j));
                numberList.set(j, aux);
                i = 0;
                j = 1;
            }
        }
    }

    private static boolean isLessThan(int number1, int number2) {

        boolean isLess = true;
        int i = 0;
        while(i < rules.size() && isLess) {
            if(rules.get(i).get(1) == number1 && rules.get(i).get(0) == number2) {
                isLess = false;
            }
            i++;
        }
        return isLess;
    }
}
