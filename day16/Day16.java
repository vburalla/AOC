package day16;

import utils.ReadFiles;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Day16 {
    
    public static void main(String[] args) {
        var lines = ReadFiles.getInputData("day16/test.txt");
        var valves = getValvesInfo(lines);
    }

    private static LinkedList<Valve> getValvesInfo(List<String> infoLines) {

        LinkedList<Valve> valves = new LinkedList<>();
        for(String line : infoLines) {
            
            Pattern namesPattern = Pattern.compile("[A-Z]{2}");
            Matcher namesMatcher = namesPattern.matcher(line);
            LinkedList<String> names = new LinkedList<>();
            int i = 0; 
            while(namesMatcher.find()) {
                names.add(namesMatcher.group(0));
            }
            String name = (String) names.pollFirst();
            int flowRate = Integer.parseInt(line.substring(line.indexOf("=") + 1, line.indexOf(";")));
            valves.add(new Valve(name, flowRate, names));
        }
        return valves;
    }
}
