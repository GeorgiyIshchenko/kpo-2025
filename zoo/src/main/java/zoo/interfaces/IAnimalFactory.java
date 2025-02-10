package zoo.interfaces;

import zoo.domains.Animal;
import zoo.exceptions.ZooException;

/**
 * Интерфейс фабрики животных.
 */
public interface IAnimalFactory {

    Animal createAnimal(String entity, Integer foodPerDay);

}
