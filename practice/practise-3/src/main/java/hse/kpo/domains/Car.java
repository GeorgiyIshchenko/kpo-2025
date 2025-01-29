package hse.kpo.domains;

import hse.kpo.interfaces.InEngine;
import lombok.Getter;
import lombok.ToString;

/**
 * Car contains engine and VIN.
 */
@ToString
public class Car {

    private InEngine engine;

    @Getter
    private int vin;

    public Car(int vin, InEngine engine) {
        this.vin = vin;
        this.engine = engine;
    }

    
    /** 
     * This method is used to recognize if customer fits the car.
     *
     * @return boolean
     */
    public boolean isCompatible(Customer customer) {
        return this.engine.isCompatible(customer); // внутри метода просто вызываем соответствующий метод двигателя
    }
}
