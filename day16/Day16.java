package day16;

import utils.ReadFiles;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.regex.Matcher;

public class Day16 {
    
    public static void main(String[] args) {

        var valvesMap = getValvesInfo(ReadFiles.getInputData("day16/test.txt"));
        createPathMap(valvesMap);
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

        Map<String, List<String>> bestPaths = new HashMap<>();
        for(String valveOrigin : valves.keySet()) {
            for(String valveDestination : valves.keySet()) {
                if(!valveDestination.equals(valveOrigin)) {
                    if(!bestPaths.containsKey(valveOrigin + "-" + valveDestination)) {
                        var best = findBestPath(valveOrigin, valveDestination, valves).get(0).path;
                        bestPaths.put(valveOrigin + "-" + valveDestination, best);
                        if(!bestPaths.containsKey(valveDestination + "-" + valveOrigin)) {
                            List<String> reversedBest = new ArrayList<>(best);
                            Collections.reverse(reversedBest);
                            bestPaths.put(valveDestination + "-" + valveOrigin, reversedBest);
                        }
                    }
                }
            }
        }
        return bestPaths;
    }

    private static List<Path> findBestPath(String origin, String destination, Map<String, Valve> valves) {

        var valvesSet = valves.keySet();
        //valvesSet.remove(origin);
        List<Path> paths = new ArrayList<>();
        paths.add(new Path(origin,0, Arrays.asList(origin)));
        boolean reached = false;
        while(!reached) {
            List<Path> newPaths = new ArrayList<>();
            while (!paths.isEmpty()) {
                var path = paths.remove(0);
                List<String> nodeNeighbours = valves.get(path.currentNode).neighbours;
                for (String neighbour : nodeNeighbours) {
                    if (!path.path.contains(neighbour)) {
                        Path newPath = new Path(neighbour, path);
                        if (neighbour.equals(destination)) {
                            reached = true;
                            newPath.reach();
                        }
                        newPaths.add(newPath);
                    }
                }
            }
            if(!reached) {
                paths.addAll(newPaths);
            } else {
                paths = newPaths.stream().filter(Path::isReached).collect(Collectors.toList());
                Collections.sort(paths);
            }
        }

        return paths;
    }
}
