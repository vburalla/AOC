package day11;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.List;

public class Day11 {

    public static void main(String[] args) {

        List<String> information = ReadFiles.getInputData("day11/test.txt");
    }

    public List<Monkey> getMonkeys(List<String> monkeyInfo) {

        List<Monkey> monkeys = new ArrayList<>();
        int line = 0;
        String lineText = "";
        do {
            Monkey monkey = new Monkey();
            while(line < monkeyInfo.size() && !(lineText = monkeyInfo.get(line)).equals("")) {
                if(lineText.contains("Starting items:")) {

                } else if(lineText.contains("Operation:")){

                } else if(lineText.contains("Test")){

                } else if(lineText.contains("If true")) {

                } else if(lineText.contains("If false")) {

                }
                line++;
            }
            monkeys.add(monkey);
        } while (monkeyInfo.size()>0);
    }

    private void createMonkeyWithGivenInfo(List<String> info) {
        Monkey monkey = new Monkey();
        
    }


        return monkeys;
    }

}