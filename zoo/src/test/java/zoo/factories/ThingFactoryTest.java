package zoo.factories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zoo.domains.Computer;
import zoo.domains.Table;
import zoo.params.ThingParams;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ThingFactoryTest {

    @Autowired
    private ThingFactory thingFactory;

    @Test
    @DisplayName("Создание стола с помощью фабрики")
    public void testCreateTiger() {
        Table table = (Table) thingFactory.createInstance("Table", new ThingParams(), 100);
        assertEquals(100, table.getNumber());

        table.setNumber(300);
        assertEquals(300, table.getNumber());
    }

    @Test
    @DisplayName("Создание компьютера с помощью фабрики")
    public void testCreateWolf() {
        Computer wolf = (Computer) thingFactory.createInstance("Computer", new ThingParams(), 200);
        assertEquals(200, wolf.getNumber());
    }

}
