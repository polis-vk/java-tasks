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

    private static long currentId = 1;

    private final long id;
    private final Date date;
    private final Account outgoingAccount;
    private final Account incomingAccount;
    private final long sum;

    public Transaction(Date date, Account outgoingAccount,
                       Account incomingAccount, long sum) {

        this.id = currentId++;
        this.date = date;
        this.outgoingAccount = outgoingAccount;
        this.incomingAccount = incomingAccount;
        this.sum = sum;
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Account getOutgoingAccount() {
        return outgoingAccount;
    }

    public Account getIncomingAccount() {
        return incomingAccount;
    }

    public long getSum() {
        return sum;
    }

    public String getOutgoingAccountIdAsStr() {
        return outgoingAccount.getId().toString();
    }
}
