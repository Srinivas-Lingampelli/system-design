package com.example.demo;

import com.example.demo.compositePattern.File;
import com.example.demo.compositePattern.FileSystem;
import com.example.demo.compositePattern.FileSystemItem;
import com.example.demo.compositePattern.Folder;
import com.example.demo.decorator.Coffee;
import com.example.demo.decorator.MilkDecorator;
import com.example.demo.decorator.PlainCoffee;
import com.example.demo.decorator.SugarDecorator;
import com.example.demo.facade.OrderFacade;
import com.example.demo.observer.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import startegy.*;

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


        // Client only knows ONE class — OrderFacade
        OrderFacade orderFacade = new OrderFacade();

        // ── Place an order — ONE LINE ─────────────────────────
        boolean success = orderFacade.placeOrder(
                "USER-42",      // userId
                "PROD-iPhone",  // productId
                1,              // quantity
                79999.00        // amount in rupees
        );

        if (success) {
            // ── Track the order ───────────────────────────────
            orderFacade.trackOrder("TRACK-PROD-iPhone-USER-42");

            // ── Cancel if needed ──────────────────────────────
            // orderFacade.cancelOrder(
            //     "USER-42",
            //     "TXN-123456789",
            //     "TRACK-PROD-iPhone-USER-42",
            //     "PROD-iPhone",
            //     1
            // );
        }

        // Create the subject
        StockMarket tcs = new StockMarket("TCS", 3500.00);

        // Create observers
        Observer mobileRavi  = new MobileAppObserver("Ravi");
        Observer mobilePriya = new MobileAppObserver("Priya");
        Observer emailAlert  = new EmailAlertObserver("ravi@email.com", 5.0);
        Observer tradingBot  = new TradingBotObserver(3200.00);
        Observer dashboard   = new DashboardObserver(3500.00);

        // Subscribe observers
        System.out.println("\n── Subscribing observers ──");
        tcs.subscribe(mobileRavi);
        tcs.subscribe(mobilePriya);
        tcs.subscribe(emailAlert);
        tcs.subscribe(tradingBot);
        tcs.subscribe(dashboard);

        // Price changes — ALL observers get notified
        tcs.setPrice(3550.00);   // price went up
        tcs.setPrice(3280.00);   // price dropped — email alert fires

        // Unsubscribe Priya — she no longer gets updates
        System.out.println("\n── Priya unsubscribes ──");
        tcs.unsubscribe(mobilePriya);

        tcs.setPrice(3150.00);   // drops further — bot buys!



        String from = "Hitech City";
        String to   = "Charminar";

        // Start with Driving
        Navigator nav = new Navigator(new DrivingStrategy());
        nav.navigate(from, to);

        // User switches to Walking — same Navigator
        nav.setStrategy(new WalkingStrategy());
        nav.navigate(from, to);

        // User switches to Cycling
        nav.setStrategy(new CyclingStrategy());
        nav.navigate(from, to);

        // New strategy added later — Navigator never changed
        nav.setStrategy(new PublicTransportStrategy());
        nav.navigate(from, to);



    }


}
