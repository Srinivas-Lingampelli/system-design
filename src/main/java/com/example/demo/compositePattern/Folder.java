package com.example.demo.compositePattern;

// ============================================================
// Folder.java — The COMPOSITE
//
// This is where the magic happens.
//
// A Folder holds a List<FileSystemItem> — NOT List<File> and
// NOT List<Folder>. It holds the INTERFACE type. This means
// it can contain BOTH files and other folders without caring
// which is which.
//
// getSize() is the most important method here. It loops through
// children and calls getSize() on each. If a child is a File,
// it gets the file's size. If a child is another Folder, that
// folder loops through ITS children and calls getSize() on them.
// This repeats until it hits leaf Files at the bottom.
// That is recursive composition in action.
// ============================================================
import java.util.ArrayList;
import java.util.List;

public class Folder implements FileSystemItem {

    private final String  name;
    private final List<FileSystemItem> children;

    public Folder(String name) {
        this.name     = name;
        this.children = new ArrayList<>();
    }

    // ── Child management ────────────────────────────────────
    public Folder add(FileSystemItem item) {
        children.add(item);
        return this; // fluent API — enables method chaining
    }

    public void remove(FileSystemItem item) {
        children.remove(item);
    }

    public List<FileSystemItem> getChildren() {
        return new ArrayList<>(children); // defensive copy
    }

    // ── FileSystemItem contract ──────────────────────────────
    @Override
    public String getName() { return name; }

    @Override
    public long getSize() {
        // THE KEY LINE — recursion via the interface
        // Each child handles its OWN getSize() calculation
        // Folder doesn't care if child is File or another Folder
        return children.stream()
                .mapToLong(FileSystemItem::getSize)
                .sum();
    }

    @Override
    public String getType() { return "Folder"; }

    @Override
    public void display(String indent) {
        long totalSize = getSize();
        System.out.printf("%s📁 %-25s [Folder]  %s  (%d items)%n",
                indent,
                name + "/",
                formatSize(totalSize),
                children.size());

        // Recursively display all children with increased indent
        for (FileSystemItem child : children) {
            child.display(indent + "    ");
        }
    }

    private String formatSize(long kb) {
        if (kb >= 1024) return String.format("%.1f MB", kb / 1024.0);
        return kb + " KB";
    }

    // ── Utility methods (bonus — real-world useful) ──────────

    // Count ALL files recursively including nested folders
    public int countFiles() {
        int count = 0;
        for (FileSystemItem child : children) {
            if (child instanceof File) {
                count++;
            } else if (child instanceof Folder) {
                count += ((Folder) child).countFiles();
            }
        }
        return count;
    }

    // Search for a file by name across the entire tree
    public FileSystemItem search(String targetName) {
        for (FileSystemItem child : children) {
            if (child.getName().equals(targetName)) {
                return child;
            }
            if (child instanceof Folder) {
                FileSystemItem found = ((Folder) child).search(targetName);
                if (found != null) return found;
            }
        }
        return null;
    }
}