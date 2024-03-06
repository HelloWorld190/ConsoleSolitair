package com.ConsoleSolitair.Code;
import java.util.Collections;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
class GameManager {
    static ArrayList<Card> deck = new ArrayList<Card>(Arrays.asList(
        new Card(Rank.Two,Suit.SPADES),new Card(Rank.Three,Suit.SPADES),new Card(Rank.Four,Suit.SPADES),
        new Card(Rank.Five,Suit.SPADES),new Card(Rank.Six,Suit.SPADES),new Card(Rank.Seven,Suit.SPADES),
        new Card(Rank.Eight,Suit.SPADES),new Card(Rank.Nine,Suit.SPADES),new Card(Rank.Ten,Suit.SPADES),
        new Card(Rank.Jack,Suit.SPADES),new Card(Rank.Queen,Suit.SPADES),new Card(Rank.King,Suit.SPADES),
        new Card(Rank.Ace,Suit.SPADES),
        new Card(Rank.Two,Suit.CLUBS),new Card(Rank.Three,Suit.CLUBS),new Card(Rank.Four,Suit.CLUBS),
        new Card(Rank.Five,Suit.CLUBS),new Card(Rank.Six,Suit.CLUBS),new Card(Rank.Seven,Suit.CLUBS),
        new Card(Rank.Eight,Suit.CLUBS),new Card(Rank.Nine,Suit.CLUBS),new Card(Rank.Ten,Suit.CLUBS),
        new Card(Rank.Jack,Suit.CLUBS),new Card(Rank.Queen,Suit.CLUBS),new Card(Rank.King,Suit.CLUBS),
        new Card(Rank.Ace,Suit.CLUBS),
        new Card(Rank.Two,Suit.HEARTS),new Card(Rank.Three,Suit.HEARTS),new Card(Rank.Four,Suit.HEARTS),
        new Card(Rank.Five,Suit.HEARTS),new Card(Rank.Six,Suit.HEARTS),new Card(Rank.Seven,Suit.HEARTS),
        new Card(Rank.Eight,Suit.HEARTS),new Card(Rank.Nine,Suit.HEARTS),new Card(Rank.Ten,Suit.HEARTS),
        new Card(Rank.Jack,Suit.HEARTS),new Card(Rank.Queen,Suit.HEARTS),new Card(Rank.King,Suit.HEARTS),
        new Card(Rank.Ace,Suit.HEARTS),
        new Card(Rank.Two,Suit.DIAMONDS),new Card(Rank.Three,Suit.DIAMONDS),new Card(Rank.Four,Suit.DIAMONDS),
        new Card(Rank.Five,Suit.DIAMONDS),new Card(Rank.Six,Suit.DIAMONDS),new Card(Rank.Seven,Suit.DIAMONDS),
        new Card(Rank.Eight,Suit.DIAMONDS),new Card(Rank.Nine,Suit.DIAMONDS),new Card(Rank.Ten,Suit.DIAMONDS),
        new Card(Rank.Jack,Suit.DIAMONDS),new Card(Rank.Queen,Suit.DIAMONDS),new Card(Rank.King,Suit.DIAMONDS),
        new Card(Rank.Ace,Suit.DIAMONDS)
    ));
    public static ArrayList<Card> stack0 = new ArrayList<Card>();
    public static ArrayList<Card> stack1 = new ArrayList<Card>();
    public static ArrayList<Card> stack2 = new ArrayList<Card>();
    public static ArrayList<Card> stack3 = new ArrayList<Card>();
    public static ArrayList<Card> stack4 = new ArrayList<Card>();
    public static ArrayList<Card> stack5 = new ArrayList<Card>();
    public static ArrayList<Card> pile = new ArrayList<Card>();
    public static ArrayList<Card> discard = new ArrayList<Card>();
    @SuppressWarnings("unchecked")
    public static ArrayList<Card>[] stacks = new ArrayList[]{stack0,stack1,stack2,stack3,stack4,stack5};
    public static Rank[] acePiles = new Rank[] {null,null,null,null};

    public static boolean gameOver = false;
    public enum Input {
        DRAW, MOVE, MOVE_ALL
    }
    public static class PlayerMove {
        public Input type; 
        public String loc;
        public String dest;
        public PlayerMove(Input type, String loc, String dest) {
            this.type = type; this.loc = loc; this.dest = dest;}
        public String toString() {
            return "Type: " + type.name() + " Location: " + loc + " Destination: " + dest;
        }
    }
    public static void dealCards() {
        for (int i = 0; i < 6; i++) {
            for (int j = 7; i+j>6; j--) {
                stacks[i].add(deck.get(0));
                deck.remove(0);
            }
            stacks[i].get(stacks[i].size()-1).isFaceUp = true;
        }
        pile = deck;
    }
    //TODO: Kinda broken, fix it 
    public static PlayerMove userInput() throws Exception {
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine(); 
        if (userInput.contains("draw") || userInput.contains("d") || userInput.equals("")) {
            return new PlayerMove(Input.DRAW, null, null);
        } else if (userInput.contains("move") || userInput.contains("m")) {
            userInput=userInput.substring((userInput.contains("move"))? 4 : 1);
            if (userInput.contains("to")) {
                String[] split = userInput.split("to");
                return new PlayerMove(Input.MOVE, split[0].trim(), split[1].trim());
            } else if (userInput.contains("-")) {
                String[] split = userInput.split("-");
                return new PlayerMove(Input.MOVE, split[0].trim(), split[1].trim());
            } else {
                throw new Exception("Invalid input, please try again");
            }
        } else if (userInput.contains("move all") || userInput.contains("ma")) {
            userInput=userInput.substring((userInput.contains("move all"))? 8 : 2);
            if (userInput.contains("to")) {
                String[] split = userInput.split("to");
                return new PlayerMove(Input.MOVE_ALL, split[0].trim(), split[1].trim());
            } else if (userInput.contains("-")) {
                String[] split = userInput.split("-");
                return new PlayerMove(Input.MOVE_ALL, split[0].trim(), split[1].trim());
            } else {
                throw new Exception("Invalid input, please try again");
            }
        }
        throw new Exception("Invalid input, please try again");
    }

    public static void draw() throws Exception {
        if (pile.size() == 0) {
            throw new Exception("No cards left to draw");
        }
        discard.add(pile.get(pile.size()-1));
        discard.get(discard.size()-1).isFaceUp = true;
        pile.remove(pile.size()-1);
    }
    public static void move(PlayerMove move) throws Exception {
        ArrayList<Card> loc = null, dest = null;
        boolean isAcePile = false;
        try {loc = stacks[Integer.parseInt(move.loc)-1];}
        catch (NumberFormatException e) {
            if (move.loc.equals("discard") || move.loc.equals("d")) {
                loc = discard;
            } else {
                throw new Exception("Invalid input - Stack DNE, please try again");
            }
        }
        try {dest = stacks[Integer.parseInt(move.dest)-1];}
        catch (NumberFormatException e) {
            if (move.dest.charAt(0) == 'a') {
                isAcePile = true;
            } else {
                throw new Exception("Invalid input - Stack DNE, please try again");
            }
        }
        if (loc.size() == 0) {
            throw new Exception("Invalid input - stack is empty, please try again");
        }
        if (isAcePile) {
            int pile = 0;
            try {pile=Integer.parseInt(move.dest.substring(1,2))-1;
            if (pile < 0 || pile > 4) {throw new Exception();} 
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Invalid input - Ace pile DNE, please try again");}
            if (acePiles[pile] == null) {
                if (sameCard(loc.get(loc.size()-1),new Card(Rank.Ace,Suit.values()[pile]))) {
                    acePiles[pile] = loc.get(loc.size()-1).rank;
                    loc.remove(loc.size()-1);
                } else {
                    throw new Exception("Invalid input - cannot start a pile with "+
                    loc.get(loc.size()-1).rank.name()+" of "+
                    loc.get(loc.size()-1).suit.name()+", please try again");
                }
            } else if (acePiles[pile] == Rank.King) {
                throw new Exception("Invalid input - pile is full, please try again");
            } else {
                Rank rankNeeded = Rank.values()[acePiles[pile].ordinal()+1];
                if (sameCard(loc.get(loc.size()-1),new Card(rankNeeded,Suit.values()[pile]))) {
                    acePiles[pile] = loc.get(loc.size()-1).rank;
                    loc.remove(loc.size()-1);
                } else {
                    throw new Exception("Invalid input - cannot add to "+move.dest
                    +" pile with "+loc.get(loc.size()-1).rank.name()+" of "+
                    loc.get(loc.size()-1).suit.name()+", please try again");
                }
            }
        } else if (dest.size() == 0) {
            if (loc.get(loc.size()-1).rank == Rank.King) {
                dest.add(loc.get(loc.size()-1));
                loc.remove(loc.size()-1);
            } else {
                throw new Exception("Invalid input - cannot start a pile with "+
                loc.get(loc.size()-1).rank.name()+", please try again");
            }
        } else {
            switch (loc.get(loc.size()-1).suit) {
                case SPADES:
                case CLUBS:
                    switch(dest.get(dest.size()-1).suit) {
                        case CLUBS:
                        case SPADES:
                            throw new Exception("Invalid input - cannot move "
                            +loc.get(loc.size()-1).rank.name()+" of "+loc.get(loc.size()-1).suit.name()
                            +" onto "+dest.get(dest.size()-1).rank.name()+ " of " + dest.get(dest.size()-1).suit.name() 
                            +", please try again");
                        case HEARTS:
                        case DIAMONDS:
                            if (loc.get(loc.size()-1).rank.ordinal() == dest.get(dest.size()-1).rank.ordinal()-1) {
                                dest.add(loc.get(loc.size()-1));
                                loc.remove(loc.size()-1);
                            } else {
                                throw new Exception("Invalid input - cannot move "
                                +loc.get(loc.size()-1).rank.name()+" of "+loc.get(loc.size()-1).suit.name()
                                +" onto "+dest.get(dest.size()-1).rank.name()+ " of " + dest.get(dest.size()-1).suit.name() 
                                +", please try again");
                            }
                    }
                    break;
                case HEARTS:
                case DIAMONDS:
                    switch(dest.get(dest.size()-1).suit) {
                        case HEARTS:
                        case DIAMONDS:
                            throw new Exception("Invalid input - cannot move "
                            +loc.get(loc.size()-1).rank.name()+" of "+loc.get(loc.size()-1).suit.name()
                            +" onto "+dest.get(dest.size()-1).rank.name()+ " of " + dest.get(dest.size()-1).suit.name() 
                            +", please try again");
                        case SPADES:
                        case CLUBS:
                            if (loc.get(loc.size()-1).rank.ordinal() == dest.get(dest.size()-1).rank.ordinal()-1) {
                                dest.add(loc.get(loc.size()-1));
                                loc.remove(loc.size()-1);
                            } else {
                                throw new Exception("Invalid input - cannot move "
                                +loc.get(loc.size()-1).suit.name()+" of "+loc.get(loc.size()-1).rank.name()
                                +" onto "+dest.get(dest.size()-1).suit.name()+ " of " + dest.get(dest.size()-1).rank.name() 
                                +", please try again");
                            }
                    }
                    break;
            }
        }
    }

    private static boolean sameCard(Card card1, Card card2) {
        return card1.rank == card2.rank && card1.suit == card2.suit;
    }

    public static void main(String[] args) {
        Collections.shuffle(deck);
        // for (int i = 0; i < deck.size(); i++) {
        //     System.out.println(deck.get(i).rank);
        // }
        TextManager.initialize();
        dealCards();
        TextManager.loadAllStacks(stacks);
        TextManager.printBoard();
        while (true){
            PlayerMove move = null;
            try {move = userInput(); 
                if (move.type == Input.DRAW) {draw(); 
                TextManager.loadSingleStack(discard); TextManager.printBoard();}
                else if (move.type == Input.MOVE) {move(move); 
                    if (move.loc.equals("discard") || move.loc.equals("d")) {
                        TextManager.loadSingleStack(discard);
                    } else {
                        stacks[Integer.parseInt(move.loc)-1].get(stacks[Integer.parseInt(move.loc)-1].size()-1).isFaceUp = true;
                        TextManager.loadSingleStack(stacks[Integer.parseInt(move.loc)-1]);
                        TextManager.cleanStack(stacks[Integer.parseInt(move.loc)-1]);
                    }
                    if (move.dest.charAt(0) == 'a') {
                        TextManager.loadAcesStack();
                    } else {
                        TextManager.loadSingleStack(stacks[Integer.parseInt(move.dest)-1]);
                    }
                    TextManager.printBoard();
                }
            }
            catch (Exception e) {e.printStackTrace();}
        
            //TODO: Implement display for last 3 cards in pile\discard
        }
    }
}