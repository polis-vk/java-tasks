package ru.mail.polis.homework.collections.streams.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

/**
 * Реализуйте класс Account с полями:
 * id
 * список всех транзакций с аккаунта (входящие и исходящие)
 * баланс
 * 1 балл
 */
public class Account {
    private final long id;
    private final List<Transaction> transactionList = new ArrayList<>();
    private final TreeMap<Date, Long> balanceOnDate = new TreeMap<>();
    private long curBalance = 0L;
    private static long idCounter = 1;
    
    public Account() {
        this.id = idCounter++;
        balanceOnDate.put(new Date(0L), 0L);
    }
    
    public void addTransaction(Transaction transaction) {
        if (transactionList.isEmpty()
                || transactionList.get(transactionList.size() - 1).getId() < transaction.getId()) {
            boolean isDest = transaction.getDestAcc() == this;
            boolean isSrc = transaction.getSrcAcc() == this;
            if (!isSrc && !isDest) {
                return;
            } else if (isDest && !isSrc) {
                curBalance += transaction.getSum();
            } else if (!isDest){
                curBalance -= transaction.getSum();
            }
            transactionList.add(transaction);
            balanceOnDate.put(transaction.getDate(), curBalance);
        }
    }
    
    public long getBalanceOnDate(Date date) {
        return balanceOnDate.floorEntry(date).getValue();
    }
    
    public long getCurBalance() {
        return curBalance;
    }
    
    public long getId() {
        return id;
    }
    
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                '}';
    }
}
