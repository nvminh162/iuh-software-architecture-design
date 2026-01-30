package com.nvminh162.designpattern.adapter;

/**
 * Demo class to demonstrate Adapter Design Pattern
 * Shows how to adapt XmlService to work with JsonService interface
 */
public class AdapterDemo {
    public static void main(String[] args) {
        System.out.println("=== Adapter Design Pattern Demo ===\n");
        System.out.println("Scenario: Web service requires JSON, but system only supports XML\n");

        // Create the existing XML service (Adaptee)
        XmlService xmlService = new XmlService();
        
        System.out.println("=== Example 1: Basic Adapter ===");
        System.out.println("---------------------------------");
        
        // Create adapter that wraps XmlService
        JsonService adapter = new XmlToJsonAdapter(xmlService);
        
        // Client code works with JsonService interface
        String jsonData1 = "{\"name\": \"John Doe\", \"age\": 30, \"city\": \"New York\"}";
        System.out.println("\nClient sending JSON data:");
        adapter.sendJson(jsonData1);
        
        System.out.println("\n\n=== Example 2: Improved Adapter ===");
        System.out.println("-----------------------------------");
        
        // Create improved adapter
        JsonService improvedAdapter = new XmlToJsonAdapterImproved(xmlService);
        
        String jsonData2 = "{\"product\": \"Laptop\", \"price\": 999.99, \"inStock\": true}";
        System.out.println("\nClient sending JSON data:");
        improvedAdapter.sendJson(jsonData2);
        
        System.out.println("\n\n=== Example 3: Multiple JSON Requests ===");
        System.out.println("------------------------------------------");
        
        String jsonData3 = "{\"userId\": 12345, \"email\": \"user@example.com\", \"active\": true}";
        System.out.println("\nClient sending JSON data:");
        improvedAdapter.sendJson(jsonData3);
        
        System.out.println("\n=== Demo completed ===");
        System.out.println("\nKey Points:");
        System.out.println("1. Client code only knows about JsonService interface");
        System.out.println("2. Adapter converts JSON to XML internally");
        System.out.println("3. XmlService (existing system) receives XML as expected");
        System.out.println("4. No changes needed to existing XmlService code");
    }
}
