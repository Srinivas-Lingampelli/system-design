package com.example.demo.compositePattern;

public class File implements FileSystemItem {
    private final String name;
    private final long   sizeInKB;
    private final String extension;

    public File(String name, long sizeInKB) {
        this.name      = name;
        this.sizeInKB  = sizeInKB;
        this.extension = extractExtension(name);
    }

    private String extractExtension(String name) {
        int dot = name.lastIndexOf('.');
        return dot >= 0 ? name.substring(dot + 1).toUpperCase() : "UNKNOWN";
    }
    @Override
    public String getName() { return name; }

    @Override
    public long getSize() {
        return sizeInKB; // No recursion — just return own size
    }

    @Override
    public String getType() { return extension + " File"; }

    @Override
    public void display(String indent) {
        System.out.printf("%s📄 %-25s [%s]  %s%n",
                indent,
                name,
                getType(),
                formatSize(sizeInKB));
    }

    private String formatSize(long kb) {
        if (kb >= 1024) return String.format("%.1f MB", kb / 1024.0);
        return kb + " KB";
    }
}
