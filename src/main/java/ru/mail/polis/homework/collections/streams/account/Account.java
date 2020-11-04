package ru.mail.polis.homework.collections.streams.account;

import java.util.Calendar;
import java.util.List;

/**
 * Реализуйте класс Account с полями:
 * id
 * список всех транзакций с аккаунта (входящие и исходящие)
 * баланс
 * 1 балл
 */
public class Account {
    private String id;

    public long getBalance() {
        return balance;
    }

    private Long balance;
    private List<Transaction> transactionsList;

    public Account(String id, Long balance, List<Transaction> transactionsList) {
        this.id = id;
        this.balance = balance;
        this.transactionsList = transactionsList;
    }

    public void addTransaction(Transaction tr){
        transactionsList.add(tr);
    }
    public void makeTransaction(Account to, long amount){
        Transaction tr = new Transaction(Transaction.getNextID(), Calendar.getInstance().getTimeInMillis(), this, to, amount);
        this.balance -= amount;
        to.balance += amount;
        this.addTransaction(tr);
        to.addTransaction(tr);
    }

    public String getId() {
        return id;
    }

    public List<Transaction> getTransactionsList(){
        return transactionsList;
    }
}
