package ru.mail.polis.homework.collections.streams.account;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализуйте класс Account с полями:
 * id
 * список всех транзакций с аккаунта (входящие и исходящие)
 * баланс
 * 1 балл
 */
public class Account {
    private final String id;
    private final List<Transaction> transactions;
    private Long balance;

    public Account(String id, List<Transaction> transactions, Long balance) {
        this.id = id;
        this.transactions = transactions;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public List<Transaction> getIncomingTransaction() {
        return transactions.stream()
                .filter(e -> !e.getIncomingId().equals(this.id))
                .collect(Collectors.toList());
    }

    public List<Transaction> getOutgoingTransaction() {
        return transactions.stream()
                .filter(e -> !e.getOutgoingId().equals(this.id))
                .collect(Collectors.toList());
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Long getBalance(Long time) {
        Long out = balance;
        out += getIncomingTransaction().stream()
                .filter(e -> e.getData().getTime() >= time)
                .map(Transaction::getSum)
                .reduce(0L, Long::sum);
        out -= getOutgoingTransaction().stream()
                .filter(e -> e.getData().getTime() >= time)
                .map(Transaction::getSum)
                .reduce(0L, Long::sum);
        return out;
    }

    public void setBalance(Long sum) {
        this.balance = sum;
    }
}
