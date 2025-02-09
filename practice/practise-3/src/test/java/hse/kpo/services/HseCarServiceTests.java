package hse.kpo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import hse.kpo.domains.Customer;
import hse.kpo.factories.FlyingCarFactory;
import hse.kpo.factories.HandCarFactory;
import hse.kpo.factories.PedalCarFactory;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Hse car service tests.
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class HseCarServiceTests {

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
    @DisplayName("General test for car selling")
    void hseCarServiceSell() {

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
            var carInfo = customer.getCar();
            assertNull(carInfo);
        }

        hseCarService.sellCars();

        var car0 = customerStorage.getCustomers().getFirst().getCar();
        assertEquals(1, car0.getVin());
        var car1 = customerStorage.getCustomers().get(1).getCar();
        assertEquals(3, car1.getVin());
        var car2 = customerStorage.getCustomers().get(2).getCar();
        assertEquals(2, car2.getVin());
        var car3 = customerStorage.getCustomers().get(3).getCar();
        assertNull(car3);
        var car4 = customerStorage.getCustomers().get(4).getCar();
        assertEquals(5, car4.getVin());

    }

}
