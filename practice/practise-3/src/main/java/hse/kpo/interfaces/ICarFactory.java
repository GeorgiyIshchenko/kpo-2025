package hse.kpo.interfaces;

import hse.kpo.domains.Car;

public interface ICarFactory<TParams> {
    /**
     * Метод абстрактной автомобильной фабрики для создания автомобиля.
     * 
     * @param carParams - параметры создаваемого автомобиля
     * @param carNumber - номер автомобиля
     * @return - созданный автомобиль
     */
    Car createCar(TParams carParams, int carNumber);
}
