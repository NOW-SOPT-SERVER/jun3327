package org.example.Account;

import org.example.Customer.Customer;

public class Account {

    private int accountNumber;
    private double balance;
    private Customer owner;

    public Account(int accountNumber, Customer owner) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        owner.setAccount(this); //Customer의 Account 필드를 Account 객체 생성 시 setter로 초기화
    }

    public double getBalance() {
        return balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public Customer getOwner() {
        return owner;
    }

    //예금
    public void deposit(double amount) {
        if(amount < 0) {
            System.out.println("0원 이하로는 예금하실 수 없습니다.");
            System.out.println();
        } else {
            balance += amount;
            System.out.println();
        }
    }

    //출금
    public void withdraw(double amount) {
        if(amount > balance) {
            System.out.println("출금 한도 초과");
            System.out.println();
        } else {
            balance -= amount;
            System.out.println("**************************");
            System.out.println("출금 " + amount + "원 완료.");
            System.out.println("잔액: " + balance + "원");
            System.out.println("**************************");
            System.out.println();
        }
    }
}
