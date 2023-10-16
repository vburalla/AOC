package day15;

import utils.ReadFiles;
import utils.Point;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15 {

    private static final int PART1_OBJECTIVE_ROW = 2000000;
    private static final int PART2_LEFT_BOUNDARY = 0;
    private static final int PART2_RIGHT_BOUNDARY = 4000000;

    public static void main(String[] args) {

        var sensors = getSensortsAndBeacons(ReadFiles.getInputData("day15/input1.txt"));
        System.out.println("Part 1: " + calculatePossiblePoints(PART1_OBJECTIVE_ROW, sensors));
        findBeaconPositionInRestrictedRange(sensors, PART2_LEFT_BOUNDARY, PART2_RIGHT_BOUNDARY);
    }

    public static int calculatePossiblePoints(int line, List<Sensor> sensorList) {

        Set<Point> coveredPoints = new HashSet<>();
        for(Sensor sensor : sensorList) {
            if(sensor.getDistanceToRow(line) <= sensor.getManhattan()) {
                coveredPoints.addAll(getCoveredPointsInLine(sensor, line));
                coveredPoints.remove(sensor.getUbication());
                coveredPoints.remove(sensor.getBeaconUbication());
            }
        }
        return coveredPoints.size();
    }

    private static List<Point> getCoveredPointsInLine(Sensor currentSensor, int targetLine) {

        List<Point> points = new ArrayList<>();
        int variation = currentSensor.getManhattan() - currentSensor.getDistanceToRow(targetLine);
        int maxX = currentSensor.getUbicationX() + variation;
        int minX = currentSensor.getUbicationX() - variation;

        for(int i=minX; i<=maxX; i++) {
            points.add(new Point(i, targetLine));
        }
        return points;
    }

    private static List<Sensor> getSensortsAndBeacons(List<String> info) {

        var sensorsAndBeacons = new ArrayList<Sensor>();
        Pattern pattern = Pattern.compile("x=-?\\d+, y=-?\\d+");
        for(String line : info) {
            int j = 0;
            Matcher mt = pattern.matcher(line);
            Point[] points = new Point[2];
            while(mt.find()) {
                points[j] = new Point(mt.group());
                j++;
            }
            sensorsAndBeacons.add(new Sensor(points[0], points[1]));
        }
        return sensorsAndBeacons;
    }

    private static void findBeaconPositionInRestrictedRange(List<Sensor> sensors, int restrictionMin, int restrictionMax) {

        List<List<Range>> coveredRanges = new ArrayList<>(Collections.nCopies(restrictionMax-restrictionMin + 1, null));
        for(Sensor sensor : sensors) {
            int manhattan = sensor.getManhattan();
            int sensorRow = sensor.getUbicationY();
            int initialRow = ((sensorRow - manhattan) >= restrictionMin)? (sensorRow - manhattan) : restrictionMin;
            int finalRow = ((sensorRow + manhattan) <= restrictionMax)? (sensorRow + manhattan) : restrictionMax;
            for(int row = initialRow; row <= finalRow; row++) {

                List<Range> lineRanges = coveredRanges.get(row);
                if(lineRanges == null) {
                    Range range = sensor.getRangeAtRow(row);
                    if(range.leftLimit < restrictionMin) range.leftLimit = restrictionMin;
                    if(range.rightLimit > restrictionMax) range.rightLimit = restrictionMax;
                    if(range != null) lineRanges = (Arrays.asList(range));
                } else {
                    lineRanges = updateRangesInRow(lineRanges, sensor.getRangeAtRow(row), restrictionMin, restrictionMax);
                }
                coveredRanges.set(row, lineRanges);
            }
        }
        long rowNumber = 0;
        long columnNumber = 0;
        long tunningFrequency = 0;
        for(int i=0; i< coveredRanges.size(); i++) {
            if(coveredRanges.get(i).size() > 1) {
                rowNumber = i;
                List<Range> ranges = coveredRanges.get(i);
                columnNumber = (ranges.get(0).getRightLimit() < ranges.get(1).getRightLimit())? ranges.get(0).getRightLimit()+1 : ranges.get(1).getRightLimit()+1;
                tunningFrequency = PART2_RIGHT_BOUNDARY * columnNumber + rowNumber;
                break;
            }
        }
        System.out.println(String.format("Part 2: Point x=%s, y=%s. Tunning frequency = %s",columnNumber, rowNumber, tunningFrequency));
    }

    private static List<Range> updateRangesInRow(List<Range> currentRanges, Range newRange, int minRange, int maxRange) {

        List<Range> finalRanges = new ArrayList<>();
        for(Range range : currentRanges) {
            if(newRange.isIntersected(range)) {
                newRange.mergeIntersected(range, minRange, maxRange);
            } else {
                finalRanges.add(range);
            }
        }
        finalRanges.add(newRange);
        return finalRanges;
    }

    
}
