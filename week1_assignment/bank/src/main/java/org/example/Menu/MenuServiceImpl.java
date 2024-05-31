package org.example.Menu;

import org.example.Account.Account;
import org.example.Account.AccountService;
import org.example.Customer.Customer;
import org.example.Customer.CustomerRepo;
import org.example.Customer.CustomerService;

import java.util.Scanner;

public class MenuServiceImpl implements MenuService{

    private final CustomerService customerService;
    private final CustomerRepo customerRepo;
    private final AccountService accountService;

    public MenuServiceImpl (CustomerService customerService, CustomerRepo customerRepo, AccountService accountService) {
        this.customerService = customerService;
        this.customerRepo = customerRepo;
        this.accountService = accountService;
    }

    @Override
    public String beforeLogin() {
        while(true) {
            Scanner sc = new Scanner(System.in);
            beforeLoginMenu();
            int choice;
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                if((choice < 1) || (choice > 3)) {
                    System.out.println("1~3번 사이에서 선택해주세요");
                    System.out.println();
                    continue;
                }
            } else {
                System.out.println("알맞은 번호를 입력해주세요!");
                System.out.println();
                continue;
            }

            if(choice == 1) {
                customerService.join();
            } else if(choice == 2) {
                String customerId = customerService.login();

                //로그인이 완료됐다면 customerId 반환하면서 예금 메뉴로 이동
                if(!customerId.isEmpty()){
                    return customerId;
                }
            } else if (choice == 3) {
                System.exit(0);
            }
        }
    }

    @Override
    public void afterLogin(String customerId) {

        while(true) {
            Scanner sc = new Scanner(System.in);
            Customer findCustomer = customerRepo.findById(customerId);
            Account account = findCustomer.getAccount();

            writeAccountInfo(findCustomer, account);
            afterLoginMenu();

            int choice;
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                if((choice < 1) || (choice > 5)) {
                    System.out.println("1~5번 사이에서 선택해주세요");
                    System.out.println();
                    continue;
                }
            } else {
                System.out.println("알맞은 번호를 입력해주세요!");
                System.out.println();
                continue;
            }

            if(account == null && (choice == 2 || choice == 3 || choice == 4)) {
                System.out.println("**************************");
                System.out.println("계좌 개설을 먼저 해주세요!");
                System.out.println("**************************");
                continue;
            }

            if(choice == 5) {
                return;
            } else if(choice == 1) {
                accountService.create(customerId);
            } else if (choice == 2) {
                accountService.deposit(account);
            } else if (choice == 3) {
                accountService.withdraw(account);
            } else if (choice == 4) {
                accountService.transfer(account);
            }
        }
    }

    public void beforeLoginMenu() {
        System.out.println("------SOPT BANK------");
        System.out.println("--------MENU---------");
        System.out.println("1. 회원가입");
        System.out.println("2. 로그인");
        System.out.println("3. 프로그램 종료");
        System.out.println("메뉴를 선택하려면 메뉴에 해당하는 숫자를 입력하세요.");
    }

    public void afterLoginMenu() {
        System.out.println("------SOPT BANK------");
        System.out.println("--------MENU---------");
        System.out.println("1. 계좌 개설");
        System.out.println("2. 예금");
        System.out.println("3. 출금");
        System.out.println("4. 송금");
        System.out.println("5. 이전 메뉴 보기(자동 로그아웃)");
        System.out.println("메뉴를 선택하려면 메뉴에 해당하는 숫자를 입력하세요.");
    }

    private void writeAccountInfo(Customer customer, Account account) {
        System.out.println("안녕하세요 " + customer.getName() + "님");
        if(account ==null) {
            System.out.println("아직 계좌가 없습니다! 1번 메뉴를 눌러 계좌를 개설해주세요.");
            System.out.println();
        } else {
            System.out.println("사용중이신 계좌 번호: " + account.getAccountNumber());
            System.out.println("잔액: " + account.getBalance() + "원");
        }
    }
}
