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

    private String id;
    private Date date;
    private Account outgoing;
    private Account incoming;
    private long sum;

    public Account getAccount() {
        return null;
    }

    public Long getSum() {
        return sum;
    }

    public Long getOutSum() {
        return -sum;
    }

    public String getOutgoingId() {
        return outgoing.getId();
    }

    Transaction(String id, Date date, Account incoming, Account outgoing) {
        this.id = id;
        this.date = date;
        this.incoming = incoming;
        this.outgoing = outgoing;

        incoming.addIncomingTransaction(this);
        outgoing.addOutgoingTransaction(this);
    }

    public Date getDate() {
        return date;
    }

    public Account getOutgoingAccount() {
        return outgoing;
    }

    public String getId() {
        return id;
    }
}
