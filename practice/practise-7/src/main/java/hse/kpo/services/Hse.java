package hse.kpo.services;

import hse.kpo.domains.*;
import hse.kpo.factories.cars.HandCarFactory;
import hse.kpo.factories.cars.LevitationCarFactory;
import hse.kpo.factories.cars.PedalCarFactory;
import hse.kpo.factories.catamarans.HandCatamaranFactory;
import hse.kpo.factories.catamarans.LevitationCatamaranFactory;
import hse.kpo.factories.catamarans.PedalCatamaranFactory;
import hse.kpo.factories.catamarans.WheelCatamaranFactory;
import hse.kpo.observers.ReportSalesObserver;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import hse.kpo.storages.CarStorage;
import hse.kpo.storages.CatamaranStorage;
import hse.kpo.storages.CustomerStorage;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class Hse {
    ReportSalesObserver reportSalesObserver;
    HseCarService hseCarService;
    HseCatamaranService hseCatamaranService;
    @Getter
    CustomerStorage customerStorage;
    @Getter
    CarStorage carStorage;
    @Getter
    CatamaranStorage catamaranStorage;
    PedalCarFactory pedalCarFactory;
    HandCarFactory handCarFactory;
    LevitationCarFactory levitationCarFactory;
    PedalCatamaranFactory pedalCatamaranFactory;
    HandCatamaranFactory handCatamaranFactory;
    LevitationCatamaranFactory levitationCatamaranFactory;
    WheelCatamaranFactory wheelCatamaranFactory;

    public Hse(ReportSalesObserver reportSalesObserver, HseCarService hseCarService, HseCatamaranService hseCatamaranService, CustomerStorage customerStorage, CarStorage carStorage, PedalCarFactory pedalCarFactory, HandCarFactory handCarFactory, WheelCatamaranFactory wheelCatamaranFactory) {
        this.reportSalesObserver = reportSalesObserver;
        this.hseCarService = hseCarService;
        this.hseCatamaranService = hseCatamaranService;
        this.customerStorage = customerStorage;
        this.carStorage = carStorage;
        this.pedalCarFactory = pedalCarFactory;
        this.handCarFactory = handCarFactory;
        this.wheelCatamaranFactory = wheelCatamaranFactory;
    }

    @PostConstruct
    public void init() {
        this.hseCarService.addObserver(this.reportSalesObserver);
        this.hseCatamaranService.addObserver(this.reportSalesObserver);
    }

    public void addCustomer(String name, int legPower, int handPower, int iq) {
        customerStorage.addCustomer(Customer.builder().name(name).legPower(legPower).handPower(handPower).iq(iq).build());
    }

    public void addPedalCar(int pedalSize) {
        carStorage.addCar(pedalCarFactory, new PedalEngineParams(pedalSize));
    }

    public void addHandCar() {
        carStorage.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
    }

    public void addWheelCatamaran(){
        carStorage.getCars().add(wheelCatamaranFactory.create(new Catamaran(carStorage.getCars().size() + 1000, new LevitationEngine())));
    }

    public void sell() {
        hseCarService.sellCars();
        hseCatamaranService.sellCatamarans();
    }

    public Report generateReport() {
        return reportSalesObserver.buildReport();
    }
}
