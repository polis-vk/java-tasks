package ru.mail.polis.homework.collections.streams.account;

import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;

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
    private static BigInteger lastTransaction = new BigInteger("0");
    private static BigInteger increment = new BigInteger("1");
    private static synchronized BigInteger generateId() {
        lastTransaction = lastTransaction.add(increment);
        return lastTransaction;
    }

    private BigInteger id;
    private final Date date;
    private final Account sender;
    private final Account receiver;
    private final BigInteger sum;

    public Transaction(Date date, Account sender, Account receiver, BigInteger sum) {
        this.id = generateId();
        this.date = date;
        this.sender = sender;
        this.receiver = receiver;
        this.sum = sum;
        sender.addTransaction(this);
        receiver.addTransaction(this);
    }

    public BigInteger getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Account getReceiver() {
        return receiver;
    }

    public Account getSender() {
        return sender;
    }

    public BigInteger getSum() {
        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(date, that.date) &&
                Objects.equals(sender, that.sender) &&
                Objects.equals(receiver, that.receiver) &&
                Objects.equals(sum, that.sum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, sender, receiver, sum);
    }
}
