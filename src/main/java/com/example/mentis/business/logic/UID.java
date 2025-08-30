package com.example.mentis.business.logic;

public class UID {

    private static long id = 0;

    public static long next() {
        id++;
        return id;
    }

}
