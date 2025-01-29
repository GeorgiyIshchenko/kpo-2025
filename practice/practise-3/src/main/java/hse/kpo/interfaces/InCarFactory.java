package hse.kpo.interfaces;

import hse.kpo.domains.Car;

/**
 * Interface for creating cars with different engines.
 *
 * @param <T> - type of parameters
 */
public interface InCarFactory<T> {
    /**
     * Метод абстрактной автомобильной фабрики для создания автомобиля.
     *
     * @param carParams - параметры создаваемого автомобиля
     * @param carNumber - номер автомобиля
     * @return - созданный автомобиль
     */
    Car createCar(T carParams, int carNumber);
}
