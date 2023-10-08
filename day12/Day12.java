package day12;

import utils.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day12 {

    private static int[][] map;
    private static Point startPoint;
    private static Point targetPoint;

    private static List<String> data;
    public static void main(String[] args) {

        data = ReadFiles.getInputData("day12/input1.txt");
        System.out.println("Part 1: " + findBestPath(data, false, 0));
        System.out.println("Part 2: " + findBestPath(data, true, 'a'));
    }

    private static void createHeightmap(List<String> mapLines, boolean isReachHeighTargetMode) {
        int index;
        map = new int[mapLines.size()][mapLines.get(0).length()];
        for(int i=0; i < mapLines.size(); i++) {
            String line = mapLines.get(i);
            map[i] = line.chars().toArray();
                if ((index = line.indexOf('E')) >= 0) {
                    targetPoint = new Point(i, index);
                    map[i][index] = 'z';
                }
                if ((index = line.indexOf('S')) >= 0) {
                    startPoint = new Point(i, index);
                    map[i][index] = 'a';
                }
        }
    }

    private static int findBestPath(List<String> mapLines, boolean isReachHeightMode, int targetHeight) {

        Set<Point> visitedPoints = new HashSet<>();
        Set<Point> nextStepVisitablePoints = new HashSet<>();
        createHeightmap(mapLines, isReachHeightMode);
        Point currentPosition = isReachHeightMode? targetPoint : startPoint;
        boolean foundTargetHeight = false;

        visitedPoints.add(currentPosition);
        var visitablePoints = getVisitablePoints(currentPosition, visitedPoints);
        int step = 0;
        while((isReachHeightMode && !foundTargetHeight) || (!isReachHeightMode && !visitablePoints.contains(targetPoint))) {
            visitedPoints.addAll(visitablePoints);
            nextStepVisitablePoints.clear();
            while(!visitablePoints.isEmpty()) {
                currentPosition = visitablePoints.remove(0);
                if(isReachHeightMode && (map[currentPosition.getX()][currentPosition.getY()])==targetHeight) {
                    foundTargetHeight = true;
                    step--;
                    break;
                }
                nextStepVisitablePoints.addAll(getVisitablePoints(currentPosition, visitedPoints));
            }
            step++;
            visitablePoints.addAll(nextStepVisitablePoints);
        }
        step++;
        return step;

    }

    private static List<Point> getVisitablePoints(Point position, Set<Point> visited) {

        List<Point> allowedPoints = new ArrayList<>();
        Point point;
        int positionHeight = map[position.getX()][position.getY()];

        if(position.getX() > 0 && (!visited.contains(point = new Point(position.getX() -1, position.getY())))
                && (isOneStepHeight(map[point.getX()][point.getY()], positionHeight))) allowedPoints.add(point);
        if(position.getX() < (map.length - 1) && (!visited.contains(point = new Point(position.getX() + 1, position.getY())))
                && (isOneStepHeight(map[point.getX()][point.getY()], positionHeight))) allowedPoints.add(point);
        if(position.getY() > 0 && (!visited.contains(point = new Point(position.getX(), position.getY() - 1)))
                && (isOneStepHeight(map[point.getX()][point.getY()], positionHeight))) allowedPoints.add(point);
        if(position.getY() < (map[0].length -1) && (!visited.contains(point = new Point(position.getX(), position.getY() + 1)))
                && (isOneStepHeight(map[point.getX()][point.getY()], positionHeight))) allowedPoints.add(point);
        return allowedPoints;
    }

    private static boolean isOneStepHeight(int height1, int height2) {

        return Math.abs(height1 - height2 ) < 2;
    }

}
