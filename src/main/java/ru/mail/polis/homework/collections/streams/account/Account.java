package ru.mail.polis.homework.collections.streams.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    private final TreeMap<Date, BalanceAndOperation> balanceAndOperationOnDate = new TreeMap<>();
    private long curBalance = 0L;
    private static long idCounter = 1;
    
    private static class BalanceAndOperation {
        private long balance;
        private long operation;
        
        public BalanceAndOperation(long balance, long operation) {
            this.balance = balance;
            this.operation = operation;
        }
    }
    
    public Account() {
        this.id = idCounter++;
        balanceAndOperationOnDate.put(new Date(0L), new BalanceAndOperation(0L, 0L));
    }
    
    public void addTransaction(Transaction transaction) {
        if (transactionList.isEmpty()
                || transactionList.get(transactionList.size() - 1).getId() < transaction.getId()) {
            boolean isDest = transaction.getDestAcc() == this;
            boolean isSrc = transaction.getSrcAcc() == this;
            long balanceChange = 0;
            if (!isSrc && !isDest) {
                return;
            } else if (isDest && !isSrc) {
                balanceChange = transaction.getSum();
            } else if (!isDest) {
                balanceChange = -transaction.getSum();
            }
            curBalance += balanceChange;
            transactionList.add(transaction);
            balanceAndOperationOnDate.merge(transaction.getDate(), new BalanceAndOperation(curBalance, balanceChange),
                    (oldValue, newValue) -> new BalanceAndOperation(
                            oldValue.balance + newValue.operation, oldValue.operation + newValue.operation));
            
            if (balanceAndOperationOnDate.lastKey().compareTo(transaction.getDate()) != 0) {
                recalculateBalances(transaction);
            }
        }
    }
    
    private void recalculateBalances(Transaction transaction) {
        BalanceAndOperation prevRecord = balanceAndOperationOnDate.lowerEntry(transaction.getDate()).getValue();
        for (Map.Entry<Date, BalanceAndOperation> curBalanceAndOperationOnDate : balanceAndOperationOnDate.tailMap(transaction.getDate()).entrySet()) {
            BalanceAndOperation tempRecord = curBalanceAndOperationOnDate.getValue();
            tempRecord.balance = prevRecord.balance + tempRecord.operation;
            prevRecord = tempRecord;
        }
        curBalance = prevRecord.balance;
    }
    
    public long getBalanceOnDate(Date date) {
        return balanceAndOperationOnDate.floorEntry(date).getValue().balance;
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
