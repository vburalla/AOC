import utils.ReadFiles;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

class Day9 {

    private static Set<String> visitedPositions = new HashSet<>();
    private static int x,y;

    public static void main(String[] args) {

        List<String> instructions = ReadFiles.getInputData("day9/test.txt");
        moveTail(instructions);
    }

    private static void moveTail(List<String> movements) {
        Position headPosition = new Position(0, 0);
        Position tailPosotion = new Position(0, 0);
        
        visitedPositions.add(tailPosotion.toString());
        for(String movement : movements) {

        }
    }

    private static void moveRope(Position head, Position tail, String movement) {

        String[] movementParts = movement.split(" ");
        int steps = Integer.parseInt(movementParts[1]);
        for(int i=0; i<steps;i++){
            Position stepPos = getDisplacement(movementParts[0]);
        }
    }

    private static Position getDisplacement(String direction) {

        Position position;

        switch (direction) {
            case "U":
                position = new Position(0,1);
            case "D":
                position = new Position(0,-1);
            case "L":
                position = new Position(-1,0);
            default:
                position = new Position(1,0);
        }
        return position;
    }

}