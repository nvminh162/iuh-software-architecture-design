package com.nvminh162.eager;


public class Main {
    public static void main(String[] args) {
        System.out.println("=== EAGER INITIALIZATION ===");

        EagerInitialization playerA = EagerInitialization.getInstance();
        playerA.setCurrentSong("See Tình");

        EagerInitialization playerB = EagerInitialization.getInstance();

        System.out.println("playerA hash: " + System.identityHashCode(playerA));
        System.out.println("playerB hash: " + System.identityHashCode(playerB));
        System.out.println("Song đọc từ playerB: " + playerB.getCurrentSong());
        System.out.println("Kết luận: cùng 1 object được dùng chung.\n");
    }
}