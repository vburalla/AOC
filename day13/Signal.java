package day13;

import java.util.ArrayList;
import java.util.List;

public class Signal implements Comparable<Signal> {

    public String packet;

    public Signal(String packet) {
        this.packet = packet;
    }

    @Override
    public int compareTo(Signal s) {
        return compareSignals(this.packet, s.packet);
    }

    public int compareSignals(String s1, String s2) {

        int result = 0;
        List<String> e1 = getInnerElements(s1);
        List<String> e2 = getInnerElements(s2);
        while((!e1.isEmpty() && !e2.isEmpty()) && result == 0) {
            var elmnt1 = e1.remove(0);
            var elmnt2 = e2.remove(0);
            if(elmnt1.contains("[") || elmnt2.contains("[") || elmnt1.contains(",") || elmnt2.contains(",")) {
                result = compareSignals(elmnt1, elmnt2);
            } else {
                if(elmnt1.equals("") && elmnt2.equals("")) {
                    result = 0;
                } else if(elmnt1.equals("") || elmnt2.equals("")){
                    result = elmnt1.equals("")? -1 : 1;
                } else {
                    if (Integer.parseInt(elmnt1) < Integer.parseInt(elmnt2)) result = -1;
                    if (Integer.parseInt(elmnt1) > Integer.parseInt(elmnt2)) result = 1;
                }
            }
        }
        if(result == 0) {
            if (e1.isEmpty() && !e2.isEmpty()) {
                result = -1;
            } if(e2.isEmpty() && !e1.isEmpty()) {
                result = 1;
            }
        }
        return result;
    }

    private List<String> getInnerElements(String text) {
        int level = 0;
        int position = 0;
        List<String> elements = new ArrayList<>();
        String element = "";
        while(position < text.length()) {
            char ch = text.charAt(position);
            if(ch == '['){
                if(level > 0) element += ch;
                level++;
            } else if(ch == ']') {
                if(level > 1) element += ch;
                level--;
            } else if(ch != ',') {
                boolean isNumber = true;
                while(position < text.length() && isNumber) {
                    element += ch;
                    if(position < (text.length()-1) && (text.charAt(position + 1) <= '9' && text.charAt(position + 1) >= '0')) {
                        ch = text.charAt(position+1);
                        position++;
                    } else {
                        isNumber = false;
                    }
                }
            } else if(ch == ',' && level > 0){
                element += ch;
            }
            if(level == 0 && ch!=',') {
                elements.add(element);
                element = "";
            }
            position ++;
        }
        return elements;
    }

    private int checkEmptyOperators(String op1, String op2) {
        int result = 0;
        if(op1.equals("") && op2.equals("")) {
            result = 0;
        } else {
            if (op1.equals("") || op2.equals("")) result = op1.equals("")? -1 : 1;
        }
        return result;
    }
}
