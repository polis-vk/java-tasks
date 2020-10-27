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
    private String id;
    private List<Transaction> transactions;
    private Long balance;

    public String getId() { return id; }
    public List<Transaction> getTransactions() { return transactions; }
    public Long getBalance() { return balance; }
}
