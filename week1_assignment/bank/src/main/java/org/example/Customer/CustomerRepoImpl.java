package org.example.Customer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CustomerRepoImpl implements CustomerRepo{

    private static Map<String, Customer> repo = new ConcurrentHashMap<>();

    //메모리 repo에 저장
    @Override
    public void save(Customer customer) {
        repo.put(customer.getId(), customer);
    }

    //고유 아이디로 찾기
    @Override
    public Customer findById(String id) {
        return repo.get(id);
    }
}
