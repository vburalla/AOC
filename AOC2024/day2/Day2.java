package AOC2024.day2;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day2 {

    private static void populateData(List<List<Integer>> reports, String filePath) {

        List<String> lines = ReadFiles.getInputData(filePath);

        for(String line : lines) {
            reports.add(Arrays.stream(line.split(" ")).map(Integer::valueOf).toList());
        }
    }

    private static void countUnsafeReports(List<List<Integer>> reports) {

        System.out.println("Safe reports: " + reports.stream().map(Day2::validateReport).filter(Boolean::booleanValue).count());
        System.out.println("Safe reports with dampener: " + reports.stream().map(Day2::validateReportWithDampener).filter(Boolean::booleanValue).count());
    }

    private static boolean validateReport(List<Integer> report) {

        boolean valid = true;

        int i = 1;
        int distance = report.get(i) - report.get(i-1);

        if(Math.abs(distance) < 4 && distance != 0) {

            boolean increasing = distance > 0;

            do {
                i++;
                distance = report.get(i) - report.get(i - 1);
                valid = distance != 0 && (distance > 0 == increasing) && Math.abs(distance) < 4;
                increasing = distance > 0;

            } while (i < report.size() - 1 && valid);
        } else {
            valid = false;
        }

        return valid;
    }

    private static boolean validateReportWithDampener(List<Integer> report) {

        boolean isValid = validateReport(report);

        if(!isValid) {
            int i = 0;
            while(!isValid && i < report.size()) {

                List<Integer> newReport = new ArrayList<>(report);
                newReport.remove(i);
                isValid = validateReport(newReport);
                i++;
            }
        }

        return isValid;
    }

    public static void main(String[] args) {

        List<List<Integer>> reportLines = new ArrayList<>();
        populateData(reportLines, "AOC2024/day2/input1.txt");
        countUnsafeReports(reportLines);
    }
}
