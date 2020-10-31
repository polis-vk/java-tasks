package ru.mail.polis.homework.collections.streams.account;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Реализуйте класс Account с полями:
 * id
 * список всех транзакций с аккаунта (входящие и исходящие)
 * баланс
 * 1 балл
 */
public class Account {
    private final String id;
    private final Map<Transaction, TransactionType> transactionHistory = new HashMap<>();
    private long balance;

    private enum TransactionType {
        INCOMING,
        OUTGOING
    }

    public Account(String id, long balance) {
        this.id = id;
        this.balance = balance;
    }

    public Account(String id) {
        this.id = id;
        this.balance = 0L;
    }

    public String getId() {
        return id;
    }

    public Set<Transaction> getTransactionHistory() {
        return transactionHistory.keySet();
    }

    public void addTransaction(Transaction transaction, TransactionType transactionType) {
        transactionHistory.put(transaction, transactionType);
        if (transactionType == TransactionType.INCOMING) {
            balance += transaction.getCost();
        } else {
            balance -= transaction.getCost();
        }
    }

    public long getBalance() {
        return balance;
    }

    public TransactionType getTransactionType(Transaction transaction) throws IllegalArgumentException {
        if (!transactionHistory.containsKey(transaction)) {
            throw new IllegalArgumentException();
        }
        return transactionHistory.get(transaction);
    }

    public long getBalanceAtTime(long time) {
        return getBalance() -
                transactionHistory.entrySet().stream()
                        .filter(transaction ->
                                transaction.getValue() == TransactionType.INCOMING)
                        .filter(transaction ->
                                transaction.getKey().getDate().getTime() > time)
                        .map(transaction -> transaction.getKey().getCost())
                        .reduce(0L, Long::sum) +
                transactionHistory.entrySet().stream()
                        .filter(transaction ->
                                transaction.getValue() == TransactionType.OUTGOING)
                        .filter(transaction ->
                                transaction.getKey().getDate().getTime() > time)
                        .map(transaction -> transaction.getKey().getCost())
                        .reduce(0L, Long::sum);
    }
}
