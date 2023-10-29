package day17;

import utils.ReadFiles;

import java.util.*;
import java.util.stream.Collectors;

public class Day17 {

    private static List<Integer> chamber;
    private static CircularList<List<Integer>> piecesQueue;
    private static CircularList<Character> windDirections;
    private static final Character DOWN_MOVEMENT_CHAR = 'd';
    private static final int PART1_ROCKS_NUMBER = 2022;
    private static LinkedList<CycleInfo> cycleInfoList;
    private static List<String> cycleHash;
    private static final int MIN_CYCLE_WINDOW = 64;
    private static int cycleDelta;
    private static int cycleSize;
    private static long SECOND_PART_PIECES_NUMBER = 1000000000000L;

    public static void main(String[] args) {
        cycleInfoList = new LinkedList<>();
        cycleHash = new ArrayList<>();
        chamber = TetrisResources.getEmptyChamber();
        piecesQueue = TetrisResources.getPiecesQueue();
        String instructionText = ReadFiles.getInputData("day17/input1.txt").get(0);
        windDirections = new CircularList<>();
        windDirections.addAll(instructionText.chars().mapToObj(ch -> Character.valueOf((char) ch)).collect(Collectors.toCollection(CircularList::new)));
        throwPieces();
    }

    private static void throwPieces() {

        int pieceNumber = 0;
        int freeRows = 0;
        boolean fixed;
        boolean part1 = false;
        boolean part2 = false;
        int previousRowPosition = 0;
        int currentTopRowPosition = 0;

        while (!part1 || !part2) {
            if ((freeRows = chamber.size() - getTopRowPosition()) != TetrisResources.INITIAL_EMPTY_ROWS)
                addEmptyRowsToChamber(TetrisResources.INITIAL_EMPTY_ROWS - freeRows);
            var currentPiece = new ArrayList<>(piecesQueue.getFirstAndRotate());
            var originalCurrentPiece = currentPiece.get(0);
            fixed = false;
            addEmptyRowsToChamber(currentPiece.size());
            var currentRow = chamber.size()-currentPiece.size();
            while(!fixed) {
                Character ch = windDirections.getFirstAndRotate();
                if (canMove(ch, currentRow, currentPiece)) {
                    move(ch, currentRow, currentPiece);
                }
                if (canMove(DOWN_MOVEMENT_CHAR, currentRow, currentPiece)) {
                    currentRow--;
                } else {
                    fixed = true;
                    fixPiece(currentRow, currentPiece);
                    previousRowPosition = currentTopRowPosition;
                    currentTopRowPosition = getTopRowPosition();
                    String hash = originalCurrentPiece.toString() + "#" + (currentTopRowPosition - previousRowPosition);
                    cycleInfoList.add(new CycleInfo(hash, pieceNumber + 1, currentTopRowPosition, currentTopRowPosition - previousRowPosition));
                    if(!part2) {
                        cycleHash.add(hash);
                        if (pieceNumber % 2000 == 0) {
                            part2 = checkForCycle(cycleHash);
                        }
                    }
                }
            }
            pieceNumber++;
            if(pieceNumber == PART1_ROCKS_NUMBER) {
                System.out.println(String.format("Part 1: tower tall after %d is %d", PART1_ROCKS_NUMBER, cycleInfoList.get(cycleInfoList.size()-1).pileTall));
                part1 = true;
            }
        }
    }

    private static int getTopRowPosition() {
        int rowNumber = chamber.size() - 1;
        while (rowNumber > 0 && chamber.get(rowNumber).equals(TetrisResources.EMPTY_ROW_VALUE))
            rowNumber--;

        return !chamber.get(rowNumber).equals(TetrisResources.EMPTY_ROW_VALUE) ? rowNumber + 1 : rowNumber;
    }

    private static void addEmptyRowsToChamber(int rowsToAdd) {

        for (int i = 0; i < Math.abs(rowsToAdd); i++) {
            if (rowsToAdd > 0) {
                chamber.add(TetrisResources.EMPTY_ROW_VALUE);
            } else if (rowsToAdd < 0) {
                chamber.remove(chamber.size() - 1);
            }
        }
    }

    private static boolean canMove(Character direction, int currentRow, List<Integer> piece) {

        var vMovement = direction.equals('d')? -1 : 0;
        if(currentRow > 0 || (currentRow == 0 && !direction.equals('d'))) {
            for (int i = 0; i < piece.size(); i++) {
                int movedPieceRow = piece.get(i);
                if(!direction.equals('d')) {
                    movedPieceRow = direction.equals('<') ? movedPieceRow << 1 : movedPieceRow >> 1;
                }
                int chamberRow = chamber.get(i + currentRow + vMovement);
                if ((chamberRow & movedPieceRow) > 0)
                    return false;
            }
            return true;
        } else {
            return false;
        }
    }

    private static void move(Character direction, int currentRow, List<Integer> piece) {

        for(int i = 0; i < piece.size(); i++) {
            int movedPieceRow = piece.get(i);
            if(!direction.equals('d')) {
                movedPieceRow = direction.equals('<') ? movedPieceRow << 1 : movedPieceRow >> 1;
            }
            piece.set(i, movedPieceRow);
        }
    }

    private static boolean checkForCycle(List<String> hashes) {
        int i = 0;
        int index = 0;
        while(i < hashes.size() - MIN_CYCLE_WINDOW) {

            List<String> sublist1 = hashes.subList(i, i + MIN_CYCLE_WINDOW);
            List<String> sublist2 = hashes.subList(i + MIN_CYCLE_WINDOW, hashes.size());
            if((index = Collections.indexOfSubList(sublist2, sublist1)) > 0) {
                System.out.println(String.format("Start cycle delta at index = %d, size of cycle = %d", i, index + i + MIN_CYCLE_WINDOW));
                cycleSize = index + MIN_CYCLE_WINDOW;
                cycleDelta = i;

                long deltaTall = cycleInfoList.get(cycleDelta).pileTall;
                long cycleTall = cycleInfoList.get(cycleDelta + cycleSize).pileTall - deltaTall;
                long rounds = (SECOND_PART_PIECES_NUMBER - cycleDelta) / cycleSize;
                long additionalPieces = (SECOND_PART_PIECES_NUMBER - cycleDelta) % cycleSize;
                long finalTall = deltaTall + rounds * cycleTall + cycleInfoList.get((int) (cycleDelta + additionalPieces-1)).pileTall - cycleInfoList.get(cycleDelta).pileTall;
                System.out.println(String.format("Part 2: Tower tall after %d pieces: %d", SECOND_PART_PIECES_NUMBER, finalTall));
                return true;
            }
            i++;
        }
        return false;
    }

    private static void fixPiece(int currentPieceRow, List<Integer> piece) {
        for(int i = 0; i < piece.size(); i++) {
            int pieceRow = piece.get(i);
            chamber.set(currentPieceRow + i, chamber.get(currentPieceRow + i) | pieceRow);
        }
    }

}
