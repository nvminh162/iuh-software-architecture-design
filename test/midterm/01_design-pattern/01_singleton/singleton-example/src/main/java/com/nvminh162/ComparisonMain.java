package com.nvminh162;

public class ComparisonMain {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("================ SO SÁNH SINGLETON ================");
        System.out.println("Mục tiêu: chạy 1 lần để thấy sự khác nhau giữa 4 cách singleton.\n");

        com.nvminh162.eager.Main.main(args);
        com.nvminh162.lazy.Main.main(args);
        com.nvminh162.thread_safe.Main.main(args);
        com.nvminh162.double_check_locking.Main.main(args);

        System.out.println("================ KẾT THÚC DEMO ================");
    }
}
