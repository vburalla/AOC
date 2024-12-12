package AOC2024.day9;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.List;


public class Day9 {

    public static void main(String[] args) {
        System.out.println("Day 9");
        expandFileMap("AOC2024/day9/input1.txt");
    }

    private static void expandFileMap(String path) {

        String line = ReadFiles.getInputData(path).get(0);

        List<Integer> fileMap = line.chars().mapToObj(Character::getNumericValue).toList();

        List<Integer> deflatedMap = new ArrayList<>();
        List<Integer> nullsList = new ArrayList<>();
        List<Position> nullsPositions = new ArrayList<>();
        List<Position> valuePositions = new ArrayList<>();

        fillFileMap(fileMap, deflatedMap, nullsList, nullsPositions, valuePositions);
        List<Integer> deflatedMap2 = new ArrayList<>(deflatedMap);
        List<Integer> nullsList2 = new ArrayList<>(nullsList);
        compress(deflatedMap, nullsList);
        compress2(deflatedMap2, nullsList2, valuePositions, nullsPositions);
        System.out.println(sumPositions(deflatedMap));
        System.out.println(sumPositions(deflatedMap2));
    }

    private static void fillFileMap(List<Integer> map,
                                    List<Integer> deflatedMap,
                                    List<Integer> nullsList,
                                    List<Position> nullsPositions,
                                    List<Position> valuePositions) {

        int i = 0;

        for (int j = 0; j < map.size(); j++) {

            if (j == (map.size() - 1)) {
                getElements(i, map.get(j), null, deflatedMap, nullsList, nullsPositions, valuePositions);
            } else {
                getElements(i, map.get(j), map.get(j + 1), deflatedMap, nullsList, nullsPositions, valuePositions);
            }
            j++;
            i++;
        }
    }

    private static void getElements(Integer index,
                                    Integer values,
                                    Integer freeSpaces,
                                    List<Integer> deflatedMap,
                                    List<Integer> nullsList,
                                    List<Position> nullsPositions,
                                    List<Position> valuePositions) {

        if (freeSpaces != null && freeSpaces > 0) {

            nullsPositions.add(new Position(deflatedMap.size() + values, deflatedMap.size() + values + freeSpaces - 1));
        }
        valuePositions.add(new Position(deflatedMap.size(), deflatedMap.size() + values - 1));
        int limit = freeSpaces == null ? values : values + freeSpaces;
        for (int i = 0; i < limit; i++) {

            deflatedMap.add(i < values ? index : null);
            if (i >= values) {
                nullsList.add(deflatedMap.size() - 1);
            }
        }
    }

    private static void compress(List<Integer> values, List<Integer> nullsPositions) {

        int i = 0;
        Integer nextNotNullPosition = null;
        while (!nullsPositions.isEmpty()) {

            Integer nullPosition = nullsPositions.remove(0);
            nextNotNullPosition = getFirsNotNullPosition(values, nextNotNullPosition);
            if (nextNotNullPosition < nullPosition) {
                break;
            }
            values.set(nullPosition, values.get(nextNotNullPosition));
            values.set(nextNotNullPosition, null);
        }
    }

    private static void compress2(List<Integer> values, List<Integer> nullsList, List<Position> valuesPositions, List<Position> nullsPositions) {

        for (int i = valuesPositions.size() - 1; i > 0; i--) {

            Position valuePosition = valuesPositions.get(i);
            Integer fitPosition = findFirstFitFreeSpace(nullsPositions, valuePosition);

            if (fitPosition != null) {
                Position nullPosition = nullsPositions.get(fitPosition);
                for (int k = 0; k < valuePosition.getSize(); k++) {
                    values.set(nullPosition.startPosition + k, values.get(valuePosition.startPosition + k));
                    values.set(valuePosition.startPosition + k, null);
                }
                if (nullPosition.getSize() > valuePosition.getSize()) {
                    nullsPositions.set( fitPosition, new Position(nullPosition.startPosition + valuePosition.getSize(), nullPosition.endPosition));
                } else {
                    nullsPositions.set(fitPosition, new Position(0, 0));
                }
            }
            //System.out.println(values);
        }
    }

    private static Integer findFirstFitFreeSpace(List<Position> nullPositions, Position valuePosition) {

        boolean fit = false;
        int i = 0;
        Position nullPosition = nullPositions.get(i);

        while (!fit && i < nullPositions.size() && nullPosition.endPosition < valuePosition.startPosition) {
            fit = nullPosition.getSize() >= valuePosition.getSize();
            if(fit) {
                break;
            } else {
                i++;
                if (i < nullPositions.size())
                    nullPosition = nullPositions.get(i);
            }
        }
        return fit ? i : null;
    }

    private static Integer getFirsNotNullPosition(List<Integer> values, Integer start) {

        if (start == null) {
            start = values.size() - 1;
        }
        while (values.get(start) == null) {
            start--;
        }
        return start;
    }

    private static Long sumPositions(List<Integer> values) {

        Long result = 0L;

        for (int i = 0; i < values.size(); i++) {
            Integer value = values.get(i);
            if (value != null) {
                result += i * value;
            }
        }
        return result;
    }
}

class Position {

    public Integer startPosition;

    public Integer endPosition;

    public Position(Integer startPosition, Integer endPosition) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public Integer getSize() {
        return endPosition - startPosition + 1;
    }
}
