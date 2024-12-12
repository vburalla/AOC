package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReadFiles {
    
    public static List<String> getInputData(String inputFile) {

        try{
            return Files.readAllLines(Paths.get(inputFile));
        } catch (IOException ex) {
            System.out.println("Exception" + ex.getLocalizedMessage());
        }
        return null;
    }

    public static char[][] getInputDataAsCharMatrix(String inputFile) {
        List<String> lines = getInputData(inputFile);
        char[][] charMatrix = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            charMatrix[i] = lines.get(i).toCharArray();
        }
        return charMatrix;
    }

    public static Integer[][] getInputDataAsIntegerMatrix(String inputFile) {
        List<String> lines = getInputData(inputFile);
        Integer[][] integerMatrix = new Integer[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            integerMatrix[i] = lines.get(i).chars().mapToObj(Character::getNumericValue).toArray(Integer[]::new);
        }
        return integerMatrix;
    }
}
