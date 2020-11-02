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
    private final String id;
    private static long lastId;
    private final List<Transaction> inTransactions = new ArrayList<>();
    private final List<Transaction> outTransactions = new ArrayList<>();
    private long balance;

    private String createId() {
        lastId++;
        return String.valueOf(lastId);
    }

    public Account(){
        this.id = createId();
    }

    public String getId() {
        return id;
    }

    public long getBalance() {
        return balance;
    }

    public List<Transaction> getOutTransactions() {
        return outTransactions;
    }

    public List<Transaction> getInTransactions() {
        return inTransactions;
    }

    public void addTransaction(Transaction transaction){
        boolean in = transaction.getRecipientAccount().equals(this);
        boolean out = transaction.getSourceAccount().equals(this);


        if (out && !in){ // проверка, что транзакция списания
            balance -= transaction.getSum();
            outTransactions.add(transaction);
        } else if (!out && in) { // проверка, что транзакция пополнения
            balance += transaction.getSum();
            inTransactions.add(transaction);
        }
    }

    long getBalanceByDate(long date){
        long balanceIn =  inTransactions.stream()
                .filter(t -> t.getDate().getTime() < date)
                .map(Transaction::getSum)
                .reduce((long) 0, Long::sum);
        long balanceOut = outTransactions.stream()
                        .filter(t -> t.getDate().getTime() < date)
                        .map(Transaction::getSum)
                        .reduce((long) 0, Long::sum);
        return balanceIn - balanceOut;
    }
}
