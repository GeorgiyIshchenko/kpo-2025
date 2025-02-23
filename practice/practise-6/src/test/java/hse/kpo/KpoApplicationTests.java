package hse.kpo;

import hse.kpo.builders.ReportBuilder;
import hse.kpo.domains.Customer;
import hse.kpo.factories.cars.HandCarFactory;
import hse.kpo.factories.cars.PedalCarFactory;
import hse.kpo.factories.catamarans.HandCatamaranFactory;
import hse.kpo.factories.catamarans.PedalCatamaranFactory;
import hse.kpo.observers.ReportSalesObserver;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import hse.kpo.services.HseCatamaranService;
import hse.kpo.storages.CarStorage;
import hse.kpo.storages.CatamaranStorage;
import hse.kpo.storages.CustomerStorage;
import hse.kpo.services.HseCarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KpoApplicationTests {

	@Autowired
	private CarStorage carStorage;

	@Autowired
	private CustomerStorage customerStorage;

	@Autowired
	private HseCarService hseCarService;

	@Autowired
	private PedalCarFactory pedalCarFactory;

	@Autowired
	private HandCarFactory handCarFactory;

	@Autowired
	private CatamaranStorage shipService;

	@Autowired
	private CustomerStorage shipCustomerStorage;

	@Autowired
	private HseCatamaranService hseShipService;

	@Autowired
	private PedalCatamaranFactory pedalShipFactory;

	@Autowired
	private HandCatamaranFactory handShipFactory;

	@Autowired
	private ReportSalesObserver reportSalesObserver;

	@Test
	@DisplayName("Тест загрузки контекста")
	void contextLoads() {
		Assertions.assertNotNull(carStorage);
		Assertions.assertNotNull(customerStorage);
		Assertions.assertNotNull(hseCarService);
	}

	@Test
	@DisplayName("Тест загрузки контекста")
	void hseCarServiceTest() {
		hseCarService.addObserver(reportSalesObserver);

		customerStorage.addCustomer(Customer.builder().name("Ivan1").legPower(6).handPower(4).build());
		customerStorage.addCustomer(Customer.builder().name("Maksim").legPower(4).handPower(6).build());
		customerStorage.addCustomer(Customer.builder().name("Petya").legPower(6).handPower(6).build());
		customerStorage.addCustomer(Customer.builder().name("Nikita").legPower(4).handPower(4).build());

		carStorage.addCar(pedalCarFactory, new PedalEngineParams(6));
		carStorage.addCar(pedalCarFactory, new PedalEngineParams(6));

		carStorage.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
		carStorage.addCar(handCarFactory, EmptyEngineParams.DEFAULT);

		hseCarService.sellCars();

		System.out.println(reportSalesObserver.buildReport().toString());
	}

	@Test
	@DisplayName("Тест продажи кораблей")
	void hseShipServiceTest() {
		hseShipService.addObserver(reportSalesObserver);

		shipCustomerStorage.addCustomer(Customer.builder().name("Ivan1").legPower(6).handPower(4).build());
		shipCustomerStorage.addCustomer(Customer.builder().name("Maksim").legPower(4).handPower(6).build());
		shipCustomerStorage.addCustomer(Customer.builder().name("Petya").legPower(6).handPower(6).build());
		shipCustomerStorage.addCustomer(Customer.builder().name("Nikita").legPower(4).handPower(4).build());

		shipService.addCatamaran(pedalShipFactory, new PedalEngineParams(6));
		shipService.addCatamaran(pedalShipFactory, new PedalEngineParams(6));

		shipService.addCatamaran(handShipFactory, EmptyEngineParams.DEFAULT);
		shipService.addCatamaran(handShipFactory, EmptyEngineParams.DEFAULT);

		hseShipService.sellCatamarans();

		System.out.println(reportSalesObserver.buildReport().toString());
	}

}
