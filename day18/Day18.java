package day18;

import utils.ReadFiles;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day18 {
    static int maxX = 0;
    static int minX = 100;
    static int maxY = 0;
    static int minY = 100;
    static int maxZ = 0;
    static int minZ = 100;
    static List<Cube> cubes;
    static Set<String> freeFacesSet;

    public static void main(String[] args) throws IOException {

        List<String> lines = ReadFiles.getInputData("day18/input1.txt");
        cubes = lines.stream().map(Cube::new).collect(Collectors.toList());
        freeFacesSet = new HashSet<>();
        for (Cube cube : cubes) {
            updateLimits(cube);
            cube.removeFreeFaces(cubes);
            freeFacesSet.addAll(cube.freeFaces);
        }

        var sum = cubes.stream().map(cube -> cube.freeFaces.size()).reduce(0, Integer::sum);
        System.out.println(String.format("Part 1: %d free faces", sum));

        var innerCubelets = getInnerCubelets();
        for (Cube cube : cubes) {
            cube.removeFreeFacesFromHash(innerCubelets);
        }
        sum = cubes.stream().map(cube -> cube.freeFaces.size()).reduce(0, Integer::sum);
        System.out.println(String.format("Part 2: %d free faces", sum));
    }

    private static void updateLimits(Cube cube) {

        if (cube.getX() > maxX) maxX = cube.getX();
        if (cube.getX() < minX) minX = cube.getX();
        if (cube.getY() > maxY) maxY = cube.getY();
        if (cube.getY() < minY) minY = cube.getY();
        if (cube.getZ() > maxZ) maxZ = cube.getZ();
        if (cube.getZ() < minZ) minZ = cube.getZ();
    }

    private static List<String> getInnerCubelets() {

        Set<String> freeCubes = new HashSet<>();
        for (String cubelet : freeFacesSet) {
            var cube = new Cube(cubelet);
            if (!isBlockedInAxis("x", 1, cube) || !isBlockedInAxis("x", -1, cube)
                    || !isBlockedInAxis("y", 1, cube) || !isBlockedInAxis("y", -1, cube)
                    || !isBlockedInAxis("z", 1, cube) || !isBlockedInAxis("z", -1, cube)) {
                freeCubes.add(cube.createHash(cube.getX(), cube.getY(), cube.getZ()));
            }
        }
        freeFacesSet.removeAll(freeCubes);
        return freeFacesSet.stream().collect(Collectors.toList());
    }

    private static boolean isBlockedInAxis(String axis, int direction, Cube cub) {
        Cube c = new Cube(cub.getX(), cub.getY(), cub.getZ());
        boolean hasEscape = true;
        while (hasEscape && !c.exceedsLimits(minX, maxX, minY, maxY, minZ, maxZ)) {
            c.move(axis, direction);
            if (cubes.contains(c)) {
                hasEscape = false;
            }
        }
        return !hasEscape;
    }
}