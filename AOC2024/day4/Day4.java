package AOC2024.day4;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.List;

public class Day4 {

    private static final String xmas = "XMAS";

    public static void main(String[] args) {

        List<String> lines = ReadFiles.getInputData("AOC2024/day4/input1.txt");
        detectXmas(lines);
    }

    private static void detectXmas(List<String> lines) {

        int matching = 0;
        int xMatching = 0;

        List<String> extractedLines = new ArrayList<>(lines);
        char[][] charMatrix = getCharMatrix(extractedLines);
        for(int i=0; i<charMatrix.length; i++) {
            for(int j=0; j<charMatrix[i].length; j++) {
                if(charMatrix[i][j] == 'X') {
                    matching += matchingPatterns(charMatrix,i, j);
                }
                if(charMatrix[i][j] == 'A') {
                    xMatching += hasXXmas(i, j,charMatrix);
                }
            }
        }

        System.out.println("Matching patterns: " + matching);
        System.out.println("XMatching patterns: " + xMatching);

    }

    private static char[][] getCharMatrix(List<String> lines) {

        char[][] charMatrix = new char[lines.size()][lines.get(0).length()];

        for(int i=0; i<lines.size(); i++) {
            charMatrix[i] = lines.get(i).toCharArray();
        }
        return charMatrix;
    }

    private static Integer matchingPatterns(char[][] charMatrix, int x, int y) {

        int count = 0;
        count += hasXmas(x, y, charMatrix, 0, 1);
        count += hasXmas(x, y, charMatrix, 0, -1);
        count += hasXmas(x, y, charMatrix, 1, 0);
        count += hasXmas(x, y, charMatrix, -1, -0);
        count += hasXmas(x, y, charMatrix, 1, 1);
        count += hasXmas(x, y, charMatrix, 1, -1);
        count += hasXmas(x, y, charMatrix, -1, -1);
        count += hasXmas(x, y, charMatrix, -1, 1);
        return count;

    }

    private static int hasXmas(int x, int y, char[][] charMatrix, int incX, int incY) {

        boolean found = false;
        boolean badPattern = false;

        String current = "X";
        x+=incX;
        y+=incY;

        while(!found && !badPattern && x >= 0 && x < charMatrix[0].length && y >= 0 && y < charMatrix.length) {

            current += charMatrix[x][y];
            badPattern = !xmas.startsWith(current);
            x += incX;
            y+= incY;
            found = xmas.equals(current);
        }
        return found? 1 : 0;
    }

    private static int hasXXmas(int x, int y, char[][] charMatrix) {

        int value = 0;
        if(x>0 && x<charMatrix.length-1 && y>0 && y<charMatrix[0].length-1) {
            if(charMatrix[x-1][y-1] == 'S' && charMatrix[x-1][y+1] == 'S' && charMatrix[x+1][y+1] == 'M' && charMatrix[x+1][y-1] == 'M') {
                value = 1;
            } else if(charMatrix[x-1][y-1] == 'M' && charMatrix[x-1][y+1] == 'S' && charMatrix[x+1][y+1] == 'S' && charMatrix[x+1][y-1] == 'M') {
                value = 1;
            } else if(charMatrix[x-1][y-1] == 'M' && charMatrix[x-1][y+1] == 'M' && charMatrix[x+1][y+1] == 'S' && charMatrix[x+1][y-1] == 'S') {
                value = 1;
            } else if(charMatrix[x-1][y-1] == 'S' && charMatrix[x-1][y+1] == 'M' && charMatrix[x+1][y+1] == 'M' && charMatrix[x+1][y-1] == 'S') {
                value = 1;
            }
        }
        return value;

    }

}
