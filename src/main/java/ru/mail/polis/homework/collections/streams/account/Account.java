package ru.mail.polis.homework.collections.streams.account;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализуйте класс Account с полями:
 * id
 * список всех транзакций с аккаунта (входящие и исходящие)
 * баланс
 * 1 балл
 */
public class Account {
    private Long id;
    private List<Transaction> transactions;
    private int balance;

    public Account(){

    }

    public Account(Long id){
        this.id = id;
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }

    public Account(Long id, List<Transaction> transactions, int balance){
        this.id = id;
        this.transactions = transactions;
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
