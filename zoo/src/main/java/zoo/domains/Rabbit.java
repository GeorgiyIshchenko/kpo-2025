package zoo.domains;

import zoo.annotations.HerboRegister;

/**
 * Кролик.
 */
@HerboRegister
public class Rabbit extends Herbo {

    public Rabbit(Integer foodPerDay, int kindness) {
        super(foodPerDay, kindness);
    }

}
