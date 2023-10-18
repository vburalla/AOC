package day16;

import java.util.ArrayList;
import java.util.List;

public class Path {
    
    int cost;
    List<String> path;

    public Path(int cost, List<String> path) {

        this.cost = cost;
        this.path = path;
    }

    public Path(String destination) {
        this.cost = 0;
        this.path = new ArrayList<>();
    }
}
