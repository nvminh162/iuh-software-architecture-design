package com.nvminh162;

public class Main {
    public static void main(String[] args) {

        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();

        db1.query("SELECT * FROM users");
        db2.query("SELECT * FROM orders");

        // kiểm tra cùng instance
        System.out.println(db1 == db2); // true
    }
}

