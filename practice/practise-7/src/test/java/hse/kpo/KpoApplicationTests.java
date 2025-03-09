package hse.kpo;

import hse.kpo.factories.cars.HandCarFactory;
import hse.kpo.factories.cars.PedalCarFactory;
import hse.kpo.factories.catamarans.HandCatamaranFactory;
import hse.kpo.factories.catamarans.PedalCatamaranFactory;
import hse.kpo.factories.catamarans.WheelCatamaranFactory;
import hse.kpo.observers.SalesObserver;
import hse.kpo.services.Hse;
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
	private CatamaranStorage catamaranStorage;

	@Autowired
	private CustomerStorage customerStorage;

	@Autowired
	private HseCarService hseCarService;

	@Autowired
	private HseCatamaranService hseCatamaranService;

	@Autowired
	private PedalCarFactory pedalCarFactory;

	@Autowired
	private HandCarFactory handCarFactory;

	@Autowired
	private PedalCatamaranFactory pedalCatamaranFactory;

	@Autowired
	private HandCatamaranFactory handCatamaranFactory;

	@Autowired
	private WheelCatamaranFactory wheelCatamaranFactory;

	@Autowired
	private SalesObserver salesObserver;

	@Autowired
	private Hse hse;

	@Test
	@DisplayName("Тест загрузки контекста")
	void contextLoads() {
		Assertions.assertNotNull(hse.getCarStorage());
		Assertions.assertNotNull(hse.getCustomerStorage());
	}

	@Test
	@DisplayName("Тест")
	void hseCarServiceTest() {

		hse.addCustomer("Ivan1",6,4, 300);
		hse.addCustomer("Maksim", 4, 6, 80);
		hse.addCustomer("Petya", 6, 6, 20);
		hse.addCustomer("Nikita", 6, 6, 300);

		hse.addWheelCatamaran();
//		hse.addPedalCar(6);
//		hse.addPedalCar(6);
//		hse.addHandCar();

		hse.sell();

		System.out.println(hse.generateReport());
	}

}
