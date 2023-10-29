package day17;

import java.util.*;

public class TetrisResources {

    public static final int EMPTY_ROW_VALUE = 257;
    public static final int INITIAL_EMPTY_ROWS = 3;

    public static List<Integer> getEmptyChamber() {

        List<Integer> chamber = new ArrayList<>();

        for(int i=0; i < INITIAL_EMPTY_ROWS; i++) {
            chamber.add(EMPTY_ROW_VALUE);
        }
        return chamber;
    }

    public static CircularList<List<Integer>> getPiecesQueue() {

        CircularList<List<Integer>> piecesQueue = new CircularList<>();

        piecesQueue.add(getHLinePiece());
        piecesQueue.add(getCrossPiece());
        piecesQueue.add(getLPiece());
        piecesQueue.add(getVLinePiece());
        piecesQueue.add(getSquarePiece());

        return piecesQueue;
    }

    private static List<Integer> getHLinePiece() {
        List<Integer> piece = new ArrayList<>();
        piece.add(60);
        return piece;
    }

    private static List<Integer> getLPiece() {
        List<Integer> piece = new ArrayList<>();
        piece.add(56);
        piece.add(8);
        piece.add(8);
        return piece;
    }

    private static List<Integer> getCrossPiece() {
        List<Integer> piece = new ArrayList<>();
        piece.add(16);
        piece.add(56);
        piece.add(16);
        return piece;
    }

    private static List<Integer> getSquarePiece() {
        List<Integer> piece = new ArrayList<>();
        piece.add(48);
        piece.add(48);
        return piece;
    }

    private static List<Integer> getVLinePiece() {
        List<Integer> piece = new ArrayList<>();
        piece.add(32);
        piece.add(32);
        piece.add(32);
        piece.add(32);
        return piece;
    }
}
