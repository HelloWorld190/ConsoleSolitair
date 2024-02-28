package com.ConsoleSolitair.Code;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ArrayList;
class GameManager {
    static ArrayList<Card> deck = new ArrayList<Card>(Arrays.asList(
        new Card(Rank.Two,Suit.Spade),new Card(Rank.Three,Suit.Spade),new Card(Rank.Four,Suit.Spade),
        new Card(Rank.Five,Suit.Spade),new Card(Rank.Six,Suit.Spade),new Card(Rank.Seven,Suit.Spade),
        new Card(Rank.Eight,Suit.Spade),new Card(Rank.Nine,Suit.Spade),new Card(Rank.Ten,Suit.Spade),
        new Card(Rank.Jack,Suit.Spade),new Card(Rank.Queen,Suit.Spade),new Card(Rank.King,Suit.Spade),
        new Card(Rank.Ace,Suit.Spade),
        new Card(Rank.Two,Suit.Club),new Card(Rank.Three,Suit.Club),new Card(Rank.Four,Suit.Club),
        new Card(Rank.Five,Suit.Club),new Card(Rank.Six,Suit.Club),new Card(Rank.Seven,Suit.Club),
        new Card(Rank.Eight,Suit.Club),new Card(Rank.Nine,Suit.Club),new Card(Rank.Ten,Suit.Club),
        new Card(Rank.Jack,Suit.Club),new Card(Rank.Queen,Suit.Club),new Card(Rank.King,Suit.Club),
        new Card(Rank.Ace,Suit.Club),
        new Card(Rank.Two,Suit.Heart),new Card(Rank.Three,Suit.Heart),new Card(Rank.Four,Suit.Heart),
        new Card(Rank.Five,Suit.Heart),new Card(Rank.Six,Suit.Heart),new Card(Rank.Seven,Suit.Heart),
        new Card(Rank.Eight,Suit.Heart),new Card(Rank.Nine,Suit.Heart),new Card(Rank.Ten,Suit.Heart),
        new Card(Rank.Jack,Suit.Heart),new Card(Rank.Queen,Suit.Heart),new Card(Rank.King,Suit.Heart),
        new Card(Rank.Ace,Suit.Heart),
        new Card(Rank.Two,Suit.Diamond),new Card(Rank.Three,Suit.Diamond),new Card(Rank.Four,Suit.Diamond),
        new Card(Rank.Five,Suit.Diamond),new Card(Rank.Six,Suit.Diamond),new Card(Rank.Seven,Suit.Diamond),
        new Card(Rank.Eight,Suit.Diamond),new Card(Rank.Nine,Suit.Diamond),new Card(Rank.Ten,Suit.Diamond),
        new Card(Rank.Jack,Suit.Diamond),new Card(Rank.Queen,Suit.Diamond),new Card(Rank.King,Suit.Diamond),
        new Card(Rank.Ace,Suit.Diamond)
    ));
    public static ArrayList<Card> stack0 = new ArrayList<Card>();
    public static ArrayList<Card> stack1 = new ArrayList<Card>();
    public static ArrayList<Card> stack2 = new ArrayList<Card>();
    public static ArrayList<Card> stack3 = new ArrayList<Card>();
    public static ArrayList<Card> stack4 = new ArrayList<Card>();
    public static ArrayList<Card> stack5 = new ArrayList<Card>();
    public static ArrayList<Card> pile = new ArrayList<Card>();
    public static ArrayList<Card>[] stacks = new ArrayList[]{stack0,stack1,stack2,stack3,stack4,stack5};
    public static void dealCards() {
        for (int i = 0; i < 6; i++) {
            deck.get(0).isFaceUp = true;
            for (int j = 6; j-i>0; j--) {
                stacks[i].add(deck.get(0));
                deck.remove(0);
            }
        }
        pile = deck;
    }
    public static void main(String[] args) {
        Collections.shuffle(deck);
        for (int i = 0; i < deck.size(); i++) {
            System.out.println(deck.get(i).rank);
        }
        TextManager.initialize();
        dealCards();
        TextManager.printStacks(stacks);
    }
}