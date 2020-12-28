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
    private long curId = 0;
    private long id;
    private long sum;
    private List<Transaction> transactionList = new ArrayList<>();

    public Account() { }

    public Account(long sum, List<Transaction> transactionList) {
        this.id = curId++;
        this.sum = sum;
        this.transactionList = transactionList;
    }

    public Account(long sum) {
        this.id = curId++;
        this.sum = sum;
    }

    public long getId() {
        return id;
    }

    public long getSum() {
        return sum;
    }

    public List<Transaction> getTransactionList() {
        return Collections.unmodifiableList(transactionList);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSum(long a) {
        this.sum += a;
    }

    public void setTransaction(List<Transaction> transactionList) {
        this.transactionList = transactionList;
        transactionList.stream().map(i -> this.sum += i.getSum());
    }
}
