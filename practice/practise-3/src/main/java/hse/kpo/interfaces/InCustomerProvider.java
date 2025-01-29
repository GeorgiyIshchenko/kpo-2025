package hse.kpo.interfaces;

import hse.kpo.domains.Customer;
import java.util.List;

/**
 * Interface for getting customers.
 */
public interface InCustomerProvider {

    /**
     * Метод для получения всех покупателей.
     *
     * @return - список покупателей
     */
    List<Customer> getCustomers();
}
