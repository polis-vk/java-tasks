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
    final private String id;
    final private Date date;
    final private Account recipientAccount;
    final private Account senderAccount;
    final private Long cost;

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
