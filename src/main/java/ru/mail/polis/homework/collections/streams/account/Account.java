package ru.mail.polis.homework.collections.streams.account;

import java.util.Arrays;
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
    private long balance;

    public Account(String id, List<Transaction> transactions, long balance) {
        this.id = id;
        this.transactions = transactions;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public long getBalance() {
        return balance;
    }

    public long getBalanceBeforeDate(long t) {
        return balance +
                getIncomingTransactions().stream()
                        .filter(transaction -> transaction.getDate().getTime() >= t)
                        .map(Transaction::getSum)
                        .reduce(0L, Long::sum) -
                getOutgoingTransactions().stream()
                        .filter(transaction -> transaction.getDate().getTime() >= t)
                        .map(Transaction::getSum)
                        .reduce(0L, Long::sum);
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<Transaction> getIncomingTransactions() {
        return transactions.stream()
                .filter(e -> !e.getReceiverId().equals(this.id))
                .collect(Collectors.toList());
    }

    public List<Transaction> getOutgoingTransactions() {
        return transactions.stream()
                .filter(e -> !e.getSenderId().equals(this.id))
                .collect(Collectors.toList());
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void addTransactions(Transaction... newTransactions) {
        transactions.addAll(Arrays.asList(newTransactions));
    }

}
