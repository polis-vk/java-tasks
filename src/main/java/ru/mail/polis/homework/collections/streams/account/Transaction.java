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
    private final Date data;
    private final String incomingId;
    private final String outgoingId;
    private final Long sum;

    public Transaction(String id, Date data, String incomingAcc, String outgoingAcc, Long sum) {
        this.id = id;
        this.data = data;
        this.incomingId = incomingAcc;
        this.outgoingId = outgoingAcc;
        this.sum = sum;
    }

    public String getId() {
        return id;
    }

    public Date getData() {
        return data;
    }

    public Long getSum() {
        return sum;
    }

    public String getIncomingId() {
        return incomingId;
    }

    public String getOutgoingId() {
        return outgoingId;
    }
}
