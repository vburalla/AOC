package day21;

import utils.ReadFiles;

import java.util.*;

public class Day21 {

    private static Map<String, Monkey> calculableMonkeys;
    private static List<Monkey> notCalculableMonkeys;
    private static final String ROOT_NAME = "root";
    private static Monkey rootMonkey;
    private static Monkey humnMonkey;

    public static void main(String[] args) {

        createMonkeys(ReadFiles.getInputData("day21/input1.txt"));
        calculateMonkeys();
        createMonkeys(ReadFiles.getInputData("day21/input1.txt"));
        calculateMonkeys2();
    }

    public static void createMonkeys(List<String> monkeysDefinition) {

        calculableMonkeys = new HashMap<>();
        notCalculableMonkeys = new ArrayList<>();

        for (String info : monkeysDefinition) {
            var data = info.split(": ");
            Monkey monkey = new Monkey(data[0], data[1]);
            if(monkey.getName().equals("humn")) humnMonkey = monkey;
            if(monkey.getName().equals("root")) rootMonkey = monkey;
            if (monkey.hasNumber()) {
                calculableMonkeys.put(data[0], monkey);
            } else {
                notCalculableMonkeys.add(monkey);
            }
        }
    }

    private static void calculateMonkeys() {

        int i = 0;
        while (!calculableMonkeys.containsKey(ROOT_NAME) && !notCalculableMonkeys.isEmpty() && i < notCalculableMonkeys.size()) {
            Monkey currentMonkey = notCalculableMonkeys.get(i);
            if (calculableMonkeys.containsKey(currentMonkey.getParents().get(0)) && calculableMonkeys.containsKey(currentMonkey.getParents().get(1))) {
                currentMonkey.calculate(
                        calculableMonkeys.get(currentMonkey.getParents().get(0)).getValue(),
                        calculableMonkeys.get(currentMonkey.getParents().get(1)).getValue());
                calculableMonkeys.put(currentMonkey.getName(), currentMonkey);
                notCalculableMonkeys.remove(i);
                i = 0;
            } else {
                i++;
            }
        }
        System.out.println(String.format("%s value = %d.", ROOT_NAME, calculableMonkeys.get(ROOT_NAME).getValue()));
    }

    private static void calculateMonkeys2() {

        var humnMonkey = calculableMonkeys.remove("humn");
        int i = 0;
        while (i < notCalculableMonkeys.size()) {
            Monkey currentMonkey = notCalculableMonkeys.get(i);
            if (calculableMonkeys.containsKey(currentMonkey.getParents().get(0)) && calculableMonkeys.containsKey(currentMonkey.getParents().get(1))) {
                currentMonkey.calculate(
                        calculableMonkeys.get(currentMonkey.getParents().get(0)).getValue(),
                        calculableMonkeys.get(currentMonkey.getParents().get(1)).getValue());
                calculableMonkeys.put(currentMonkey.getName(), currentMonkey);
                notCalculableMonkeys.remove(i);
                i = 0;
            } else {
                i++;
            }
        }
        createFormula();
    }

    private static void createFormula() {

        Long value1 = 0L;
        String uncalculableMonkeyName = "";
        if(calculableMonkeys.containsKey(rootMonkey.getParents().get(0))) {
            value1 = calculableMonkeys.get(rootMonkey.getParents().get(0)).getValue();
            uncalculableMonkeyName = rootMonkey.getParents().get(1);
        } else if(calculableMonkeys.containsKey(rootMonkey.getParents().get(1))) {
            value1 = calculableMonkeys.get(rootMonkey.getParents().get(1)).getValue();
            uncalculableMonkeyName = rootMonkey.getParents().get(0);
        }

        for(Monkey monkey : notCalculableMonkeys) {
            calculableMonkeys.put(monkey.getName(), monkey);
        }

        String formula = uncalculableMonkeyName + " = " + String.valueOf(value1);
        LinkedList<String> monkeyNames = new LinkedList<>();
        monkeyNames.add(uncalculableMonkeyName);
        String monkeyName;
        while(!monkeyNames.isEmpty()) {
            monkeyName = monkeyNames.pollFirst();
            Monkey monkey = calculableMonkeys.get(monkeyName);
            if(!monkeyName.equals("humn")) {
                formula = formula.replace(monkeyName, createFormula(monkey));
                if(monkey.getValue()==null){
                    monkeyNames.addLast(monkey.getParents().get(0));
                    monkeyNames.addLast(monkey.getParents().get(1));
                }
            }
        }


        System.out.println(formula);
    }

    private static String createFormula(Monkey rootMonkey) {
        String formula = "";
        if(rootMonkey.getValue() != null) {
            formula = String.valueOf(rootMonkey.getValue().toString());
        } else {
            formula+= "(" + rootMonkey.getParents().get(0) + " " + rootMonkey.getOperator() + " " + rootMonkey.getParents().get(1) + ")";
        }
        return formula;
    }
}
