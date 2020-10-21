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

// 2 варианта, для ДТО я не считаю зазорным сделать все поля публичными

public class Account {
    private Long id;
    // Выделял бы отдельный класс - баланс, в котором представлял бы интами миллионы, тысячи, сотни и т.д.
    // Дробные числа использовать для баланса не безопасно
    private int balance;
    private List<Transaction> transactions;

    public Account() {

    }

    public Account(Long id) {
        this.id = id;
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }

    public Account(Long id, int balance, List<Transaction> transactions) {
        this.id = id;
        this.balance = balance;
        this.transactions = transactions;
    }

    public Long getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}


//public class Account {
//    public Long id;
//    public int balance;
//    public List<Transaction> transactions;
//
//    public Account() {
//    }
//
//    public Account(Long id) {
//        this.id = id;
//        this.balance = 0;
//        this.transactions = new ArrayList<>();
//    }
//
//    public Account(Long id, int balance, List<Transaction> transactions) {
//        this.id = id;
//        this.balance = balance;
//        this.transactions = transactions;
//    }
//}
