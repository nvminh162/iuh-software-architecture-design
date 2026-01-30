package com.nvminh162.designpattern.composite;

/**
 * Leaf class in Composite Design Pattern
 * Represents a file in the system
 */
public class File implements FileSystemItem {
    private String name;
    private int size; // file size in bytes

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "📄 File: " + name + " (" + size + " bytes)");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
