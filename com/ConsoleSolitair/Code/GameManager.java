package com.ConsoleSolitair.Code;
import java.util.Collections;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
class GameManager {
    public static void main(String[] args) {
        Collections.shuffle(deck);
        // for (int i = 0; i < deck.size(); i++) {
        //     System.out.println(deck.get(i).rank);
        // }
        Scanner scanner = new Scanner(System.in);  
        System.out.print("\033[H\033[2J");  
        System.out.flush();   
        System.out.println("Welcome to Console Solitaire!");
        System.out.println("To draw a card, type 'draw card' or 'draw', followed by enter, or simply press enter.");
        System.out.println("To move a card, type 'move' or 'm', followed by the stack number, a hyphen ('-') or 'to', and the destination stack number, followed by enter.");
        System.out.println("To move all face-up cards, type 'move all' or 'a', followed by the stack number, a hyphen ('-') or 'to', and the destination stack number, followed by enter.");
        System.out.println("To shuffle the pickup pile back to the pile, type 'shuffle' or 's', followed by enter.");
        System.out.println("Press enter to continue.");
        scanner.nextLine();
        TextManager.initialize();
        TextManager.printBoard();
        System.out.println("These are the possible stack numbers and their locations. Press any key to continue.");
        scanner.nextLine();
        dealCards();
        for (ArrayList<Card> stack : stacks) {
            TextManager.loadSingleStack(stack);
        }
        TextManager.loadSingleStack(pickUp);
        TextManager.printBoard();
        System.out.println("Let's play! Enter your move below.");
        PlayerMove move = null;
        while (true){
            move = null;
            try {move = userInput(); 
                if (move.dest == "p" || move.dest=="pick up") {
                    throw new Exception("Invalid input - cannot move to pick up pile, please try again");
                }
                if (move.type == Input.DRAWING) {
                    draw(); TextManager.loadSingleStack(pickUp); 
                    if (pile.size() < 3) {TextManager.loadPile(pile.size());}
                }
                else if (move.type == Input.MOVING) {
                    if (move.loc.equals("pick up") || move.loc.equals("p")) {
                        int originalSize = pickUp.size();
                        move(move); 
                        TextManager.loadSingleStack(pickUp);
                        TextManager.cleanStack(pickUp,originalSize-pickUp.size());
                    } else {
                        ArrayList<Card> loc = stacks[Integer.parseInt(move.loc)-1];
                        int originalSize = loc.size();
                        move(move);
                        if (loc.size() != 0) loc.get(loc.size()-1).isFaceUp = true;
                        TextManager.loadSingleStack(loc);
                        TextManager.cleanStack(loc,originalSize-loc.size());
                    }
                    if (move.dest.charAt(0) == 'a') {
                        TextManager.loadAcesStack();
                    } else {
                        TextManager.loadSingleStack(stacks[Integer.parseInt(move.dest)-1]);
                    }
                } else if (move.type == Input.MOVING_ALL) {
                    
                    ArrayList<Card> loc = stacks[Integer.parseInt(move.loc)-1];
                    int originalSize = loc.size();
                    moveAll(move);
                    if (loc.size() !=0) loc.get(loc.size()-1).isFaceUp = true;
                    TextManager.loadSingleStack(loc);
                    TextManager.cleanStack(loc,originalSize-loc.size());
                    TextManager.loadSingleStack(stacks[Integer.parseInt(move.dest)-1]);
                } else if (move.type==Input.SHUFFLING) {
                    shuffle();
                    TextManager.loadPile(pile.size());;
                    TextManager.loadSingleStack(pickUp);
                    TextManager.cleanStack(pickUp,3);
                }
                TextManager.printBoard();
                System.out.println(move.toString());
            }
            catch (Exception e) {e.printStackTrace();}
            for (Rank rank : acePiles) {
                if (rank != Rank.King) {
                    break;
                }
               System.out.println("Congratulations! You've won!");
                return;
            }
        }
    }
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
    public static ArrayList<Card> stack0 = new ArrayList<Card>(Arrays.asList(new Card(Rank.Two,Suit.DIAMONDS)));
    public static ArrayList<Card> stack1 = new ArrayList<Card>();
    public static ArrayList<Card> stack2 = new ArrayList<Card>();
    public static ArrayList<Card> stack3 = new ArrayList<Card>();
    public static ArrayList<Card> stack4 = new ArrayList<Card>();
    public static ArrayList<Card> stack5 = new ArrayList<Card>();
    public static ArrayList<Card> pile = new ArrayList<Card>();
    public static ArrayList<Card> pickUp = new ArrayList<Card>();
    @SuppressWarnings("unchecked")
    public static ArrayList<Card>[] stacks = new ArrayList[]{stack0,stack1,stack2,stack3,stack4,stack5};
    public static Rank[] acePiles = new Rank[] {null,null,null,null};

    public enum Input {
        DRAWING, MOVING, MOVING_ALL, SHUFFLING
    }
    public static class PlayerMove {
        public Input type; 
        public String loc;
        public String dest;
        public Card card;
        public PlayerMove(Input type, String loc, String dest) {
            this.type = type; this.loc = loc; this.dest = dest;}
        public String toString() {
            return type.name()+" "+((type==Input.DRAWING)? "a card" : 
            ((type==Input.SHUFFLING)? "the pickup pile" :
                ((type==Input.MOVING_ALL)?"of stack " + loc:
                card.rank.name() + " of "+card.suit.name())+" to stack "+dest));
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
    public static PlayerMove userInput() throws Exception {
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine(); 
        if (userInput.contains("draw") || userInput.equals("")) {
            return new PlayerMove(Input.DRAWING, null, null);
        } else if (userInput.contains("move") || userInput.contains("m")) {
            userInput=userInput.substring((userInput.contains("move"))? 4 : 1);
            if (userInput.contains("to")) {
                String[] split = userInput.split("to");
                return new PlayerMove(Input.MOVING, split[0].trim(), split[1].trim());
            } else if (userInput.contains("-")) {
                String[] split = userInput.split("-");
                return new PlayerMove(Input.MOVING, split[0].trim(), split[1].trim());
            } else {
                throw new Exception("Invalid input, please try again");
            }
        } else if (userInput.contains("move all") || userInput.contains("a")) {
            userInput=userInput.substring((userInput.contains("move all"))? 8 : 1);
            if (userInput.contains("to")) {
                String[] split = userInput.split("to");
                return new PlayerMove(Input.MOVING_ALL, split[0].trim(), split[1].trim());
            } else if (userInput.contains("-")) {
                String[] split = userInput.split("-");
                return new PlayerMove(Input.MOVING_ALL, split[0].trim(), split[1].trim());
            } else {
                throw new Exception("Invalid input, please try again");
            }
        } else if (userInput.equals("shuffle") || userInput.equals("s")) {
            return new PlayerMove(Input.SHUFFLING, null, null);
        } 
        throw new Exception("Invalid input, please try again");
    }

    public static void draw() throws Exception {
        if (pile.size() == 0) {
            throw new Exception("No cards left to draw");
        }
        pickUp.add(pile.get(pile.size()-1));
        pickUp.get(pickUp.size()-1).isFaceUp = true;
        pile.remove(pile.size()-1);
    }
    public static void move(PlayerMove move) throws Exception {
        ArrayList<Card> loc = null, dest = null;
        boolean isAcePile = false;
        try {loc = stacks[Integer.parseInt(move.loc)-1];}
        catch (NumberFormatException e) {
            if (move.loc.equals("pick up") || move.loc.equals("p")) {
                loc = pickUp;
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
                    move.card = loc.get(loc.size()-1);
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
                    move.card = loc.get(loc.size()-1);
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
                move.card = loc.get(loc.size()-1);
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
                                move.card = loc.get(loc.size()-1);
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
                                move.card = loc.get(loc.size()-1);
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
    //TODO: Make sure moveAll checks for the bottom-most face-up card, not the top-most
    public static void moveAll(PlayerMove move) throws Exception {
        if (move.loc=="p"||move.loc=="pick up") {
            throw new Exception("Invalid input - cannot move all from discard pile, please try again");
        } else if (move.loc.charAt(0) == 'a' || move.dest.charAt(0) == 'a') {
            throw new Exception("Invalid input - cannot move all to or from ace pile, please try again");
        } 
        ArrayList<Card> loc = stacks[Integer.parseInt(move.loc)-1];
        ArrayList<Card> dest = stacks[Integer.parseInt(move.dest)-1];
        ArrayList<Card> temp = new ArrayList<Card>();
        for (Card card : loc) {
            if (card.isFaceUp) {
                temp.add(card);
            }
        }
        if (dest.size() == 0) {
            if (temp.get(0).rank == Rank.King) {
                dest.addAll(temp);
                move.card = temp.get(0);
                loc.removeAll(temp);
            } else {
                throw new Exception("Invalid input - cannot start a pile with "+
                temp.get(0).rank.name()+", please try again");
            }
        } else {
            switch (temp.get(0).suit) {
                case SPADES:
                case CLUBS:
                    switch(dest.get(dest.size()-1).suit) {
                        case CLUBS:
                        case SPADES:
                            throw new Exception("Invalid input - cannot move "
                            +temp.get(0).rank.name()+" of "+temp.get(0).suit.name()
                            +" onto "+dest.get(dest.size()-1).rank.name()+ " of " + dest.get(dest.size()-1).suit.name() 
                            +", please try again");
                        case HEARTS:
                        case DIAMONDS:
                            if (temp.get(0).rank.ordinal() == dest.get(dest.size()-1).rank.ordinal()-1) {
                                dest.addAll(temp);
                                move.card = temp.get(0);
                                loc.removeAll(temp);
                            } else {
                                throw new Exception("Invalid input - cannot move "
                                +temp.get(0).rank.name()+" of "+temp.get(0).suit.name()
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
                            +temp.get(0).rank.name()+" of "+temp.get(0).suit.name()
                            +" onto "+dest.get(dest.size()-1).rank.name()+ " of " + dest.get(dest.size()-1).suit.name() 
                            +", please try again");
                        case SPADES:
                        case CLUBS:
                            if (temp.get(0).rank.ordinal() == dest.get(dest.size()-1).rank.ordinal()-1) {
                                dest.addAll(temp);
                                move.card = temp.get(0);
                                loc.removeAll(temp);
                            } else {
                                throw new Exception("Invalid input - cannot move "
                                +temp.get(0).suit.name()+" of "+temp.get(0).rank.name()
                                +" onto "+dest.get(dest.size()-1).suit.name()+ " of " + dest.get(dest.size()-1).rank.name() 
                                +", please try again");
                            }
                    }
                    break;
            }
        }
    }

    public static void shuffle() throws Exception {
        if (pile.size() != 0) {
            throw new Exception("Invalid input - cannot shuffle with cards left in pile, please try again");
        }
        Collections.reverse(pickUp);
        pile.addAll(pickUp);
        pickUp.clear();
    }

    private static boolean sameCard(Card card1, Card card2) {
        return card1.rank == card2.rank && card1.suit == card2.suit;
    }

    
}