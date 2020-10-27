package ru.mail.polis.homework.collections.streams.account;

import java.util.Date;

/**
 * Реализуйте класс Transaction с полями:
 * id
 * дата
 * исходящий аккаунт
 * аккаунт получателя
 * сумма
 * 1 балл
 */
public class Transaction {
    private final long id;
    private static long currentID;
    private final Date date;
    private final Account outgoingAccount;
    private final Account incomingAccount;
    private final long sum;

    public Transaction(Date date, Account outgoingAccount, Account incomingAccount, long sum) {
        this.id = generateID();
        this.date = date;
        this.outgoingAccount = outgoingAccount;
        this.incomingAccount = incomingAccount;
        this.sum = sum;
        this.outgoingAccount.addTransaction(this);
        this.incomingAccount.addTransaction(this);
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Account getOutgoingAccount() {
        return outgoingAccount;
    }

    public Account getIncomingAccount() {
        return incomingAccount;
    }

    public long getSum() {
        return sum;
    }

    private long generateID() {
        currentID++;
        return currentID;
    }
}
