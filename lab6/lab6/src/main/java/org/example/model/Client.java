package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Client {

    private final String name;
    private final ReentrantLock clientLock = new ReentrantLock();
    private final List<Account> accounts = new ArrayList<>();

    public Client(String name) {
        this.name = name;
    }

    public void addAccount(Account account) {
        clientLock.lock();
        try {
            accounts.add(account);
        } finally {
            clientLock.unlock();
        }
    }

    public List<Account> getAccounts() {
        clientLock.lock();
        try {
            return new ArrayList<>(accounts);
        } finally {
            clientLock.unlock();
        }
    }

    public String getName() {
        return name;
    }
}