package zoo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
 * Конфигурация bins.
 */
@Configuration
@ComponentScan(basePackages = "zoo")
public class AppConfig {

    @Bean
    public HerboFactory herboFactory() {
        return new HerboFactory();
    }

    @Bean
    public PredatorFactory predatorFactory() {
        return new PredatorFactory();
    }

    @Bean
    public ThingFactory thingFactory() {
        return new ThingFactory();
    }

    @Bean
    public SimpleClinic simpleClinic() {
        return new SimpleClinic(3);
    }

    /**
     * Провайдер с какими-то объектами для тестирования.
     */
    @Bean
    public AnimalInventoryProvider animalInventoryProvider() {
        AnimalInventoryProvider provider = new AnimalInventoryProvider(simpleClinic());

        provider.addInventory("Rabbit", herboFactory(), new HerboParams(5, 7));
        provider.addInventory("Rabbit", herboFactory(), new HerboParams(3, 3));
        provider.addInventory("Monkey", herboFactory(), new HerboParams(2, 3)); // Blocked by clinic
        provider.addInventory("Wolf", predatorFactory(), new PredatorParams(8));
        provider.addInventory("Tiger", predatorFactory(), new PredatorParams(10));
        provider.addInventory("Tiger", predatorFactory(), new PredatorParams(10));
        provider.addInventory("Tiger", predatorFactory(), new PredatorParams(10));
        provider.addInventory("Computer", thingFactory(), new ThingParams());

        return provider;
    }

    /**
     * И сюда этот провайдер запихнем.
     */
    @Bean
    public HseZoo hseZoo() {
        HseZoo zoo = new HseZoo();
        zoo.setProvider(animalInventoryProvider());
        return zoo;
    }

    /**
     * И в отсчет наш зоопарк
     */
    @Bean
    public ReportBuilder reportBuilder() {

    }

}
