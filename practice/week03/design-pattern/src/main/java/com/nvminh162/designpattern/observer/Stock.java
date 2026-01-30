package com.nvminh162.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete Subject for Stock Price Notification
 * Represents a stock that notifies investors when its price changes
 */
public class Stock implements Subject {
    private String name;
    private double price;
    private List<Observer> observers;

    public Stock(String name, double initialPrice) {
        this.name = name;
        this.price = initialPrice;
        this.observers = new ArrayList<>();
    }

    /**
     * Set new price and notify all observers
     * @param price new stock price
     */
    public void setPrice(double price) {
        if (this.price != price) {
            this.price = price;
            System.out.println("\n[Stock] " + name + " price changed to $" + price);
            notifyObservers();
        }
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
        System.out.println("[Stock] Investor attached to " + name);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
        System.out.println("[Stock] Investor detached from " + name);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(name, price);
        }
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
