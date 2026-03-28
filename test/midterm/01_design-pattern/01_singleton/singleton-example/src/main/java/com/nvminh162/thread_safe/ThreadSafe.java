package com.nvminh162.thread_safe;

public class ThreadSafe {
    private static ThreadSafe instance;

    private String currentSong;

    private ThreadSafe() {
    }

    // synchronized trên cả method: an toàn đa luồng.
    // Ưu điểm: dễ đọc, dễ bảo trì và đúng trong môi trường nhiều thread.
    public static synchronized ThreadSafe getInstance() {
        if (instance == null) {
            instance = new ThreadSafe();
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
