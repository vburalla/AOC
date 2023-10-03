package day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

class Day8 {

    private static List<List<Integer>> treeMatrix;

    private static List<String> getInputData(String inputFile) {

        try{
            return Files.readAllLines(Paths.get(inputFile));
        } catch (IOException ex) {
            System.out.println("Exception" + ex.getLocalizedMessage());
        }
        return null;
    }

    private static List<List<Integer>> convertLinesToIntegerMatrix(List<String> lines) {

        return lines.stream().map(Day8::convertToIntArray).collect(Collectors.toList());

    }

    private static List<Integer> convertToIntArray(String stringLine) {

        return stringLine.chars().mapToObj(Character::getNumericValue).collect(Collectors.toList());

    }

    private static void countVisibleTrees() {

        int visibleTrees = 0;
        int maxScenicScore = 0;
        int score;

        for(int i=1; i < treeMatrix.size()-1; i++) {
            var row = treeMatrix.get(i);
            for(int j=1; j < row.size()-1; j++) {
                Integer treeHeight = row.get(j);
                if(isVisibleFromLeft(treeHeight, i, j) || isVisibleFromRight(treeHeight, i, j) || isVisibleFromTop(treeHeight, i, j) || isVisibleFromBottom(treeHeight, i, j)) visibleTrees++;
                if((score = calculateScenicScore(treeHeight, i, j)) > maxScenicScore) maxScenicScore = score;
            }
        }
        System.out.println("Part 1: " +  (visibleTrees + perimeterTrees()));
        System.out.println("Part 2: " + maxScenicScore);
    }

    private static int perimeterTrees() {

        int columns = treeMatrix.size();
        int rows = treeMatrix.get(0).size();

        return (2 * rows + 2 * columns) - 4;
    }

    private static boolean isVisibleFromLeft(Integer currentTreeHeigth, int rowNumber, int columnNumber) {

        List<Integer> currentRow = treeMatrix.get(rowNumber);
        int k = columnNumber - 1;
        boolean tallest = true;

        do {
            var treeH = currentRow.get(k);
            if(treeH >= currentTreeHeigth) tallest = false;
            k--; 
        } while (k >= 0 && tallest);
        return tallest;
    }

    private static boolean isVisibleFromRight(Integer currentTreeHeigth, int rowNumber, int columnNumber) {

        List<Integer> currentRow = treeMatrix.get(rowNumber);
        int k = columnNumber + 1;
        boolean tallest = true;

        do {
            var treeH = currentRow.get(k);
            if(treeH >= currentTreeHeigth) tallest = false;
            k++; 
        } while (k < currentRow.size() && tallest);
        return tallest;
    }

    private static boolean isVisibleFromTop(Integer currentTreeHeigth, int rowNumber, int columnNumber) {

        int k = rowNumber - 1;
        boolean tallest = true;

        do {
            var treeH = treeMatrix.get(k).get(columnNumber);
            if(treeH >= currentTreeHeigth) tallest = false;
            k--; 
        } while (k >= 0 && tallest);
        return tallest;
    }

    private static boolean isVisibleFromBottom(Integer currentTreeHeigth, int rowNumber, int columnNumber) {

        int k = rowNumber + 1;
        boolean tallest = true;

        do {
            var treeH = treeMatrix.get(k).get(columnNumber);
            if(treeH >= currentTreeHeigth) tallest = false;
            k++; 
        } while (k < treeMatrix.size() && tallest);
        return tallest;
    }

    private static int calculateScenicScore(Integer currentTreeHeigth, int rowNumber, int columnNumber) {

        List<Integer> currentRow = treeMatrix.get(rowNumber);
        int k = columnNumber - 1;
        int score = 1;
        int generalScore = 1;
        while(k > 0 && currentRow.get(k) < currentTreeHeigth) {
            score++;
            k--;
        }
        generalScore*=score;
        score = 1;
        k = columnNumber +1;
        while(k < currentRow.size()-1 && (currentRow.get(k) < currentTreeHeigth)) {
            score++;
            k++;
        }
        generalScore*=score;
        score = 1;
        k = rowNumber-1;
        while(k > 0 && (treeMatrix.get(k).get(columnNumber) < currentTreeHeigth)) {
            score++;
            k--;
        }
        generalScore*=score;
        score = 1;
        k = rowNumber + 1;
        while(k < treeMatrix.size()-1 && (treeMatrix.get(k).get(columnNumber) < currentTreeHeigth)) {
            score++;
            k++;
        }
        generalScore*=score;
        
        return generalScore;

    }

    public static void main(String[] args) {

        treeMatrix = convertLinesToIntegerMatrix(getInputData("day8/input1.txt"));
       countVisibleTrees();
        
    }
}