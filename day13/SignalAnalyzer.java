package day13;

import utils.ReadFiles;

public class SignalAnalyzer {

    public static void main(String[] args) {

        var lines = ReadFiles.getInputData("day14/test.txt");
        int i=0;
        while(i<lines.size()){
            String s1 = lines.get(i);
            String s2 = lines.get(i++);
            i++;
        }
    }

    public static int compare(String signal1, String signal2) {

        while(signal1.startsWith("[") && signal2.startsWith("[")){
            if(signal1.startsWith("[")) signal1 = removeEnclosures(signal1);
            if(signal2.startsWith("[")) signal2 = removeEnclosures(signal2);
        }
        return 0;
    }

    private static String removeEnclosures(String t1) {
        t1.(t1.indexOf("["), "");
        t1.replace(t1.lastIndexOf("["), "");
        return t1;
    }

}


