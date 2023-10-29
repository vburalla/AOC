package day17;

import java.util.ArrayDeque;

public class CircularList<T> extends ArrayDeque<T> {

    private int elementNumber;
    public T getFirstAndRotate() {
        T tData = this.pollFirst();
        if(tData != null) {
            this.addLast(tData);
            this.elementNumber = (elementNumber == this.size()-1)? 0 : elementNumber+1;
        }
        return tData;
    }
    public CircularList() {
        super();
        this.elementNumber = 0;
    }

    public int getElementNumber() {
        return this.elementNumber;
    }

}
