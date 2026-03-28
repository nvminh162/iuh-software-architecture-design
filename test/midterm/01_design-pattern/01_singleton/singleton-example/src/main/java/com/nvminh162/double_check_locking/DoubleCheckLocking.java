package com.nvminh162.double_check_locking;

public class DoubleCheckLocking {
    // volatile đảm bảo các thread thấy được giá trị mới nhất của instance.
    private static volatile DoubleCheckLocking instance;

    private String currentSong;

    private DoubleCheckLocking() {
    }

    public static DoubleCheckLocking getInstance() {
        if (instance == null) {
            // Chỉ lock lúc chưa tạo object; sau đó bỏ qua lock để nhanh hơn.
            // Ưu điểm: vừa an toàn đa luồng, vừa giảm chi phí synchronized sau khởi tạo.
            synchronized (DoubleCheckLocking.class) {
                if (instance == null) {
                    instance = new DoubleCheckLocking();
                }
            }
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
