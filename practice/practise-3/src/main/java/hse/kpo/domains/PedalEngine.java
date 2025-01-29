package hse.kpo.domains;

import hse.kpo.interfaces.InEngine;
import lombok.Getter;
import lombok.ToString;

/**
 * Engine powered by legs.
 */
@ToString
@Getter
public class PedalEngine implements InEngine {
    private final int size;

    /** 
     * Проверка совместимости педального двигателя и покупателя.
     *
     * @param customer - покупатель, с которым мы сравниваем двигатель
     * @return true, если двигатель подходит покупателю
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getLegPower() > 5;
    }

    public PedalEngine(int size) {
        this.size = size;
    }
}
