package com.nvminh162.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete Subject for Task Status Notification
 * Represents a task that notifies team members when its status changes
 */
public class Task implements Subject {
    private String name;
    private String status;
    private List<Observer> observers;

    public Task(String name, String initialStatus) {
        this.name = name;
        this.status = initialStatus;
        this.observers = new ArrayList<>();
    }

    /**
     * Set new status and notify all observers
     * @param status new task status
     */
    public void setStatus(String status) {
        if (!this.status.equals(status)) {
            String oldStatus = this.status;
            this.status = status;
            System.out.println("\n[Task] " + name + " status changed from \"" 
                + oldStatus + "\" to \"" + status + "\"");
            notifyObservers();
        }
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
        System.out.println("[Task] Team member attached to " + name);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
        System.out.println("[Task] Team member detached from " + name);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(name, status);
        }
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }
}
