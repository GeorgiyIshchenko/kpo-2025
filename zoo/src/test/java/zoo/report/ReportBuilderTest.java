package zoo.report;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zoo.services.HseZoo;

// TODO: assertions to report output

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ReportBuilderTest {

    @Autowired
    ReportBuilder reportBuilder;

    @Autowired
    HseZoo zoo;

    @Test
    @DisplayName("Получение отсчета о еде")
    public void testGetMappedFood() {
        reportBuilder.addFood(zoo.getFoodMap(), zoo.getFoodSum());
        Report report = reportBuilder.build();
        System.out.println(report);
    }

    @Test
    @DisplayName("Получение отсчета о животных")
    public void testGetPettingAnimals() {
        reportBuilder.addPettingZoo(zoo.getPettingAnimals());
        Report report = reportBuilder.build();
        System.out.println(report);
    }

    @Test
    @DisplayName("Получение отсчета о зоопарке")
    public void testGetZoo() {
        reportBuilder.addZoo(zoo.getProvider().getInventoryList());
        Report report = reportBuilder.build();
        System.out.println(report);
    }

}
