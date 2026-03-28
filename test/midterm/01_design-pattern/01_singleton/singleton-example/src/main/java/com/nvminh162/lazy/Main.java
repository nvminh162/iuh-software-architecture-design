package com.nvminh162.lazy;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== LAZY INITIALIZATION ===");

        LazyInitialization playerA = LazyInitialization.getInstance();
        playerA.setCurrentSong("Lạc Trôi");

        LazyInitialization playerB = LazyInitialization.getInstance();

        System.out.println("playerA hash: " + System.identityHashCode(playerA));
        System.out.println("playerB hash: " + System.identityHashCode(playerB));
        System.out.println("Song đọc từ playerB: " + playerB.getCurrentSong());
        System.out.println("Kết luận: vẫn là 1 object trong ví dụ đơn luồng, nhưng có thể lỗi khi chạy đa luồng.\n");
    }
}
