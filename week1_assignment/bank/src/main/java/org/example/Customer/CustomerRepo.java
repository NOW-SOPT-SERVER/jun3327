package org.example.Customer;

public interface CustomerRepo {
    void save(Customer customer);
    Customer findById(String id);
}
