package com.nvminh162;

import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) {
        Candy hardCandy = CandyFactory.getCandy(CandyType.HARD);
        System.out.println(hardCandy.getCandyName());
        System.out.println("+++++++++");
        Candy mintyCandy = CandyFactory.getCandy(CandyType.MINTY);
        System.out.println(mintyCandy.getCandyName());
        System.out.println("+++++++++");
        Candy none = CandyFactory.getCandy(null);
        System.out.println(none);

        // thực tế từng class có nhiều method khác như phục vụ mục đích riêng
        HardCandy hardCandyClazz = (HardCandy) CandyFactory.getCandy(CandyType.HARD);
        System.out.println("+++++++++");
        System.out.println(hardCandyClazz.getCandyName());
    }
}
