package hse.kpo.services;

import hse.kpo.domains.Customer;
import hse.kpo.interfaces.InCustomerProvider;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Класс для хранения покупателей.
 */
@Component
public class CustomerStorage implements InCustomerProvider {
    private List<Customer> customers = new ArrayList<>();

    /**
     * Метод для получения всех покупателей из списка customers.
     *
     * @return - список покупателей
     */
    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Метод для добавления покупателя.
     *
     * @param customer - покупатель
     */
    public void addCustomer(Customer customer) {
        customers.add(customer); // просто добавляем покупателя в список
    }
}
