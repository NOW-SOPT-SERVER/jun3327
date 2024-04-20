package org.example.Customer;

import org.example.Account.Account;

public class Customer {

    private String name;
    private String id;
    private String passWord;
    private Account account;

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassWord() {
        return passWord;
    }

    public Account getAccount() {
        return account;
    }

    //정적 팩토리 메소드로 객체 생성
    public static Customer create(String name, String id, String passWord) {
        Customer customer = new Customer();
        customer.name = name;
        customer.id = id;
        customer.passWord = passWord;

        return customer;
    }
}
