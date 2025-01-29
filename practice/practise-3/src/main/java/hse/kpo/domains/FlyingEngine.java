package hse.kpo.domains;

import hse.kpo.interfaces.InEngine;
import lombok.ToString;

/**
 * Проверка совместимости летающего двигателя и покупателя.
 */
@ToString
public class FlyingEngine implements InEngine {

    /** 
     * Проверка совместимости летающего двигателя и покупателя.
     *
     * @param customer - покупатель, с которым мы сравниваем двигатель
     * @return true, если двигатель подходит покупателю
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getIq() > 300;
    } 
    
}
