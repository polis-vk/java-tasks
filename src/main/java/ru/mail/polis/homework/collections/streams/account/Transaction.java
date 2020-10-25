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
    private Date date;
    private Account inAccount;
    private Account outAccount;
    private long sum;

    public Transaction(Account inAccount, Account outAccount, Date date, long sum) {
        this.inAccount = inAccount;
        this.outAccount = outAccount;
        this.date = date;
        this.sum = sum;
        id = inAccount.getLastTransactionId() + 1;
        inAccount.addTransaction(this);
        outAccount.addTransaction(this);
    }

    public long getId() {
        return id;
    }
    
    public Date getDate() {
        return date;
    }
    
    public Account getInAccount() {
        return inAccount;
    }
    
    public Account getOutAccount() {
        return outAccount;
    }

    public long getSum() {
        return sum;
    }
}
