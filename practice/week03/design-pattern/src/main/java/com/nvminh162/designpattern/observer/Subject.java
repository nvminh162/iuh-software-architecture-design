package com.nvminh162.designpattern.observer;

/**
 * Subject interface in Observer Design Pattern
 * Defines methods to attach, detach, and notify observers
 */
public interface Subject {
    /**
     * Attach an observer to receive notifications
     * @param observer the observer to attach
     */
    void attach(Observer observer);

    /**
     * Detach an observer to stop receiving notifications
     * @param observer the observer to detach
     */
    void detach(Observer observer);

    /**
     * Notify all attached observers about state changes
     */
    void notifyObservers();
}
