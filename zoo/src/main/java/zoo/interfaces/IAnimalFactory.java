package zoo.interfaces;

import zoo.domains.Animal;
import zoo.exceptions.ZooException;

/**
 * Интерфейс фабрики животных.
 *
 * @param <T> - параметры животного
 */
public interface IAnimalFactory<T> {

    Animal createAnimal(String entity, T params);

}
