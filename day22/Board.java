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
            this.leftLimit.add(getLeftLimit(boardMatrix[i]));
            this.rightLimit.add(getRightLimit(boardMatrix[i]));
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
        int curentRow = point.getY();
        int rowLimit = leftLimit.size();

        while( curentRow < rowLimit && curentRow >= 0
                && (leftLimit.get(curentRow) <= point.getX())
                && (rightLimit.get(curentRow) >= point.getX())) {
            i++;
            curentRow = point.getY() - (direction * i);
        }

        if(curentRow > 0) {
            curentRow = curentRow + direction;
        } else {
            curentRow = 0;
        }
        return boardMatrix[curentRow][point.getX()] != WALL_POSITION? curentRow : point.getY();

    }

    private int getMirrorColumn(Point point, int direction) {

        int col = direction > 0? leftLimit.get(point.getY()) : rightLimit.get(point.getY());

        return boardMatrix[point.getY()][col] == WALL_POSITION? point.getX() : col;

    }

    private int getLeftLimit(char[] chars ) {

        int i=0;

        while(chars[i] == OUTSIDE_POSITION) {
            i++;
        }
        return i;
    }

    private int getRightLimit(char[] chars ) {

        int i=chars.length - 1;

        while(chars[i] == OUTSIDE_POSITION) {
            i--;
        }
        return i;
    }
}
