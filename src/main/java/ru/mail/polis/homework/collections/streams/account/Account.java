package ru.mail.polis.homework.collections.streams.account;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Реализуйте класс Account с полями:
 * id
 * список всех транзакций с аккаунта (входящие и исходящие)
 * баланс
 * 1 балл
 */
public class Account {
    final private String id;
    private final Queue<Transaction> transactions = new PriorityQueue<>();
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

    public Queue<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction, TransactionType transactionType) {
        transactions.add(transaction);
        transactionTypeMap.put(transaction, transactionType);
    }

    public Long getBalance() {
        return balance;
    }

    public TransactionType getTransactionType(Transaction transaction) throws IllegalArgumentException {
        if (!transactionTypeMap.containsKey(transaction)) throw new IllegalArgumentException();
        return transactionTypeMap.get(transaction);
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
