package hse.kpo.factories;


import hse.kpo.domains.Ship;
import hse.kpo.domains.PedalEngine;
import hse.kpo.interfaces.IShipFactory;
import hse.kpo.params.PedalEngineParams;
import org.springframework.stereotype.Component;

@Component
public class PedalShipFactory implements IShipFactory<PedalEngineParams> {
    @Override
    public Ship createShip(PedalEngineParams shipParams, int shipNumber) {
        var engine = new PedalEngine(shipParams.pedalSize()); // создаем двигатель на основе переданных параметров

        return new Ship(shipNumber, engine); // возвращаем собранный автомобиль с установленным двигателем и определенным номером
    }
}
