package com.nvminh162;


public class Main {
    public static void main(String[] args) {

        EagerInitialization eager1 = EagerInitialization.getInstance();
        eager1.setName("nvminh162");
        System.out.println(eager1.getName()); // expect nvminh162

        EagerInitialization eager2 = EagerInitialization.getInstance();
        System.out.println(eager2.getName()); // expect nvminh162
    }
}