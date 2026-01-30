package com.nvminh162.designpattern.adapter;

/**
 * Adapter class in Adapter Design Pattern
 * Adapts XmlService to work with JsonService interface
 * Converts JSON input to XML format and delegates to XmlService
 */
public class XmlToJsonAdapter implements JsonService {
    private XmlService xmlService;

    public XmlToJsonAdapter(XmlService xmlService) {
        this.xmlService = xmlService;
    }

    @Override
    public void sendJson(String jsonData) {
        System.out.println("[Adapter] Received JSON data:");
        System.out.println(jsonData);
        
        // Convert JSON to XML
        String xmlData = convertJsonToXml(jsonData);
        
        System.out.println("[Adapter] Converted to XML:");
        System.out.println(xmlData);
        
        // Delegate to XmlService
        xmlService.sendXml(xmlData);
    }

    /**
     * Convert JSON string to XML string
     * Uses JsonToXmlConverter utility for conversion
     * @param jsonData JSON formatted string
     * @return XML formatted string
     */
    private String convertJsonToXml(String jsonData) {
        return JsonToXmlConverter.convert(jsonData);
    }
}
