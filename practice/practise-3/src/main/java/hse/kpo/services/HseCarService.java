package hse.kpo.services;

import hse.kpo.interfaces.InCarProvider;
import hse.kpo.interfaces.InCustomerProvider;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * HseCarService is a service that sells cars to customers according to technical requirements.
 */
@Component
@RequiredArgsConstructor
public class HseCarService {

    private final InCarProvider carProvider;

    private final InCustomerProvider customerProvider;

    /**
     * Метод для продажи всех автомобилей всем покупателям.
     * Пробегаемся по всем покупателям, которые не имеют автомобиля.
     * Если у покупателя нет автомобиля, продаем ему подходящий.
     *
     * @see InCarProvider
     * @see InCustomerProvider
     */
    public void sellCars() {
        // получаем список покупателей
        var customers = customerProvider.getCustomers();
        // пробегаемся по полученному списку
        customers.stream().filter(customer -> Objects.isNull(customer.getCar()))
                .forEach(customer -> {
                    var car = carProvider.takeCar(customer);
                    if (Objects.nonNull(car)) {
                        customer.setCar(car);
                    }
                });
    }
}