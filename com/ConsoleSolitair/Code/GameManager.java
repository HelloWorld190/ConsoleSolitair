package com.ConsoleSolitair.Code;
import java.util.Collections;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
class GameManager {
    public static Scanner scanner = new Scanner(System.in); 
    public static FileWriter fileWriter;
    public static void main(String[] args) throws Exception {
        // for (int i = 0; i < deck.size(); i++) {
        //     System.out.println(deck.get(i).rank);
        // }
        System.out.print("\033[H\033[2J");  
        System.out.flush();   
        System.out.println("Welcome to Console Solitaire!");
        System.out.println("To draw a card, type 'draw card' or 'draw', followed by enter, or simply press enter.");
        System.out.println("To move a card, type 'move' or 'm', followed by the stack number, a hyphen ('-') or 'to', and the destination stack number, followed by enter.");
        System.out.println("To move all face-up cards, type 'move all' or 'a', followed by the stack number, a hyphen ('-') or 'to', and the destination stack number, followed by enter.");
        System.out.println("To shuffle the pickup pile back to the pile, type 'shuffle' or 's', followed by enter.");
        System.out.println("To undo the last move, type 'undo' or 'u', followed by enter.");
        System.out.println("Press enter to continue.");
        scanner.nextLine();
        TextManager.initialize();
        TextManager.printBoard();
        System.out.println("These are the possible stack numbers and their locations. Please press enter to generate a new seed or press any key to read the previous game's files.");
        
        File progress = new File("com/ConsoleSolitair/TextFiles/progress.txt");
        Random rand; long seed;
        if (scanner.nextLine().equals("")) {
            rand = new Random();
            seed = rand.nextLong();
            System.out.println("Seed: "+seed);
            rand.setSeed(seed);
            progress.delete(); progress.createNewFile();
            FileWriter seedWriter = new FileWriter(progress);
            seedWriter.append(seed+"\n");
            Collections.shuffle(deck,rand);
            dealCards();
            seedWriter.close();
        } else {
            Scanner progressReader = new Scanner(progress);
            if (progressReader.hasNext()) {seed = Long.parseLong(progressReader.nextLine());}
            else {
                progressReader.close(); 
                throw new Exception("Seed does not exist in progress.txt");
            }
            System.out.println("Seed: "+seed);
            Thread.sleep(1000);
            rand = new Random(seed);
            Collections.shuffle(deck,rand);
            dealCards();
            while (progressReader.hasNextLine()) {
                String input = progressReader.nextLine();
                if (input.equals("")) {continue;}
                PlayerMove move = userInput(input);
                try {fulfillMove(move);}
                catch (Exception e) {System.out.println("Save File attempted an illegal move, please restart");
                progressReader.close(); e.printStackTrace();}
            }
            progressReader.close();
        }
        System.out.print("\033[H\033[2J");  
        System.out.flush();   
        System.out.print("Loading game");
        for (int i = 0; i < 3; i++) { System.out.print(".");Thread.sleep(500);}
        System.out.println("");
        Thread.sleep(1000);
        System.out.print("\033[H\033[2J");  
        System.out.flush();
        for (int i=0; i<stacks.length;i++) {
            TextManager.loadSingleStack(i);
        }
        TextManager.loadSingleStack(PICK_UP_INDEX);
        TextManager.printBoard();
        System.out.println("Let's play! Enter your move below.");
        int startTime = (int) System.currentTimeMillis()/1000;
        PlayerMove move = null;  fileWriter = new FileWriter(new File("com/ConsoleSolitair/TextFiles/progress.txt"),true);
        while (true){
            move = null;
            String input = scanner.nextLine();
            try {move = userInput(input);
            fulfillMove(move); 
            
            System.out.print("\033[H\033[2J");  
            System.out.flush();

            TextManager.printBoard();
            System.out.println("Total Moves: "+moveList.size()+" | "+
            "Total Time: "+((int)System.currentTimeMillis()/1000-startTime)+"s | "+
            move.toString() + " | "+
            "Current Seed: "+seed);

            fileWriter.append((move.type==Input.MOVING_ALL)?"a"+move.loc+"-"+move.dest+" "+move.moveAllShorthand+"\n"
            :((input.equals(""))?"d"
            :input)+"\n"); 
            fileWriter.flush();
            } catch (Exception e) {System.out.println(e.getMessage());}
            for (Rank rank : acePiles) {
                if (rank != Rank.King) {
                    break;
                }
                System.out.println("Congratulations! You've won!");
                scanner.close(); fileWriter.close();
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
    public static ArrayList<Card> stack0 = new ArrayList<Card>();
    public static ArrayList<Card> stack1 = new ArrayList<Card>();
    public static ArrayList<Card> stack2 = new ArrayList<Card>();
    public static ArrayList<Card> stack3 = new ArrayList<Card>();
    public static ArrayList<Card> stack4 = new ArrayList<Card>();
    public static ArrayList<Card> stack5 = new ArrayList<Card>();
    public static ArrayList<Card> pile = new ArrayList<Card>();
    public static ArrayList<Card> pickUp = new ArrayList<Card>(); 
    private static int PICK_UP_INDEX = 7;
    @SuppressWarnings("unchecked")
    public static ArrayList<Card>[] stacks = new ArrayList[]{stack0,stack1,stack2,stack3,stack4,stack5};
    public static Rank[] acePiles = new Rank[] {null,null,null,null};
    public static ArrayList<PlayerMove> moveList = new ArrayList<PlayerMove>();

    public enum Input {
        DRAWING, MOVING, MOVING_ALL, SHUFFLING, UNDOING
    }
    public static class PlayerMove {
        public Input type; 
        public String loc;
        public String dest;
        
        public Card card;
        public String moveAllShorthand;
        public int moveAmount;
        public boolean wasCardFlipped;
        public PlayerMove(Input type, String loc, String dest) {
            this.type = type; this.loc = loc; this.dest = dest;}
        public PlayerMove(String loc, String dest, String moveAllShorthand) {
            type = Input.MOVING_ALL;
            this.loc = loc; this.dest = dest; this.moveAllShorthand = moveAllShorthand;
        }
        public String toString() {
            return ((type==Input.MOVING_ALL)?"MOVING":type.name())
            +" "+
            ((type==Input.DRAWING) ? "a card" : 
            ((type==Input.UNDOING) ? "the last move" :
            ((type==Input.SHUFFLING) ? "the pickup pile" :
            ((type==Input.MOVING_ALL) ? moveAmount + " card(s) from stack " + loc :
            card.rank.name() + " of "+card.suit.name())+" to stack "+dest)));
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
    public static PlayerMove userInput(String userInput) throws Exception {
        if (userInput.contains("draw") || userInput.equals("") || userInput.equals("d")) {
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
                if (split[1].trim().length()>1) {
                    String[] split2 = split[1].trim().split(" ");
                    return new PlayerMove(split[0].trim(),split2[0],split2[1]);
                }
                return new PlayerMove(Input.MOVING_ALL, split[0].trim(), split[1].trim());
            } else if (userInput.contains("-")) {
                String[] split = userInput.split("-");
                if (split[1].trim().length()>1) {
                    String[] split2 = split[1].trim().split(" ");
                    return new PlayerMove(split[0].trim(),split2[0],split2[1]);
                }
                return new PlayerMove(Input.MOVING_ALL, split[0].trim(), split[1].trim());
            } else {
                throw new Exception("Invalid input, please try again");
            }
        } else if (userInput.equals("shuffle") || userInput.equals("s")) {
            return new PlayerMove(Input.SHUFFLING, null, null);
        } else if (userInput.equals("undo") || userInput.equals("u")) {
            return new PlayerMove(Input.UNDOING, null, null);
        } 
        throw new Exception("Invalid input, please try again");
    }

    public static void draw() throws Exception {
        if (pile.size() == 0) {
            shuffle();
            TextManager.loadPile(pile.size());;
            TextManager.loadSingleStack(PICK_UP_INDEX);
            TextManager.cleanStack(PICK_UP_INDEX,2);
            System.out.println("No cards left to draw - shuffling pile");
            Thread.sleep(1000);
            TextManager.printBoard();
            moveList.add(new PlayerMove(Input.SHUFFLING, null, null));
            fileWriter.append("s\n"); fileWriter.flush();
            return;
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
                throw new Exception("Invalid input - Stack Does Not Exist, please try again");
            }
        }
        catch (IndexOutOfBoundsException e) {
            throw new Exception("Invalid input - Stack Does Not Exist, please try again");
        }
        try {dest = stacks[Integer.parseInt(move.dest)-1];}
        catch (NumberFormatException e) {
            if (move.dest.charAt(0) == 'a') {
                isAcePile = true;
            } else {
                throw new Exception("Invalid input - Stack Does Not Exist, please try again");
            }
        }
        catch (IndexOutOfBoundsException e) {
            throw new Exception("Invalid input - Stack Does Not Exist, please try again");
        }
        if (loc.size() == 0) {
            throw new Exception("Invalid input - stack is empty, please try again");
        }
        if (isAcePile) {
            int pile = 0;
            try {pile=Integer.parseInt(move.dest.substring(1,2))-1;
            if (pile < 0 || pile > 4) {throw new Exception("Fatal Error: Invalid Ace Pile");} 
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
    public static void moveAll(PlayerMove move) throws Exception {
        if (move.loc=="p"||move.loc=="pick up") {
            throw new Exception("Invalid input - cannot move all from discard pile, please try again");
        } else if (move.loc.charAt(0) == 'a' || move.dest.charAt(0) == 'a') {
            throw new Exception("Invalid input - cannot move all to or from ace pile, please try again");
        }
        ArrayList<Card> loc,dest,temp;
        try {
            loc = stacks[Integer.parseInt(move.loc)-1]; 
            dest = stacks[Integer.parseInt(move.dest)-1];
        } catch (NumberFormatException e) {
            throw new Exception("Invalid input - Stack Does Not Exist, please try again");
        } catch (IndexOutOfBoundsException e) {
            throw new Exception("Invalid input - Stack Does Not Exist, please try again");
        }
        temp = removeNonFaceUps(loc);

        temp = new ArrayList<Card>(temp.subList(temp.size()-move.moveAmount,temp.size()));

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

    public static void undo() throws Exception{
        if (moveList.size() == 0) {
            throw new Exception("Invalid input - no moves to undo, please try again");
        }
        PlayerMove move = moveList.get(moveList.size()-1); int i=1;
        while (move.type == Input.UNDOING) {
            move = moveList.get(moveList.size()-i);
            i++;
        }
        if (move.type == Input.DRAWING) {
            pile.add(pickUp.get(pickUp.size()-1));
            pickUp.remove(pickUp.size()-1);
            TextManager.loadPile(pile.size());
            TextManager.loadSingleStack(PICK_UP_INDEX);
            TextManager.cleanStack(PICK_UP_INDEX,1);
        } else if (move.type == Input.MOVING) {
            // if (move.loc.equals("pick up") || move.loc.equals("p")) {
            if (move.dest.charAt(0) == 'a') {
                int destNum = Integer.parseInt(move.dest.substring(1,2))-1;
                if (move.card.rank != acePiles[destNum]) {
                    throw new Exception("Fatal Error: Card Mismatch");
                }
                if (acePiles[destNum] == Rank.Ace) {acePiles[destNum] = null;} 
                else {
                    int currentOrdinal = acePiles[destNum].ordinal();
                    acePiles[destNum] = Rank.values()[currentOrdinal-1];
                }
                if (move.loc.equals("p") || move.loc.equals("pick up")){
                    pickUp.add(move.card);
                    TextManager.loadSingleStack(PICK_UP_INDEX);
                    TextManager.loadAcesStack();
                } else {
                    int locNum = Integer.parseInt(move.loc)-1;
                    if (move.wasCardFlipped) {
                        stacks[locNum].get(stacks[locNum].size()-1).isFaceUp = false;
                    }
                    stacks[locNum].add(move.card);
                    TextManager.loadSingleStack(locNum);
                    TextManager.loadAcesStack();
                }
            } else {
                int destNum = Integer.parseInt(move.dest)-1;
                int cardPos = stacks[destNum].size()-1;
                if (move.card != stacks[destNum].get(cardPos)) {
                    throw new Exception("Fatal Error: Card Mismatch");
                }
                stacks[destNum].remove(cardPos);
                TextManager.loadSingleStack(destNum);
                if (move.loc.equals("p") || move.loc.equals("pick up")){
                    pickUp.add(move.card);
                    TextManager.loadSingleStack(PICK_UP_INDEX);
                } else {
                    int locNum = Integer.parseInt(move.loc)-1;
                    if (move.wasCardFlipped) {
                        stacks[locNum].get(stacks[locNum].size()-1).isFaceUp = false;
                    }
                    stacks[locNum].add(move.card);
                    TextManager.loadSingleStack(locNum);
                    TextManager.cleanStack(Integer.parseInt(move.dest)-1, 1);
                }
            }
        } else if (move.type == Input.SHUFFLING) {
            Collections.reverse(pile);
            pickUp.addAll(pile);
            pile.clear();
            TextManager.loadPile(pile.size());
            TextManager.loadSingleStack(PICK_UP_INDEX);
        } else if (move.type == Input.MOVING_ALL) {
            ArrayList<Card> loc = stacks[Integer.parseInt(move.loc)],
            dest = stacks[Integer.parseInt(move.dest)],temp;
            temp = new ArrayList<Card>(dest.subList(move.moveAmount, dest.size()-1));
            if (move.wasCardFlipped) {
                int locNum = Integer.parseInt(move.loc)-1;
                stacks[locNum].get(stacks[locNum].size()-1).isFaceUp = false;
            }
            dest.removeAll(temp); loc.addAll(temp);
        }
        moveList.remove(moveList.size()-1);
    }

    private static void fulfillMove(PlayerMove move) throws Exception {
        try {
            if (move.dest == "p" || move.dest=="pick up") {
                throw new Exception("Invalid input - cannot move to pick up pile, please try again");
            }
            if (move.type == Input.DRAWING) {
                draw(); TextManager.loadSingleStack(PICK_UP_INDEX); 
                if (pile.size() < 3) {TextManager.loadPile(pile.size());}
            }
            else if (move.type == Input.MOVING) {
                if (move.loc.equals("pick up") || move.loc.equals("p")) {
                    int originalSize = pickUp.size();
                    move(move); 
                    TextManager.loadSingleStack(PICK_UP_INDEX);
                    TextManager.cleanStack(PICK_UP_INDEX,originalSize-pickUp.size());
                } else {
                    int locNum = Integer.parseInt(move.loc)-1;
                    ArrayList<Card> loc = stacks[locNum];
                    int originalSize = loc.size();
                    move(move);
                    if (loc.size() != 0){
                        if (loc.get(loc.size()-1).isFaceUp == false) {
                            move.wasCardFlipped = true;
                            loc.get(loc.size()-1).isFaceUp = true;
                        }
                    }
                    TextManager.loadSingleStack(locNum);
                    TextManager.cleanStack(locNum,originalSize-loc.size());
                }
                if (move.dest.charAt(0) == 'a') {
                    TextManager.loadAcesStack();
                } else {
                    TextManager.loadSingleStack(Integer.parseInt(move.dest)-1);
                }
            } else if (move.type == Input.MOVING_ALL) {
                int locNum = Integer.parseInt(move.loc)-1;
                ArrayList<Card> loc = stacks[locNum];
                int originalSize = loc.size();
                while(true){
                    if (move.moveAllShorthand == null){ 
                    System.out.print("Move how many cards? Type 'all' to move all face-up cards. ");
                    move.moveAllShorthand = scanner.nextLine();
                    }
                    try {
                        if (move.moveAllShorthand.equals("all")) {move.moveAmount=removeNonFaceUps(loc).size(); break;}
                        move.moveAmount = Integer.parseInt(move.moveAllShorthand);
                        if (move.moveAmount < 1 || move.moveAmount > removeNonFaceUps(loc).size()) {
                            throw new Exception("Invalid input - please enter a number between 1 and "+removeNonFaceUps(loc).size());
                        }
                        break;
                    } catch (Exception e) {
                        move.moveAllShorthand = null;
                        System.out.println(e.getMessage());
                    }
                }
                moveAll(move);
                if (loc.size() != 0){
                    if (loc.get(loc.size()-1).isFaceUp == false) {
                        move.wasCardFlipped = true;
                        loc.get(loc.size()-1).isFaceUp = true;
                    }
                }
                TextManager.loadSingleStack(locNum);
                TextManager.cleanStack(locNum,originalSize-loc.size());
                TextManager.loadSingleStack(Integer.parseInt(move.dest)-1);
            } else if (move.type==Input.SHUFFLING) {
                shuffle();
                TextManager.loadPile(pile.size());;
                TextManager.loadSingleStack(PICK_UP_INDEX);
                TextManager.cleanStack(PICK_UP_INDEX,2);
            } else if (move.type==Input.UNDOING) {
                undo();
            }
            moveList.add(move);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new Exception("Fatal Exception: String Processing Failed");
        }
        catch (IndexOutOfBoundsException e) {
            throw new Exception("Fatal Exception: String Processing Failed");
        }
        catch (Exception e) {throw e;} //TODO: Debug purposes, change to e.getMessage()
    }

    private static boolean sameCard(Card card1, Card card2) {
        return card1.rank == card2.rank && card1.suit == card2.suit;
    }

    private static ArrayList<Card> removeNonFaceUps(ArrayList<Card> input) {
        ArrayList<Card> returnee = new ArrayList<Card>();
        for (Card card : input) {
            if (card.isFaceUp) {
                returnee.add(card);
            }
        }
        return returnee;
    }
}