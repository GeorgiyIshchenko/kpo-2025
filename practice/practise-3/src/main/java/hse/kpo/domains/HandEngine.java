package hse.kpo.domains;

import lombok.ToString;
import hse.kpo.interfaces.IEngine;

@ToString
public class HandEngine implements IEngine {
    
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
