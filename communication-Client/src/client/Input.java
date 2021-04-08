package client;

import java.util.Scanner;

public class Input {
    private static Scanner input = new Scanner(System.in);
    private Input() {
    }
    public static Scanner getImporter() {
        return input;
    }
}
