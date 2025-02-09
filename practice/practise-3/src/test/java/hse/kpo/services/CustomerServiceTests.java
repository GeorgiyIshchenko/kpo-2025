package hse.kpo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import hse.kpo.domains.Customer;
import hse.kpo.factories.PedalCarFactory;
import hse.kpo.params.PedalEngineParams;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Customer service tests.
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CustomerServiceTests {

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
