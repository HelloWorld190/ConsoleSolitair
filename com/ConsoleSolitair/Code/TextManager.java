package com.ConsoleSolitair.Code;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

class TextManager {
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final int STACK_START = 13;
    private static final int STACK_SPACE = 2;

    public static String[] board = new String[27];
    public static String[] spades = new String[7];
    public static String[] clubs = new String[7];
    public static String[] hearts = new String[7];
    public static String[] diamonds = new String[7];
    public static String[] emptyCard = new String[7];

    public static void initialize() {
        Scanner scanner1 = null;
        Scanner scanner2 = null;
        try {
            scanner1 = new Scanner(new File("com/ConsoleSolitair/TextFiles/Board.txt"));
            int i = 0;
            while (scanner1.hasNextLine()) {
                board[i] = scanner1.nextLine();
                i++;
            }
            scanner2 = new Scanner(new File("com/ConsoleSolitair/TextFiles/cards.txt"));
            for (int j = 0; j < Card.height; j++) {
                spades[j] = scanner2.nextLine();
            }
            scanner2.nextLine();
            for (int j = 0; j < Card.height; j++) {
                clubs[j] = scanner2.nextLine();
            }
            scanner2.nextLine();
            for (int j = 0; j < Card.height; j++) {
                hearts[j] = scanner2.nextLine();
            }
            scanner2.nextLine();
            for (int j = 0; j < Card.height; j++) {
                diamonds[j] = scanner2.nextLine();
            }
            scanner2.nextLine();
            for (int j = 0; j < Card.height; j++) {
                emptyCard[j] = scanner2.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (scanner1 != null) {
                scanner1.close();
            }
            if (scanner2 != null) {
                scanner2.close();
            }
        }
        System.out.println("Welcome to Solitaire!");
    }

    public static void printBoard() {
        for (int i = 0; i < board.length; i++) {
            System.out.println(board[i]);
        }
    }

    public static void printStacks(ArrayList<Card>[] stacks) {
        for (int i = 0; i < 6; i++) {
            ArrayList<Card> currentStack = GameManager.stacks[i];
            if (currentStack.size() == 0) {
                for (int j = 0; j < Card.height; j++) {
                    board[j] = 
                    board[j].substring(0, STACK_START + i*(STACK_SPACE+Card.width))
                    +emptyCard[j]+board[j].substring(STACK_START + i*(STACK_SPACE+Card.width)+Card.width,
                    board[j].length());
                }
            }
        }
        printBoard();
    }
}