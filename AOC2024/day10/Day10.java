package AOC2024.day10;

import utils.Point;
import utils.ReadFiles;

import java.util.*;

public class Day10 {

    private static Integer[][] grid;

    public static void main(String[] args) {
        System.out.println("Day 10");

        grid = ReadFiles.getInputDataAsIntegerMatrix("AOC2024/day10/input1.txt");

        List<Point> zeroPoints = getZeroPoints();
        System.out.println(zeroPoints);
        Score score = getTrailHeadsScores(zeroPoints);
        System.out.println("Total head score: " + score.headScore);
        System.out.println("Total ratings: " + score.ratings);
    }

    private static List<Point> getZeroPoints() {

        List<Point> zeroPoints = new ArrayList<>();
        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[i].length; j++) {
                if(grid[i][j].equals(0)) {
                    zeroPoints.add(new Point(i, j));
                }
            }
        }
        return zeroPoints;
    }

    private static Score getTrailHeadsScores(List<Point> zeroPoints) {

        Integer totalScore = 0;
        Integer totalRatings = 0;

        for(Point point: zeroPoints) {
            Score score = getTrailScore(point);
            totalScore += score.headScore;
            totalRatings += score.ratings;
        }
        return new Score(totalScore, totalRatings);
    }

    private static Score getTrailScore(Point point) {

        Score score = new Score(0,0);
        List<List<Point>> paths = new ArrayList();
        paths.add(List.of(point));
        Integer nextValue = 1;

        while(!paths.isEmpty() && nextValue <= 9) {
            List<List<Point>> newList = new ArrayList<>();
            for (List<Point> points : paths) {

                Set<Point> possiblePoints = getNextPoints(points.get(points.size() - 1), nextValue);
                if (!possiblePoints.isEmpty()) {
                    for (Point possiblePoint : possiblePoints) {
                        List<Point> path = new ArrayList<>(points);
                        path.add(possiblePoint);
                        newList.add(path);
                    }
                }
            }
            nextValue++;
            paths = newList;
        }
        return new Score(getTrailHeadScore(paths), paths.size());
    }

    private static Set<Point> getNextPoints(Point point, Integer nextValue) {

        Set<Point> possiblePoints = new HashSet<>();

        if(point.getX() > 0 && grid[point.getX()-1][point.getY()].equals(nextValue)) {
            possiblePoints.add(new Point(point.getX()-1, point.getY()));
        } if(point.getX() < grid.length-1 && grid[point.getX()+1][point.getY()].equals(nextValue)) {
            possiblePoints.add(new Point(point.getX()+1, point.getY()));
        } if(point.getY() > 0 && grid[point.getX()][point.getY()-1].equals(nextValue)) {
            possiblePoints.add(new Point(point.getX(), point.getY()-1));
        } if(point.getY() < grid[0].length-1 && grid[point.getX()][point.getY()+1].equals(nextValue)) {
            possiblePoints.add(new Point(point.getX(), point.getY()+1));
        }
        return possiblePoints;
    }

    private static Integer getTrailHeadScore(List<List<Point>> paths) {

        Set<Point> heads = new HashSet<>();
        for(List<Point> path: paths) {
            heads.add(path.get(path.size()-1));
        }
        return heads.size();
    }
}

class Score {

    public Integer headScore;

    public Integer ratings;

    public Score(Integer headScore, Integer ratings) {
        this.headScore = headScore;
        this.ratings = ratings;
    }
}
