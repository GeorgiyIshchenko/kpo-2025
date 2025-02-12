package zoo.domains;

import zoo.interfaces.IALive;
import zoo.interfaces.IInventory;

/**
 * Базовый класс животного.
 */
public class Animal implements IInventory, IALive {

    public Animal(int number, int foodPerDay) {
        this.number = number;
        this.foodPerDay = foodPerDay;
    }

    @Override
    public int getFoodPerDay() {
        return foodPerDay;
    }

    @Override
    public void setFoodPerDay(int foodPerDay) {
        this.foodPerDay = foodPerDay;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return String.format("%s: Number=%s,FoodPerDay=%s", getClass().getSimpleName(), number, foodPerDay);
    }

    int foodPerDay;
    int number;
}
