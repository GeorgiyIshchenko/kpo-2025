package zoo.interfaces;

import zoo.domains.Animal;

/**
 * Интерфейс фабрики животных.
 *
 * @param <T> - параметры животного
 */
public interface IInventoryFactory<T> {

    IInventory createInstance(String entity, T params, int number);

}
