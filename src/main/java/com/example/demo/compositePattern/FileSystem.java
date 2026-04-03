package com.example.demo.compositePattern;

import org.springframework.beans.factory.annotation.Autowired;

// ============================================================
// FileSystem.java — FACADE over the Composite tree
//
// In a real production system, you never expose the raw tree
// to clients. You wrap it in a service/facade class that
// provides clean, business-level operations.
//
// Tech lead note: This separation means if you swap the
// underlying data structure tomorrow, clients don't change.
// ============================================================
public class FileSystem {

    private final Folder root;

    public FileSystem(String rootName) {
        this.root = new Folder(rootName);
    }

    public Folder getRoot() { return root; }

    public void displayTree() {
        System.out.println("\n========================================");
        System.out.println("  FILE SYSTEM TREE");
        System.out.println("========================================");
        root.display("");
        System.out.println("========================================");
        System.out.printf("  TOTAL SIZE : %s%n", formatSize(root.getSize()));
        System.out.printf("  TOTAL FILES: %d%n", root.countFiles());
        System.out.println("========================================\n");
    }

    public void displaySize(FileSystemItem item) {
        System.out.printf("Size of %-20s → %s%n",
                "'" + item.getName() + "'",
                formatSize(item.getSize()));
    }

    public FileSystemItem find(String name) {
        return root.search(name);
    }

    private String formatSize(long kb) {
        if (kb >= 1024) return String.format("%.2f MB", kb / 1024.0);
        return kb + " KB";
    }
}