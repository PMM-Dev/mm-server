package com.kwon770.mm;

public class Utility {

    public static boolean isDigit(String str) {
        if (str.matches("[+-]?\\d*(\\.\\d+)?")) return true;
        else return false;
    }
}
