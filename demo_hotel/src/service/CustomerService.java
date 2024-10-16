package service;

import model.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomerService {
    private static final CustomerService SINGLETON = new CustomerService();

    public static CustomerService getSINGLETON() {
        return SINGLETON;
    }

    private final List<Customer> customers = new ArrayList<>();
    public void addCustomer(String email, String firstName, String lastName) {
        if (getCustomer(email) == null) {
            final Customer customer = new Customer(firstName, lastName, email);
            customers.add(customer);
        } else {
            throw new IllegalArgumentException("Customer is existed.");
        }
    }
    public Customer getCustomer(String customerEmail) {
        for (Customer customer : customers) {
            if (customer.getEmail().equals(customerEmail)) {
                return customer;
            }
        }
        return null;
    }

    public Collection<Customer> getAllCustomers() {
        return customers;
    }

    public void printAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customer!");
        } else {
            customers.forEach(System.out::println);
        }
    }
}
