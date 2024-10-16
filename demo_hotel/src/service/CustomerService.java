package service;

public class CustomerService {
    public static final CustomerService SINGLETON = new CustomerService();

    public static CustomerService getSingleton() {
        return SINGLETON;
    }
}
