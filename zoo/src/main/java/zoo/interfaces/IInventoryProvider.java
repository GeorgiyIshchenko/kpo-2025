package zoo.interfaces;

/**
 * Предоставляет животного по запросу.
 */
public interface IInventoryProvider {

    <T> void addInventory(String animalName, IInventoryFactory<T> factory, T params);

}
