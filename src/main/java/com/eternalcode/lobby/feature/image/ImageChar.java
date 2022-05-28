package com.eternalcode.lobby.feature.image;


/**
 * This class originally was created by arxenix.
 * GitHub: https://github.com/arxenix/ImageMessage
 * It was modified by EternalCode.
 */

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
