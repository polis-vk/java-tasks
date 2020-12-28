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
    private long id;
    private long date;
    private long idSender;
    private long idRecipient;
    private long sum;

    public Transaction() { }

    public Transaction(long id, long date, long sender, long recipient, long sum) {
        this.id = id;
        this.date = date;
        this.idSender = sender;
        this.idRecipient = recipient;
        this.sum = sum;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setIdSender(long idSender) {
        this.idSender = idSender;
    }

    public void setIdRecipient(long idRecipient) {
        this.idRecipient = idRecipient;
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

    public long getIdSender() {
        return idSender;
    }

    public long getIdRecipient() {
        return idRecipient;
    }

    public long getAccount() {
        return idRecipient;
    }
    public long getSum() {
        return sum;
    }
}
