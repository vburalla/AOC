import utils.ReadFiles;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

class Day9 {

    private static Set<String> visitedPositions = new HashSet<>();
    private static Position headPosition;
    private static Position tailPosition;

    public static void main(String[] args) {

        List<String> instructions = ReadFiles.getInputData("day9/test.txt");
        headPosition = new Position(0, 0);
        tailPosition = new Position(0, 0);
        move(instructions);
    }

    private static void move(List<String> movements) {
        
        visitedPositions.add(tailPosition.toString());
        for(String movement : movements) {
            moveRope(movement);
        }
    }

    private static void moveRope(String movement) {

        String[] movementParts = movement.split(" ");
        int steps = Integer.parseInt(movementParts[1]);
        for(int i=0; i<steps;i++){
            Position stepPos = getDisplacement(movementParts[0]);
            headPosition.changePosition(stepPos);
            moveTail();
        }
    }

    private static void moveTail() {

        var distance = tailPosition.getDistanceTo(headPosition);
        if( Math.abs(Math.max(distance.getX(), distance.getY())) > 1) {
            
        }

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