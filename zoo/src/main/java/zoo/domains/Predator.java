package zoo.domains;

/**
 * Базовый класс для всех хищников.
 */
public class Predator extends Animal {

    public Predator(int number, int foodPerDay) {
        super(number, foodPerDay);
    }

    @Override
    public String toString() {
        return String.format("%s", super.toString());
    }

}
