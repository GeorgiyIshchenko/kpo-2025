package zoo.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import zoo.domains.Animal;
import zoo.domains.Herbo;
import zoo.interfaces.IClinic;
import zoo.interfaces.IInventory;
import zoo.interfaces.IInventoryFactory;
import zoo.interfaces.IInventoryProvider;

/**
 * Провайдер животных.
 */
@Component
public class AnimalInventoryProvider implements IInventoryProvider {

    public AnimalInventoryProvider(IClinic clinic) {
        this.clinic = clinic;
    }

    /**
     * Добавляет животного, если клиника разрешила.
     */
    @Override
    public <T> void addInventory(String name, IInventoryFactory<T> factory, T params) {
        IInventory inventory = factory.createInstance(
                name,
                params,
                inventoryCnt
        );

        if (inventory instanceof Animal && !clinic.isSuitable((Animal) inventory)) {
            return;
        }

        inventoryCnt++;
        inventoryList.add(inventory);
    }

    /**
     * Получить всех животных.
     */
    public List<Animal> getAnimals() {
        List<Animal> result = new ArrayList<>();
        for (IInventory inventory : inventoryList) {
            if (inventory instanceof Animal) {
                result.add((Animal) inventory);
            }
        }
        return result;
    }

    /**
     * Получить всех травоядных.
     */
    public List<Herbo> getHerbos() {
        List<Herbo> result = new ArrayList<>();
        for (IInventory inventory : inventoryList) {
            if (inventory instanceof Herbo) {
                result.add((Herbo) inventory);
            }
        }
        return result;
    }

    public List<IInventory> getInventoryList() {
        return inventoryList;
    }

    private final List<IInventory> inventoryList = new ArrayList<>();

    private int inventoryCnt = 0;

    IClinic clinic;

}
