package day11;

import java.util.LinkedList;

public class Monkey {

    private LinkedList<Integer> items;
    int divisionTest;
    int monkeyIfDivisible;
    int monkeyIfNotDivisible;
    int operationAmount;
    String operator;

    public Monkey(LinkedList<Integer> items, String operator, int opeartionAmount, int test, int monkeyIfTrue, int monkeyIfFalse ) {
        this.items = items ;
        this.operator = operator;
        this.operationAmount = opeartionAmount;
        this.divisionTest = test;
        this.monkeyIfDivisible = monkeyIfTrue;
        this.monkeyIfNotDivisible = monkeyIfFalse;
    }

    public Monkey() {

    }

    public void setItems(LinkedList<Integer> items) {
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

    public Item executeOperation() {

        int objective;
        int item = this.items.pollFirst();
        objective = ((item = getOperationResult(item) % divisionTest) == 0)? monkeyIfDivisible : monkeyIfNotDivisible;
        return new Item(item, objective);
    }

    private int getOperationResult(int itemValue) {
        
        if(this.operator.equals("*")) {
            itemValue*=operationAmount;
        } else if(this.operator.equals("+")) {
            itemValue+=operationAmount;
        }
        return itemValue;
    }
    
}

class Item {
    private int item;
    private int objective;

    public Item(int item, int objective) {
        this.item=item;
        this.objective=objective;
    }

    public int getItem() {
        return this.item;
    }

    public int getObjective() {
        return this.objective;
    }
}
