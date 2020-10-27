package ru.mail.polis.homework.collections.streams.account;

import java.util.HashMap;
import java.util.Map;

/**
 * Реализуйте класс Account с полями:
 * id
 * список всех транзакций с аккаунта (входящие и исходящие)
 * баланс
 * 1 балл
 */
public class Account {
    private final String id;
    private Long balance;
    private Map<Transaction, Direction> transactions = new HashMap<>();

    private enum Direction {
        IN,
        OUT
    }

    public Account(String id) {
        this.id = id;
        balance = 0L;
    }

    public Account(String id, Long balance) {
        this.id = id;
        this.balance = balance;

    }

    public String getId() {
        return id;
    }

    public Long getBalance() {
        return balance;
    }

    public Long getBalance(long time) {
        return getBalance() -
                transactions.entrySet().stream()
                        .filter(transaction -> transaction.getValue() == Direction.IN)
                        .filter(transaction -> transaction.getKey().getDate().getTime() > time)
                        .map(transaction -> transaction.getKey().getSum())
                        .reduce(0L, Long::sum) +
                transactions.entrySet().stream()
                        .filter(transaction -> transaction.getValue() == Direction.OUT)
                        .filter(transaction -> transaction.getKey().getDate().getTime() > time)
                        .map(transaction -> transaction.getKey().getSum())
                        .reduce(0L, Long::sum);
    }

    public void addTransaction(Transaction transaction) throws IllegalArgumentException {
        if (transaction.getSender().getId().equals(id)) {
            transactions.put(transaction, Direction.OUT);
            balance -= transaction.getSum();
        } else if (transaction.getReceiver().getId().equals(id)) {
            transactions.put(transaction, Direction.IN);
            balance += transaction.getSum();
        } else {
            throw new IllegalArgumentException("The transaction with id: " + transaction.getId() + " is not connected with current account");
        }
    }
}
