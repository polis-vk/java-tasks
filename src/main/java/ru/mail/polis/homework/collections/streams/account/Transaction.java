package ru.mail.polis.homework.collections.streams.account;

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
    private long curId = 0;
    private long id;
    private long date;
    private Account sender;
    private Account recipient;
    private long sum;

    public Transaction() { }

    public Transaction(long date, Account sender, Account recipient, long sum) {
        this.id = curId++;
        this.date = date;
        this.sender = sender;
        this.recipient = recipient;
        this.sum = sum;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public void setRecipient(Account recipient) {
        this.recipient = recipient;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    public long getId() {
        return id;
    }

    public long getDate() {
        return date;
    }

    public Account getSender() {
        return sender;
    }

    public Account getRecipient() {
        return recipient;
    }

    public Account getAccount() {
        return recipient;
    }
    public long getSum() {
        return sum;
    }
}
