package hse.kpo.domains;

import hse.kpo.enums.ProductionTypes;
import hse.kpo.interfaces.IEngine;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Getter
public class PedalEngine implements IEngine {
    private final int size;

    @Override
    public boolean isCompatible(Customer customer, ProductionTypes type) {
        switch (type) {
            case ProductionTypes.CAR -> {
                return customer.getLegPower() > 5;
            }
            case ProductionTypes.CATAMARAN -> {
                return customer.getLegPower() > 2;
            }
            case null, default -> {
                log.error("Incorrect vehicle type!");
                throw new RuntimeException("This type of production doesn't exist");
            }
        }
    }

    public PedalEngine(int size) {
        this.size = size;
    }
}
