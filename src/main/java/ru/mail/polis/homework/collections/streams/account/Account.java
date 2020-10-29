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
    private final int id;
    private final List<Transaction> incomingTransactions_;
    private final List<Transaction> outgoingTranactions_;
    private long balance;

    private static int idCount;

    public Account() {
        this.id = idCountNext();
        incomingTransactions_ = new ArrayList<>();
        outgoingTranactions_ = new ArrayList<>();
        balance = 0;
    }

    public int getId() {
        return id;
    }

    public List<Transaction> getIncomingTransactions() {
        return incomingTransactions_;
    }

    public List<Transaction> getOutgoingTransactions() {
        return outgoingTranactions_;
    }

    public long getBalance() {
        return balance;
    }

    public int idCountNext() {
        idCount++;
        return idCount;
    }

    public void addTransaction(Transaction transaction) throws  IllegalArgumentException {
        if (transaction.getSenderAccount().getId() == this.id) {
            outgoingTranactions_.add(transaction);
            balance -= transaction.getSum();
        } else if (transaction.getRecipientAccount().getId() == this.id) {
            incomingTransactions_.add(transaction);
            balance += transaction.getSum();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public long getBalanceByDate(Date date) {
        return getIncomingTransactions().stream()
                .filter(transaction -> transaction.getDate().compareTo(date) <= 0)
                .map(Transaction::getSum)
                .reduce(0L, Long::sum)
                - getOutgoingTransactions().stream()
                .filter(transaction -> transaction.getDate().compareTo(date) <= 0)
                .map(Transaction::getSum)
                .reduce(0L, Long::sum);
    }
}
