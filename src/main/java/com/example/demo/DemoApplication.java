package com.example.demo;

import com.example.demo.compositePattern.File;
import com.example.demo.compositePattern.FileSystem;
import com.example.demo.compositePattern.FileSystemItem;
import com.example.demo.compositePattern.Folder;
import com.example.demo.decorator.Coffee;
import com.example.demo.decorator.MilkDecorator;
import com.example.demo.decorator.PlainCoffee;
import com.example.demo.decorator.SugarDecorator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);


        // ── Build the file system tree ───────────────────────
        FileSystem fs = new FileSystem("root");

        // documents/ folder with its own subfolder
        Folder documents = new Folder("documents");
        Folder projects  = new Folder("projects");

        projects.add(new File("design.pdf",      500))
                .add(new File("notes.txt",         20));

        documents.add(new File("resume.pdf",     250))
                .add(new File("coverletter.docx", 80))
                .add(projects);

        // media/ folder
        Folder media = new Folder("media");
        media.add(new File("photo.jpg",   3500))
                .add(new File("video.mp4", 45000));

        // Wire everything to root
        fs.getRoot()
                .add(documents)
                .add(media)
                .add(new File("readme.txt", 5));

        // ── Use the tree ─────────────────────────────────────
        fs.displayTree();

        // Query sizes — SAME call whether File or Folder
        System.out.println("=== SIZE QUERIES ===");
        fs.displaySize(documents);
        fs.displaySize(media);
        fs.displaySize(projects);

        // Search the tree
        System.out.println("\n=== SEARCH ===");
        FileSystemItem found = fs.find("design.pdf");
        if (found != null) {
            System.out.printf("Found: %s (%s) — %d KB%n",
                    found.getName(), found.getType(), found.getSize());
        }

        // The POWER of the pattern — add a new nested folder
        // ZERO changes to File, Folder, or FileSystem classes
        System.out.println("\n=== ADDING NEW FOLDER AT RUNTIME ===");
        Folder archive = new Folder("archive");
        archive.add(new File("old_resume.pdf", 180))
                .add(new File("backup.zip",    8200));

        documents.add(archive);
        fs.displaySize(documents); // size auto-updates — recursion handles it
        fs.displayTree();



        // Plain Coffee
        Coffee coffee = new PlainCoffee();
        System.out.println("Description: " + coffee.getDescription());
        System.out.println("Cost: $" + coffee.getPrice());

        // Coffee with Milk
        Coffee milkCoffee = new MilkDecorator(new PlainCoffee());
        System.out.println("\nDescription: " + milkCoffee.getDescription());
        System.out.println("Cost: $" + milkCoffee.getPrice());

        // Coffee with Sugar and Milk
        Coffee sugarMilkCoffee = new SugarDecorator(new MilkDecorator(new PlainCoffee()));
        System.out.println("\nDescription: " + sugarMilkCoffee.getDescription());
        System.out.println("Cost: $" + sugarMilkCoffee.getPrice());
    }


}
