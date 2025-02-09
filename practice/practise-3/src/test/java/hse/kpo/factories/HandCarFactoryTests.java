package hse.kpo.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import hse.kpo.params.EmptyEngineParams;
import hse.kpo.services.CarService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Hand car factory tests.
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class HandCarFactoryTests {

    @Mock
    private HandCarFactory mockHandCarFactory;

    @InjectMocks
    private CarService mockCarService;

    @Test
    @DisplayName("Adding car using DI")
    void testAddCar() {
        int initialSize = mockCarService.getCarNumberCounter();
        mockCarService.addCar(mockHandCarFactory, EmptyEngineParams.DEFAULT);
        assertEquals(initialSize + 1, mockCarService.getCarNumberCounter(),
                "Car count should increase by 1");
    }

}
