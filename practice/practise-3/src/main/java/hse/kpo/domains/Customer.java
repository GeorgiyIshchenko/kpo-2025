package hse.kpo.domains;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Customer with parameters: name, legPower, handPower, IQ.
 */
@Getter
@ToString
public class Customer {
    
    @Getter
    private final String name;

    private final int legPower;

    private final int handPower;

    private final int iq;

    @Setter
    private Car car;

    /**
     * Customer constructor with default iq.
     *
     * @param name - name
     * @param legPower - power of the legs
     * @param handPower - power of the hands
     */
    public Customer(String name, int legPower, int handPower) {
        this.name = name;
        this.legPower = legPower;
        this.handPower = handPower;
        this.iq = 0;
    }

    /**
     * Customer constructor with iq.
     *
     * @param name - name
     * @param legPower - power of the legs
     * @param handPower - power of the hands
     * @param iq - iq
     */
    public Customer(String name, int legPower, int handPower, int iq) {
        this.name = name;
        this.legPower = legPower;
        this.handPower = handPower;
        this.iq = iq;
    }
}
