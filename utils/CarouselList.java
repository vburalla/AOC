package utils;

import java.util.LinkedList;

public class CarouselList<T> extends LinkedList<T> {

    int currentIndex;

    public CarouselList() {
        super();
        currentIndex = 0;
    }

    public T getNext() {
        currentIndex++;
        if(currentIndex == this.size()) currentIndex = 0;
        return this.get(currentIndex);
    }

    public T getPrev() {
        currentIndex--;
        if(currentIndex < 0) currentIndex = this.size()-1;
        return this.get(currentIndex);
    }
}
