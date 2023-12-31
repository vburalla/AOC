package day4;

import utils.ReadFiles;
import java.util.List;

class Day4 {

    private static int analizeAssignmentPairs(List<String> assignments, boolean fullyContain) {

        int total = 0;

        for(String assignmentPair : assignments) {
            if(fullyContained(assignmentPair.split(","), fullyContain)) total++;
        }
        return total;
    }

    private static boolean fullyContained(String[] pairs, boolean full) {

        Sections assignmentSections1 = new Sections(pairs[0].split("-"));
        Sections assignmentSections2 = new Sections(pairs[1].split("-"));

        return full? 
        ((assignmentSections1.init <= assignmentSections2.init && assignmentSections1.end >= assignmentSections2.init) || assignmentSections1.init >= assignmentSections2.init && assignmentSections2.end >= assignmentSections1.init)        : ((assignmentSections1.init <= assignmentSections2.init && assignmentSections1.end >= assignmentSections2.end) || (assignmentSections2.init <= assignmentSections1.init && assignmentSections2.end >= assignmentSections1.end));
    }
    
    public static void main(String[] args) {
        List<String> sectionGroups = ReadFiles.getInputData("day4/input1.txt");
        System.out.println("Part 1: " + analizeAssignmentPairs(sectionGroups, false));
        System.out.println("Part 2: " + analizeAssignmentPairs(sectionGroups, true));
    }

}