package zoo.domains;

import zoo.interfaces.IInventory;

/**
 * Базовый класс предмета инвентаря.
 */
public class Thing implements IInventory {

    public Thing(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
    }

    int number;
}
