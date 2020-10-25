package ru.mail.polis.homework.collections.streams.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.*;

/**
 * Реализуйте класс Account с полями:
 * id
 * список всех транзакций с аккаунта (входящие и исходящие)
 * баланс
 * 1 балл
 */

public class Account {
    final private long id;
    private List<Transaction> transactions;
    private long balance;
    private AccountList accounts;

    public Account(AccountList accounts) throws NullPointerException {
        if (accounts != null) {
            this.accounts = accounts;
            id = accounts.getIdCounter() + 1;
            transactions = new ArrayList<>();
            balance = 0;
            accounts.add(this);
        } else {
            throw new NullPointerException();
        }
    }

    public long getId() {
        return id;
    }

    public long getBalance() {
        return balance;
    }

    public Transaction getTransactionByIndex(int index) {
        return transactions.get(index);
    }

    public List<Transaction> getTransactionByDate(Date date) {
        return transactions.stream()
                .filter(transaction -> transaction.getDate().before(date))
                .collect(Collectors.toList());
    }

    public String idToString() {
        return String.valueOf(id);
    }

    public long getLastTransactionId() {
        int size = transactions.size();
        if (size > 0) {
            return transactions.get(transactions.size() - 1).getId();
        } else {
            return 0;
        }
    }

    public int getTransactionsSize() {
        return transactions.size();
    }

    public Account getAccountFromListByIndex(int index) {
        return accounts.getByIndex(index);
    }

    public void addTransaction(Transaction newItem) throws NullPointerException, IllegalArgumentException {
        if (newItem != null) {
            if (newItem.getId() > getLastTransactionId()) {
                transactions.add(newItem);
                addToBalance(newItem);
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            throw new NullPointerException();
        }
    }

    private void addToBalance(Transaction transaction) {
        balance += transaction.getSum();
    }
}
