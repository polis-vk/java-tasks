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
    private final String fromId;
    private final String toId;
    private final long sum;

    public Transaction(String id, Date date, String from, String to, long sum) {
        this.id = id;
        this.date = date;
        this.fromId = from;
        this.toId= to;
        this.sum = sum;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getFromId() {
        return fromId;
    }

    public String getToId() {
        return toId;
    }

    public long getSum() {
        return sum;
    }

}
