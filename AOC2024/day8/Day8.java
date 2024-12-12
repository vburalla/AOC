package AOC2024.day8;

import utils.Point;
import utils.ReadFiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day8 {

    static char[][] grid;
    static Long antiNodes = 0L;
    static Long harmonics = 0L;

    public static void main(String[] args) {
        System.out.println("Day 8");
        fillGrid("AOC2024/day8/input1.txt");
        createAntennaAntiNodes();
        System.out.println("Total antiNodes: " + antiNodes);
        System.out.println("Total harmonics: " + (harmonics + antiNodes));
    }

    private static void fillGrid(String path) {

        grid = ReadFiles.getInputDataAsCharMatrix(path);
    }

    private static void createAntiNodes(Antenna antenna1, Antenna antenna2) {

        Point distance = antenna1.getUbication().substract(antenna2.getUbication());
        Point antiNode1 = antenna1.getUbication().add(distance);
        Point antiNode2 = antenna2.getUbication().substract(distance);

        if(isInside(antiNode1)) {
            drawAntiNode(antiNode1, false);
        }
        if(isInside(antiNode2)) {
            drawAntiNode(antiNode2, false);
        }
    }

    private static void createAntiNodesAndHarmonics(Antenna antenna1, Antenna antenna2) {

        Point distance = antenna1.getUbication().substract(antenna2.getUbication());

        Point antiNode1 = antenna1.getUbication().add(distance);
        Point antiNode2 = antenna2.getUbication().substract(distance);

        drawAntiNode(antenna1.getUbication(), true);
        drawAntiNode(antenna2.getUbication(), true);

        boolean harmonic = false;
        while(isInside(antiNode1)) {
            drawAntiNode(antiNode1, harmonic);
            //harmonic = true;
            antiNode1 = antiNode1.add(distance);
        }

        harmonic = false;
        while(isInside(antiNode2)) {
            drawAntiNode(antiNode2, harmonic);
            //harmonic = true;
            antiNode2 = antiNode2.substract(distance);
        }

    }

    private static boolean isInside(Point antiNode) {

        return antiNode.getX() >= 0
                && antiNode.getY() >= 0
                && antiNode.getX() < grid.length
                && antiNode.getY() < grid[0].length;
    }



    private static void drawAntiNode(Point antiNode, boolean isHarmonic) {

        if( grid[antiNode.getX()][antiNode.getY()] != '#' && grid[antiNode.getX()][antiNode.getY()] != '%') {
            if(isHarmonic) {
                grid[antiNode.getX()][antiNode.getY()] = '%';
                harmonics++;
            } else {
                grid[antiNode.getX()][antiNode.getY()] = '#';
                antiNodes++;
            }

        }
    }

    private static Map<Character, List<Antenna>> getAntennaUbications() {

        List<Antenna> antennas = new ArrayList<>();
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] != '.') {
                    antennas.add(new Antenna(new Point(i, j), grid[i][j]));
                }
            }
        }
        return antennas.stream().collect(Collectors.groupingBy(Antenna::getFrequency));
    }

    private static void createAntennaAntiNodes() {

        var antennas = getAntennaUbications();

        for(Map.Entry<Character, List<Antenna>> entry : antennas.entrySet()) {

            List<Antenna> antennaList = entry.getValue();

            if(antennaList.size() > 1) {
                for(int i=0; i<antennaList.size()-1; i++) {
                    Antenna antenna1 = antennaList.get(i);
                    for(int j= i+1; j<antennaList.size(); j++) {
                        Antenna antenna2 = antennaList.get(j);
                        createAntiNodesAndHarmonics(antenna1, antenna2);
                    }
                }
            }
        }
    }
}

class Antenna {

    Point ubication;

    Character frequency;

    public Antenna(Point ubication, Character frequency) {
        this.ubication = ubication;
        this.frequency = frequency;
    }

    public Character getFrequency() {
        return frequency;
    }

    public Point getUbication() {
        return ubication;
    }
}