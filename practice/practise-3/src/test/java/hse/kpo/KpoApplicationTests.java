package hse.kpo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
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
    @DisplayName("General test for car selling")
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

    @Test
    @DisplayName("Adding car using DI")
    void testAddCar() {
        int initialSize = mockCarService.getCarNumberCounter();
        mockCarService.addCar(mockPedalCarFactory, new PedalEngineParams(2));
        assertEquals(initialSize + 1, mockCarService.getCarNumberCounter(),
                "Car count should increase by 1");
    }

    @Test
    @DisplayName("Adding customer using DI")
    void testAddCustomer() {
        int initialSize = customerStorage.getCustomers().size();
        customerStorage.addCustomer(new Customer("TestUser", 5, 5));
        assertEquals(initialSize + 1, customerStorage.getCustomers().size(),
                "Customer count should increase by 1");
    }

    @Mock
    private PedalCarFactory mockPedalCarFactory;

    @InjectMocks
    private CarService mockCarService;

    @Test
    @DisplayName("Test adding a car with a mock factory")
    void testAddCarWithMock() {
        mockCarService.addCar(mockPedalCarFactory, new PedalEngineParams(2));
        verify(mockPedalCarFactory, times(1)).createCar(any(PedalEngineParams.class),
                anyInt());
    }

    @Spy
    private CustomerStorage spyCustomerStorage;

    @Test
    @DisplayName("Test adding a customer with a spy")
    void testAddCustomerWithSpy() {
        Customer customer = new Customer("SpyUser", 5, 5);
        spyCustomerStorage.addCustomer(customer);
        verify(spyCustomerStorage, times(1)).addCustomer(customer);
        assertTrue(spyCustomerStorage.getCustomers().contains(customer), "Customer should be added");

        when(spyCustomerStorage.getCustomers()).thenReturn(new ArrayList<>());
        assertEquals(0, spyCustomerStorage.getCustomers().size(), "Customer count should be 0");
    }

    @Test
    @DisplayName("Test adding a car with a failing factory")
    void testAddCarWithException() {
        when(mockPedalCarFactory.createCar(any(PedalEngineParams.class),
                anyInt())).thenThrow(new RuntimeException("Factory failed"));
        assertThrows(RuntimeException.class,
                () -> carService.addCar(mockPedalCarFactory, new PedalEngineParams(2)));
    }

    @Spy
    private CustomerStorage mockCustomerStorage;

    @Test
    @DisplayName("Test failing to add a customer")
    void testAddCustomerFailure() {
        Customer customer = new Customer("FailUser", 5, 5);
        doThrow(new RuntimeException("Storage failed")).when(mockCustomerStorage).addCustomer(customer);
        assertThrows(RuntimeException.class, () -> mockCustomerStorage.addCustomer(customer));
        assertFalse(mockCustomerStorage.getCustomers().contains(customer), "Customer should not be added");
    }

}
