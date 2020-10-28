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

    private final String id;
    private final Date date;
    private final String recipientAccount;
    private final String senderAccount;
    private final Long cost;
    private long sum;

    public Transaction(String id, Date date, String recipientAccount, String senderAccount, Long cost, long sum) {
        this.id = id;
        this.date = date;
        this.recipientAccount = recipientAccount;
        this.senderAccount = senderAccount;
        this.cost = cost;
        this.sum = sum;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getRecipientAccount() {
        return recipientAccount;
    }

    public String getSenderAccount() {
        return senderAccount;
    }

    public Long getCost() {
        return cost;
    }

    public long getSum() {
        return sum;
    }
}
