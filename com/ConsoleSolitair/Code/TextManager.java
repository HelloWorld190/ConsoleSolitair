package com.ConsoleSolitair.Code;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;

class TextManager {
    // private static final String ANSI_RED = "\u001B[31m";
    // private static final String ANSI_WHITE = "\u001B[37m";
    private static final int STACK_START = 13;
    private static final int STACK_SPACE = 2;
    private static int BOARD_SIZE = 24;
    private static int MAX_CARDS = 10;

    public static String[] board = new String[BOARD_SIZE];
    public static String[] spades = new String[6];
    public static String[] clubs = new String[6];
    public static String[] hearts = new String[6];
    public static String[] diamonds = new String[6];
    public static String[] emptyCard = new String[6];
    public static String[] hiddenCard = new String[6];
    public static String[] nullCards = new String[6];

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
            scanner2.nextLine();
            for (int j = 0; j < Card.height; j++) {
                nullCards[j] = scanner2.nextLine();
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
    }

    public static void printBoard() {
        for (int i = 0; i < board.length; i++) {
            System.out.println(board[i]);
        }
    }

    public static void loadSingleStack(int i) {
        //TODO: If two identical stacks exists, it will only load the first one
        ArrayList<Card> stack = (i!=7) ? GameManager.stacks[i] :
            (GameManager.pickUp.size()<=3) ? GameManager.pickUp : 
            new ArrayList<Card>(GameManager.pickUp.subList(
                GameManager.pickUp.size()-3, GameManager.pickUp.size()));
        if (stack.size() == 0) {
            for (int j = 0; j < Card.height; j++) {
                board[j] = 
                board[j].substring(0, STACK_START + i*(STACK_SPACE+Card.width))
                +emptyCard[j]+board[j].substring(STACK_START + i*(STACK_SPACE+Card.width)+Card.width,
                board[j].length());
            }
        } else {
            while (stack.size()>MAX_CARDS) {extendBoard();}
            for (int j = 0; j < stack.size(); j++) {
                String[] design = getDesign(stack.get(j));
                if (stack.get(j).isFaceUp) {
                    for (int k = 0; k < Card.height; k++) {
                        int CARD_START = k + j*2;  
                        board[CARD_START] = 
                        board[CARD_START].substring(0, STACK_START + i*(STACK_SPACE+Card.width))+
                        design[k]+board[CARD_START].substring(STACK_START + i*(STACK_SPACE+Card.width)+Card.width,
                        board[CARD_START].length());
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

    public static void extendBoard() {
        BOARD_SIZE += 2;
        MAX_CARDS += 1;
        String[] newBoard = new String[BOARD_SIZE];
        for (int i = 0; i < board.length; i++) {
            newBoard[i] = board[i];
        }
        for (int i = board.length; i < newBoard.length; i++) {
            newBoard[i] = "                                                                   ";
        }
        board = newBoard;
    }

    public static void loadAcesStack() {
        for (int i = 0; i < GameManager.acePiles.length; i++) {
            String[] design = new String[6];
            if (GameManager.acePiles[i]==null) {
                int ordinal = i;
                for (int line = 0; line < Card.height; line++) {
                    design[line] = nullCards[line].substring(ordinal*(Card.width+STACK_SPACE)
                    ,ordinal*(Card.width+STACK_SPACE)+Card.width);
                }
            } else {
                design = getDesign(new Card(GameManager.acePiles[i],Suit.values()[i]));
            }
            for (int j = 0; j < Card.height; j++) {
                board[Card.height*i+j] = design[j] +
                    board[Card.height*i+j].substring(Card.width);
            }
        }
    }
    public static void cleanStack(int i, int sizeDiff) {
        //int initialLine = ((stack.size()!=0)?(((i==7&&stack.size()>3)?2:stack.size()-1)*2):0)+Card.height;
        ArrayList<Card> stack = (i!=7) ? GameManager.stacks[i] : GameManager.pickUp;
        int initialLine = (stack.size()==0) ? 0 :
            (i==7&&stack.size()>3) ? 4 :
            ((stack.size()-1)*2); initialLine += Card.height;
        for (int j = initialLine; j < initialLine+sizeDiff*2; j++) {
            board[j] = board[j].substring(0, STACK_START + i*(STACK_SPACE+Card.width))
            +"       "+board[j].substring(STACK_START + i*(STACK_SPACE+Card.width)+Card.width,
            board[j].length());
        }
    }
    private static String[] getDesign(Card card) {
        String[] returnee = new String[6];
        String[] suitDesign = new String[6];
        switch (card.suit) {
            case SPADES:
                suitDesign = spades;
                break;
            case CLUBS:
                suitDesign = clubs;
                break;
            case HEARTS:
                suitDesign = hearts;
                break;
            case DIAMONDS:
                suitDesign = diamonds;
                break;
        }
        int ordinal = (card.rank == Rank.Ace)?12:card.rank.ordinal()-1;
        for (int line = 0; line < Card.height; line++) {
            returnee[line] = suitDesign[line].substring(ordinal*(Card.width+STACK_SPACE)
                ,ordinal*(Card.width+STACK_SPACE)+Card.width);
        }
        return returnee;
    }

    public static void loadPile(int size) {
        int i=6;
        size = (size>3)?3:size;
        if (size == 0) {
            for (int j = 0; j < Card.height; j++) {
                board[j] = 
                board[j].substring(0, STACK_START + i*(STACK_SPACE+Card.width))
                +emptyCard[j]+board[j].substring(STACK_START + i*(STACK_SPACE+Card.width)+Card.width,
                board[j].length());
            }
        } else {
            for (int k = 0; k < Card.height+(size)*2; k++) {
                board[k] = board[k].substring(0, STACK_START + i*(STACK_SPACE+Card.width))
                +"       "+board[k].substring(STACK_START + i*(STACK_SPACE+Card.width)+Card.width,
                board[k].length());
            }
            for (int j = 0; j < size; j++) {
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