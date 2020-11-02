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
    private int id;
    private Date date;
    private Account from;
    private Account to;
    private long sum;

    public Account getSender() {
        return from;
    }
    public Long getSum() {
        return sum;
    }
}
