package day3;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.IOException;
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

    private static List<String> getCommonItemsInSingleGroups(List<String> itemsGroup) {

        String group1, group2, group3;
        List<String> repeatedLetters = new ArrayList();
        for(int i=0; i<itemsGroup.size(); i+=3) {
            group1 = itemsGroup.get(i);
            group2 = itemsGroup.get(i+1);
            group3 = itemsGroup.get(i+2);

            String repeated = getCommonItems(group1, group2);
            repeated = getCommonItems(repeated, group3);
            repeatedLetters.add(repeated);
        }

        return repeatedLetters;
    }

    private static Integer sumCharValuesInStringList(List<String> groups) {

        var chars = getCommonItemsInSingleGroups(groups);
        Integer total = 0; 
        for(String ch : chars) {
            total+= getCharValue(ch.charAt(0));
        }
        return total;
    }

    private static String getCommonItems(String a, String b) {
        
        String repeated ="";
        int i = 0;
        while(i < a.length()) {
            
            char ch = a.charAt(i);
            if(b.indexOf(ch) >= 0) repeated+= ch;
            i++;
        }
        return repeated;
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
        System.out.println("Part 1:" + repeatedItems.stream().mapToInt(Integer::valueOf).sum());
        System.out.println("Part 2: " + sumCharValuesInStringList(getInputData("day3/input1.txt")));
    }
}

