package Day14;

import utils.Point;

import java.util.*;

public class Cave {

    private static Set<Point> pointsList = new HashSet<>();

    public static char[][] getCave(List<String> definitionLines) {

        for(String line : definitionLines) {
            pointsList.addAll(getPoints(line));
        }
        var cave = getEmptyCave(getMaxRow()+3,1000);
        cave = drawEdgesInCave(cave);
        return cave;
    }

    public static char[][] addEdgeToCave(char[][] cave, Point init, Point end) {
        List<Point> points = getPointsBetween2Points(init, end);
        for(Point point : points) {
            cave[point.getX()][point.getY()] = '#';
        }
        return cave;
    }

    public static List<Point> getPoints(String edgeDefinition) {

        List<Point> points = new ArrayList<>();
        List<String> boundaries =  new ArrayList<String>(Arrays.asList(edgeDefinition.split(" -> ")));
        Point point1 = createPoint(boundaries.remove(0));
        while(!boundaries.isEmpty()){
            Point point2 = createPoint(boundaries.remove(0));
            points.addAll(getPointsBetween2Points(point1, point2));
            point1 = point2;
        }
        return points;
    }
    private static Point createPoint(String strPoint) {

        String[] stCoord = strPoint.split(",");
        return new Point(Integer.parseInt(stCoord[1]),Integer.parseInt(stCoord[0]));
    }

    private static List<Point> getPointsBetween2Points(Point p1, Point p2) {

        List<Point> points = new ArrayList<>();

        if(p1.getX() > p2.getX()) {
            for(int i=p2.getX(); i<=p1.getX(); i++) {
                points.add(new Point(i, p1.getY()));
            }
        } else if(p1.getX() < p2.getX()) {
            for(int i=p1.getX(); i<=p2.getX(); i++) {
                points.add(new Point(i, p1.getY()));
            }
        } else if(p1.getY() > p2.getY()) {
            for(int i=p2.getY(); i<=p1.getY(); i++) {
                points.add(new Point(p1.getX(), i));
            }
        } else if(p1.getY() < p2.getY()) {
            for(int i=p1.getY(); i<=p2.getY(); i++) {
                points.add(new Point(p1.getX(), i));
            }
        }
        return points;
    }

    private static int getMaxRow() {
        int maxRow = 0;
        int row;
        for(Point point : pointsList) {
            if((row = point.getX()) > maxRow) {
                maxRow = row;
            }
        }
        return maxRow;
    }

    private static char[][] getEmptyCave(int rows, int columns) {

       char[][] cave = new char[rows][columns];
       char[] emptyRow =  "·".repeat(columns).toCharArray();
       for(int i=0; i<rows;i++) {
            cave[i] = emptyRow.clone();
       }
       return cave;
    }

    private static char[][] drawEdgesInCave(char[][] cave) {

        for(Point point : pointsList) {
            cave[point.getX()][point.getY()] = '#';
        }
        return cave;
    }
}
