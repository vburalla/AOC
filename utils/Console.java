package utils;

import java.io.IOException;

public class Console {

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");  
        System.out.flush();
    }

    public static void clrscr(){
        //Clears Screen in java
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {}
    }
    
}
