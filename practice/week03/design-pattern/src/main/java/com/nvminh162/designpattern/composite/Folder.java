package com.nvminh162.designpattern.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite class in Composite Design Pattern
 * Represents a folder that can contain other FileSystemItems (File or Folder)
 */
public class Folder implements FileSystemItem {
    private String name;
    private List<FileSystemItem> children;

    public Folder(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    /**
     * Add a FileSystemItem to the folder
     * @param item File or Folder to add
     */
    public void add(FileSystemItem item) {
        children.add(item);
    }

    /**
     * Remove a FileSystemItem from the folder
     * @param item File or Folder to remove
     */
    public void remove(FileSystemItem item) {
        children.remove(item);
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "📁 Folder: " + name);
        // Display child items with increased indent
        String childIndent = indent + "  ";
        for (FileSystemItem item : children) {
            item.display(childIndent);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FileSystemItem> getChildren() {
        return new ArrayList<>(children); // Return copy to ensure encapsulation
    }
}
