package zoo;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zoo.factories.HerboFactory;
import zoo.factories.PredatorFactory;
import zoo.factories.ThingFactory;
import zoo.params.HerboParams;
import zoo.params.PredatorParams;
import zoo.params.ThingParams;
import zoo.report.ReportBuilder;
import zoo.services.AnimalInventoryProvider;
import zoo.services.HseZoo;
import zoo.services.SimpleClinic;


/**
 * Console app for zoo management.
 * Firstly, setup clinic with needed value of feedPerDay to get to the zoo.
 * You can add herbo, predator and thing to zoo print information about feeding, petting and whole zoo.
 */
@SpringBootApplication
public class ZooApp {

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public static void main(String[] args) {

        SpringApplication.run(ZooApp.class, args);
        System.out.println("Hello, zoo!");

        Scanner scanner = new Scanner(System.in);
        HerboFactory herboFactory = new HerboFactory();
        PredatorFactory predatorFactory = new PredatorFactory();
        ThingFactory thingFactory = new ThingFactory();

        System.out.println("Enter feedPerDay to get to the zoo for Clinic: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Это не число!");
            scanner.next();
        }
        int feedPerDay = scanner.nextInt();
        SimpleClinic clinic = new SimpleClinic(feedPerDay);
        AnimalInventoryProvider provider = new AnimalInventoryProvider(clinic);
        HseZoo zoo = new HseZoo(provider);

        System.out.println("You can add herbo, predator and thing to zoo print information about feeding, petting and whole zoo.");
        while(true) {
            System.out.println("Following command list:\n-ah\n-ap\n-at\n-if\n-ip\n-iz");
            System.out.println("Adding format: <Name> <Param1> <Param2>");
            try {
                System.out.println("Enter command: ");

                String input = scanner.next();

                if (input.equalsIgnoreCase("exit")){
                    break;
                }

                switch (input) {
                    case "-ah":
                        System.out.println("You are adding herbo. Format: <String:Name> <Int:FoodPerDay> <Int:Kindness>");
                        provider.addInventory(scanner.next(), herboFactory, new HerboParams(scanner.nextInt(), scanner.nextInt()));
                        System.out.println("Successfully added!");
                        break;
                    case "-ap":
                        System.out.println("You are adding predator. Format: <String:Name> <Int:FoodPerDay>");
                        provider.addInventory(scanner.next(), predatorFactory, new PredatorParams(scanner.nextInt()));
                        System.out.println("Successfully added!");
                        break;
                    case "-at":
                        System.out.println("You are adding thing. Format: <String:Name>");
                        provider.addInventory(scanner.next(), thingFactory, new ThingParams());
                        System.out.println("Successfully added!");
                        break;
                    case "-if":
                        System.out.println(new ReportBuilder().addFood(zoo.getFoodMap(), zoo.getFoodSum()).build());
                        break;
                    case "-ip":
                        System.out.println(new ReportBuilder().addPettingZoo(zoo.getPettingAnimals()).build());
                        break;
                    case "-iz":
                        System.out.println(new ReportBuilder().addZoo(zoo.getProvider().getInventoryList()).build());
                        break;
                    default:
                        System.out.println("Unknown command!");
                }

            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        scanner.close();

    }
}
