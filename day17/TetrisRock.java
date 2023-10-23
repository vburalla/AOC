package day17;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class TetrisRock {

    public static final String EMPTY_ROW = ".......";

    public static Deque<List<String>> getPiecesQueue() {

        Deque<List<String>> pieces = new ArrayDeque<>();
        pieces.addLast(getRowPiece());
        pieces.addLast(getCrossPiece());
        pieces.addLast(getLPiece());
        pieces.addLast(getVerticalPiece());
        pieces.addLast(getSquarePiece());

        return pieces;
    }

    private static List<String> getRowPiece() {
        List<String> rowPiece = new ArrayList<>();
        rowPiece.add("..@@@@.");
        return rowPiece;
    }

    private static List<String> getCrossPiece() {
        List<String> crossPiece = new ArrayList<>();
        crossPiece.add("...@...");
        crossPiece.add("..@@@..");
        crossPiece.add("...@...");
        return crossPiece;
    }

    private static List<String> getLPiece() {
        List<String> lPiece = new ArrayList<>();
        lPiece.add("....@..");
        lPiece.add("....@..");
        lPiece.add("..@@@..");
        return lPiece;
    }

    private static List<String> getVerticalPiece() {
        List<String> vPiece = new ArrayList<>();
        vPiece.add("..@....");
        vPiece.add("..@....");
        vPiece.add("..@....");
        vPiece.add("..@....");
        return vPiece;
    }

    private static List<String> getSquarePiece() {
        List<String> squarePiece = new ArrayList<>();
        squarePiece.add("..@@...");
        squarePiece.add("..@@...");
        return squarePiece;
    }

    public static List<String> getChamber() {
        List<String> chamber = new ArrayList<>();

        for(int i=0; i<3; i++) {
            chamber.add(EMPTY_ROW);
        }
        return chamber;
    }

    
}
