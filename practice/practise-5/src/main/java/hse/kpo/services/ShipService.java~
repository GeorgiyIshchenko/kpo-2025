package hse.kpo.services;

import hse.kpo.domains.Customer;
import hse.kpo.domains.Ship;
import hse.kpo.interfaces.ICarFactory;
import hse.kpo.interfaces.IShipFactory;
import hse.kpo.interfaces.IShipProvider;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShipService implements IShipProvider {

    private final List<Ship> ships = new ArrayList<>();

    private int shipNumberCounter = 0;

    @Override
    public Ship takeShip(Customer customer) {

        var filteredShips = ships.stream().filter(car -> car.isCompatible(customer)).toList();

        var firstShip = filteredShips.stream().findFirst();

        firstShip.ifPresent(ships::remove);

        return firstShip.orElse(null);
    }

    public <TParams> void addShip(IShipFactory<TParams> carFactory, TParams shipParams)
    {
        // создаем автомобиль из переданной фабрики
        var ship = carFactory.createShip(
                shipParams, // передаем параметры
                ++shipNumberCounter // передаем номер - номер будет начинаться с 1
        );

        ships.add(ship); // добавляем автомобиль
    }
}