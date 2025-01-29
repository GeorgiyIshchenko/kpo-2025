package hse.kpo.services;

import hse.kpo.domains.Car;
import hse.kpo.domains.Customer;
import hse.kpo.interfaces.InCarFactory;
import hse.kpo.interfaces.InCarProvider;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;


/**
 * Basic implementation of InCarProvider interface.
 */
@Component
public class CarService implements InCarProvider {

    private final List<Car> cars = new ArrayList<>();

    private int carNumberCounter = 0;

    /**
     * Метод для создания получения подходящего для покупателя автомобиля.
     * 
     * Возвращает первый наиболее подходящий автомобиль и удаляет его из списка cars.
     *
     * @param customer - покупатель
     * @return - подходящий автомобиль, или null, если для этого покупателя не нашлось подходящих автомобилей.
     */

    @Override
    public Car takeCar(Customer customer) {

        var filteredCars = cars.stream().filter(car -> car.isCompatible(customer)).toList();

        if (filteredCars.isEmpty()) {
            return null;
        }

        var firstCar = filteredCars.stream().findFirst().get();

        cars.remove(firstCar);

        return firstCar;
    }

    /**
     * Добавляет автомобиль с переданными параметрами, выпущенный переданной автомобильной фабрикой.
     * Инкрементирует счетчик автомобилей.
     *
     * @param <T> - тип параметров
     * @param carFactory - автомобильная фабрика
     * @param carParams - параметры создаваемого автомобиля
     */
    public <T> void addCar(InCarFactory<T> carFactory, T carParams) {
        // создаем автомобиль из переданной фабрики
        var car = carFactory.createCar(
                carParams, // передаем параметры
                ++carNumberCounter // передаем номер - номер будет начинаться с 1
        );

        cars.add(car); // добавляем автомобиль
    }
}
