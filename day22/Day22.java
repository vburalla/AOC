package day22;

import utils.CarouselList;
import utils.Point;
import utils.ReadFiles;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day22 {

    private static CarouselList<Point> directions = new CarouselList<>();

    public static void main(String[] args) {

        List<String> lines = ReadFiles.getInputData("day22/test.txt");
        createDirections();
        int maxLength = 0;
        int instructionsLine = 0;
        String instructions = "";
        while (instructionsLine < lines.size()) {
            var line = lines.get(instructionsLine);
            if (!line.equals("")) {
                if (line.length() > maxLength) maxLength = line.length();
                instructionsLine++;
            } else {
                break;
            }
        }
        instructions = lines.get(instructionsLine + 1);
        var instructionsList = getInstructionsList(instructions);
        var board = new Board(lines.subList(0, instructionsLine), maxLength);
        moveFollowingInstructions(instructionsList, board);
    }

    private static void moveFollowingInstructions(List<String> instructionsList, Board board) {

        Point position = new Point(board.getLeftLimit(0),0 );
        Point direction = new Point(1,0);

        int i =0;
        while(i < instructionsList.size()) {

            String instruction = instructionsList.get(i);
            if(instruction.matches("\\d+")) {
                int steps = Integer.parseInt(instruction);
                int step = 0;
                Point newPos;
                while(step < steps) {
                    newPos = board.move(position, direction);
                    if(newPos.equals(position)) {
                        break;
                    } else {
                        position = newPos;
                    }
                    System.out.println(position);
                    step++;
                }

            } else {
                direction = rotate(instruction, direction);
            }
            i++;
        }
        System.out.printf("x = %d, y = %d%n", position.getX()+1, position.getY()+1);
        System.out.printf("Part 1: %d%n", 1000 * (position.getY() + 1) + 4 * (position.getX() + 1) + directions.getCurrentIndex());
    }

    private static Point rotate(String direction, Point currentDirection) {

        return direction.equals("R")? directions.getNext() : directions.getPrev();
    }

    private static void createDirections() {

        directions.add(new Point(1,0));
        directions.add(new Point(0,1));
        directions.add(new Point(-1,0));
        directions.add(new Point(0,-1));
    }

    private static List<String> getInstructionsList(String instructions) {

        List<String> inst = new ArrayList<>();
        Pattern pattern = Pattern.compile("([\\d]+)|([RL])");
        Matcher m = pattern.matcher(instructions);

        while(m.find()) {
            inst.add(m.group(0));
        }
        return inst;
    }
}
