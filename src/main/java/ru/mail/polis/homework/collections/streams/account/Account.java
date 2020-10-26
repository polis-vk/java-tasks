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
    private final int id;
    private final List<Transaction> allTransactions = new ArrayList<>();
    private Long balanse;

    public Account(int id, Long balanse) {
        this.id = id;
        this.balanse = balanse;
    }

    public List<Transaction> getAllTransactions() {
        return allTransactions;
    }

    public int getId() {
        return id;
    }

    public Long getBalanse() {
        return balanse;
    }

    public void addTransaction(Transaction newTransaction){
        allTransactions.add(newTransaction);
    }

    public Transaction lastTransaction(){
        if (allTransactions.size() == 0){
            return null;
        }
        return allTransactions.get(allTransactions.size()-1);
    }

    public void minusSum(Long s){
        balanse = balanse - s;
    }

    public void addSum(Long s){
        balanse = balanse + s;
    }


}
