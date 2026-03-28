package com.nvminh162.double_check_locking;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== DOUBLE-CHECK LOCKING ===");

        int threadCount = 10;
        Set<Integer> identitySet = ConcurrentHashMap.newKeySet();
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadNo = i;
            new Thread(() -> {
                DoubleCheckLocking singleton = DoubleCheckLocking.getInstance();
                if (threadNo == 0) {
                    singleton.setCurrentSong("Nơi Này Có Anh");
                }
                identitySet.add(System.identityHashCode(singleton));
                latch.countDown();
            }).start();
        }

        latch.await();

        DoubleCheckLocking check = DoubleCheckLocking.getInstance();
        System.out.println("Số lượng hash khác nhau: " + identitySet.size());
        System.out.println("Song hiện tại: " + check.getCurrentSong());
        System.out.println("Kết luận: an toàn đa luồng và giảm lock sau khi đã khởi tạo.\n");
    }
}
