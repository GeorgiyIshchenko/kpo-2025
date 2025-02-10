package hse.kpo.services;

import hse.kpo.interfaces.ICarProvider;
import hse.kpo.interfaces.ICustomerProvider;
import hse.kpo.interfaces.IShipProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class HseShipService {

    private final IShipProvider carProvider;

    private final ICustomerProvider customerProvider;

    public void sellShips()
    {
        // получаем список покупателей
        var customers = customerProvider.getCustomers();
        // пробегаемся по полученному списку
        customers.stream().filter(customer -> Objects.isNull(customer.getCar()))
                .forEach(customer -> {
                    var ship = carProvider.takeShip(customer);
                    if (Objects.nonNull(ship)) {
                        customer.setShip(ship);
                    }
                });
    }
}