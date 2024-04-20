package org.example.Account;

import org.example.Customer.Customer;
import org.example.Customer.CustomerRepo;

import java.util.Random;
import java.util.Scanner;

public class AccountServiceImpl implements AccountService {

    private final CustomerRepo customerRepo;
    private final AccountRepo accountRepo;

    public AccountServiceImpl(CustomerRepo customerRepo, AccountRepo accountRepo) {
        this.customerRepo = customerRepo;
        this.accountRepo = accountRepo;
    }

    //예금서비스
    @Override
    public void deposit(Account account) {
        Scanner sc = new Scanner(System.in);
        System.out.println("-----예금-----");
        System.out.println("예금액을 입력해주세요.");
        double amount = sc.nextDouble();
        account.deposit(amount);
    }

    //출금서비스
    @Override
    public void withdraw(Account account) {
        Scanner sc = new Scanner(System.in);
        System.out.println("-----출금-----");
        System.out.println("출금액을 입력해주세요.");
        double amount = sc.nextDouble();
        account.withdraw(amount);
    }


    //송금서비스
    @Override
    public void transfer(Account fromAcc) {
        Scanner sc = new Scanner(System.in);

        while(true) {
            System.out.println("-----송금-----");
            System.out.println("계속 진행하려면 아무 키나 입력. 이전 -> 'no' 입력");
            if(sc.next().equals("no")) {
                return;
            }

            int toAccountNumber = readAccountNumber(sc);
            if(!isAccountExist(toAccountNumber)) {
                System.out.println("계좌 정보가 없습니다. 다시 입력해주세요");
                System.out.println();
                continue;
            }

            double amount = readTransferAmount(sc);
            Account toAccount = accountRepo.findByAccountNumber(toAccountNumber);

            writeTransferInfo(toAccount, amount);

            System.out.println("계속 진행하려면 아무 키나 입력. 이전 -> 'no' 입력");
            String choice = sc.next();
            if(choice.equals("no")) {
                return;
            }

            if(fromAcc.getBalance() < amount) {
                System.out.println("출금 한도 초과!");
                continue;
            }

            send(fromAcc, toAccount, amount);
            break;
        }
    }

    //계좌 개설 서비스
    @Override
    public void create(String customerId) {

        if(alarmAccountExist(customerId)) {
            return;
        }

        int accountNumber = createAccountNumber();
        Customer customer = customerRepo.findById(customerId);
        accountRepo.save(new Account(accountNumber, customer));

        writeAccountInfo(accountNumber);
    }

    private int readAccountNumber(Scanner sc) {
        System.out.println("이체하고 싶은 계좌 번호 8자리를 입력해주세요");
        int toAccountNumber = sc.nextInt();
        return toAccountNumber;
    }

    //계좌가 존재하는지 여부
    private boolean isAccountExist(int accountNumber) {
        Account toAccount = accountRepo.findByAccountNumber(accountNumber);
        if(toAccount != null) {
            return true;
        } else {
            return false;
        }
    }

    //송금액 입력받기
    private double readTransferAmount(Scanner sc) {
        System.out.println("송금액을 입력해주세요.");
        double amount = sc.nextDouble();
        return amount;
    }

    //송금 전 이체 정보 출력
    private void writeTransferInfo(Account toAccount, double amount) {
        System.out.println("**************************");
        System.out.println("-----이체 계좌 정보-----");
        System.out.println("이체 상대방 계좌 번호:" + toAccount.getAccountNumber());
        System.out.println("상대방 계좌 예금주: " + toAccount.getOwner().getName());
        System.out.println("송금액: " + amount);
        System.out.println("**************************");
    }

    //송금
    private void send(Account fromAcc, Account toAccount, double amount) {
        fromAcc.withdraw(amount);
        toAccount.deposit(amount);
        System.out.println("**************************");
        System.out.println("송금 완료!");
        System.out.println("**************************");
    }

    //계좌가 이미 존재하는지 여부 체크 후 알림 출력
    private boolean alarmAccountExist(String customerId) {
        Customer customer = customerRepo.findById(customerId);
        if(customer.getAccount() != null) {
            System.out.println("**************************");
            System.out.println("이미 계좌 정보가 존재합니다.");
            System.out.println("**************************");
            System.out.println();
            return true;
        }
        return false;
    }

    //8자리 랜덤 계좌 번호 생성
    private int createAccountNumber() {
        Random random = new Random();
        int accountNumber;
        while(true){
            accountNumber = 10000000 + random.nextInt(90000000); // 8자리 랜덤 int로 계좌 번호를 생성
            //중복 방지
            if(accountRepo.findByAccountNumber(accountNumber) != null) {
                continue;
            }
            break;
        }
        return accountNumber;
    }

    //계좌 개설 직후 출력
    private void writeAccountInfo(int accountNumber) {
        System.out.println("**************************");
        System.out.println("계좌 개설이 완료되었습니다!");
        System.out.println("계좌 번호: " + accountNumber);
        System.out.println("**************************");
        System.out.println();
    }
}
