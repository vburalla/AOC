package day11;

import java.util.List;

public class Monkey implements Comparable<Monkey>{

    private List<Long> items;
    int divisionTest;
    int monkeyIfDivisible;
    int monkeyIfNotDivisible;
    long operationAmount;
    int inspectedItems;
    String operator;
    int id;

    public Monkey(int id) {
        this.id = id;
    }

    public int getDivisionTest() {
        return this.divisionTest;
    }

    public int getInspectedItems() {
        return this.inspectedItems;
    }

    public int getItemsAmount() {
        return items.size();
    }

    public void setItems(List<Long> items) {
        this.items = items;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setOperationAmount(int amount) {
        this.operationAmount = amount;
    }

    public void setTest(int test) {
        this.divisionTest = test;
    }

    public void setMonkeyIfDivisible(int number) {
        this.monkeyIfDivisible = number;
    }

    public void setMonkeyIfNotDivisible(int number) {
        this.monkeyIfNotDivisible = number;
    }

    public Item inspectItem(boolean divideWorryLevel, long MCM) {

        int objective;
        long item = divideWorryLevel? getOperationResult(this.items.remove(0)) / 3 : getOperationResult(this.items.remove(0));
        objective = (item % divisionTest == 0)? monkeyIfDivisible : monkeyIfNotDivisible;
        inspectedItems++;
        item = item % MCM;
        return new Item(item, objective);
    }

    private long getOperationResult(long itemValue) {
        
        switch (this.operator){
            case "^2": 
                itemValue*= itemValue;
                break;
            case "*": 
                itemValue*=operationAmount;
                break;
            case "+":
                itemValue+=operationAmount;
                break;
        }
        return itemValue;
    }

    public void catchItem(long itemValue) {
        this.items.add(itemValue);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Monkey " + this.id + ": ");
        for(int i=0; i<this.items.size(); i++) {
            sb.append(items.get(i));
            if(i < (this.items.size()-1)) sb.append(", ");            
        }
        sb.append(" - Inspected: ").append(this.inspectedItems + " items.");
        return sb.toString();
    }

    public int compareTo(Monkey monkey) {
        return (this.inspectedItems < monkey.getInspectedItems())? 1 : -1;
    }
}

class Item {
    private long item;
    private int objective;

    public Item(long item, int objective) {
        this.item=item;
        this.objective=objective;
    }

    public long getItem() {
        return this.item;
    }

    public int getObjective() {
        return this.objective;
    }
}
