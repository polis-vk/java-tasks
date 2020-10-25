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
    private List<Transaction> inTransactions;
    private List<Transaction> outTransactions;
    private Long balance;

    public Account(){

    }

    public Account(Long id){
        this.id = id;
        this.balance = 0L;
        this.inTransactions = new ArrayList<>();
        this.outTransactions = new ArrayList<>();
    }

    public Account(Long id, List<Transaction> inTransactions, List<Transaction> outTransactions, Long balance){
        this.id = id;
        this.inTransactions = inTransactions;
        this.outTransactions = outTransactions;
        this.balance = balance;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getId() {
        return id.toString();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Transaction> getInTransactions() {
        return inTransactions;
    }

    public List<Transaction> getOutTransactions() {
        return outTransactions;
    }

    public void setInTransactions(List<Transaction> inTransactions) {
        this.inTransactions = inTransactions;
    }

    public void setOutTransactions(List<Transaction> outTransactions) {
        this.outTransactions = outTransactions;
    }
}
