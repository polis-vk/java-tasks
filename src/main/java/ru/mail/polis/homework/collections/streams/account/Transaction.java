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
    private String idSender;
    private String idRecipient;
    private long sum;

    public Transaction() {
    }

    public Transaction(long id, long date, String sender, String recipient, long sum) {
        this.id = id;
        this.date = date;
        this.idSender = sender;
        this.idRecipient = recipient;
        this.sum = sum;
    }

    public long getDate() {
        return date;
    }

    public String getIdSender() {
        return idSender;
    }

    public String getIdRecipient() {
        return idRecipient;
    }


    public long getSum() {
        return sum;
    }
}
