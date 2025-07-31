package com.example.mentis.business.logic;

public enum Side {

    RIGHT,
    LEFT,
    BOTH;

    public static Side fromRadioButtonId(String buttonId) {
        switch (buttonId) {
            case "right" -> {
                return RIGHT;
            }
            case "both" -> {
                return BOTH;
            }
            default -> {
                return LEFT;
            }
        }
    }

}
