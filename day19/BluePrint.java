package day19;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BluePrint {

    String name;
    List<Robot> robots;

    public BluePrint(String name) {

        this.name = name;
        this.robots = new ArrayList<>();
    }

    public Robot getRobot(String type) {
        Optional<Robot> robot = robots.stream().filter(r -> r.getType().equals(type)).findFirst();
        return robot.orElse(null);
    }

}
