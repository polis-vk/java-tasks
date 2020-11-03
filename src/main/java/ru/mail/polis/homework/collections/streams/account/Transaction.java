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
    private static int idSeq;
    private final long id;
    private final Date date;
    private final long senderId;
    private final long recipientId;
    private final long sum;

    public Transaction(Date date, long senderId, long recipientId, long sum) {
        id = idNextValue();
        this.date = date;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.sum = sum;
    }

    public long getId() {
        return id;
    }

    public long getSenderId() {
        return senderId;
    }

    public long getRecipientId() {
        return recipientId;
    }

    public Long getSum() {
        return sum;
    }

    public Date getDate() {
        return date;
    }

    private static int idNextValue() {
        idSeq++;
        return idSeq;
    }
}
