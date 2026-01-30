package com.nvminh162.designpattern.adapter;

/**
 * Adaptee class in Adapter Design Pattern
 * Represents an existing service that only works with XML format
 */
public class XmlService {
    /**
     * Send data in XML format (existing method that we need to adapt)
     * @param xmlData XML formatted string
     */
    public void sendXml(String xmlData) {
        System.out.println("[XmlService] Sending XML data:");
        System.out.println(xmlData);
        System.out.println("[XmlService] XML data sent successfully!");
    }

    /**
     * Get service name for identification
     * @return service name
     */
    public String getServiceName() {
        return "XML Service";
    }
}
