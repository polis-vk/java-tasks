package ru.mail.polis.homework.collections.streams.account;

import javax.xml.crypto.Data;
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
    private final Account outgoing;
    private final Account incoming;
    private final long sum;

    public Long getSum() {
        return sum;
    }

    public Long getOutSum() {
        return -sum;
    }

    public String getOutgoingId() {
        return outgoing.getId();
    }

    Transaction(String id, Date date, Account incoming, Account outgoing, long sum) {
        this.id = id;
        this.date = date;
        this.incoming = incoming;
        this.outgoing = outgoing;
        this.sum = sum;

        incoming.addIncomingTransaction(this);
        outgoing.addOutgoingTransaction(this);
    }

    public Date getDate() {
        return date;
    }

    public Account getOutgoingAccount() {
        return outgoing;
    }
}
