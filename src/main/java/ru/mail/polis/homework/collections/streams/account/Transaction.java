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
    public final boolean ADDED_TO_ACCOUNT;
    private static long idCounter = 0;
    private static boolean idCounterIsSet = false;
    private final long id;
    private final Date date;
    private final Account inAccount;
    private final Account outAccount;
    private final long sum;

    public Transaction(Account inAccount, Account outAccount, Date date, long sum) {
        id = ++idCounter;
        idCounterIsSet = true;
        this.inAccount = inAccount;
        this.outAccount = outAccount;
        this.date = date;
        this.sum = sum;
        outAccount.addTransaction(this, true);
        inAccount.addTransaction(this, false);
        ADDED_TO_ACCOUNT = true;
    }

    public Transaction(long firstId, Account inAccount, Account outAccount, Date date, long sum) {
        if (idCounterIsSet) {
            throw new IllegalArgumentException();
        }
        idCounter = firstId;
        idCounterIsSet = true;
        id = firstId;
        this.inAccount = inAccount;
        this.outAccount = outAccount;
        this.date = date;
        this.sum = sum;
        outAccount.addTransaction(this, true);
        inAccount.addTransaction(this, false);
        ADDED_TO_ACCOUNT = true;
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
