package day3;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.ArrayList;

class Day3 {

    private static List<String> getInputData(String inputFile) {

        try{
            return Files.readAllLines(Paths.get(inputFile));
        } catch (IOException ex) {
            System.out.println("Exception" + ex.getLocalizedMessage());
        }
        return null;
    }

    private static List<Integer> getCommonItems(List<String> itemsGroup) {
        
        String group1, group2;
        List<Integer> repeatedList = new ArrayList<>();

        for(String items : itemsGroup) {
            group1 = items.substring(0, items.length()/2);
            group2 = items.substring(items.length()/2);

            repeatedList.add(getCommonItem(group1, group2));
        }
        return repeatedList;
    }

    private static int getCommonItem(String a, String b) {

        int repeated = 0;
        boolean found = false;
        int i = 0;
        while(!found) {
            if(a.indexOf(b.charAt(i)) >= 0 ) { 
                found = true;
                repeated = b.charAt(i);
            }
            else {
                i++;
            }
        }
        return getCharValue(repeated);
    }

    private static int getCharValue(int asciiPosition) {

        return (asciiPosition > 96)? asciiPosition - 96 : asciiPosition - 38;
        
    }

    public static void main(String[] args) {

        List<Integer> repeatedItems = getCommonItems(getInputData("day3/input1.txt"));
        System.out.println(repeatedItems);
        System.out.println(repeatedItems.stream().mapToInt(Integer::valueOf).sum());
    }
}

