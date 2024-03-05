package com.ConsoleSolitair.Code;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;

class TextManager {
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final int STACK_START = 13;
    private static final int STACK_SPACE = 2;

    public static String[] board = new String[27];
    public static String[] spades = new String[6];
    public static String[] clubs = new String[6];
    public static String[] hearts = new String[6];
    public static String[] diamonds = new String[6];
    public static String[] emptyCard = new String[6];
    public static String[] hiddenCard = new String[6];

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
            scanner2.nextLine();
            for (int j = 0; j < Card.height; j++) {
                hiddenCard[j] = scanner2.nextLine();
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

    public static void loadAllStacks(ArrayList<Card>[] stacks) {
        for (int i = 0; i < 6; i++) {
            ArrayList<Card> currentStack = stacks[i];
            if (currentStack.size() == 0) {
                for (int j = 0; j < Card.height; j++) {
                    board[j] = 
                    board[j].substring(0, STACK_START + i*(STACK_SPACE+Card.width))
                    +emptyCard[j]+board[j].substring(STACK_START + i*(STACK_SPACE+Card.width)+Card.width,
                    board[j].length());
                }
            } else {
                for (int j = 0; j < currentStack.size(); j++) {
                    String[] design = getDesign(currentStack.get(j));
                    if (currentStack.get(j).isFaceUp) {
                        for (int k = 0; k < Card.height; k++) {
                            int CARD_START = k + j*2;  
                            switch (currentStack.get(j).suit) {
                                case Spade:
                                    board[CARD_START] = 
                                    board[CARD_START].substring(0, STACK_START + i*(STACK_SPACE+Card.width))+
                                    design[k]+board[CARD_START].substring(STACK_START + i*(STACK_SPACE+Card.width)+Card.width,
                                    board[CARD_START].length());
                                    break;
                                case Club:
                                    board[CARD_START] = 
                                    board[CARD_START].substring(0, STACK_START + i*(STACK_SPACE+Card.width))+
                                    design[k]+board[CARD_START].substring(STACK_START + i*(STACK_SPACE+Card.width)+Card.width,
                                    board[CARD_START].length());
                                    break;
                                case Heart:
                                    board[CARD_START] = 
                                    board[CARD_START].substring(0, STACK_START + i*(STACK_SPACE+Card.width))+
                                    design[k]+board[CARD_START].substring(STACK_START + i*(STACK_SPACE+Card.width)+Card.width,
                                    board[CARD_START].length());
                                    break;
                                case Diamond:
                                    board[CARD_START] = 
                                    board[CARD_START].substring(0, STACK_START + i*(STACK_SPACE+Card.width))+
                                    design[k]+board[CARD_START].substring(STACK_START + i*(STACK_SPACE+Card.width)+Card.width,
                                    board[CARD_START].length());
                                    break;
                            }
                        }
                    } else {
                        for (int k = 0; k < Card.height; k++) {
                            int CARD_START = k + j*2;
                            board[CARD_START] = 
                            board[CARD_START].substring(0, STACK_START + i*(STACK_SPACE+Card.width))
                            +hiddenCard[k]+board[CARD_START].substring(STACK_START + i*(STACK_SPACE+Card.width)+Card.width,
                            board[CARD_START].length());
                        }
                    }
                }
            }
        }
    }

    public static void loadSingleStack(ArrayList<Card> stack) {
        int i = Arrays.asList(GameManager.stacks).indexOf(stack); i=(i==-1)?7:i;
        if (stack.size() == 0) {
            for (int j = 0; j < Card.height; j++) {
                board[j] = 
                board[j].substring(0, STACK_START + i*(STACK_SPACE+Card.width))
                +emptyCard[j]+board[j].substring(STACK_START + i*(STACK_SPACE+Card.width)+Card.width,
                board[j].length());
            }
        } else {
            for (int j = 0; j < stack.size(); j++) {
                String[] design = getDesign(stack.get(j));
                if (stack.get(j).isFaceUp) {
                    for (int k = 0; k < Card.height; k++) {
                        int CARD_START = k + j*2;  
                        switch (stack.get(j).suit) {
                            case Spade:
                                board[CARD_START] = 
                                board[CARD_START].substring(0, STACK_START + i*(STACK_SPACE+Card.width))+
                                design[k]+board[CARD_START].substring(STACK_START + i*(STACK_SPACE+Card.width)+Card.width,
                                board[CARD_START].length());
                                break;
                            case Club:
                                board[CARD_START] = 
                                board[CARD_START].substring(0, STACK_START + i*(STACK_SPACE+Card.width))+
                                design[k]+board[CARD_START].substring(STACK_START + i*(STACK_SPACE+Card.width)+Card.width,
                                board[CARD_START].length());
                                break;
                            case Heart:
                                board[CARD_START] = 
                                board[CARD_START].substring(0, STACK_START + i*(STACK_SPACE+Card.width))+
                                design[k]+board[CARD_START].substring(STACK_START + i*(STACK_SPACE+Card.width)+Card.width,
                                board[CARD_START].length());
                                break;
                            case Diamond:
                                board[CARD_START] = 
                                board[CARD_START].substring(0, STACK_START + i*(STACK_SPACE+Card.width))+
                                design[k]+board[CARD_START].substring(STACK_START + i*(STACK_SPACE+Card.width)+Card.width,
                                board[CARD_START].length());
                                break;
                        }
                    }
                } else {
                    for (int k = 0; k < Card.height; k++) {
                        int CARD_START = k + j*2;
                        board[CARD_START] = 
                        board[CARD_START].substring(0, STACK_START + i*(STACK_SPACE+Card.width))
                        +hiddenCard[k]+board[CARD_START].substring(STACK_START + i*(STACK_SPACE+Card.width)+Card.width,
                        board[CARD_START].length());
                    }
                }
            }
        }
    }
    private static String[] getDesign(Card card) {
        String[] returnee = new String[6];
        String[] suitDesign = new String[6];
        switch (card.suit) {
            case Spade:
                suitDesign = spades;
                break;
            case Club:
                suitDesign = clubs;
                break;
            case Heart:
                suitDesign = hearts;
                break;
            case Diamond:
                suitDesign = diamonds;
                break;
        }
        int ordinal = card.rank.ordinal();
        for (int line = 0; line < Card.height; line++) {
            returnee[line] = suitDesign[line].substring(ordinal*(Card.width+STACK_SPACE)
                ,ordinal*(Card.width+STACK_SPACE)+Card.width);
        }
        return returnee;
    }
}