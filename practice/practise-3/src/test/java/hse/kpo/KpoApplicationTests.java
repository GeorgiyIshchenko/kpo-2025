package hse.kpo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;

import hse.kpo.domains.Customer;
import hse.kpo.factories.FlyingCarFactory;
import hse.kpo.factories.HandCarFactory;
import hse.kpo.factories.PedalCarFactory;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import hse.kpo.services.CarService;
import hse.kpo.services.CustomerStorage;
import hse.kpo.services.HseCarService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KpoApplicationTests {

    @Autowired
    private CarService carService;

    @Autowired
    private CustomerStorage customerStorage;

    @Autowired
    private HseCarService hseCarService;

    @Autowired
    private PedalCarFactory pedalCarFactory;

    @Autowired
    private HandCarFactory handCarFactory;

    @Autowired
    private FlyingCarFactory flyingCarFactory;

    @Test
    @DisplayName("Тест загрузки контекста")
    void contextLoads() {

        customerStorage.addCustomer(new Customer("Вася", 6, 4));
        customerStorage.addCustomer(new Customer("Вова", 4, 6));
        customerStorage.addCustomer(new Customer("Света", 6, 6));
        customerStorage.addCustomer(new Customer("Петя", 4, 4));

        customerStorage.addCustomer(new Customer("Smart", 1, 1, 301));
        
        carService.addCar(pedalCarFactory, new PedalEngineParams(2));
        carService.addCar(pedalCarFactory, new PedalEngineParams(2));
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
        carService.addCar(handCarFactory, new EmptyEngineParams());
        carService.addCar(flyingCarFactory, EmptyEngineParams.DEFAULT);

        for (var customer : customerStorage.getCustomers()) {
            String carInfo = customer.getCar() != null ? customer.getCar().toString() : "null";
            System.out.println("Customer: " + customer.getName() + ", car: " + carInfo);
        }

        hseCarService.sellCars();

        System.out.println("------------------------------------");

        for (var customer : customerStorage.getCustomers()) {
            var car = customer.getCar();
            System.out.println("Customer: " + customer.getName() + ", car: " + (car != null ? car.toString() : "null"));
        }

	}

}
