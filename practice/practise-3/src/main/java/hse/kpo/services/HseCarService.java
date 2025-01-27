package hse.kpo.services;

import hse.kpo.interfaces.ICarProvider;
import hse.kpo.interfaces.ICustomerProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class HseCarService {

    private final ICarProvider carProvider;

    private final ICustomerProvider customerProvider;

    // public HseCarService(ICarProvider carProvider, ICustomerProvider customersProvider)
    // {
    //     this.carProvider = carProvider;
    //     this.customerProvider = customersProvider;
    // }

    /**
     * Метод для продажи всех автомобилей всем покупателям.
     * 
     * Пробегаемся по всем покупателям, которые не имеют автомобиля.
     * 
     * Если у покупателя нет автомобиля, продаем ему подходящий.
     * 
     * @see ICarProvider
     * @see ICustomerProvider
     */
    public void sellCars()
    {
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