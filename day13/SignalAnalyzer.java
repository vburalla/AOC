package day13;

import utils.ReadFiles;

public class SignalAnalyzer {

    private static String signal1, signal2;

    public static void main(String[] args) {

        var lines = ReadFiles.getInputData("day13/test.txt");
        int i=0;
        while(i<lines.size()){
            signal1 = lines.get(i);
            signal2 = lines.get(i++);
            i+=2;
            compare();
        }
    }

    public static int compare() {

        if(signal1.startsWith("[") || signal2.startsWith("[")){
                if(signal1.startsWith("[")) signal1 = removeEnclosures(signal1);
                if(signal2.startsWith("[")) signal2 = removeEnclosures(signal2);
            }
        int result = 0;
        while(result == 0) {
            String element1 = getNextElement(signal1);
            signal1 = signal1.substring(signal1.length());
            String element2 = getNextElement(signal2);
            signal2 = signal2.substring(signal2.length());
            if(element1.startsWith("[") && element2.startsWith("[")){
                if(element1.startsWith("[")) element1 = removeEnclosures(signal1);
                if(element2.startsWith("[")) element2 = removeEnclosures(signal2);
            }
        }
        return result;
    }

    private static String removeEnclosures(String t1) {
        
        return t1.substring(0, t1.length()-2).substring(1);

    }

    private static String getNextElement(String signalText) {

        int level = 0;
        int position = 0;
        String element = "";
        do {
            var ch = signalText.charAt(position);
            if(ch == '[') level++;
            if(ch == ']') level--;
            element+=ch;
            position++;
        } 
        while(position < signalText.length() && level > 0);
        signalText = element;
        return element;
    }



}


