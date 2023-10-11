package Day14;

import utils.Point;
import utils.ReadFiles;

public class Day14 {

    private static char[][] cave;
    private static int sandSourceColumn = 500;
    private static final String path = "day14/input1.txt";
    public static void main(String[] args) {

        cave = getCave(path);
        System.out.println("Part 1: " + throwSandUntilRebase());
        cave = getCave(path);
        cave = Cave.addEdgeToCave(cave, new Point(cave.length-1,0 ), new Point(cave.length-1,999));
        System.out.println("Part 2: " + throwSandUntilRebase());
    }

    private static char[][] getCave(String filePath) {
        return Cave.getCave(ReadFiles.getInputData(filePath));
    }

    private static int throwSandUntilRebase() {
        int thrown = 0;
        boolean rebasedOrBlocked = false;
        while(!rebasedOrBlocked) {
            rebasedOrBlocked = throwSand();
            thrown++;
        }
        return thrown-1;
    }

    private static boolean throwSand() {
        Point nextPoint = new Point(0,sandSourceColumn);
        Point currentPoint;
        boolean fixed = false;
        if(cave[nextPoint.getX()][nextPoint.getY()] == 'o') return true;
        while(!fixed && nextPoint.getX() < cave.length-1 && cave[0][sandSourceColumn]!='o') {
            currentPoint = nextPoint.clone();
            setPointChar(currentPoint,'+');

            nextPoint = canMove(currentPoint);
            if(nextPoint == null) {
                setPointChar(currentPoint,'o');
                nextPoint = currentPoint;
                fixed = true;
            } else {
                setPointChar(currentPoint,'·');
            }
        }
        return nextPoint.getX() == cave.length - 1;
    }

    private static Point canMove(Point point) {
        Point nextMovingPoint = null;
        if(cave[point.getX()+1][point.getY()] != '#' && cave[point.getX()+1][point.getY()] != 'o') {
            nextMovingPoint = point.clone();
            nextMovingPoint.setX(nextMovingPoint.getX()+1);
        } else {
            if (canMoveDownLeft(point)) {
                nextMovingPoint = point.clone();
                nextMovingPoint.setX(point.getX() + 1);
                nextMovingPoint.setY(point.getY() - 1);
            } else if (canMoveDownRight(point)) {
                nextMovingPoint = point.clone();
                nextMovingPoint.setX(point.getX() + 1);
                nextMovingPoint.setY(point.getY() + 1);
            }
        }
        return nextMovingPoint;
    }

    private static boolean canMoveDownLeft(Point cPoint) {
        return cave[cPoint.getX()+1][cPoint.getY()-1] == '·' ;
    }

    private static void setPointChar(Point point, char ch) {

        cave[point.getX()][point.getY()] = ch;
    }

    private static boolean canMoveDownRight(Point cPoint) {

        return cave[cPoint.getX()+1][cPoint.getY()+1] == '·' ;
    }

    private static void printCave() {

        for(int i=0; i< cave.length; i++) {
            System.out.println(new String(cave[i]).substring(400, 600));
        }
        try {
            Thread.sleep(300);
        } catch (Exception ex) {
            System.out.println("error");
        }
        System.out.println();
    }
}
