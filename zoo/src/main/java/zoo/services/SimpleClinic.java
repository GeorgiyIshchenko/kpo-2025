package zoo.services;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import zoo.domains.Animal;
import zoo.interfaces.IClinic;

/**
 * Клиника, которая проверяет состояние животного в зависимости от количества еды.
 */
@Component
@RequiredArgsConstructor
public class SimpleClinic implements IClinic {

    public SimpleClinic(int requiredFood) {
        this.requiredFood = requiredFood;
    }

    /**
     * Если количество еды больше 4, животное подходит для зоопарка.
     *
     * @param animal животное
     * @return подходит ли животное
     */
    public boolean isSuitable(Animal animal) {
        return animal.getFoodPerDay() >= this.requiredFood;
    }

    private int requiredFood;

}
