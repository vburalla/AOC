package day16;

import java.util.ArrayList;
import java.util.List;

public class Path {
    
    String destination;
    int steps;
    List<String> path;

    public Path(String destination, int steps, List<String> path) {

        this.steps = steps;
        this.destination = destination;
        this.path = path;
    }

    public Path(String destination) {
        this.steps = 0;
        this.destination = destination;
        this.path = new ArrayList<>();
    }
}
