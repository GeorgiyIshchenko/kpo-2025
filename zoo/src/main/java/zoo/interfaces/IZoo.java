package zoo.interfaces;

import java.util.List;
import java.util.Map;
import zoo.domains.Animal;
import zoo.domains.Predator;
import zoo.domains.Thing;

/**
 * Интерфейс для любого зоопарка.
 */
public interface IZoo {

    Map<String, Integer> getFoodMap();

    int getFoodSum();

    List<Animal> getPettingAnimals();

}
