package day2;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.IOException;
import java.util.stream.Collectors;

public class Day2 {
    
    private static List<String> getInputData(String inputFile) {

        try{
            return Files.readAllLines(Paths.get(inputFile));
        } catch (IOException ex) {
            System.out.println("Exception" + ex.getLocalizedMessage());
        }
        return null;
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

    private static int getResultPoints(String guideLine, int modality) {

        String[] plays = guideLine.split(" ");
        RPS elvePlay = RPS.valueOf(plays[0]);
        RPS selfPlay =  RPS.valueOf(plays[1]);

        if(modality == 2) {
            int definedResult = RPS.valueOf(plays[1]).getPoints();
            selfPlay = getDefinedPlay(elvePlay, definedResult);
        }
        

        int result = getResult(elvePlay, selfPlay) * 3 + selfPlay.getPoints();
        return result;
    }

    private static RPS getDefinedPlay(RPS play1, int resultToObtain) {

        RPS rps = play1;
        if(resultToObtain == 1) {
            rps = RPS.getByChoice(play1.getWeakOpposite());
        } else if(resultToObtain == 3) {
            rps = RPS.getByChoice(play1.getStrongOpposite());
        }
        return rps;
    }

    public static void main(String[] args) {

        List<String> guide = getInputData("day2/input1.txt");

        List<Integer> results = guide.stream().map(play -> Day2.getResultPoints(play, 1)).collect(Collectors.toList());
        System.out.println("Result part 1 is: " + results.stream().reduce(0, (a,b)-> (a+b)));
        results = guide.stream().map(play -> Day2.getResultPoints(play, 2)).collect(Collectors.toList());
        System.out.println("Result part 2 is: " + results.stream().reduce(0, (a,b)-> (a+b)));


    }
}
