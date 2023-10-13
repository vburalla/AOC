package day1;

import utils.ReadFiles;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;

class Day1 {

    private static List<Integer> getMaxCalories(List<String> calories) {

        List<Integer> elves = new ArrayList<>();
        
        Integer sum;

        Iterator<String> listIterator = calories.iterator();

        sum = Integer.valueOf(listIterator.next());

        while(listIterator.hasNext()) {
            String value = listIterator.next();
            if(value.equals("")) {

                elves.add(sum);
                sum = 0;
            } else {
                sum += Integer.valueOf(value);
            }
        }
       Collections.sort(elves);
        return elves;
    }

    public static void main(String[] args) {

        List<Integer> sortedElvesCalories = getMaxCalories(ReadFiles.getInputData("/day1/input1.txt"));

        System.out.println("Solution part 1: " + sortedElvesCalories.get(sortedElvesCalories.size()-1));
        System.out.println("Solution part 2: " + sortedElvesCalories.subList(sortedElvesCalories.size()-3,sortedElvesCalories.size())
            .stream().reduce(0, (subtotal, element) -> subtotal + element));

    }
}