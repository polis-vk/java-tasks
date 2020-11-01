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
    private final long date;
    private final Account sender;
    private final Account reciever;
    private final long sum;

    public Transaction(String id, long date, Account sender, Account reciever, long sum) {
        this.id = id;
        this.date = date;
        this.sender = sender;
        this.reciever = reciever;
        this.sum = sum;
    }

    public String getId() {
        return id;
    }

    public long getDate() {
        return date;
    }

    public Account getReciever() {
        return reciever;
    }

    public Account getSender() {
        return sender;
    }

    public long getSum() {
        return sum;
    }
}
