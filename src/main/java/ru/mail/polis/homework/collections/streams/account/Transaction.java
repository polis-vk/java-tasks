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
    private static long lastId;
    private final Date date;
    private final Account sourceAccount;
    private final Account recipientAccount;
    private final Long sum;

    private long createId() {
        lastId++;
        return lastId;
    }

    public Transaction(Date date, Account sourceAccount, Account recipientAccount, long sum){
        this.id = createId();
        this.date = date;
        this.recipientAccount = recipientAccount;
        this.sourceAccount = sourceAccount;
        this.sum = sum;
        this.sourceAccount.addTransaction(this);
        this.recipientAccount.addTransaction(this);
    }

    public Long getSum(){
        return sum;
    }

    public Date getDate() {
        return date;
    }

    public Long getId() {
        return id;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public Account getRecipientAccount() {
        return recipientAccount;
    }
}
