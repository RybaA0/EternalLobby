package com.eternalcode.lobby.feature.image;


enum ImageChar {
    BLOCK('\u2588');

    private final char imageChar;

    ImageChar(char imageChar) {
        this.imageChar = imageChar;
    }

    public char getChar() {
        return imageChar;
    }
}
