package com.nvminh162.designpattern.observer;

/**
 * Observer interface in Observer Design Pattern
 * Defines the update method that will be called when the subject's state changes
 */
public interface Observer {
    /**
     * Called when the subject's state changes
     * @param subjectName name of the subject that changed
     * @param data the updated data (can be price, status, etc.)
     */
    void update(String subjectName, Object data);
}
