package ru.mail.polis.homework.collections.streams.account;

import java.util.ArrayList;
import java.util.Collections;
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
    private long sum;
    private List<Transaction> transactionList = new ArrayList<>();

    public Account() {
    }

    public Account(String id, long sum, List<Transaction> transactionList) {
        this.id = id;
        this.sum = sum;
        this.transactionList = transactionList;
    }

    public String getId() {
        return id;
    }

    public long getSum() {
        return sum;
    }

    public List<Transaction> getTransactionList() {
        return Collections.unmodifiableList(transactionList);
    }
}
