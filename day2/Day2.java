package day2;

import utils.ReadFiles;
import java.util.List;

public class Day2 {

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
        

        return getResult(elvePlay, selfPlay) * 3 + selfPlay.getPoints();
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

        List<String> guide = ReadFiles.getInputData("day2/input1.txt");

        System.out.println("Result part 1 is: " + guide.stream().map(play -> Day2.getResultPoints(play, 1)).mapToInt(Integer::valueOf).sum());
        System.out.println("Result part 2 is: " + guide.stream().map(play -> Day2.getResultPoints(play, 2)).mapToInt(Integer::valueOf).sum());


    }
}
