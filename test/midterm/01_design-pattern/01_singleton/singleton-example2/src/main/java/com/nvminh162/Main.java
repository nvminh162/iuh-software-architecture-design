package com.nvminh162;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Database foo = Database.getInstance();
        foo.query("SELECT * FROM users");

        Database bar = Database.getInstance();
        bar.query("SELECT * FROM products");

        // Kiểm tra có cùng object không
        System.out.println(foo == bar);
    }
}