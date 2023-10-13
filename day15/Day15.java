package day15;

import utils.ReadFiles;
import utils.Point;

import java.sql.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15 {

    public static void main(String[] args) {

        var sensors = getSensortsAndBeacons(ReadFiles.getInputData("day15/input1.txt"));
        calculatePossiblePoints(2000000, sensors);
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

    private static int getManhattanDistance(Point point1, Point point2) {

        return Math.abs(point1.getX() - point2.getX()) + Math.abs(point1.getY() - point2.getY());
    }

    private static List<Sensor> getSensortsAndBeacons(List<String> info) {

        var sensorsAndBeacons = new ArrayList<Sensor>();
        Pattern pattern = Pattern.compile("x=-?\\d+, y=-?\\d+");
        int sensor = 0;
        for(String line : info) {
            int j = 0;
            Matcher mt = pattern.matcher(line);
            Point[] points = new Point[2];
            while(mt.find()) {
                points[j] = new Point(mt.group());
                j++;
            }
            sensorsAndBeacons.add(new Sensor(points[0], points[1]));
            sensor++;
        }
        return sensorsAndBeacons;
    }
}
