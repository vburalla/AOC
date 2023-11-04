package day19;

import java.util.ArrayList;
import java.util.List;

public class MineralCollect implements Comparable<MineralCollect>{

    Resource resources;
    List<Robot> robots;

    public MineralCollect() {
        this.resources = new Resource();
        this.robots = new ArrayList<>();
    }

    public MineralCollect copy() {

        MineralCollect mineralCollect = new MineralCollect();
        mineralCollect.resources = this.resources.copy();
        mineralCollect.robots = this.copyRobots();
        return mineralCollect;
    }

    public boolean canBuildRobot(Robot robot) {

        return robot.canBuild(this.resources);
    }

    public void collect() {

        for(Robot robot : this.robots) {
            this.resources.addResource(robot.collect());
        }
    }

    public void addRobot(Robot robot) {

        this.robots.add(robot);
    }

    @Override
    public int compareTo(MineralCollect o) {
        if(o == null) {
            return -1;
        }
        if(o.resources.geode == this.resources.geode) {
            return 0;
        } else {
            return o.resources.geode > this.resources.geode? 1 : -1;
        }
    }

    private List<Robot> copyRobots() {

        List<Robot> robots = new ArrayList<>();
        for(Robot robot : this.robots) {
            robots.add(robot.copy(robot.inConstruction));
        }
        return robots;
    }
}
