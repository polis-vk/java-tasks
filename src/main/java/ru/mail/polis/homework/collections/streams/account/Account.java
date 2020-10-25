package ru.mail.polis.homework.collections.streams.account;

import java.util.LinkedList;
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
    private final List<Transaction> incomingTransactions = new LinkedList<>();
    private final List<Transaction> outgoingTransactions = new LinkedList<>();
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
        boolean isIn = (this.incomingTransactions.isEmpty());
        boolean isOut = (this.outgoingTransactions.isEmpty());

        if (isIn && !isOut) {
            return balance = -this.outgoingTransactions.stream().filter(t -> t.getDate().getTime() < date)
                    .map(Transaction::getSum)
                    .reduce((long) 0, Long::sum);
        }


        if (!isIn && isOut) {
            return balance = this.incomingTransactions.stream().filter(t -> t.getDate().getTime() < date)
                    .map(Transaction::getSum)
                    .reduce((long) 0, Long::sum);
        }

        if (!isIn) {
            return balance = this.incomingTransactions.stream()
                    .filter(t -> t.getDate().getTime() < date)
                    .map(Transaction::getSum)
                    .reduce((long) 0, Long::sum) -
                    this.outgoingTransactions.stream()
                            .filter(t -> t.getDate().getTime() < date)
                            .map(Transaction::getSum)
                            .reduce((long) 0, Long::sum);
        }
        return 0;
    }

    private long generateID() {
        currentID++;
        return currentID;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }
}
