package hse.kpo.factories;

import hse.kpo.domains.Ship;
import hse.kpo.domains.HandEngine;
import hse.kpo.interfaces.IShipFactory;
import hse.kpo.params.EmptyEngineParams;
import org.springframework.stereotype.Component;

@Component
public class HandShipFactory implements IShipFactory<EmptyEngineParams> {
    @Override
    public Ship createShip(EmptyEngineParams carParams, int carNumber) {
        var engine = new HandEngine(); // Создаем двигатель без каких-либо параметров

        return new Ship(carNumber, engine); // создаем автомобиль с ручным приводом
    }
}
