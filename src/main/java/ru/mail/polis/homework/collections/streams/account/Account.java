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
    private long balance;
    private List<Transaction> transactions;
    Account(String id, int balance)
    {
        this.id = id;
        this.balance = balance;
    }
    public String getId() {return id;}
    public long getBalance() {return balance;}

    public List<Transaction> getTransactions() {return transactions;}

}
