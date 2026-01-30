package com.nvminh162.designpattern.adapter;

/**
 * Improved Adapter with better JSON to XML conversion
 * Uses JsonToXmlConverter utility for more accurate conversion
 */
public class XmlToJsonAdapterImproved implements JsonService {
    private XmlService xmlService;

    public XmlToJsonAdapterImproved(XmlService xmlService) {
        this.xmlService = xmlService;
    }

    @Override
    public void sendJson(String jsonData) {
        System.out.println("[Improved Adapter] Received JSON data:");
        System.out.println(jsonData);
        
        // Convert JSON to XML using improved converter
        String xmlData = JsonToXmlConverter.convert(jsonData);
        
        System.out.println("[Improved Adapter] Converted to XML:");
        System.out.println(xmlData);
        
        // Delegate to XmlService
        xmlService.sendXml(xmlData);
    }
}
