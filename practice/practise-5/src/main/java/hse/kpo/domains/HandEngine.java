package hse.kpo.domains;

import hse.kpo.enums.ProductionTypes;
import hse.kpo.interfaces.IEngine;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.java.JavaLoggingSystem;

@Slf4j
@ToString
public class HandEngine implements IEngine {
    @Override
    public boolean isCompatible(Customer customer, ProductionTypes type) {
        return switch (type) {
            case ProductionTypes.CAR -> customer.getHandPower() > 5;
            case ProductionTypes.CATAMARAN -> customer.getHandPower() > 2;
            case null, default -> throw new RuntimeException("This type of production doesn't exist");
        };
    }
}
