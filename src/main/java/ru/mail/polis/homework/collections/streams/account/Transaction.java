package ru.mail.polis.homework.collections.streams.account;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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
@EqualsAndHashCode()
public class Transaction {

            private static  int     count = 0; // to create id
    @Getter private final   String  id;
    @Getter private final   Date    date;
    @Getter private final   Account outAccount;
    @Getter private final   Account inAccount;
    @Getter private final   long    sum;

    public Transaction(Account outAccount, Account inAccount, long sum) {
        if (outAccount.equals(inAccount))
            throw new IllegalArgumentException();
        this.id = String.valueOf(count++);
        this.date = new Date();
        this.outAccount = outAccount;
        this.inAccount = inAccount;
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", outAccount=" + outAccount.getId() +
                ", inAccount=" + inAccount.getId() +
                ", sum=" + sum +
                '}';
    }
}
