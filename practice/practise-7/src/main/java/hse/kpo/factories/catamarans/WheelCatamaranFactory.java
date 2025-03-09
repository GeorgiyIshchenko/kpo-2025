package hse.kpo.factories.catamarans;

import hse.kpo.adapters.WheelCatamaran;
import hse.kpo.domains.Car;
import hse.kpo.domains.Catamaran;
import org.springframework.stereotype.Component;

@Component
public class WheelCatamaranFactory {

    public Car create(Catamaran catamaran) {
        return new WheelCatamaran(catamaran);
    }
}
