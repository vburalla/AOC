package AOC2024.day7;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 {

    private static final Pattern numberPattern = Pattern.compile("\\d+");

    public static void main(String[] args) {
        System.out.println("Day 7");
        List<String> lines = ReadFiles.getInputData("AOC2024/day7/input1.txt");

        Long total = 0L;
        Long total2 = 0L;
        for (String line : lines) {
            total += getResultIfPossible(line, false);
            total2 += getResultIfPossible(line, true);
        }

        System.out.println("Total: " + total);
        System.out.println("Total: " + total2);

    }

    private static Long getResultIfPossible(String line, boolean thirdOperator) {

        List<Long> operators = getNumbers(line);
        Long expectedResult = operators.remove(0);
        List<Long> results = List.of(operators.remove(0));
        List<Character> tempOperations = new ArrayList<>();
        Long prevOperator = results.get(0);
        int i = 0;

        while (i < operators.size()) {

            Long operator = operators.get(i);

            List<Long> tempResults = new ArrayList<>();

            for (Long value : results) {

                if (value >= 0) {
                    Long tempResult = value + operator;
                    if (tempResult.equals(expectedResult)) {
                        return expectedResult;
                    } else if (tempResult < expectedResult && i < operators.size() - 1) {
                        tempResults.add(tempResult.longValue());
                        tempOperations.add('+');
                    }
                } else {
                    Long tempResult = -value + Long.parseLong(String.valueOf(prevOperator) + String.valueOf(operator));
                    if (tempResult.equals(expectedResult)) {
                        return expectedResult;
                    } else if (tempResult < expectedResult && i < operators.size() - 1) {
                        tempResults.add(tempResult.longValue());
                        tempOperations.add('+');
                    }
                }

                if (value >= 0) {
                    Long tempResult = value * operator;
                    if (tempResult.equals(expectedResult)) {
                        return expectedResult;
                    } else if (tempResult < expectedResult && i < operators.size() - 1) {
                        tempResults.add(tempResult.longValue());
                        tempOperations.add('*');
                    }
                } else {
                    Long tempResult = -value * Long.parseLong(String.valueOf(prevOperator) + String.valueOf(operator));
                    if (tempResult.equals(expectedResult)) {
                        return expectedResult;
                    } else if (tempResult < expectedResult && i < operators.size() - 1) {
                        tempResults.add(tempResult.longValue());
                        tempOperations.add('*');
                    }
                }
                if (thirdOperator) {
                    Long tempResult = Long.parseLong(String.valueOf(prevOperator) + String.valueOf(operator));
                    if (tempResult.equals(expectedResult)) {
                        return expectedResult;
                    } else if (tempResult < expectedResult && i < operators.size() - 1) {
                        tempResults.add(tempResult.longValue());
                    }
                    tempResults.add(-value);
                }
            }
            prevOperator = operator;
            results = tempResults;
            i++;
        }
        return 0L;
    }


    private static List<Long> getNumbers(String line) {

        List<Long> numbers = new ArrayList<>();
        Matcher matcher = numberPattern.matcher(line);

        while (matcher.find()) {
            numbers.add(Long.parseLong(matcher.group()));
        }
        return numbers;
    }

}
