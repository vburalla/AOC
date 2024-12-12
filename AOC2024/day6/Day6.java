package AOC2024.day6;

import utils.Point;
import utils.ReadFiles;

import java.util.*;

public class Day6 {

    static char[][] grid;

    static Set<Point> visitedPoints = new HashSet<>();
    static Set<Point> loopPositions = new HashSet<>();
    static Point startPosition;
    static Map<GuardUbication, Integer> guardUbicationMap = new HashMap<>();


    public static void main(String[] args) {

        grid = ReadFiles.getInputDataAsCharMatrix("AOC2024/day6/input1.txt");
        startPosition = getGuardStartPosition();
        moveUntilExitGrid();
        System.out.println("Part 1: " + visitedPoints.size());
        System.out.println("Part 2: " + getLoopPositions());

    }

    private static Integer getLoopPositions() {

        Integer totalOptions = 0;

        for(Point visitedPoint : visitedPoints) {

            if(!visitedPoint.equals(startPosition)) {

                grid[visitedPoint.getX()][visitedPoint.getY()] = '#';
                guardUbicationMap.clear();

                boolean isLoop = moveUntilRepeatPattern();
                if (!isLoop) {
                    totalOptions++;
                    loopPositions.add(visitedPoint);
                    grid[visitedPoint.getX()][visitedPoint.getY()] = 'O';
                } else {
                    grid[visitedPoint.getX()][visitedPoint.getY()] = '.';
                }
            }
        }

        return totalOptions;
    }

    private static Point getGuardStartPosition() {

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '^') {
                    return new Point(i, j);
                }
            }
        }
        return null;
    }

    private static GuardUbication moveInside(GuardUbication guardUbication) {

        Point newPosition = guardUbication.position.add(guardUbication.direction);

        if(isOutside(newPosition)) {
            return null;
        }

        while (grid[newPosition.getX()][newPosition.getY()] == '#') {
            turnRight(guardUbication.direction);
            newPosition = guardUbication.position.add(guardUbication.direction);

            if(isOutside(newPosition)) {
                return null;
            }
        }
        guardUbication.position = newPosition;

        return guardUbication;
    }

    private static void turnRight(Point direction) {
        if (direction.getX() == 0) {
            direction.setX(direction.getY());
            direction.setY(0);
        } else {
            direction.setY(-direction.getX());
            direction.setX(0);
        }
    }

    private static boolean moveUntilRepeatPattern() {

        GuardUbication guardUbication = new GuardUbication(startPosition.clone(), new Point(-1, 0));
        GuardUbication currentGuardUbication = guardUbication.clone();
        Integer visitedTimes = 0;

        do {

            visitedTimes = Day6.guardUbicationMap.getOrDefault(currentGuardUbication, 0);
            if(visitedTimes.equals(0)) {
                Day6.guardUbicationMap.put(currentGuardUbication, 1);
            } else {
                Day6.guardUbicationMap.put(currentGuardUbication, visitedTimes + 1);
            }

            currentGuardUbication = moveInside(currentGuardUbication);

        } while (currentGuardUbication != null && visitedTimes < 6);

        return currentGuardUbication == null;
    }

    private static boolean isOutside(Point position) {
        return position.getX() < 0
                || position.getX() >= grid.length
                || position.getY() < 0
                || position.getY() >= grid[0].length;
    }

    private static void moveUntilExitGrid() {

        GuardUbication guardUbication = new GuardUbication(startPosition.clone(), new Point(-1, 0));
        GuardUbication currentGuardUbication = guardUbication.clone();

        do {
            visitedPoints.add(currentGuardUbication.position);

            currentGuardUbication = moveInside(currentGuardUbication);

        } while (currentGuardUbication != null);
    }
}

class GuardUbication {
    Point position;
    Point direction;

    public GuardUbication(Point position, Point direction) {
        this.position = position;
        this.direction = direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, direction);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuardUbication that = (GuardUbication) o;
        return Objects.equals(position, that.position) && Objects.equals(direction, that.direction);
    }

    public GuardUbication clone() {
        return new GuardUbication(position.clone(), direction.clone());
    }
}