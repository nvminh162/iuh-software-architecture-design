package com.nvminh162;

public class CandyFactory {
    public static Candy getCandy(CandyType candyType) {
        if (candyType == CandyType.HARD) {
            return new HardCandy();
        } else if (candyType == CandyType.MINTY) {
            return new MintyCandy();
        }
        return null;
    }
}