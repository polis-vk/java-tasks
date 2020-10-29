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
    private final String senderId;
    private final String receiverId;
    private final long sum;

    public Transaction(String id, Date date, String senderId, String recieverId, long sum) {
        this.id = id;
        this.date = date;
        this.senderId = senderId;
        this.receiverId = recieverId;
        this.sum = sum;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public long getSum() {
        return sum;
    }
}
