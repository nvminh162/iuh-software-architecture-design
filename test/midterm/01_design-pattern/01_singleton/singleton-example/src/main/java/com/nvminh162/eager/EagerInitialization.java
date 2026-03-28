package com.nvminh162.eager;

public class EagerInitialization {

    // Eager: object được tạo ngay khi class được load.
    // Ưu điểm: đơn giản, không cần xử lý khóa, luôn an toàn trong đa luồng.
    private static final EagerInitialization INSTANCE = new EagerInitialization();

    // Shared state de thay ro 2 bien tham chieu cung tro vao 1 object.
    private String currentSong;

    // Constructor bắt buộc private để chặn tạo object từ bên ngoài.
    private EagerInitialization() {
    }

    public static EagerInitialization getInstance() {
        return INSTANCE;
    }

    public String getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(String currentSong) {
        this.currentSong = currentSong;
    }
}
