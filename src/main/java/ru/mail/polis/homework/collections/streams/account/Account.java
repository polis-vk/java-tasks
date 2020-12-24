package ru.mail.polis.homework.collections.streams.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.*;

/**
 * Реализуйте класс Account с полями:
 * id
 * список всех транзакций с аккаунта (входящие и исходящие)
 * баланс
 * 1 балл
 */

public class Account {
    private static long idCounter = 0;
    private static boolean idCounterIsSet = false;
    private final long id;
    private final List<Transaction> transactions = new ArrayList<>();
    private long balance;

    public Account() {
        id = ++idCounter;
        idCounterIsSet = true;
    }

    public Account(long firstId) {
        if (idCounterIsSet) {
            throw new IllegalArgumentException();
        }
        idCounter = firstId;
        idCounterIsSet = true;
        id = firstId;
    }

    public long getId() {
        return id;
    }

    public long getBalance() {
        return balance;
    }

    public Optional<Transaction> getTransactionById(long searchId) {
        return transactions.stream()
                .filter(transaction -> transaction.getId() == searchId)
                .findFirst();

    }

    public List<Transaction> getTransactionByDate(Date searchDate) {
        return transactions.stream()
                .filter(transaction -> transaction.getDate().before(searchDate))
                .collect(Collectors.toList());
    }

    public String idToString() {
        return String.valueOf(id);
    }

    public void addTransaction(Transaction newItem, boolean isOut) {
        if (newItem == null) {
            throw new NullPointerException();
        }
        if (newItem.ADDED_TO_ACCOUNT) {
            throw new IllegalArgumentException();
        }
        transactions.add(newItem);
        balance += isOut ? -1*newItem.getSum() : newItem.getSum();
    }
}
