package day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class Day6 {

    private static String getInputData(String inputFile) {

        try{
            return Files.readString(Paths.get(inputFile));
        } catch (IOException ex) {
            System.out.println("Exception" + ex.getLocalizedMessage());
        }
        return null;
    }

    private static int findMarkerPosition(String dataStream, int differentChars) {

        int position = 0;
        String initialStream;
        boolean found = false;
        

        while(!found && position <= dataStream.length()) {

            initialStream = dataStream.substring(position, position + differentChars);

            if(!hasRepeatedChars(initialStream)) {
                found = true;
            } else {
                position++;
            }
           
        }
        return found? position + differentChars : -1; 
    }

    private static boolean hasRepeatedChars(String text) {

        boolean repeated = false;

        for(int i=0; i<text.length()-1;i++) {
            if(text.indexOf(text.charAt(i), i+1) >= 0) {
                repeated = true;
                break;
            }
        }
        return repeated;
    }

    public static void main(String[] args) {

        var datastreamBuffer = getInputData("/day1/input1.txt");
        System.out.println("Part 1: " + findMarkerPosition(datastreamBuffer, 4));
        System.out.println("Part 2: " + findMarkerPosition(datastreamBuffer, 14));

    }

}