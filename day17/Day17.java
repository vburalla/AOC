package day17;

import utils.Console;
import utils.ReadFiles;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Day17 {

    private static final char FIXED_PIECE_CHAR = '#';
    private static final char CURRENT_PIECE_CHAR = '@';
    private static final char EMPTY_POSITION_CHAR = '.';
    private static final char LEFT_MOVING_CHAR = '<';
    private static final char RIGHT_MOVING_CHAR = '>';
    private static final int CHAMBER_COLUMNS = 7;

    private static Deque<Character> instructions;
    private static Deque<List<String>> pieces;
    private static List<String> chamber;


    public static void main(String[] args) {

        String instructionText = ReadFiles.getInputData("day17/test.txt").get(0);
        instructions = instructionText.chars().mapToObj(ch -> Character.valueOf((char) ch)).collect(Collectors.toCollection(ArrayDeque::new));
        pieces = TetrisRock.getPiecesQueue();
        chamber = TetrisRock.getChamber();
        throwPieces();
    }

    private static void throwPieces() {

        boolean fixed;
        int instruction = 0;

        while (instruction < 2023) {
            int freeRows = chamber.size() - getTopRockPosition();
            if (freeRows != 3) addEmptyRows(3 - freeRows);
            var currentLowPieceRow = chamber.size();
            var piece = pieces.pollFirst();
            pieces.addLast(new ArrayList<>(piece));
            chamber.addAll(piece);
            //printChamber();
            fixed = false;
            while (!fixed) {
                char ch = instructions.pollFirst();
                instructions.addLast(ch);
                if (!isBlockedBySides(ch, currentLowPieceRow, piece) && !isBlockedByPieces(ch, currentLowPieceRow, piece)) {
                    if (ch == LEFT_MOVING_CHAR) {
                        moveLeft(currentLowPieceRow, piece);
                    } else {
                        moveRight(currentLowPieceRow, piece);
                    }
                    //printChamber();
                }
                if (canMoveDown(currentLowPieceRow, piece)) {
                    moveDown(currentLowPieceRow, piece);
                    currentLowPieceRow--;
                    //printChamber();
                } else {
                    fixPiece(currentLowPieceRow, piece);
                    fixed = true;
                    //printChamber();
                }
                instruction++;
            }
        }
        System.out.println(String.format("Part 1: tower is %s units tall", getTopRockPosition()+1));
    }

    private static boolean canMoveDown(int row, List<String> piece) {

        boolean canMove;
        if (row > 0) {
            canMove = true;
            for (int i = row; i < (row + piece.size()); i++) {
                var lineChars = chamber.get(i).toCharArray();
                for (int j = 0; j < CHAMBER_COLUMNS - 1; j++) {
                    if (lineChars[j] == CURRENT_PIECE_CHAR && chamber.get(i - 1).charAt(j) == FIXED_PIECE_CHAR) {
                        canMove = false;
                        break;
                    }
                }
                if (!canMove) break;
            }
        } else {
            canMove = false;
        }
        return canMove;
    }

    private static boolean isBlockedBySides(char direction, int row, List<String> piece) {

        var positionToCheck = (direction == LEFT_MOVING_CHAR) ? 0 : CHAMBER_COLUMNS - 1;
        boolean isBlocked = false;

        for (int i = row; i < row + piece.size(); i++) {
            var currentRow = chamber.get(i);
            if (currentRow.charAt(positionToCheck) == CURRENT_PIECE_CHAR) {
                return true;
            }
        }
        return isBlocked;
    }

    private static boolean isBlockedByPieces(char direction, int row, List<String> piece) {

        boolean isBlocked = false;
        for (int i = row; i < (row + piece.size()); i++) {
            var currentRow = chamber.get(row).toCharArray();
            if (direction == LEFT_MOVING_CHAR) {
                for (int j = CHAMBER_COLUMNS - 1; j > 0; j--) {
                    if (currentRow[j] == CURRENT_PIECE_CHAR && currentRow[j - 1] == FIXED_PIECE_CHAR) return true;
                }
            } else {
                for (int j = 0; j < CHAMBER_COLUMNS - 1; j++) {
                    if (currentRow[j] == CURRENT_PIECE_CHAR && currentRow[j + 1] == FIXED_PIECE_CHAR) return true;
                }
            }
        }
        return isBlocked;
    }

    private static int getTopRockPosition() {

        int rowNumber = chamber.size()-1;

        while (rowNumber > 0 && !chamber.get(rowNumber).contains("#")) {
            rowNumber--;
        }
        return chamber.get(rowNumber).contains("#")? rowNumber+1 : rowNumber;
    }

    private static void addEmptyRows(int n) {
        if(n > 0) {
            for (int i = 0; i < n; i++) {
                chamber.add(TetrisRock.EMPTY_ROW);
            }
        } else if(n < 0) {
            for (int i = 0; i < Math.abs(n); i++) {
                chamber.remove(chamber.size()-1-i);
            }
        }
    }

    private static void printChamber() {
        Console.clearScreen();
        for (int i = chamber.size() - 1; i >= 0; i--) {
            System.out.println(chamber.get(i));
        }
        System.out.println();
        try {
            Thread.sleep(200);
        } catch (Exception ex) {

        }
    }

    private static void moveLeft(int row, List<String> piece) {

        for (int i = row; i < (row + piece.size()); i++) {

            var existingRow = chamber.get(i).toCharArray();

            for (int j = 1; j < (CHAMBER_COLUMNS); j++) {
                if (existingRow[j] == CURRENT_PIECE_CHAR) {
                    existingRow[j - 1] = CURRENT_PIECE_CHAR;
                    existingRow[j] = EMPTY_POSITION_CHAR;
                }
            }
            chamber.set(i, new String(existingRow));
        }
    }

    private static void moveRight(int row, List<String> piece) {

        for (int i = row; i < (row + piece.size()); i++) {

            var existingRow = chamber.get(i).toCharArray();

            for (int j = CHAMBER_COLUMNS - 1; j >= 0; j--) {
                if (existingRow[j] == CURRENT_PIECE_CHAR) {
                    existingRow[j + 1] = CURRENT_PIECE_CHAR;
                    existingRow[j] = EMPTY_POSITION_CHAR;
                }
            }
            chamber.set(i, new String(existingRow));
        }
    }

    private static void moveDown(int row, List<String> piece) {

        for (int i = row; i < (row + piece.size()); i++) {

            var existingRow = chamber.get(i).toCharArray();
            var lowRow = chamber.get(i - 1).toCharArray();
            for (int j = 0; j < CHAMBER_COLUMNS; j++) {
                if (existingRow[j] == CURRENT_PIECE_CHAR) {
                    lowRow[j] = CURRENT_PIECE_CHAR;
                    existingRow[j] = EMPTY_POSITION_CHAR;
                }
            }
            chamber.set(i, new String(existingRow));
            chamber.set(i - 1, new String(lowRow));
        }
    }

    private static void fixPiece(int row, List<String> piece) {

        for (int i = row; i < (row + piece.size()); i++) {
            String currentRow = chamber.set(i, chamber.get(i).replace(CURRENT_PIECE_CHAR, FIXED_PIECE_CHAR));
        }
    }
}
 