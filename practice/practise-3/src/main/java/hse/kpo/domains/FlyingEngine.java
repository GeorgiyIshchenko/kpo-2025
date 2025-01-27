package hse.kpo.domains;

import lombok.ToString;
import hse.kpo.interfaces.IEngine;

@ToString
public class FlyingEngine implements IEngine{

    /** 
     * Проверка совместимости летающего двигателя и покупателя.
     * 
     * @param customer - покупатель, с которым мы сравниваем двигатель
     * @return true, если двигатель подходит покупателю
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getIQ() > 300;
    } 
    
}
