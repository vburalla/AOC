package day16;

import java.util.ArrayList;
import java.util.List;

public class Path implements Comparable<Path>{

    String currentNode;
    int cost;
    List<String> path;
    boolean reached;
    double costCoefficient;

    public Path(String currrentNode, int cost, List<String> path) {
        this.currentNode = currrentNode;
        this.cost = cost;
        this.path = path;
        this.reached = false;
        this.costCoefficient = 0;
    }

    public Path(String currentNode, Path previousPath) {
        this.currentNode = currentNode;
        this.cost = previousPath.cost + 1;
        this.path =new ArrayList<>(previousPath.path);
        this.path.add(currentNode);
        this.reached = false;
        this.costCoefficient = 0;
    }

    public void reach(int destinationFlowRate) {
        this.reached = true;
        this.costCoefficient = (cost!=0)? destinationFlowRate/cost : 0;
    }

    public boolean isReached() {
        return  this.reached;
    }

    @Override
    public int compareTo(Path o) {
        int result = 0;
        if(o == null) {
            result = 1;
        } else {
            if(this.costCoefficient != o.costCoefficient) {
                result = this.costCoefficient > o.costCoefficient? -1 : 1;
            }
        }
        return result;
    }
}
