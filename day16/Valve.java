package day16;

import java.util.LinkedList;

public class Valve implements Comparable<Valve>{
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

    public String getName() {
        return this.name;
    }

    public void openValve() {
        this.open = true;
    }

    public void releasePressure() {
        if(open) this.releasedPressure += flowRate;
    }

    @Override
    public int compareTo(Valve o) {
       
        int result = 0;
        if(o == null) {
            result = 1;
        } else if(o.flowRate != this.flowRate) {
            result = (this.flowRate > o.flowRate)? 1 : -1;
        }
        return result;
    }


}

