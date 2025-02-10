package zoo.domains;

import zoo.interfaces.IALive;

/**
 * Базовый класс животного.
 */
public class Animal implements IALive {

    public Animal(Integer foodPerDay) {
        this.foodPerDay = foodPerDay;
    }

    @Override
    public Integer getFoodPerDay() {
        return foodPerDay;
    }

    @Override
    public void setFoodPerDay(Integer foodPerDay) {
        this.foodPerDay = foodPerDay;
    }

    Integer foodPerDay = 0;
}
