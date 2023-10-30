package day19;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19 {

    private static List<BluePrint> bluePrints = new ArrayList<>();

    public static void main(String[] args) {

        var lines = ReadFiles.getInputData("day19/test.txt");
        readBluePrints(lines);
        System.out.println();
    }

    private static void readBluePrints(List<String> textLines) {
        String pattern1 = " Each ([a-z]+) robot costs (\\d+ [a-z]+)";
        String pattern2 = "Each ([a-z]+) robot costs (\\d+ [a-z]+) and (\\d+ [a-z]+)";
        Pattern p1 = Pattern.compile(pattern1);
        Pattern p2 = Pattern.compile(pattern2);

        for(String line : textLines) {
            String[] info = line.split(":");
            BluePrint bluePrint = new BluePrint(info[0].replace("Blueprint ",""));
            info = info[1].split("\\.");
            for(String infoLine : info) {
                Matcher m1;
                if(infoLine.matches(pattern1)) {
                    m1 = p1.matcher(infoLine);
                } else {
                    m1 = p2.matcher(infoLine);
                }
                m1.find();
                Robot robot = new Robot(m1.group(1));
                for(int i=2; i <= m1.groupCount(); i++) {
                    robot.addCost(m1.group(i));
                }
                bluePrint.robots.add(robot);
            }
            bluePrints.add(bluePrint);
        }
    }
}
