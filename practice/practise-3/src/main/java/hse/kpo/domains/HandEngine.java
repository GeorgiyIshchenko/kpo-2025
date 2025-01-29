package hse.kpo.domains;

import hse.kpo.interfaces.InEngine;
import lombok.ToString;

/**
 * Engine powered by hands.
 */
@ToString
public class HandEngine implements InEngine {
    
    /** 
     * Проверка совместимости ручного двигателя и покупателя.
     *
     * @param customer - покупатель, с которым мы сравниваем двигатель
     * @return true, если двигатель подходит покупателю
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getHandPower() > 5;
    }
}
