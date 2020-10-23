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
    
    public Transaction(Date date, Account srcAcc, Account destAcc, long sum) {
        this(idCounter++, date, srcAcc, destAcc, sum);
    }
    
    private Transaction(long id, Date date, Account srcAcc, Account destAcc, long sum) {
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
    
    public Long getSum() {
        return sum;
    }
}
