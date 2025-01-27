package hse.kpo.factories;

import hse.kpo.domains.Car;
import hse.kpo.domains.HandEngine;
import hse.kpo.interfaces.ICarFactory;
import hse.kpo.params.EmptyEngineParams;

public class HandCarFactory implements ICarFactory<EmptyEngineParams> {

    /**
     * Метод автомобильной фабрики по сборке ручных автомобилей для создания автомобиля.
     * 
     * @param carParams - параметры создаваемого автомобиля EmptyEngineParams
     * @param carNumber - номер автомобиля
     * @return - созданный автомобиль
     */
    @Override
    public Car createCar(EmptyEngineParams carParams, int carNumber) {
        var engine = new HandEngine(); // Создаем двигатель без каких-либо параметров

        return new Car(carNumber, engine); // создаем автомобиль с ручным приводом
    }
}
