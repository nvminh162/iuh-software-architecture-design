package com.nvminh162;

public class DatabaseConnection {
    // instance duy nhất
    private static DatabaseConnection instance;

    // private constructor → không cho new từ bên ngoài
    private DatabaseConnection() {
        System.out.println("Kết nối CSDL được tạo!");
    }

    // global access point
    public static DatabaseConnection getInstance() {

        // nếu chưa có thì tạo
        if (instance == null) {
            instance = new DatabaseConnection();
        }

        return instance;
    }

    // method giả lập query
    public void query(String sql) {
        System.out.println("Thực thi query: " + sql);
    }
}

