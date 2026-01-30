package com.nvminh162.designpattern.composite;

/**
 * Demo class to demonstrate how to use Composite Design Pattern
 */
public class CompositeDemo {
    public static void main(String[] args) {
        System.out.println("=== Composite Design Pattern Demo - File System ===\n");

        // Create files
        File file1 = new File("document.txt", 1024);
        File file2 = new File("image.jpg", 2048);
        File file3 = new File("readme.txt", 512);
        File file4 = new File("config.xml", 256);

        // Create folders
        Folder rootFolder = new Folder("Root");
        Folder documentsFolder = new Folder("Documents");
        Folder imagesFolder = new Folder("Images");
        Folder subFolder = new Folder("SubFolder");

        // Build tree structure
        // Root contains Documents and Images
        rootFolder.add(documentsFolder);
        rootFolder.add(imagesFolder);

        // Documents contains document.txt and SubFolder
        documentsFolder.add(file1);
        documentsFolder.add(subFolder);

        // SubFolder contains readme.txt
        subFolder.add(file3);

        // Images contains image.jpg
        imagesFolder.add(file2);

        // Add file directly to Root
        rootFolder.add(file4);

        // Display tree structure
        System.out.println("File system structure:");
        System.out.println("------------------------");
        rootFolder.display("");

        System.out.println("\n=== Demo completed ===");
    }
}

/* 
cd src\main\java\com\nvminh162\designpattern\composite
javac -encoding UTF-8 FileSystemItem.java File.java Folder.java CompositeDemo.java

cd src\main\java; java com.nvminh162.designpattern.composite.CompositeDemo
*/