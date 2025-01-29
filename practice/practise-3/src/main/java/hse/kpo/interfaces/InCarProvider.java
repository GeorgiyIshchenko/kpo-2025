package hse.kpo.interfaces;

import hse.kpo.domains.Car;
import hse.kpo.domains.Customer;

/**
 * Interface for providing cats instances.
 */
public interface InCarProvider {

    /**
     * Метод для создания получения подходящего для покупателя автомобиля.
     *
     * @param customer - покупатель
     * @return - подходящий автомобиль
     */
    Car takeCar(Customer customer); // Метод возвращает optional на Car, что означает, что метод может ничего не вернуть
}
