package org.example.Account;

public interface AccountService {
    void deposit(Account account);
    void withdraw(Account account);
    void transfer(Account account);
    void create(String customerId);
}
