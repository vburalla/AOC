package day16;

import java.util.LinkedList;

public class Valve {
    String name;
    boolean open;
    int flowRate;
    LinkedList<String> neighbours = new LinkedList<>();
    int releasedPressure;

    public Valve(String name, int flowRate, LinkedList<String> neighbours) {
        this.name = name;
        this.open = false;
        this.flowRate = flowRate;
        this.neighbours = neighbours;
        releasedPressure = 0;
    }

    public void openValve() {
        this.open = true;
    }

    public void releasePressure() {
        if(open) this.releasedPressure += flowRate;
    }

}

