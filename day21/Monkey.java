package day21;

import java.util.ArrayList;
import java.util.List;

public class Monkey {

    private List<String> parents;

    private String operator;
    private Long value;
    private String name;
    public Monkey(String name){
        this.name = name;
        this.parents = new ArrayList<>();
        this.value = null;
        this.operator = null;
    }

    public Monkey(String name, String operation) {

        this.name = name;
        if(operation.matches("^\\d+$")) {
            this.value = Long.valueOf(operation);
            this.parents = new ArrayList<>();
            this.operator = null;
        } else {
            String[] completeOperation = operation.split(" ");
            this.parents = new ArrayList<>();
            this.parents.add(completeOperation[0]);
            this.parents.add(completeOperation[2]);
            this.operator = completeOperation[1];
            this.value = null;
        }
    }

    public Monkey(String name, Long value) {

        this.name = name;
        this.value = value;
        this.parents = new ArrayList<>();
        this.operator = null;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Long getResult(Monkey monkey1, Monkey monkey2) {

        if(monkey1.name.equals(this.parents.get(0)) && monkey2.name.equals(this.parents.get(1))) {
            return calculate(monkey1.value, monkey2.value);
        } else if(monkey2.name.equals(this.parents.get(0)) && monkey1.name.equals(this.parents.get(1))) {
            return calculate(monkey2.value, monkey1.value);
        }
        return null;
    }

    public Long calculate(Long value1, Long value2) {

        this.value = switch (this.operator) {
            case "+": yield value1 + value2;
            case "-": yield value1 - value2;
            case "*": yield value1 * value2;
            case "/": yield value1 / value2;
            default: yield null;
        };
        return this.value;
    }

    public boolean hasNumber() {
        return this.value != null;
    }

    public List<String> getParents() {
        return this.parents;
    }

    public String getName() {
        return this.name;
    }

    public Long getValue() {
        return this.value;
    }

    public String getOperator() {
        return this.operator;
    }
}
