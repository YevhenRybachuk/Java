package org.example.concurrency;


import org.example.model.Account;

import java.util.concurrent.TimeUnit;

public class TransferService {

    public static void transfer(Account from, Account to, double amount) throws InterruptedException {

        if (from.getLock().tryLock(1, TimeUnit.SECONDS)) {
            try {
                if (to.getLock().tryLock(1, TimeUnit.SECONDS)) {
                    try {
                        from.withdraw(amount);
                        to.deposit(amount);

                        System.out.println(Thread.currentThread().getName() +
                                " transferred " + amount +
                                " from " + from.getId() +
                                " to " + to.getId());

                    } finally {
                        to.getLock().unlock();
                    }
                }
            } finally {
                from.getLock().unlock();
            }
        }
    }
}
