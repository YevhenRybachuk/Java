package org.example.service;

import org.example.model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class BankService {

    private final ReentrantLock serviceLock = new ReentrantLock();
    private final List<Account> accounts = new ArrayList<>();

    public void addAccount(Account account) {
        serviceLock.lock();
        try {
            accounts.add(account);
        } finally {
            serviceLock.unlock();
        }
    }

    public List<Account> getAccounts() {
        serviceLock.lock();
        try {
            return new ArrayList<>(accounts);
        } finally {
            serviceLock.unlock();
        }
    }
}