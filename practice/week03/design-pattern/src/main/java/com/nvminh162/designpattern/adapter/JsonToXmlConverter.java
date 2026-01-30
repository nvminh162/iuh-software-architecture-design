package com.nvminh162.designpattern.adapter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for converting JSON to XML
 * Provides a more robust conversion than the simple adapter method
 */
public class JsonToXmlConverter {
    
    /**
     * Convert a simple JSON object to XML format
     * Handles basic JSON structures: {"key": "value", "key2": 123}
     * @param json JSON string
     * @return XML string
     */
    public static String convert(String json) {
        if (json == null || json.trim().isEmpty()) {
            return "<root></root>";
        }
        
        // Remove outer braces and whitespace
        String cleaned = json.trim()
            .replaceAll("^\\s*\\{\\s*", "")
            .replaceAll("\\s*\\}\\s*$", "");
        
        StringBuilder xml = new StringBuilder("<root>\n");
        
        // Pattern to match JSON key-value pairs
        // Matches: "key": "value" or "key": 123 or "key": true
        Pattern pattern = Pattern.compile("\"([^\"]+)\"\\s*:\\s*(\"[^\"]*\"|\\d+(?:\\.\\d+)?|true|false|null)");
        Matcher matcher = pattern.matcher(cleaned);
        
        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2);
            
            // Remove quotes from string values
            if (value.startsWith("\"") && value.endsWith("\"")) {
                value = value.substring(1, value.length() - 1);
            }
            
            xml.append("  <").append(key).append(">")
               .append(value)
               .append("</").append(key).append(">\n");
        }
        
        xml.append("</root>");
        return xml.toString();
    }
}
