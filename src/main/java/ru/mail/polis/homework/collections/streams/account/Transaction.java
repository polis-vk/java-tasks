package ru.mail.polis.homework.collections.streams.account;

import java.util.*;
import java.util.stream.Stream;

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
    public List<Account> account_set = new LinkedList<>();
    private String id;
    private long data;
    private String sender_id;
    private String recipient_id;
    private long sum;
    Transaction(String account_id, long data, String sender_id, String recipient_id, long sum)
    {
        this.id = account_id;
        this.data = data;
        this.sender_id = sender_id;
        this.recipient_id = recipient_id;
        this.sum = sum;
    }


    public Account getAccount() {
        return account_set
                .stream()
                .filter(acc -> acc.getId().equals(sender_id)).findFirst().orElse(null);
    }
    public Long getSum() {
        return sum;
    }
}
