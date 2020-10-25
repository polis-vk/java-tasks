package ru.mail.polis.homework.collections.streams.account;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализуйте класс Account с полями:
 * id
 * список всех транзакций с аккаунта (входящие и исходящие)
 * баланс
 * 1 балл
 */
public class Account {
    private int id;
    private List<Transaction> transactions;
    private int balance;


    public Account(int id) {
        this.id = id;
        this.transactions = new ArrayList<>();
        this.balance = 0;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int sum) {
        balance += sum;
    }

    public Transaction getTransactions(int number) throws Exception {

        if (number < transactions.size())
            return transactions.get(number);

        else throw new Exception();
    }

    public int getId() {
        return id;
    }

}
