package ru.mail.polis.homework.collections.streams.account;

import java.util.ArrayList;
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
    private final String id;
    private int balance;
    private final List<Transaction> transactions = new ArrayList<>();

    public Account(String id, int balance, List<Transaction> transactions) {
        this.id = id;
        this.balance = balance;
        this.transactions.addAll(transactions);
    }

    public String getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public long balanceBefore(Date date) {
       return transactions.stream()
                .filter(x -> x.getDate().before(date))
                .mapToLong(x -> x.getReceiver().getId().equals(id) ? x.getSum() : -x.getSum())
                .sum();
    }

}
