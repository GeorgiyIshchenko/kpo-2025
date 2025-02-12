package zoo.interfaces;

import java.util.List;

/**
 * Предоставляет животного по запросу.
 */
public interface IInventoryProvider {

    <T> void addInventory(String animalName, IInventoryFactory<T> factory, T params);

    List<IInventory> getInventoryList();

}
