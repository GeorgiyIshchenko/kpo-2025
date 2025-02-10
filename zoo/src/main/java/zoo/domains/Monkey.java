package zoo.domains;

import zoo.annotations.HerboRegister;

/**
 * Обезьяна.
 */
@HerboRegister
public class Monkey extends Herbo {

    public Monkey(Integer foodPerDay, int kindness) {
        super(foodPerDay, kindness);
    }

}


