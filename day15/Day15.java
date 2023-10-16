package day15;

import utils.ReadFiles;
import utils.Point;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15 {

    public static void main(String[] args) {

        var sensors = getSensortsAndBeacons(ReadFiles.getInputData("day15/input1.txt"));
        System.out.println("Part 1: " + calculatePossiblePoints(2000000, sensors));
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

    /**private static int getManhattanDistance(Point point1, Point point2) {

        return Math.abs(point1.getX() - point2.getX()) + Math.abs(point1.getY() - point2.getY());
    }*/

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

        List<List<Range>> coveredRanges = new ArrayList<>(restrictionMax-restrictionMin);
        for(Sensor sensor : sensors) {
            int manhattan = sensor.getManhattan();
            int sensorRow = sensor.getUbicationY();
            int initialRow = ((sensorRow - manhattan) >= restrictionMin)? (sensorRow - manhattan) : restrictionMin;
            int finalRow = ((sensorRow + manhattan) <= restrictionMax)? (sensorRow + manhattan) : restrictionMax;
            for(int row = 0; (row <= manhattan) && ((row + manhattan) < restrictionMax); row++) {

                List<Range> lineRanges = coveredRanges.get(row + sensorRow);
                if(!lineRanges.isEmpty()) {
                    lineRanges.add(sensor.getRangeAtRow(row + sensorRow));
                } else {
                    lineRanges.set(row, sensor.getRangeAtRow(row + sensorRow));
                }
                if((row - manhattan) >= restrictionMin) {
                    lineRanges = coveredRanges.get(sensorRow - row);
                    if(!lineRanges.isEmpty()) {
                        lineRanges.add(sensor.getRangeAtRow(sensorRow - row));
                    } else {
                        lineRanges.set(row, sensor.getRangeAtRow(sensorRow - row));
                    } 
                }
            }
            
        }
    }

    
}
