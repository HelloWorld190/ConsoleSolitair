package com.ConsoleSolitair.Code;
class Card {
    public String rank;
    public Suit suit;
    public boolean isFaceUp;
    static int height = 6;
    static int width = 7;
    public Card (String rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }
}