package zoo.interfaces;

/**
 * Интерфейс определяет сущность как предмет инвентаря. Каждый предмет инвентаря должен иметь Number.
 */
public interface IInventory {

    Integer getNumber();

    void setNumber(Integer number);

}
