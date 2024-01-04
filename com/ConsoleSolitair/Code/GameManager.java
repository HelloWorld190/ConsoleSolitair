package com.ConsoleSolitair.Code;
import com.ConsoleSolitair.Code.Card;
import com.ConsoleSolitair.Code.Suit;
import java.util.Collections;
import java.util.Arrays;
import java.util.list;
class GameManager {
    Card[] cards = {
        new Card("2",Suit.Spade),new Card("3",Suit.Spade),new Card("4",Suit.Spade),
        new Card("5",Suit.Spade),new Card("6",Suit.Spade),new Card("7",Suit.Spade),
        new Card("8",Suit.Spade),new Card("9",Suit.Spade),new Card("10",Suit.Spade),
        new Card("J",Suit.Spade),new Card("Q",Suit.Spade),new Card("K",Suit.Spade),
        new Card("A",Suit.Spade),
        new Card("2",Suit.Club),new Card("3",Suit.Club),new Card("4",Suit.Club),
        new Card("5",Suit.Club),new Card("6",Suit.Club),new Card("7",Suit.Club),
        new Card("8",Suit.Club),new Card("9",Suit.Club),new Card("10",Suit.Club),
        new Card("J",Suit.Club),new Card("Q",Suit.Club),new Card("K",Suit.Club),
        new Card("A",Suit.Club),
        new Card("2",Suit.Heart),new Card("3",Suit.Heart),new Card("4",Suit.Heart),
        new Card("5",Suit.Heart),new Card("6",Suit.Heart),new Card("7",Suit.Heart),
        new Card("8",Suit.Heart),new Card("9",Suit.Heart),new Card("10",Suit.Heart),
        new Card("J",Suit.Heart),new Card("Q",Suit.Heart),new Card("K",Suit.Heart),
        new Card("A",Suit.Heart),
        new Card("2",Suit.Diamond),new Card("3",Suit.Diamond),new Card("4",Suit.Diamond),
        new Card("5",Suit.Diamond),new Card("6",Suit.Diamond),new Card("7",Suit.Diamond),
        new Card("8",Suit.Diamond),new Card("9",Suit.Diamond),new Card("10",Suit.Diamond),
        new Card("J",Suit.Diamond),new Card("Q",Suit.Diamond),new Card("K",Suit.Diamond),
        new Card("A",Suit.Diamond)
    };
    public static void main(String[] args) {
        cards = list.toArray(Collection.shuffle(Arrays.asList(card)));
        System.out.println(cards[i].rank);
    }
}