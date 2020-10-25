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
    private static int idSeq;
    private int id;
    private List<Transaction> transactions;
    private long balance;

    public Account() {
        id = idNextValue();
        transactions = new ArrayList<>();
        balance = 0;
    }

    public int getId() {
        return id;
    }

    public long getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        if (transaction.getSenderId() == this.id) {
            transactions.add(transaction);
            balance -= transaction.getSum();
        }
        if (transaction.getRecipientId() == this.id) {
            transactions.add(transaction);
            balance += transaction.getSum();
        }
    }

    public long getBalanceByDate(long date) {
        long tmpBalance = 0;
        tmpBalance += transactions.stream().filter(transaction -> transaction.getDate().getTime() < date).filter(transaction -> transaction.getRecipientId() == this.id)
        .mapToLong(Transaction::getSum).sum();
        tmpBalance -= transactions.stream().filter(transaction -> transaction.getDate().getTime() < date).filter(transaction -> transaction.getSenderId() == this.id)
        .mapToLong(Transaction::getSum).sum();
        return tmpBalance;
    }

    private static int idNextValue() {
        idSeq++;
        return idSeq;
    }
}
