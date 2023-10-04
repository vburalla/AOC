package day3;

import utils.ReadFiles;
import java.util.List;
import java.util.ArrayList;

class Day3 {

    private static List<Character> getCommonItems(List<String> itemsGroup) {
        
        String group1, group2;
        List<Character> repeatedList = new ArrayList<>();

        for(String items : itemsGroup) {
            group1 = items.substring(0, items.length()/2);
            group2 = items.substring(items.length()/2);

            repeatedList.add(getCommonChars(group1, group2).get(0));
        }
        return repeatedList;
    }

    private static List<Character> getCommonItemsInSingleGroups(List<String> itemsGroup) {

        String group1, group2, group3;
        List<Character> repeatedLetters = new ArrayList();
        for(int i=0; i<itemsGroup.size(); i+=3) {
            group1 = itemsGroup.get(i);
            group2 = itemsGroup.get(i+1);
            group3 = itemsGroup.get(i+2);

            var repeated = getCommonChars("" + getCommonChars(group1, group2), group3);
            repeatedLetters.add(repeated.get(0));
        }

        return repeatedLetters;
    }

    private static Integer sumCharValuesInStringList(List<String> groups, int part) {

        var chars = part==1? getCommonItems(groups) : getCommonItemsInSingleGroups(groups);
        Integer total = 0; 
        for(Character ch : chars) {
            total+= getCharValue(ch);
        }
        return total;
    }

    private static List<Character> getCommonChars(String a, String b) {

        List<Character> commonChars = new ArrayList<>();

        int i = 0;
        while(i < a.length()) {
            
            char ch = a.charAt(i);
            if(b.indexOf(ch) >= 0) commonChars.add(ch);;
            i++;
        }
        return commonChars;
        
    }

    private static int getCharValue(int asciiPosition) {

        return (asciiPosition > 96)? asciiPosition - 96 : asciiPosition - 38;
        
    }

    public static void main(String[] args) {

        List<String> itemGroups = ReadFiles.getInputData("day3/input1.txt");
        System.out.println("Part 1:" + sumCharValuesInStringList(itemGroups, 1));
        System.out.println("Part 2: " + sumCharValuesInStringList(itemGroups, 2));
    }
}

