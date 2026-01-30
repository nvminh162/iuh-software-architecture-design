package com.nvminh162.designpattern.observer;

/**
 * Demo class to demonstrate Observer Design Pattern
 * Shows two use cases:
 * 1. Stock price notification to investors
 * 2. Task status notification to team members
 */
public class ObserverDemo {
    public static void main(String[] args) {
        System.out.println("=== Observer Design Pattern Demo ===\n");

        // ===== Use Case 1: Stock Price Notification =====
        System.out.println("=== Use Case 1: Stock Price Notification ===");
        System.out.println("--------------------------------------------");

        // Create a stock
        Stock appleStock = new Stock("Apple Inc.", 150.00);

        // Create investors
        Investor investor1 = new Investor("John Doe");
        Investor investor2 = new Investor("Jane Smith");
        Investor investor3 = new Investor("Bob Johnson");

        // Attach investors to the stock
        appleStock.attach(investor1);
        appleStock.attach(investor2);
        appleStock.attach(investor3);

        // Change stock price - all investors will be notified
        appleStock.setPrice(155.50);
        appleStock.setPrice(152.25);

        // Detach one investor
        appleStock.detach(investor2);

        // Change price again - only remaining investors will be notified
        appleStock.setPrice(158.00);

        // ===== Use Case 2: Task Status Notification =====
        System.out.println("\n\n=== Use Case 2: Task Status Notification ===");
        System.out.println("--------------------------------------------");

        // Create a task
        Task projectTask = new Task("Implement Login Feature", "To Do");

        // Create team members
        TeamMember member1 = new TeamMember("Alice");
        TeamMember member2 = new TeamMember("Charlie");
        TeamMember member3 = new TeamMember("David");

        // Attach team members to the task
        projectTask.attach(member1);
        projectTask.attach(member2);
        projectTask.attach(member3);

        // Change task status - all team members will be notified
        projectTask.setStatus("In Progress");
        projectTask.setStatus("Code Review");

        // Detach one team member
        projectTask.detach(member3);

        // Change status again - only remaining members will be notified
        projectTask.setStatus("Completed");

        System.out.println("\n=== Demo completed ===");
    }
}
