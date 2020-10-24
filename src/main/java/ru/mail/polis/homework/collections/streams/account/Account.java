package ru.mail.polis.homework.collections.streams.account;

import java.util.Date;
import java.util.List;

/**
 * Реализуйте класс Account с полями:
 * id
 * список всех транзакций с аккаунта (входящие и исходящие)
 * баланс
 * 1 балл
 */

public class Account {

    private static long currentId = 1;

    private final long id;
    private long balance;
    private List<Transaction> transactions;

    public Account() {
        this.id = currentId++;
        this.balance = 0;
    }

    public Account(long balance, List<Transaction> transactions) {
        this.id = currentId++;
        this.balance = balance;
        this.transactions = transactions;
    }

    public Account(Long balance) {
        this.id = currentId++;
        this.balance = balance;
    }

    public void newTransaction(Date date, Account outgoing,
                               Account incoming, long sum) {

        transactions.add(new Transaction(date, outgoing, incoming, sum));
        if (outgoing.getId() == this.id) {
            this.balance -= sum;
        } else if (incoming.getId() == this.id) {
            this.balance += sum;
        }
    }

    public Long getId() {
        return id;
    }

    public long getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
