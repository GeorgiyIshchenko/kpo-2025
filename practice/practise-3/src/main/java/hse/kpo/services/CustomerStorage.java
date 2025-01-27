package hse.kpo.services;

import hse.kpo.domains.Customer;
import hse.kpo.interfaces.ICustomerProvider;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerStorage implements ICustomerProvider {
    private final List<Customer> customers = new ArrayList<>();

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
     * @param customer - покупатель.
     */
    public void addCustomer(Customer customer)
    {
        customers.add(customer); // просто добавляем покупателя в список
    }
}
