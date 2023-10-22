package day16;

import utils.ReadFiles;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.regex.Matcher;

import static day16.Combination.getAllCombinations;

public class Day16 {

    private static final int MINUTES = 30;
    private static final String START_VALVE = "AA";
    
    public static void main(String[] args) {

        var valvesMap = getValvesInfo(ReadFiles.getInputData("day16/input1.txt"));
        moveReleasingPressure(valvesMap);
        System.out.println();
    }

    private static Map<String, Valve> getValvesInfo(List<String> infoLines) {

        Map<String, Valve> valvesMap = infoLines.stream().map(Day16::createValveFromTextLine).collect(Collectors.toMap(Valve::getName,Function.identity()));
        
        return valvesMap;
    }

    private static void moveReleasingPressure(Map<String, Valve> valves) {

        var completePathMapCost = createCompletePathMap(valves,START_VALVE);
        var notBrokenValves = getNotBrokenValvesList(valves);
        List<String> allPossiblePaths = getAllPossiblePaths(notBrokenValves, START_VALVE);

        int max = 0;
        String maxPath = "";
        for(String path : allPossiblePaths) {

            int releasedPressure = calculateMaxReleasedPression(path, completePathMapCost, valves, MINUTES);
            if(releasedPressure > max) {
                max = releasedPressure;
                maxPath = path;
            }
        }
        System.out.println(String.format("Part 1: Released %s pressure following path %s",max, maxPath));

    }

    private static Valve createValveFromTextLine(String valveData) {

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

    private static Map<String, Integer> createCompletePathMap(Map<String, Valve> valves, String originValve) {

        Map<String, Integer> bestPaths = new HashMap<>();
        var notBrokenValves = getNotBrokenValvesList(valves);
        notBrokenValves.add(originValve);
        for(String valveOrigin : notBrokenValves) {
            for(String valveDestination : notBrokenValves) {
                if (!valveDestination.equals(valveOrigin)) {
                    if(!bestPaths.containsKey(valveOrigin + "-" + valveDestination)) {
                        var best = findBestPath(valveOrigin, valveDestination, valves).get(0);
                        bestPaths.put(valveOrigin + "-" + valveDestination, best.cost);
                        if(!bestPaths.containsKey(valveDestination + "-" + valveOrigin)) {
                            bestPaths.put(valveDestination + "-" + valveOrigin, best.cost);
                        }
                    }
                }
            }
        }
        return bestPaths;
    }

    private static List<String> getNotBrokenValvesList(Map<String, Valve> vMap) {

        return vMap.keySet().stream().filter(key -> !vMap.get(key).isBroken()).collect(Collectors.toList());
    }

    private static List<Path> findBestPath(String origin, String destination, Map<String, Valve> valves) {

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

    private static List<String> getAllPossiblePaths(List<String> nodes, String startNode) {

        return getAllCombinations(nodes, startNode);
    }

    private static int calculateMaxReleasedPression(String followedPath, Map<String, Integer> costs, Map<String, Valve> valves, int time) {

        String[] visitedNodes = followedPath.split("-");
        int released = 0;
        for(int i=1; i < visitedNodes.length; i++) {
            String route = visitedNodes[i-1] + "-" + visitedNodes[i];
            int cost = costs.get(route);
            time -= cost + 1;
            released += ( time ) * valves.get(visitedNodes[i]).flowRate;

        }
        return released;

    }

}
