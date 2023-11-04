package day19;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day19 {

    private static List<BluePrint> bluePrints = new ArrayList<>();
    private static final int MAX_MINUTES = 24;

    public static void main(String[] args) {

        var lines = ReadFiles.getInputData("day19/test.txt");
        readBluePrints(lines);
        for (BluePrint bluePrint : bluePrints) {
            getMaxCollectedGeodesForBluePrint(bluePrint);
        }

    }

    public static int getMaxCollectedGeodesForBluePrint(BluePrint bluePrint) {

        int minute = 0;

        MineralCollect mineralCollect = new MineralCollect();
        mineralCollect.addRobot(getInitialOreRobot());
        List<MineralCollect> mineralCollects = new ArrayList<>();
        mineralCollects.add(mineralCollect);

        while (minute < MAX_MINUTES) {
            List<MineralCollect> newMineralCollects = new ArrayList<>();
            for (MineralCollect currentMineralCollect : mineralCollects) {
                int i = 0;
                boolean canBuild = false;
                while (i < bluePrint.robots.size() && !(canBuild = bluePrint.robots.get(i).canBuild(currentMineralCollect.resources))) {
                    i++;
                }
                if (canBuild) {
                    MineralCollect mineralCollectCopy = currentMineralCollect.copy();
                    mineralCollectCopy.addRobot(bluePrint.robots.get(i).copy(true));
                    mineralCollectCopy.resources.spendResources(bluePrint.robots.get(i).costs);
                    newMineralCollects.add(mineralCollectCopy);
                }
                newMineralCollects.add(currentMineralCollect.copy());
            }
            for (MineralCollect mc : newMineralCollects) {
                mc.collect();
            }
            minute++;
            Collections.sort(newMineralCollects);
            mineralCollects = new ArrayList<>(newMineralCollects);
        }
        Collections.sort(mineralCollects);
        System.out.println(String.format("Blueprint %s can collect %d geodes.", bluePrint.name, mineralCollects.get(0).resources.geode));
        return mineralCollects.get(0).resources.geode;
    }

    private static void readBluePrints(List<String> textLines) {
        String pattern1 = " Each ([a-z]+) robot costs (\\d+ [a-z]+)";
        String pattern2 = "Each ([a-z]+) robot costs (\\d+ [a-z]+) and (\\d+ [a-z]+)";
        Pattern p1 = Pattern.compile(pattern1);
        Pattern p2 = Pattern.compile(pattern2);

        for (String line : textLines) {
            String[] info = line.split(":");
            BluePrint bluePrint = new BluePrint(info[0].replace("Blueprint ", ""));
            info = info[1].split("\\.");
            for (String infoLine : info) {
                Matcher m1;
                if (infoLine.matches(pattern1)) {
                    m1 = p1.matcher(infoLine);
                } else {
                    m1 = p2.matcher(infoLine);
                }
                m1.find();
                Robot robot = new Robot(m1.group(1));
                for (int i = 2; i <= m1.groupCount(); i++) {
                    robot.addCost(m1.group(i));
                }
                bluePrint.robots.add(robot);
            }
            Collections.sort(bluePrint.robots);
            bluePrints.add(bluePrint);
        }
    }

    private static Robot getInitialOreRobot() {

        Robot robot = new Robot("ore");
        robot.inConstruction = false;

        return robot;
    }
}
