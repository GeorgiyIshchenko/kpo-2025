package hse.kpo;

import hse.kpo.enums.ReportFormat;
import hse.kpo.enums.SerializeFormat;
import hse.kpo.facade.Hse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@SpringBootTest
class KpoApplicationTests {

	@Autowired
	private Hse hse;

	@Test
	@DisplayName("Тест загрузки контекста")
	void hseCarServiceTest() {

		hse.addCustomer("Ivan1",6,4, 150);
		hse.addCustomer("Maksim", 4, 6, 80);
		hse.addCustomer("Petya", 6, 6, 20);
		hse.addCustomer("Nikita", 4, 4, 300);

		hse.addPedalCar(6);
		hse.addPedalCar(6);
		hse.addWheelCatamaran();

		hse.addHandCar();
		hse.addHandCar();

		// Экспорт в файл в формате XML
		try (FileWriter fileWriter = new FileWriter("transports.xml")) {
			hse.exportTransport(SerializeFormat.XML, fileWriter);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// Экспорт в файл в формате CSV
		try (FileWriter fileWriter = new FileWriter("transports.csv")) {
			hse.exportTransport(SerializeFormat.CSV, fileWriter);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		hse.sell();

		System.out.println(hse.generateReport());

		// Экспорт в консоль в формате Markdown
		hse.exportReport(ReportFormat.MARKDOWN, new PrintWriter(System.out));
		// Экспорт в файл в формате MARKDOWN
		try (FileWriter fileWriter = new FileWriter("report.MD")) {
			hse.exportReport(ReportFormat.MARKDOWN, fileWriter);
		} catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Экспорт в файл в формате JSON
		try (FileWriter fileWriter = new FileWriter("report.json")) {
			hse.exportReport(ReportFormat.JSON, fileWriter);
		} catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
