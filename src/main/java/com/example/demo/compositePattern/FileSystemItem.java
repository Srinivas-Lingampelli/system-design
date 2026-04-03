package com.example.demo.compositePattern;

public interface FileSystemItem {


    // Every item must know its name
    String getName();

    // Every item must know its total size
    // For File   → returns its own size
    // For Folder → returns sum of ALL children recursively
    long getSize();

    // Every item must know how to display itself
    // indent controls how deep in the tree we are (for pretty printing)
    void display(String indent);

    // Every item must know its type for display purposes
    String getType();

}
