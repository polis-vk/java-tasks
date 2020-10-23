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
    private long id;
    private static long currentID;
    private Date date;
    private Account outgoingAccount;
    private Account incomingAccount;
    private long sum;

    public Transaction(Date date, Account outgoingAccount, Account incomingAccount, long sum) {
        generateID();
        this.date = date;
        this.outgoingAccount = outgoingAccount;
        this.incomingAccount = incomingAccount;
        this.sum = sum;
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

    public String getOutgoingAccountIDAndBalance() {
        return outgoingAccount.toString();
    }

    public Account getIncomingAccount() {
        return incomingAccount;
    }

    public String getIncomingAccountIDAndBalance() {
        return incomingAccount.toString();
    }

    public long getSum() {
        return sum;
    }

    private void generateID() {
        currentID++;
        this.id = currentID;
    }
}
