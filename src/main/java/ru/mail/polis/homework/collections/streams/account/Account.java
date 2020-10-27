package ru.mail.polis.homework.collections.streams.account;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.math.BigInteger;
import java.util.*;
import java.util.function.BiFunction;

/**
 * Реализуйте класс Account с полями:
 * id
 * список всех транзакций с аккаунта (входящие и исходящие)
 * баланс
 * 1 балл
 */
public class Account {
    private static long lastAccount;

    private static synchronized long generateId() {
        return lastAccount++;
    }

    private final long id;
    private final List<Transaction> inTransactions;
    private final List<Transaction> outTransactions;
    private final BigInteger startBalance;
    private BigInteger balance;

    public Account(BigInteger startBalance) {
        id = generateId();
        inTransactions = new ArrayList<>();
        outTransactions = new ArrayList<>();
        this.startBalance = startBalance;
        balance = startBalance;
    }

    public Account() {
        this(new BigInteger("0"));
    }

    public long getId() {
        return id;
    }

    public List<Transaction> getInTransactions() {
        return Collections.unmodifiableList(inTransactions);
    }

    public List<Transaction> getOutTransactions() {
        return Collections.unmodifiableList(outTransactions);
    }

    public synchronized boolean addTransaction(Transaction transaction) {
        if (transaction.getReceiver() == this) {
            inTransactions.add(transaction);
            balance = balance.add(transaction.getSum());
            return true;
        } else if (transaction.getSender() == this) {
            outTransactions.add(transaction);
            balance = balance.subtract(transaction.getSum());
            return true;
        }
        return false;
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> buffer = new ArrayList<>(inTransactions);
        buffer.addAll(outTransactions);
        return buffer;
    }

    public BigInteger getBalanceToDate(Date date) {
        BigInteger sum = startBalance;
        sum = inTransactions.stream()
                .filter(transaction -> transaction.getDate().before(date))
                .map(Transaction::getSum)
                .reduce(sum, BigInteger::add);
        return outTransactions.stream()
                .filter(transaction -> transaction.getDate().before(date))
                .map(Transaction::getSum)
                .reduce(sum, BigInteger::subtract);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
