package day13;

import utils.ReadFiles;

public class SignalAnalyzer {

    private static String signal1, signal2;

    public static void main(String[] args) {

        var lines = ReadFiles.getInputData("day13/test.txt");
        int i=3;
        while(i<lines.size()){
            signal1 = lines.get(i);
            signal2 = lines.get(i++);
            i+=2;
            compare();
        }
    }

    public static int compare() {

        int result = 0;
        String[] elements = getNextElement(signal1);
        signal1 = elements[1];
        String element1 = elements[0];
        elements = getNextElement(signal2);
        signal2 = elements[1];
        String element2 = elements[0];
        while(result == 0) {

        }
        return result;
    }

    private static String removeEnclosures(String t1) {
        
        if(t1.startsWith("[")) t1 = t1.substring(0, t1.length()-2).substring(1);
        if(t1.endsWith(",")) t1 = t1.substring(0, t1.length()-1);
        return t1;

    }

    private static String[] getNextElement(String signalText) {

        int level = 0;
        int position = -1;
        String element = "";
        do {
            position++;
            var ch = signalText.charAt(position);
            if(ch == '[') level++;
            if(ch == ']') level--;
            element+=ch;
        } while(position < signalText.length() && level > 0);
        signalText = signalText.substring(position+1);
        return new String[]{removeEnclosures(element), signalText};
    }

}


