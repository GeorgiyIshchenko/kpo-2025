package zoo.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import zoo.domains.Animal;
import zoo.interfaces.IInventory;
import zoo.interfaces.IZoo;

/**
 * Реализация по контракту.
 */
@Component
public class HseZoo implements IZoo {

    /**
     * Возвращает словарь с тем, сколько каждый вид животного употребляет еды.
     */
    public Map<String, Integer> getFoodMap() {
        Map<String, Integer> result = new HashMap<>();
        for (IInventory inventory : provider.getInventoryList()) {
            if (inventory instanceof Animal) {
                String className = inventory.getClass().getSimpleName();
                result.put(className, result.getOrDefault(className, 0) + ((Animal) inventory).getFoodPerDay());
            }
        }
        return result;
    }

    public int getFoodSum() {
        return provider.getAnimals().stream().mapToInt(Animal::getFoodPerDay).sum();
    }

    public List<Animal> getPettingAnimals() {
        return provider.getHerbos().stream().filter(herbo -> herbo.getKindness() > 5).collect(Collectors.toList());
    }

    @Getter
    @Setter
    AnimalInventoryProvider provider = new AnimalInventoryProvider(new SimpleClinic());

}
