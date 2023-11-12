package day22;

import utils.Point;

import java.util.LinkedList;
import java.util.List;

public class Board {

    private static final char VALID_POSITION = '.';
    private static final char WALL_POSITION = '#';
    private static final char OUTSIDE_POSITION = ' ';

    private char[][] boardMatrix;
    private LinkedList<Integer> leftLimit = new LinkedList<>();
    private LinkedList<Integer> rightLimit = new LinkedList<>();
    private int rows;
    private int cols;

    public Board(List<String> boardLines, int maxLength) {

        int i = 0;
        this.boardMatrix = new char[boardLines.size()][maxLength];
        this.rows = boardLines.size();
        this.cols = maxLength;

        for(String line : boardLines) {
            if(line.length() < maxLength) {
                line = line + " ".repeat(maxLength - line.length());
                boardLines.set(i, line);
            }
            this.boardMatrix[i] = line.toCharArray();
            this.leftLimit.add(line.indexOf(VALID_POSITION));
            this.rightLimit.add(line.lastIndexOf(VALID_POSITION));
            i++;
        }
    }

    public Integer getLeftLimit(int row) {
        return this.leftLimit.get(row);
    }

    public Point move(Point currentPosition, Point direction) {

        Point newPosition = currentPosition.add(direction);
        if(newPosition.getX() < 0 || newPosition.getX() >= cols || newPosition.getY() < 0 || newPosition.getY() >= rows ) {
            newPosition = getMirrorPosition(currentPosition, direction);
        } else {
            char positionType = this.boardMatrix[newPosition.getY()][newPosition.getX()];
            if (positionType == WALL_POSITION) {
                newPosition = currentPosition;
            } else if (positionType == OUTSIDE_POSITION) {
                newPosition = getMirrorPosition(currentPosition, direction);
            }
        }
        return newPosition;
    }

    private Point getMirrorPosition(Point cPos, Point dir) {

        Point newPoint;
        if(dir.getX() == 0) {
            newPoint = new Point(cPos.getX(), getMirrorRow(cPos, dir.getY()));
        } else {
            newPoint = new Point(getMirrorColumn(cPos, dir.getX()), cPos.getY());
        }
        return newPoint;
    }

    private int getMirrorRow(Point point, int direction) {

        int i = 0;

        while((leftLimit.get(point.getY() - (direction * i)) <= point.getX()) && (rightLimit.get(point.getY()) - (direction * i)) >= point.getX()) {
            i++;
        }
        i--;
        return boardMatrix[point.getY() - (direction * i)][point.getX()] != WALL_POSITION? point.getY() - (direction * i) : point.getY();

    }

    private int getMirrorColumn(Point point, int direction) {

        int col = direction > 0? leftLimit.get(point.getY()) : rightLimit.get(point.getY());

        return boardMatrix[point.getY()][col] == WALL_POSITION? point.getX() : col;

    }
}
