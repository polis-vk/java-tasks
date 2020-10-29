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
    private final int id;
    private final Date date;
    private final Account sender;
    private final Account recipient;
    private long sum;

    private static int idCount;

    public Transaction(int id, Date date, Account sender, Account recipient, long sum) {
        this.id = id;
        this.date = date;
        this.sender = sender;
        this.recipient = recipient;
        this.sum = sum;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public int idCountNext() {
        idCount++;
        return idCount;
    }

    public Account getSenderAccount() {
        return sender;
    }
    public Account getRecipientAccount() {
        return recipient;
    }

    public long getSum() {
        return sum;
    }
}
