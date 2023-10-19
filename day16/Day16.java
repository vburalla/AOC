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
        moveReleasingPressure(valvesMap);
        System.out.println();
    }

    private static Map<String, Valve> getValvesInfo(List<String> infoLines) {

        Map<String, Valve> valvesMap = infoLines.stream().map(Day16::createValveFromTextLine).collect(Collectors.toMap(Valve::getName,Function.identity()));
        
        return valvesMap;
    }

    private static void moveReleasingPressure(Map<String, Valve> valves) {

        int minutes = 30;
        String currentValve = "AA";
        while(minutes > 0) {
            var valvePaths = createPathMap(currentValve, valves);
            var bestValvePath = valvePaths.get(0);
            List<String> destinationPath = bestValvePath.path;
            destinationPath.remove(0);
            for(String valve : destinationPath) {
                releasePressure(valves);
                if(valve.equals(bestValvePath.currentNode)){
                    valves.get(valve).openValve();
                    currentValve = valve;
                    break;
                }
                minutes--;
                System.out.println(String.format("Remaining %s", minutes));
            }
        }
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

    private static List<Path> createPathMap(String originValve, Map<String, Valve> valves) {

        //Map<String, List<String>> bestPaths = new HashMap<>();
        List<Path> bPaths = new ArrayList();
        
        for(String valveDestination : valves.keySet()) {
            if(!valveDestination.equals(originValve) && !valves.get(valveDestination).open) {
                //if(!bestPaths.containsKey(originValve + "-" + valveDestination)) {
                    //var best = findBestPath(originValve, valveDestination, valves).get(0).path;
                    var bPath = findBestPath(originValve, valveDestination, valves).get(0);
                    //bestPaths.put(originValve + "-" + valveDestination, best);
                    bPaths.add(bPath);
                //}
            }
        }
        Collections.sort(bPaths);
        return bPaths;
    }

    private static void releasePressure(Map<String, Valve> valves) {
        
        for(String valveName : valves.keySet()) {
            valves.get(valveName).releasePressure();
        }
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
                            newPath.reach(valves.get(destination).flowRate);
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
