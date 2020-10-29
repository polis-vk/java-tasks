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
    private final String id;
    private final long date;
    private final Account sender, receiver;
    private final long sum;

    public Transaction(String id, long date, Account sender, Account receiver, long sum) {
        this.id = id;
        this.date = date;
        this.sender = sender;
        this.receiver = receiver;
        this.sum = sum;
    }

    public String id() {
        return id;
    }

    public Account receiver() {
        return receiver;
    }

    public Account sender() {
        return sender;
    }

    public Long sum() {
        return sum;
    }

    public Long date() {
        return date;
    }
}
