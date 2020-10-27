package ru.mail.polis.homework.collections.streams.account;

import java.util.ArrayList;
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
    private static long currentID;
    private final List<Transaction> incomingTransactions = new ArrayList<>();
    private final List<Transaction> outgoingTransactions = new ArrayList<>();
    private long balance;

    public Account() {
        this.id = generateID();
    }

    public void addTransaction(Transaction transaction) {
        boolean isIn = (transaction.getIncomingAccount() == this);
        boolean isOut = (transaction.getOutgoingAccount() == this);

        if (isIn && !isOut) {
            balance += transaction.getSum();
            incomingTransactions.add(transaction);
        }
        if (!isIn && isOut) {
            balance -= transaction.getSum();
            outgoingTransactions.add(transaction);
        }
    }

    public long getId() {
        return id;
    }

    public List<Transaction> getIncomingTransactions() {
        return incomingTransactions;
    }

    public List<Transaction> getOutgoingTransactions() {
        return outgoingTransactions;
    }

    public long getBalance() {
        return balance;
    }

    public long getBalanceByDate(long date) {
        return incomingTransactions.stream()
                .filter(t -> t.getDate().getTime() < date)
                .map(Transaction::getSum)
                .reduce((long) 0, Long::sum) -
                outgoingTransactions.stream()
                        .filter(t -> t.getDate().getTime() < date)
                        .map(Transaction::getSum)
                        .reduce((long) 0, Long::sum);
    }

    private long generateID() {
        currentID++;
        return currentID;
    }
}
