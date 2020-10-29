package ru.mail.polis.homework.collections.streams.account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Реализуйте класс Account с полями:
 * id
 * список всех транзакций с аккаунта (входящие и исходящие)
 * баланс
 * 1 балл
 */
public class Account {
    private final String id;
    private List<Transaction> transactions;
    private long balance;

    public Account(String id) {
        this.id = id;
        transactions = new ArrayList<>();
    }

    public String id() {
        return id;
    }

    public Long balance() {
        return balance;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public Stream<Transaction> transactions() {
        return transactions.stream();
    }

    public void addTransactions(Transaction... newTransactions) {
        transactions.addAll(Arrays.asList(newTransactions));
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (this.getClass() != otherObject.getClass()) {
            return false;
        }

        Account other = (Account) otherObject;
        return this.id.equals(other.id);
    }
}
