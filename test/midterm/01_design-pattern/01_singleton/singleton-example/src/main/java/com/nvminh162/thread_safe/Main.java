package com.nvminh162.thread_safe;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== THREAD-SAFE (synchronized method) ===");

        int threadCount = 10;
        Set<Integer> identitySet = ConcurrentHashMap.newKeySet();
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadNo = i;
            new Thread(() -> {
                ThreadSafe singleton = ThreadSafe.getInstance();
                if (threadNo == 0) {
                    singleton.setCurrentSong("Hãy Trao Cho Anh");
                }
                identitySet.add(System.identityHashCode(singleton));
                latch.countDown();
            }).start();
        }

        latch.await();

        ThreadSafe check = ThreadSafe.getInstance();
        System.out.println("Số lượng hash khác nhau: " + identitySet.size());
        System.out.println("Song hiện tại: " + check.getCurrentSong());
        System.out.println("Kết luận: đa luồng vẫn chỉ có 1 object.\n");
    }
}
