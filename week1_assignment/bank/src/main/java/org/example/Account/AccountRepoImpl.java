package org.example.Account;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccountRepoImpl implements AccountRepo{

    private static Map<Integer, Account> repo = new ConcurrentHashMap<>();

    //메모리 repo에 저장
    @Override
    public void save(Account account) {
        repo.put(account.getAccountNumber(), account);
    }

    //고유한 계좌 번호로 찾기
    @Override
    public Account findByAccountNumber(int accountNumber) {
        return repo.get(accountNumber);
    }
}
