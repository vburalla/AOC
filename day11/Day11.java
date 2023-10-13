package day11;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day11 {

    public static void main(String[] args) {

        Monkey[] monkeysList = getMonkeys(ReadFiles.getInputData("day11/input1.txt"));
        playWithMonkeys(monkeysList.clone(), 20, true, 1);
        playWithMonkeys(monkeysList.clone(), 10000, false, 2);
    }

    public static Monkey[] getMonkeys(List<String> monkeyInfo) {

        List<Monkey> monkeys = new ArrayList<>();
        String lineText = "";
        int id = 0;
        do {
            Monkey monkey = new Monkey(id);
            while(!monkeyInfo.isEmpty() && !(lineText = monkeyInfo.remove(0)).equals("")) {
                if(lineText.contains("Starting items:")) {
                    setMonkeyItems(lineText, monkey);
                } else if(lineText.contains("Operation:")){
                    setMonkeyOpeartion(lineText, monkey);
                } else if(lineText.contains("Test")){
                    setMonkeyTestValue(lineText, monkey);
                } else if(lineText.contains("If true")) {
                    setMonkeyIfTestTrue(lineText, monkey);
                } else if(lineText.contains("If false")) {
                    setMonkeyIfTestFalse(lineText, monkey);
                }
            }
            monkeys.add(monkey);
            id++;
        } while (!monkeyInfo.isEmpty());
        return monkeys.toArray(new Monkey[monkeys.size()]);
    }

    private static void setMonkeyItems(String text, Monkey monk) {

        var a = Arrays.asList(text.replace("Starting items ", "").split(": ")[1].split(", "))
        .stream().map(Long::valueOf).collect(Collectors.toList());
        monk.setItems(a);
    }

    private static void setMonkeyOpeartion(String text, Monkey monk) {

        var splittedInfo = text.split(" ");
        
        String op = splittedInfo[splittedInfo.length-1];
        if(op.equals("old")){
            monk.setOperator("^2");
        } else {
            monk.setOperator(splittedInfo[splittedInfo.length-2]);
            monk.setOperationAmount(Integer.parseInt(op));
        }
    }

    private static void setMonkeyTestValue(String text, Monkey monk) {

        var splittedInfo = text.split(" ");
        monk.setTest(Integer.parseInt(splittedInfo[splittedInfo.length-1]));
    }

    private static void setMonkeyIfTestTrue(String text, Monkey monk) {

        var splittedInfo = text.split(" ");
        monk.setMonkeyIfDivisible(Integer.parseInt(splittedInfo[splittedInfo.length-1]));
    }

    private static void setMonkeyIfTestFalse(String text, Monkey monk) {

        var splittedInfo = text.split(" ");
        monk.setMonkeyIfNotDivisible(Integer.parseInt(splittedInfo[splittedInfo.length-1]));
    }

    private static void playWithMonkeys(Monkey[] monkeyPlayers, int rounds, boolean divideWorryLevel, int part) {

        long mcm = calculateLCM(monkeyPlayers);

        for(int i=0; i<rounds; i++) {
            for(int j=0; j<monkeyPlayers.length; j++) {
                while(monkeyPlayers[j].getItemsAmount() > 0) {
                    Item item = monkeyPlayers[j].inspectItem(divideWorryLevel, mcm);
                    monkeyPlayers[item.getObjective()].catchItem(item.getItem());
                }
            }            
        }
        Arrays.sort(monkeyPlayers);

        System.out.println("Round rounds");
        System.out.println(monkeyPlayers[0].toString());
        System.out.println(monkeyPlayers[1].toString());
        System.out.println(monkeyPlayers[2].toString());
        System.out.println(monkeyPlayers[3].toString());

        System.out.println("Part " + part + ": " + getMonkeyBusiness(monkeyPlayers[0], monkeyPlayers[1]));

    }

    private static long getMonkeyBusiness(Monkey monkey1, Monkey monkey2) {

        return (long) monkey1.getInspectedItems() * monkey2.getInspectedItems();
    }

    private static long calculateLCM(Monkey[] allMonkeys) {

        long MCM = 1;

        for(Monkey monk : allMonkeys) {
            MCM*=monk.getDivisionTest();
        }

        return MCM;
    }
}