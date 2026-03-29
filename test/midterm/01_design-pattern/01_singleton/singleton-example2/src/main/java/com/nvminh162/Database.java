package com.nvminh162;

public class Database {

    // 1. Biến static để lưu instance duy nhất của class
    // static => dùng chung cho toàn bộ chương trình
    private static Database instance;

    // 2. Constructor private
    // => không cho class bên ngoài dùng "new Database()"
    private Database() {
        // Giả lập kết nối database
        System.out.println("Database connected...");
    }

    // 3. Phương thức static để lấy instance
    public static Database getInstance() {

        // Nếu chưa có instance thì tạo mới
        if (instance == null) {
            instance = new Database();
        }

        // Nếu đã có thì trả về instance cũ
        return instance;
    }

    // 4. Method business (giống trong tài liệu)
    public void query(String sql) {
        System.out.println("Executing query: " + sql);
    }
}
