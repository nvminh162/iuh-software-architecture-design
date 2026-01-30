package com.nvminh162.designpattern.composite;

/**
 * Component interface in Composite Design Pattern
 * Defines common methods for both File and Folder
 */
public interface FileSystemItem {
    /**
     * Display item information with indent to show tree structure
     * @param indent indent string to show nesting level
     */
    void display(String indent);
}
