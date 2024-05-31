package org.example.Account;

public interface AccountRepo {
    void save(Account account);
    Account findByAccountNumber(int accountNumber);
}
