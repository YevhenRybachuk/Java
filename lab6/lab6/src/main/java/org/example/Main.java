package org.example;

import org.example.model.Account;
import org.example.model.Client;
import org.example.service.BankService;
import org.example.service.Transaction;
import org.example.util.IdGenerator;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BankService bankService = new BankService();

        Client ivan = new Client("Іван");
        Account ivanAcc = new Account(IdGenerator.nextId(), 1000.0);
        ivan.addAccount(ivanAcc);
        bankService.addAccount(ivanAcc);

        Client olena = new Client("Олена");
        Account olenaAcc = new Account(IdGenerator.nextId(), 1000.0);
        olena.addAccount(olenaAcc);
        bankService.addAccount(olenaAcc);

        System.out.println("Старт системи:");
        System.out.println("Клієнт " + ivan.getName() + ", Рахунок №" + ivanAcc.getId() + ": " + ivanAcc.getBalance());
        System.out.println("Клієнт " + olena.getName() + ", Рахунок №" + olenaAcc.getId() + ": " + olenaAcc.getBalance());

        Thread t1 = new Thread(new Transaction(ivanAcc, olenaAcc, 250.0), "Потік-1");
        Thread t2 = new Thread(new Transaction(olenaAcc, ivanAcc, 100.0), "Потік-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("\nРЕЗУЛЬТАТИ ПІСЛЯ ТРАНЗАКЦІЙ");

        System.out.println("Баланс: " + ivan.getName() + " " +
                ivan.getAccounts().get(0).getBalance());
        System.out.println("Баланс: " + olena.getName() + " " +
                olena.getAccounts().get(0).getBalance());
    }
}