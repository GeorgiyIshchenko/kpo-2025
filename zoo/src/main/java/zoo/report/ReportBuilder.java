package zoo.report;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import zoo.domains.Animal;

/**
 * Построитель отсчета.
 */
public class ReportBuilder {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ReportBuilder() {
        content = new StringBuilder();
    }

    /**
     * Вывод отсчета о еде.
     */
    public ReportBuilder addFood(Map<String, Integer> mappedAnimals, int sum) {
        content.append("Потребляемое количество еды:");
        for (Map.Entry<String, Integer> animalFood : mappedAnimals.entrySet()) {
            content.append(String.format("Name: %s, FoodPerDay: %s", animalFood.getKey(), animalFood.getValue()));
        }
        content.append(String.format("Всего нужно: %d", sum));
        content.append("\n");
        return this;
    }


    /**
     * Выводит информацию о животных, пригодных для контактного зоопарка.
     */
    public ReportBuilder addPettingZoo(List<Animal> animals) {
        content.append("Животные для контактного зоопарка:");
        animals.forEach(animal -> content.append(String.format(" - %s", animal)));
        content.append("\n");
        return this;
    }


    /**
     * TODO: Печатает всю смету зоопарка.
     */
    public ReportBuilder addZoo() {
        return this;
    }

    public Report build() {
        return new Report(String.format("Отчет за %s", ZonedDateTime.now().format(DATE_TIME_FORMATTER)),
                content.toString());
    }

    StringBuilder content;

}
