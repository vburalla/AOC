package day16;

import java.util.ArrayList;
import java.util.List;

public class Combination {

    public static List<String> getAllCombinations(List<String> elements, String first) {

        List<String> combinedElements = new ArrayList<>();
        for(String element : elements) {
            List<String> reducedElements = new ArrayList<>(elements);
            reducedElements.remove(element);
            String currentPath = first + "-" + element;
            combinedElements.addAll(combine(reducedElements, currentPath));
        }
        return combinedElements;
    }

    private static List<String> combine(List<String> elm, String comb) {

        List<String> combined = new ArrayList<>();
        if(elm.size() > 1) {
            for (String el : elm) {
                List<String> redElm = new ArrayList<>(elm);
                redElm.remove(el);
                combined.addAll(combine(redElm, comb + "-" + el));
            }
        } else {
            return List.of(comb + "-" + elm.get(0));
        }
        return combined;
    }

}
