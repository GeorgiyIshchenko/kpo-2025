package hse.kpo;

import hse.kpo.domains.Car;
import hse.kpo.domains.Customer;
import hse.kpo.factories.HandCarFactory;
import hse.kpo.factories.HandShipFactory;
import hse.kpo.factories.PedalCarFactory;
import hse.kpo.factories.PedalShipFactory;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import hse.kpo.report.ReportBuilder;
import hse.kpo.services.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KpoApplicationTests {

	@Autowired
	private CarService carService;

	@Autowired
	private CustomerStorage carCustomerStorage;

	@Autowired
	private HseCarService hseCarService;

	@Autowired
	private PedalCarFactory pedalCarFactory;

	@Autowired
	private HandCarFactory handCarFactory;

	@Autowired
	private ShipService shipService;

	@Autowired
	private CustomerStorage shipCustomerStorage;

	@Autowired
	private HseShipService hseShipService;

	@Autowired
	private PedalShipFactory pedalShipFactory;

	@Autowired
	private HandShipFactory handShipFactory;

	@Test
	@DisplayName("Тест загрузки контекста")
	void contextLoads() {
		Assertions.assertNotNull(carService);
		Assertions.assertNotNull(carCustomerStorage);
		Assertions.assertNotNull(hseCarService);
	}

	@Test
	@DisplayName("Тест продажи авто")
	void hseCarServiceTest() {
		carCustomerStorage.addCustomer(new Customer("Ivan1",6,4));
		carCustomerStorage.addCustomer(new Customer("Maksim",4,6));
		carCustomerStorage.addCustomer(new Customer("Petya",6,6));
		carCustomerStorage.addCustomer(new Customer("Nikita",4,4));
		
		carService.addCar(pedalCarFactory, new PedalEngineParams(6));
		carService.addCar(pedalCarFactory, new PedalEngineParams(6));

		carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
		carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);

		carCustomerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);

		var reportBuilder = new ReportBuilder()
				.addOperation("Инициализация системы")
				.addCustomers(carCustomerStorage.getCustomers())
				.addCars(carService.getCars());

		hseCarService.sellCars();

		var report = reportBuilder
				.addOperation("Продажа автомобилей")
				.addCustomers(carCustomerStorage.getCustomers())
				.build();

		System.out.println(report.toString());

		carCustomerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);
	}

	@Test
	@DisplayName("Тест продажи кораблей")
	void hseShipServiceTest() {
		shipCustomerStorage.addCustomer(new Customer("Ivan1",6,4));
		shipCustomerStorage.addCustomer(new Customer("Maksim",4,6));
		shipCustomerStorage.addCustomer(new Customer("Petya",6,6));
		shipCustomerStorage.addCustomer(new Customer("Nikita",4,4));

		shipService.addShip(pedalShipFactory, new PedalEngineParams(6));
		shipService.addShip(pedalShipFactory, new PedalEngineParams(6));

		shipService.addShip(handShipFactory, EmptyEngineParams.DEFAULT);
		shipService.addShip(handShipFactory, EmptyEngineParams.DEFAULT);

		shipCustomerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);

		var reportBuilder = new ReportBuilder()
				.addOperation("Инициализация системы")
				.addCustomers(shipCustomerStorage.getCustomers())
				.addShips(shipService.getShips());

		hseShipService.sellShips();

		var report = reportBuilder
				.addOperation("Продажа кораблей")
				.addCustomers(shipCustomerStorage.getCustomers())
				.build();

		System.out.println(report.toString());

		shipCustomerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);
	}

}
