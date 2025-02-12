package zoo.interfaces;

/**
 * Интерфейс определяет живое существо, которое употребляет food кг в день.
 */
public interface IALive {
    int getFoodPerDay();

    void setFoodPerDay(int foodPerDay);
}
