package hse.kpo.adapters;

import hse.kpo.domains.Car;
import hse.kpo.domains.Catamaran;
import hse.kpo.interfaces.Engine;
import lombok.Getter;
import lombok.ToString;

@ToString
public class WheelCatamaran extends Car {

    Catamaran catamaran;

    public WheelCatamaran(Catamaran catamaran) {
        super(catamaran.getVin(), catamaran.getEngine());
        this.catamaran = catamaran;
    }

}
