package org.example.service;

import org.example.model.Account;
import org.example.concurrency.TransferService;

import java.util.Random;

public class Transaction implements Runnable {

    private final Account from;
    private final Account to;
    private final double amount;
    private final Random random = new Random();

    public Transaction(Account from, Account to, double amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(random.nextInt(200));
            TransferService.transfer(from, to, amount);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " interrupted");
        }
    }
}

