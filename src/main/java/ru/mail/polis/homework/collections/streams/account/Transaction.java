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
    private final Date date;
    private final Account recipientAccount;
    private final Account senderAccount;
    private final Long cost;

    public Transaction(String id, Date date, Account recipientAccount, Account senderAccount, Long cost) {
        this.id = id;
        this.date = date;
        this.recipientAccount = recipientAccount;
        this.senderAccount = senderAccount;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Account getRecipientAccount() {
        return recipientAccount;
    }

    public Account getSenderAccount() {
        return senderAccount;
    }

    public Long getCost() {
        return cost;
    }
}
