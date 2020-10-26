package ru.mail.polis.homework.collections.streams.account;

import lombok.Getter;

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
public class Transaction {

    private static         int     count = 0; // to create id
    private final  @Getter String  id;
    private final  @Getter Date    date;
    private final  @Getter Account outAccount;
    private final  @Getter Account inAccount;
    private final  @Getter long    sum;

    public Transaction(Account outAccount, Account inAccount, long sum) {
        if (outAccount.equals(inAccount)) throw new IllegalArgumentException();
        this.id = String.valueOf(count++);
        this.date = new Date();
        this.outAccount = outAccount;
        this.inAccount = inAccount;
        this.sum = sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return sum == that.sum &&
                Objects.equals(id, that.id) &&
                Objects.equals(date, that.date) &&
                Objects.equals(outAccount, that.outAccount) &&
                Objects.equals(inAccount, that.inAccount);
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
