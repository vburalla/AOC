package day16;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.regex.Matcher;
import java.util.Map;
import java.util.Set;

public class Day16 {
    
    public static void main(String[] args) {

        var valvesMap = getValvesInfo(ReadFiles.getInputData("day16/test.txt"));
        System.out.println();
    }

    private static Map<String, Valve> getValvesInfo(List<String> infoLines) {

        Map<String, Valve> valvesMap = infoLines.stream().map(Day16::createValveFromLine).collect(Collectors.toMap(Valve::getName,Function.identity()));
        
        return valvesMap;
    }

    private static Valve createValveFromLine(String valveData) {

        Pattern pattern = Pattern.compile("Valve ([A-Z]{2}) has flow rate=(\\d+); tunnels? leads? to valves? ([[A-Z]{2},?\\s?]+)");
        Matcher m = pattern.matcher(valveData);
        Valve valve = null;
        if(m.find()) {
            String name = m.group(1);
            int flowRate = Integer.parseInt(m.group(2));
            LinkedList<String> neighbours  = new LinkedList<>();
            neighbours.addAll(List.of(m.group(3).split(", ")));
            valve = new Valve(name, flowRate, neighbours);
        }
        return valve;
    }

    private static Map<String, List<String>> createPathMap(Map<String, Valve> valves) {

        for(String valveId : valves.keySet()) {

        }
    }

    private static List<String> findBestPath(String origin, Map<String, Valve> valves) {

        var valvesSet = valves.keySet();
        valvesSet.remove(origin);
        String path = origin;
        List<String> nodeNeighbours = valves.get(origin).neighbours;
        for(String neighbour : nodeNeighbours) {
            List<String> newNodeNeighbours = new ArrayList<>();
        }
        

    }
}
