import utils.ReadFiles;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

class Day9 {

    private static Set<String> visitedPositions = new HashSet<>();
    private static Position[] knotPositions;

    private static Position[] createFoldedRope(int knots) {

        Position[] rope = new Position[knots + 1];
        for(int i = 0; i < knots + 1; i++) {
            rope[i] = new Position(0, 0);
        }
        return rope;
    }

    public static void main(String[] args) {

        List<String> instructions = ReadFiles.getInputData("day9/input1.txt");
        knotPositions = createFoldedRope(1);
        move(instructions, 1);
        System.out.println("Part 1: " + visitedPositions.size());
        knotPositions = createFoldedRope(9);
        move(instructions, 9);
        System.out.println("Part 2: " + visitedPositions.size());
    }

    private static void move(List<String> movements, int monitorizedKnot) {
        visitedPositions.clear();
        visitedPositions.add(knotPositions[monitorizedKnot].toString());
        for(String movement : movements) {
            moveRope(movement, monitorizedKnot);
        }
    }

    private static void moveRope(String movement, int monKnot) {

        String[] movementParts = movement.split(" ");
        int steps = Integer.parseInt(movementParts[1]);
        for(int i=0; i < steps; i++) {
            Position stepPos = getDisplacement(movementParts[0]);
            knotPositions[0].changePosition(stepPos);
            moveTailKnots();
            visitedPositions.add(knotPositions[monKnot].toString());
        }
    }

    private static void moveTailKnots() {

        for (int i = 1; i < knotPositions.length; i++) {
            var distance = knotPositions[i].getDistanceTo(knotPositions[i-1]);
            if( Math.max(Math.abs(distance.getX()), Math.abs(distance.getY())) > 1) {
                Position tailDiplacement = new Position(getNewCoordinate(distance.getX()),getNewCoordinate(distance.getY()));
                knotPositions[i].changePosition(tailDiplacement);
            }        
        }
    }

    private static int getNewCoordinate(int coordinateDifference) {

        return coordinateDifference == 0? 0 : -1 * coordinateDifference + (coordinateDifference / 2);
    }

    private static Position getDisplacement(String direction) {

        Position position;

        switch (direction) {
            case "U":
                position = new Position(0,1);
                break;
            case "D":
                position = new Position(0,-1);
                break;
            case "L":
                position = new Position(-1,0);
                break;
            default :
                position = new Position(1,0);
        }
        return position;
    }

}