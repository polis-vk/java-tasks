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
    private Long balance;
    private List<Transaction> transactionsList;

    public Account(String id, Long balance, List<Transaction> transactionsList) {
        this.id = id;
        this.balance = balance;
        this.transactionsList = transactionsList;
    }

    public String getId() {
        return id;
    }

    public List<Transaction> getTransactionsList(){
        return transactionsList;
    }
}
