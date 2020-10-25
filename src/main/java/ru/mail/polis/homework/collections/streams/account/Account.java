package ru.mail.polis.homework.collections.streams.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

/**
 * Реализуйте класс Account с полями:
 * id
 * список всех транзакций с аккаунта (входящие и исходящие)
 * баланс
 * 1 балл
 */
public class Account {
    private final String id;
    private final List<Transaction> inTransactions = new ArrayList<>();   //входящие транзакции
    private final List<Transaction> outTransactions = new ArrayList<>();  //исходящие транзакции
    private long balance;
    private final long beginBalance;

    Account(String id, List<Transaction> transactions, long balance) {
        this.id = id;
        this.balance = balance;
        this.beginBalance = balance;
    }

    public String getId() {
        return id;
    }

    private void incomingTransactions(Transaction transaction) {
        this.balance += transaction.getSum();
    }

    private void outgoingTransaction(Transaction transaction) {
        this.balance -= transaction.getSum();
    }

    public void addIncomingTransaction(Transaction transaction) {
        incomingTransactions(transaction);
        inTransactions.add(transaction);
    }

    public void addOutgoingTransaction(Transaction transaction) {
        incomingTransactions(transaction);
        outTransactions.add(transaction);
    }

    public long getBalanceBeforeDate(Date date) {
        long sum = balance;
        sum = inTransactions.stream()
                .filter(transaction -> transaction.getDate().before(date))
                .map(Transaction::getSum)
                .reduce(sum, Long::sum);
        return outTransactions.stream()
                .filter(transaction -> transaction.getDate().before(date))
                .map(Transaction::getOutSum)
                .reduce(sum, Long::sum);
    }

}
