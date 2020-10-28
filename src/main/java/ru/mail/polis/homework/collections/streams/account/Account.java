package ru.mail.polis.homework.collections.streams.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Реализуйте класс Account с полями:
 * id
 * список всех транзакций с аккаунта (входящие и исходящие)
 * баланс
 * 1 балл
 */
public class Account {
    private final long id;
    private final List<Transaction> outgoingTransactions = new ArrayList<>();
    private final List<Transaction> incomingTransactions = new ArrayList<>();
    private static long idCounter = 1;
    private long lastOutgoingTransactionId = -1;
    private long lastIncomingTransactionId = -1;
    
    
    public Account() {
        this.id = idCounter++;
    }
    
    public void addTransaction(Transaction transaction) {
        long transactionId = transaction.getId();
        if (transaction.getDestAcc() == this && lastIncomingTransactionId < transactionId) {
            lastIncomingTransactionId = transactionId;
            incomingTransactions.add(transaction);
        }
        if (transaction.getSrcAcc() == this && lastOutgoingTransactionId < transactionId) {
            lastOutgoingTransactionId = transactionId;
            outgoingTransactions.add(transaction);
        }
    }
    
    public long getBalanceOnDate(Date date) {
        return incomingTransactions.stream()
                .filter(transaction -> transaction.getDate().compareTo(date) <= 0)
                .reduce(0L, (sum, transaction) -> sum + transaction.getSum(), (sum, transaction) -> sum)
                - outgoingTransactions.stream()
                .filter(transaction -> transaction.getDate().compareTo(date) <= 0)
                .reduce(0L, (sum, transaction) -> sum + transaction.getSum(), (sum, transaction) -> sum);
    }
    
    public long getId() {
        return id;
    }
}
