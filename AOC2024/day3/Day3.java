package AOC2024.day3;

import utils.ReadFiles;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

    private static final Pattern expression = Pattern.compile("(mul\\(\\d{1,3},\\d{1,3}\\))|(do(n't)?\\(\\))");
    private static final String avoidMultiply = "don't\\(\\)";
    private static final String enableMultiply = "do\\(\\)";

    private static Long calculateResult(String values) {

        values = values.replace("mul(","").replace(")","");
        String[] numbers = values.split(",");
        return Long.valueOf(numbers[0]) * Long.valueOf(numbers[1]);
    }

    public static void main (String[] args) {

        List<String> lines = ReadFiles.getInputData("AOC2024/day3/input1.txt");
        Long result = 0L;
        Boolean multiplyStatus = null;
        int i = 0;
        for(String line : lines) {
            Matcher matcher = expression.matcher(lines.get(i));
            while(matcher.find()) {
                if(matcher.group(0).matches(avoidMultiply)) {
                    multiplyStatus = false;
                } else if (matcher.group(0).matches(enableMultiply)) {
                    multiplyStatus = true;
                } else {
                    if (multiplyStatus == null || multiplyStatus.equals(Boolean.TRUE))
                        result += calculateResult(matcher.group(0));
                }
            }
            i++;
        }

        System.out.println("Result part1: " + result);

    }
}
