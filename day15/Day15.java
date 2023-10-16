package day15;

import utils.ReadFiles;
import utils.Point;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15 {

    public static void main(String[] args) {

        var sensors = getSensortsAndBeacons(ReadFiles.getInputData("day15/test.txt"));
        System.out.println("Part 1: " + calculatePossiblePoints(10, sensors));
        findBeaconPositionInRestrictedRange(sensors, 0, 20);
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
        System.out.println(coveredRanges.get(10));
    }

    private static List<Range> updateRangesInRow(List<Range> currentRanges, Range newRange, int minRange, int maxRange) {

        List<Range> finalRanges = new ArrayList<>();
        for(Range range : currentRanges) {
            if(newRange.isIntersected(range)) {
                newRange.mergeIntersected(newRange, minRange, maxRange);
            } else {
                finalRanges.add(range);
            }
        }
        finalRanges.add(newRange);
        return finalRanges;
    }

    
}
