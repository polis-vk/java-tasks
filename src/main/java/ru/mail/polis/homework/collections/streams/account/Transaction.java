package ru.mail.polis.homework.collections.streams.account;

import java.time.LocalDate;

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
    private final LocalDate date;
    private final Account outgoingAcc;
    private final Account receiverAcc;
    private final Long sum;

    public Transaction(int id, LocalDate date, Account outgoingAcc, Account receiverAcc, Long sum) {
        this.id = id;
        this.date = date;
        this.outgoingAcc = outgoingAcc;
        this.receiverAcc = receiverAcc;
        this.sum = sum;
    }

    public int getId() {
        return id;
    }

    public Account getAccount() {
        return outgoingAcc;
    }
    public Long getSum() {
        return sum;
    }
}
