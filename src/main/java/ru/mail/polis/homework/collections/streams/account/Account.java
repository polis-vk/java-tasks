package ru.mail.polis.homework.collections.streams.account;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Реализуйте класс Account с полями:
 * id
 * список всех транзакций с аккаунта (входящие и исходящие)
 * баланс
 * 1 балл
 */
public class Account {
    final private String id;
    private final List<Transaction> transactionHistory = new LinkedList<>();
    private final Map<Transaction, TransactionType> transactionTypeMap = new HashMap<>();
    private Long balance;

    private enum TransactionType {
        INCOMING,
        OUTGOING
    }

    public Account(String id, Long balance) {
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

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void addTransaction(Transaction transaction, TransactionType transactionType) {
        transactionHistory.add(transaction);
        transactionTypeMap.put(transaction, transactionType);
        if (transactionType == TransactionType.INCOMING) {
            balance += transaction.getCost();
        } else {
            balance -= transaction.getCost();
        }
    }

    public Long getBalance() {
        return balance;
    }

    public TransactionType getTransactionType(Transaction transaction) throws IllegalArgumentException {
        if (!transactionTypeMap.containsKey(transaction)) throw new IllegalArgumentException();
        return transactionTypeMap.get(transaction);
    }

    public Long getBalanceAtTime(long time) {
        return getBalance() -
                transactionHistory.stream()
                        .filter(transaction ->
                                transaction.getDate().getTime() > time &&
                                        transactionTypeMap.get(transaction) == TransactionType.INCOMING)
                        .map(Transaction::getCost)
                        .reduce(0L, Long::sum) +
                transactionHistory.stream()
                        .filter(transaction ->
                                transaction.getDate().getTime() > time &&
                                        transactionTypeMap.get(transaction) == TransactionType.OUTGOING)
                        .map(Transaction::getCost)
                        .reduce(0L, Long::sum);
    }
}
