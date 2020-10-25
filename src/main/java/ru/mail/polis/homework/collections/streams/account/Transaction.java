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
    private Long id;
    private Date date;
    private Account sourceAccount;
    private Account recipientAccount;
    private Long sum;

    public Transaction(Long id, Date date, Account sourceAccount, Account recipientAccount){
        this.id = id;
        this.date = date;
        this.recipientAccount = recipientAccount;
        this.sourceAccount = sourceAccount;
    }

    public Long getSum(){
        return sum;
    }

    public Date getDate() {
        return date;
    }

    public String getSourceAccountId(){
        return sourceAccount.getId();
    }

    public Long getId() {
        return id;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public Account getRecipientAccount() {
        return recipientAccount;
    }
}
