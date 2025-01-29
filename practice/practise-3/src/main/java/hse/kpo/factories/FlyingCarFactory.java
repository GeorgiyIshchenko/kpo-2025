package hse.kpo.factories;

import hse.kpo.domains.Car;
import hse.kpo.domains.FlyingEngine;
import hse.kpo.interfaces.InCarFactory;
import hse.kpo.params.EmptyEngineParams;
import org.springframework.stereotype.Component;

/**
 * Factory for creating flying cars.
 */
@Component
public class FlyingCarFactory implements InCarFactory<EmptyEngineParams> {

    /**
     * Метод автомобильной фабрики по сборке леающих автомобилей для создания автомобиля.
     *
     * @param carParams - параметры создаваемого автомобиля EmptyEngineParams
     * @param carNumber - номер автомобиля
     * @return - созданный автомобиль
     */
    @Override
    public Car createCar(EmptyEngineParams carParams, int carNumber) {
        var engine = new FlyingEngine(); // Создаем двигатель без каких-либо параметров

        return new Car(carNumber, engine); // создаем автомобиль с ручным приводом
    }
}