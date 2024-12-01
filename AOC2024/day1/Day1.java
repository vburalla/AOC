package AOC2024.day1;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day1 {

    private static void populateLists(List<Integer> list1, List<Integer> list2, String filepath) {

        List<String> lines = ReadFiles.getInputData(filepath);

        for (String line : lines) {
            String[] data = line.split("   ");
            list1.add(Integer.parseInt(data[0]));
            list2.add(Integer.parseInt(data[1]));
        }

        Collections.sort(list1);
        Collections.sort(list2);

    }

    private static Long calculateDistance(List<Integer> list1, List<Integer> list2) {

        Long distance = 0L;
        for (int i = 0; i < list1.size(); i++) {
            distance += Math.abs(list1.get(i) - list2.get(i));
        }
        return distance;
    }

    private static Long calculateSimilarity(List<Integer> list1, List<Integer> list2) {

        Long similarity = 0L;

        Map<Integer, Long> list2Map = list2.stream()
                .collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));

        for (int i = 0; i < list1.size(); i++) {
            Integer value1 = list1.get(i);
            similarity += value1 * list2Map.getOrDefault(value1,0L);
        }
        return similarity;
    }

    public static void main(String[] args) {

        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        populateLists(list1, list2, "AOC2024/day1/input1.txt");
        System.out.println("Distance between two lists = " + calculateDistance(list1, list2));
        System.out.println("Similarity between two lists = " + calculateSimilarity(list1, list2));

    }


}
