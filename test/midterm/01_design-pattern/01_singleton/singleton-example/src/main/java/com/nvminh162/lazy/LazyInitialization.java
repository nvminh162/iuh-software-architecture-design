package com.nvminh162.lazy;

public class LazyInitialization {
    // Lazy: chưa tạo object ngay, chỉ tạo khi gọi getInstance lần đầu.
    // Ưu điểm: tiết kiệm tài nguyên nếu instance có thể không bao giờ được dùng.
    private static LazyInitialization instance;

    private String currentSong;

    private LazyInitialization() {
    }

    // Không an toàn đa luồng: 2 thread có thể cùng đi vào if(instance == null)
    // trước khi thread đầu tiên kịp gán, dẫn đến tạo ra nhiều object.
    public static LazyInitialization getInstance() {
        if (instance == null) {
            instance = new LazyInitialization();
        }
        return instance;
    }

    public String getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(String currentSong) {
        this.currentSong = currentSong;
    }
}
