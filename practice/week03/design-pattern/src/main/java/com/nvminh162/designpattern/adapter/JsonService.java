package com.nvminh162.designpattern.adapter;

/**
 * Target interface in Adapter Design Pattern
 * Defines the interface that clients expect to work with
 */
public interface JsonService {
    /**
     * Send data in JSON format
     * @param jsonData JSON formatted string
     */
    void sendJson(String jsonData);
}
