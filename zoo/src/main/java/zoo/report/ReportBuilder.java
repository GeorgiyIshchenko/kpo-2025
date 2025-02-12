package zoo.report;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import zoo.domains.Animal;
import zoo.interfaces.IInventory;

/**
 * Построитель отсчета.
 */
@Component
@Scope("prototype")
public class ReportBuilder {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ReportBuilder() {
        content = new StringBuilder();
    }

    /**
     * Вывод отсчета о еде.
     */
    public ReportBuilder addFood(Map<String, Integer> mappedAnimals, int sum) {
        content.append("Потребляемое количество еды:\n");
        for (Map.Entry<String, Integer> animalFood : mappedAnimals.entrySet()) {
            content.append(String.format("Name: %s, FoodPerDay: %s кг\n", animalFood.getKey(), animalFood.getValue()));
        }
        content.append(String.format("Всего нужно: %d кг\n", sum));
        return this;
    }


    /**
     * Выводит информацию о животных, пригодных для контактного зоопарка.
     */
    public ReportBuilder addPettingZoo(List<Animal> animals) {
        content.append("Животные для контактного зоопарка:\n");
        animals.forEach(animal -> content.append(String.format(" - %s\n", animal.toString())));
        return this;
    }


    /**
     * Печатает всю смету зоопарка.
     */
    public ReportBuilder addZoo(List<IInventory> inventories) {
        content.append("Смета зоопарка:\n");
        inventories.forEach(inventory -> content.append(String.format(" - %s\n", inventory.toString())));
        return this;
    }

    public Report build() {
        return new Report(String.format("Отчет за %s", ZonedDateTime.now().format(DATE_TIME_FORMATTER)),
                content.toString());
    }

    StringBuilder content;

}
