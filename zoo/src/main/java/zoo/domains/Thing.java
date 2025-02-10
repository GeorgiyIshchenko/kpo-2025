package zoo.domains;

import zoo.interfaces.IInventory;

/**
 * Базовый класс предмета инвентаря.
 */
public class Thing implements IInventory {

    @Override
    public Integer getNumber() {
        return number;
    }

    @Override
    public void setNumber(Integer number) {
        this.number = number;
    }

    Integer number;
}
