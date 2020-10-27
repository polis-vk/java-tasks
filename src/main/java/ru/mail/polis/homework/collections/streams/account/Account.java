package ru.mail.polis.homework.collections.streams.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Реализуйте класс Account с полями:
 * id
 * список всех транзакций с аккаунта (входящие и исходящие)
 * баланс
 * 1 балл
 */

public class Account {

    private static long currentId = 1;

    private final long id;
    private long balance;
    private List<Transaction> transactions;

    public Account() {
        this.id = currentId++;
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }

    public Account(long balance, List<Transaction> transactions) {
        this.id = currentId++;
        this.balance = balance;
        this.transactions = transactions;
    }

    public Account(Long balance) {
        this.id = currentId++;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public void newTransaction(Date date, Account outgoing,
                               Account incoming, long sum) {

        transactions.add(new Transaction(date, outgoing, incoming, sum));
        if (outgoing.getId() == this.id) {
            this.balance -= sum;
        } else if (incoming.getId() == this.id) {
            this.balance += sum;
        }
    }

    public Long getId() {
        return id;
    }

    public long getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<Transaction> getTransactionsAfter(long time) {
        List<Transaction> transactionsBeforeCertainTime = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getDate().getTime() > time) {
                transactionsBeforeCertainTime.add(transaction);
            }
        }
        return transactionsBeforeCertainTime;
    }

    //да, наверное, можно было бы задать айдишники сразу как строки, но так неудобнооо
    public String getStrId() {
        return getId().toString();
    }
}
