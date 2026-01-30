package com.nvminh162.designpattern.observer;

/**
 * Concrete Observer for Stock Price Notification
 * Represents an investor who receives notifications when stock prices change
 */
public class Investor implements Observer {
    private String name;

    public Investor(String name) {
        this.name = name;
    }

    @Override
    public void update(String subjectName, Object data) {
        if (data instanceof Double) {
            double price = (Double) data;
            System.out.println("[Investor] " + name + " received notification: " 
                + subjectName + " stock price is now $" + price);
        }
    }

    public String getName() {
        return name;
    }
}
