package ru.mail.polis.homework.collections.streams.account;

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
    private final Transaction[] transactions;
    private final long balance;

    public Account(String id, Transaction[] transactions, long balance) {
        this.id = id;
        this.transactions = transactions;
        this.balance = balance;
    }

    public String getId() { return id; }
    public Transaction[] getTransactions() { return transactions; }
    public long getBalance() { return balance; }
}
