package day2;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.IOException;
import java.util.stream.Collectors;
import day2.RPS;

public class Day2 {
    
    private static List<String> getInputData(String inputFile) {

        try{
            return Files.readAllLines(Paths.get(inputFile));
        } catch (IOException ex) {
            System.out.println("Exception" + ex.getLocalizedMessage());
        }
        return null;
    }

    private static int getResultPoints(String guideLine) {

        String[] plays = guideLine.split(" ");
        RPS elvePlay = RPS.valueOf(plays[0]);
        RPS selfPlay = RPS.valueOf(plays[1]);

        int result = getResult(elvePlay, selfPlay) * 3 + selfPlay.getPoints();
        return result;
    }

    private static int getResult(RPS a, RPS b) {

        int result = 2;

        if(a.getChoice().equals(b.getChoice())) {
            result = 1;
        } else if(a.getWeakOpposite().equals(b.getChoice())) {
            result = 0;
        }
        return result;
    }

    public static void main(String[] args) {

        var guide = getInputData("day2/input1.txt");

        List<Integer> results = guide.stream().map(Day2::getResultPoints).collect(Collectors.toList());
        System.out.println("Result part 1 is: " + results.stream().reduce(0, (a,b)-> (a+b)));


    }
}
