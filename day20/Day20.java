package day20;

import utils.ReadFiles;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day20 {

    private static final long DECRYPTION_KEY = 811589153;

    public static void main(String[] args) throws IOException {

        List<String> tokens = ReadFiles.getInputData("day20/input1.txt");
        System.out.println(String.format("Part 1: %d", mixAndSort(tokens,1,1)));
        System.out.println(String.format("Part 2: %d: ", mixAndSort(tokens, 10, DECRYPTION_KEY)));

    }

    private static long mixAndSort(List<String> tokens, int mixNumber, long multiplyCoeficient) {

        List<Element> elements = tokens.stream().map(Element::new).collect(Collectors.toList());
        LinkedList<Element> originalElements = new LinkedList<>(elements);
        Element zeroElement = null;
        for(int i=0; i<mixNumber; i++) {
            for (Element element : originalElements) {

                int elementPosition = elements.indexOf(element);
                elements.remove(elementPosition);
                long finalPosition = (elementPosition + element.value * multiplyCoeficient) % elements.size();
                if(finalPosition < 0) finalPosition += elements.size();
                if(element.value == 0) zeroElement = element;

                elements.add((int) finalPosition, element);
            }
        }
       return elements.get((elements.indexOf(zeroElement) + 1000) % elements.size()).value * multiplyCoeficient
                + elements.get((elements.indexOf(zeroElement) + 2000) % elements.size()).value * multiplyCoeficient
                + elements.get((elements.indexOf(zeroElement) + 3000) % elements.size()).value * multiplyCoeficient;
    }


}
