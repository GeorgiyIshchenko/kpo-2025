package hse.kpo.domains;

import hse.kpo.interfaces.IEngine;
import lombok.ToString;
import org.springframework.boot.logging.java.JavaLoggingSystem;

@ToString
public class HandEngine implements IEngine {
    @Override
    public boolean isCompatible(Customer customer, ProductionTypes type) {
        return switch (type) {
            case ProductionTypes.CAR -> customer.getHandPower() > 5;
            case ProductionTypes.CATAMARAN -> customer.getHandPower() > 2;
        };
    }
}
