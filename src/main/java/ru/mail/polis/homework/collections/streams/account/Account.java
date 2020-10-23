package ru.mail.polis.homework.collections.streams.account;

import java.util.List;

/**
 * Реализуйте класс Account с полями:
 * id
 * список всех транзакций с аккаунта (входящие и исходящие)
 * баланс
 * 1 балл
 */
public class Account {
    private long id;
    private static long currentID;
    private List<Transaction> incomingTransactions;
    private List<Transaction> outgoingTransactions;
    private long balance;

    public Account() {
        generateID();
    }

    public Account(List<Transaction> incomingTransactions, List<Transaction> outgoingTransactions, long balance) {
        generateID();
        this.incomingTransactions = incomingTransactions;
        this.outgoingTransactions = outgoingTransactions;
        this.balance = balance;
        updateBalance();
    }

    public void setIncomingTransactions(List<Transaction> incomingTransactions) {
        this.incomingTransactions = incomingTransactions;
        updateBalance();
    }

    public void setOutgoingTransactions(List<Transaction> outgoingTransactions) {
        this.outgoingTransactions = outgoingTransactions;
        updateBalance();
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
        boolean isIn = (this.incomingTransactions == null);
        boolean isOut = (this.outgoingTransactions == null);

        if (isIn && !isOut)
            return balance = -this.outgoingTransactions.stream().filter(t -> t.getDate().getTime() < date)
                    .map(Transaction::getSum).reduce((long) 0, Long::sum);


        if (!isIn && isOut)
            return balance = this.incomingTransactions.stream().filter(t -> t.getDate().getTime() < date)
                    .map(Transaction::getSum).reduce((long) 0, Long::sum);

        if (!isIn && !isOut)
            return balance = this.incomingTransactions.stream().filter(t -> t.getDate().getTime() < date)
                    .map(Transaction::getSum).reduce((long) 0, Long::sum) -
                    this.outgoingTransactions.stream().filter(t -> t.getDate().getTime() < date)
                            .map(Transaction::getSum).reduce((long) 0, Long::sum);

        return 0;
    }

    private void generateID() {
        currentID++;
        this.id = currentID;
    }

    private void updateBalance() {
        boolean isIn = (this.incomingTransactions == null);
        boolean isOut = (this.outgoingTransactions == null);

        if (isIn && !isOut)
            balance = -this.outgoingTransactions.stream().map(Transaction::getSum).reduce((long) 0, Long::sum);
        if (!isIn && isOut)
            balance = this.incomingTransactions.stream().map(Transaction::getSum).reduce((long) 0, Long::sum);
        if (!isIn && !isOut)
            balance = this.incomingTransactions.stream().map(Transaction::getSum).reduce((long) 0, Long::sum) -
                    this.outgoingTransactions.stream().map(Transaction::getSum).reduce((long) 0, Long::sum);

    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }
}
