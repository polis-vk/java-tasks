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
    private String id;
    private Long date;
    private Account sender;
    private Account reciever;
    private Long sum;

    public String getId() { return id; }
    public Long getDate() { return date; }
    public Account getReciever() {
        return sender;
    }
    public Account getSender() { return sender; }
    public Long getSum() {
        return sum;
    }
}
