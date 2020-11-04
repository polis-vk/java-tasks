package ru.mail.polis.homework.collections.streams.account;

import java.util.Calendar;
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
    private int id;
    private long timestamp;
    private Account from;
    private Account to;
    private long sum;

    private static int nextID = 0;
    public static int getNextID() { return nextID++;}

    public Transaction(int id, long timestamp, Account from, Account to, long sum) {
        this.id = id;
        this.timestamp = timestamp;
        this.from = from;
        this.to = to;
        this.sum = sum;
    }

    public Account getSender() {
        return from;
    }
    public Long getSum() {
        return sum;
    }
    public long getTimestamp() { return timestamp; }
}
