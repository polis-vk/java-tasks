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
    private final Date date;
    private final Account srcAcc;
    private final Account destAcc;
    private final long sum;
    private static long idCounter = 1;
    private static Date lastTransactionDate = new Date(0L);
    
    public Transaction(Date date, Account srcAcc, Account destAcc, long sum) {
        this(idCounter++, date, srcAcc, destAcc, sum);
    }
    
    private Transaction(long id, Date date, Account srcAcc, Account destAcc, long sum) {
        if (lastTransactionDate.after(date)) {
            throw new IllegalStateException("Illegal transaction date");
        }
        lastTransactionDate = date;
        this.id = id;
        this.date = date;
        this.srcAcc = srcAcc;
        this.destAcc = destAcc;
        this.sum = sum;
        srcAcc.addTransaction(this);
        destAcc.addTransaction(this);
    }
    
    public long getId() {
        return id;
    }
    
    public Account getSrcAcc() {
        return srcAcc;
    }
    
    public Account getDestAcc() {
        return destAcc;
    }
    
    public Date getDate() {
        return date;
    }
    
    public long getSum() {
        return sum;
    }
}
