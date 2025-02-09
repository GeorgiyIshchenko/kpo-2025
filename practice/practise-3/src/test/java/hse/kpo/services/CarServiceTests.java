package hse.kpo.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import hse.kpo.factories.PedalCarFactory;
import hse.kpo.params.PedalEngineParams;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Car service tests.
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CarServiceTests {

    @Autowired
    private CarService carService;

    @Mock
    private PedalCarFactory mockPedalCarFactory;

    @Test
    @DisplayName("Test adding a car with a failing factory")
    void testAddCarWithException() {
        when(mockPedalCarFactory.createCar(any(PedalEngineParams.class),
                anyInt())).thenThrow(new RuntimeException("Factory failed"));
        assertThrows(RuntimeException.class,
                () -> carService.addCar(mockPedalCarFactory, new PedalEngineParams(2)));
    }

}
