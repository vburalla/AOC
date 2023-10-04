package day7;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import utils.ReadFiles;

public class Day7 {

    private static final String LEVEL_UP = "..";
    private static final String CHANGE_DIR = "cd";
    private static final String LIST_DIR_CONTENT = "ls";
    private static final int TOTAL_DISK_SPACE = 70000000;

    static Map<String, FileSystem> folders;
    static List<String> instructionList;
    static String currentPath ="";
    static int instructionNumber = 0;

    public static void main(String[] args) {

        instructionList = ReadFiles.getInputData("day7/input1.txt");
        folders = new HashMap<>();
        executeInstructions();
        System.out.println("Part 1: " + getFolderWithSizeUnder(100000));
        System.out.println("Part 2: " + getFirstFolderWithSizeAbove(30000000 - (TOTAL_DISK_SPACE - getTotalUsedSpace())));

    }

    private static int getTotalUsedSpace() {

        return folders.get("/").getSize();
    }

    private static int getFirstFolderWithSizeAbove(int minValue) {

        List<Integer> sortedSizes = folders.keySet().stream().map(k -> folders.get(k).getSize()).filter(s -> s > minValue).collect(Collectors.toList());
        Collections.sort(sortedSizes);
        return sortedSizes.get(0);
        
    }

    private static Integer getFolderWithSizeUnder(int maxSize) {

        return folders.keySet().stream().map(k -> folders.get(k).getSize()).filter(s -> s < maxSize).reduce(0, (subtotal, element) -> subtotal + element);
    }

    private static void executeInstructions() {

        while(instructionNumber < instructionList.size()) {

            var inst = instructionList.get(instructionNumber);
            if(inst.startsWith("$")){
                String[] instructionParts = inst.split(" ");
                if(instructionParts[1].equals(CHANGE_DIR)) {
                    if(instructionParts[2].equals(LEVEL_UP)){
                        currentPath = getUpperLevelPath(currentPath);
                    } else {
                        String folderName = instructionParts[2];
                        String path = folderName.equals("/")? "/" : currentPath + folderName + "/";
                        folders.putIfAbsent(path, new FileSystem(path, currentPath, instructionParts[2]));
                        currentPath = path;
                    }
                    instructionNumber++;

                } else if(instructionParts[1].equals(LIST_DIR_CONTENT)) {
                    analyzeFolderContent();
                }
            } else {
                instructionNumber++;
            }
            
        }

    }

    private static String getUpperLevelPath(String currentFolderPath) {

        String newPath = currentFolderPath.substring(0, currentFolderPath.lastIndexOf("/"));
        return currentFolderPath.equals("/")? currentFolderPath : newPath.substring(0, newPath.lastIndexOf("/")+1);

    }


    private static void updateFolderAndParentsSize(String folderPath, int size ) {
        
        String currentFolder = folderPath; 
        do {
            FileSystem fs = folders.get(currentFolder);
            updateFolderSize(currentFolder, size);
            currentFolder = fs.parent;
        }  while(!currentFolder.equals(""));
    }

    private static void updateFolderSize(String folderPath, int valueToAdd) {
        FileSystem currentFolder = folders.get(folderPath);
        currentFolder.addSize(valueToAdd);
        folders.put(folderPath, currentFolder);
    }

    private static void analyzeFolderContent() {

        String newInstruction;
        instructionNumber++;
        
        while(instructionNumber < instructionList.size() && !(newInstruction = instructionList.get(instructionNumber)).startsWith("$")) {

            var instructionParts = newInstruction.split(" ");

            if(instructionParts[0].equals("dir")) {
                String path = currentPath.equals("/")? currentPath + instructionParts[1] : currentPath + instructionParts[1] + "/";
                folders.putIfAbsent(path, new FileSystem(path, currentPath, instructionParts[1]));
            } else {
                updateFolderAndParentsSize(currentPath, Integer.valueOf(instructionParts[0]));
            }
            instructionNumber++;
        }
    }
}
