package org.example.Customer;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepo customerRepo;

    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    //회원 가입 서비스
    @Override
    public void join() {

        Scanner sc = new Scanner(System.in);
        System.out.println("-----회원가입----");
        System.out.println("계속 진행하려면 아무 키나 입력. 이전 -> 'no' 입력");
        String choice = sc.nextLine();
        if(choice.equals("no")) {
            return;
        }

        Map<String, String> info = readCustomerInfo(sc);
        createAndSaveCustomer(info);
    }

    //로그인 서비스
    @Override
    public String login() {
        while(true) {
            Scanner sc = new Scanner(System.in);

            System.out.println("-----로그인-----");
            System.out.println("계속 진행하려면 아무 키나 입력. 이전 -> 'no' 입력");
            String choice = sc.nextLine();
            if(choice.equals("no")) {
                return "";
            }

            Map<String, String> loginData = readIdAndPw(sc);
            if(loginData == null) {
                continue;
            }

            String customerId = verify(loginData);
            if(customerId != null) {
                return customerId;
            }
        }
    }

    //이름, 아이디, 비밀번호 입력받아서 리턴
    private Map<String, String> readCustomerInfo(Scanner sc) {
        Map<String, String> data = new HashMap<>();
        String name;
        String id;
        String passWord;

        System.out.println("이름: ");
        name = sc.nextLine();

        System.out.println("아이디: ");
        id = sc.nextLine();

        System.out.println("비밀번호: ");
        passWord = sc.nextLine();

        data.put("name", name);
        data.put("id", id);
        data.put("passWord", passWord);

        return data;
    }

    //아이디 중복 방지
    private boolean isDuplicateCustomer(String id) {

        Customer findCustomer = customerRepo.findById(id);

        if(findCustomer != null) {
            return true;
        } else {
            return false;
        }
    }

    //Customer 객체 생성 후 메모리 db에 저장
    private void createAndSaveCustomer(Map<String, String> info) {

        if(isDuplicateCustomer(info.get("id"))) {
            System.out.println("**************************");
            System.out.println("이미 존재하는 회원 정보 입니다.");
            System.out.println("**************************");
            System.out.println();
            return;
        }

        Customer customer = Customer.create(info.get("name"), info.get("id"), info.get("passWord"));

        customerRepo.save(customer);

        System.out.println("**************************");
        System.out.println("회원 가입 성공!");
        System.out.println("이름: " + customer.getName());
        System.out.println("아이디: " + customer.getId());
        System.out.println("비밀번호: " + customer.getPassWord());
        System.out.println("**************************");
        System.out.println();
    }

    //입력 받은 아이디가 메모리 db에 존재 여부 확인
    private boolean isIdExist(String id) {
        Customer customer = customerRepo.findById(id);
        if(customer == null) {
            System.out.println("**************************");
            System.out.println("존재하지 않는 아이디입니다.");
            System.out.println("**************************");
            System.out.println();
            return false;
        }
        return true;
    }

    //사용자로부터 아이디, 비밀번호 입력받기
    private Map<String, String> readIdAndPw(Scanner sc) {

        System.out.println("아이디: ");
        String id = sc.nextLine();

        if(!isIdExist(id)) {
            return null;
        }

        System.out.println("비밀번호: ");
        String passWord = sc.nextLine();

        Map<String, String> loginData = new HashMap<>();
        loginData.put("id", id);
        loginData.put("passWord", passWord);
        return loginData;
    }

    //비밀번호 검증
    private String verify(Map<String, String> loginData) {

        Customer customer = customerRepo.findById(loginData.get("id"));
        if(loginData.get("passWord").equals(customer.getPassWord())) {
            System.out.println("**************************");
            System.out.println("로그인 성공!");
            System.out.println("**************************");
            System.out.println();
            return customer.getId();
        } else {
            System.out.println("**************************");
            System.out.println("비밀번호가 틀렸습니다!");
            System.out.println("**************************");
            System.out.println();
            return null;
        }
    }
}
