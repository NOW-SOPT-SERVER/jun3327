package org.example;

import org.example.Account.AccountRepo;
import org.example.Account.AccountRepoImpl;
import org.example.Account.AccountService;
import org.example.Account.AccountServiceImpl;
import org.example.Customer.CustomerRepo;
import org.example.Customer.CustomerRepoImpl;
import org.example.Customer.CustomerService;
import org.example.Customer.CustomerServiceImpl;
import org.example.Menu.MenuService;
import org.example.Menu.MenuServiceImpl;

//DI(제어의 역전)를 위해 구성한 Config 클래스
public class Configuration {

    public MenuService menuService() {
        return new MenuServiceImpl(customerService(), customerRepo(), accountService());
    }

    public CustomerRepo customerRepo() {
        return new CustomerRepoImpl();
    }

    public AccountRepo accountRepo() {
        return new AccountRepoImpl();
    }

    public CustomerService customerService() {
        return new CustomerServiceImpl(customerRepo());
    }

    public AccountService accountService() {
        return new AccountServiceImpl(customerRepo(), accountRepo());
    }
}
