package zoo.interfaces;

/**
 * Интерфейс определяет живое существо, которое употребляет food кг в день.
 */
public interface IALive {
    Integer getFoodPerDay();
    void setFoodPerDay(Integer foodPerDay);
}
