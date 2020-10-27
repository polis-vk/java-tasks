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
    private final Account sender;
    private final Account receiver;
    private final Long sum;

    public Transaction(String id, Date date, Account sender, Account receiver, Long sum) {
        this.id = id;
        this.date = date;
        this.sender = sender;
        this.receiver = receiver;
        this.sum = sum;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Account getSender() {
        return sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    public Long getSum() {
        return sum;
    }
}
